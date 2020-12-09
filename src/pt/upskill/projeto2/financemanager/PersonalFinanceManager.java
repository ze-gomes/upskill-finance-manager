package pt.upskill.projeto2.financemanager;

import pt.upskill.projeto2.financemanager.accounts.Currency;
import pt.upskill.projeto2.financemanager.accounts.*;
import pt.upskill.projeto2.financemanager.accounts.formats.FileAccountFormat;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.exceptions.BadFormatException;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;
import pt.upskill.projeto2.financemanager.filters.AccountIdSelector;
import pt.upskill.projeto2.financemanager.filters.NoCategorySelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

import static pt.upskill.projeto2.financemanager.accounts.StatementLine.newStatementLine;
import static pt.upskill.projeto2.financemanager.gui.PersonalFinanceManagerUserInterface.SEPARATOR;


public class PersonalFinanceManager {
    private List<Account> listaContas = new ArrayList<Account>();
    private List<Category> listCategories;


    public PersonalFinanceManager() {
        try {
            // When initialized, must immediatly read all categories first, create accounts from accounts and statements folders respectively
            listCategories = Category.readCategories(new File("account_info/categories"));
            criarContasFicheiros();
            lerFicheirosStatements();
            getEarliestStartDate();
            getLatestEndDate();
        } catch (Exception e) {

        }
    }

    // Creates all accounts from the .csv files in /account_info_test
    public void criarContasFicheiros() throws FileNotFoundException, BadFormatException, ParseException, UnknownAccountException {
        // Obtem lista de ficheiros de contas mas filtra antes para só carregar os .csv
        File[] listFiles = new File("account_info/").listFiles((d, name) -> name.endsWith(".csv"));
        for (File f : listFiles) {
            addAccount(Account.newAccount(f));
        }
    }

    // Read all statement files from /statements folder
    public void lerFicheirosStatements() throws FileNotFoundException, ParseException {
        // Obtem lista de ficheiros de contas mas filtra antes para só carregar os .csv
        File[] listFiles = new File("statements/").listFiles();
        for (File statementf : listFiles) {
            addfromStatements(statementf);
        }
    }

    // Test to create categories
//    public void createCategories(){
//        List<Category> listCategories = new ArrayList<Category>();
//        Category cat1 = new Category("HOME");
//        Category cat2 = new Category("CAR");
//        Category cat3 = new Category("FOOD");
//        Category cat4 = new Category("EXTRAS");
//        Category cat5 = savingsCategory;
//        cat1.addTag("PURCHASE");
//        cat1.addTag("TRANSF");
//        cat2.addTag("GASOLINA");
//        cat2.addTag("SUMMARY");
//        cat3.addTag("LOW VALUE");
//        listCategories.add(cat1);
//        listCategories.add(cat2);
//        listCategories.add(cat3);
//        listCategories.add(cat4);
//        listCategories.add(cat5);
//        try {
//            File f = new File("account_info/categories");
//            Category.writeCategories(f, listCategories);
//        } catch (Exception e) {
//
//        }
//
//    }


    // Add account to list but first autocategorize Statements
    // and sort statements to guaratee everything is displayed correctly
    public void addAccount(Account a) {
        a.sortStatementLines();
        a.autoCategorizeStatements(listCategories);
        listaContas.add(a);
    }

    // Check if there is account with accID already added to the list, returns that Account if true
    public Account checkIfExistingAccID(long accId) {
        AccountIdSelector idSelector = new AccountIdSelector(accId);
        for (Account a : listaContas) {
            if (idSelector.isSelected(a)) {
                return a;
            }
        }
        return null;
    }

    // Gets a array of Ids of the current accounts in String format as used by the menu
    public String[] getArrayIds() {
        String[] arrIds = new String[listaContas.size()];
        for (int i = 0; i < listaContas.size(); i++) {
            arrIds[i] = "" + listaContas.get(i).getId();
        }
        return arrIds;
    }

    // Gets a array of categories in String format as used by the menu
    public String[] getArrayCategories() {
        String[] arrCats = new String[listCategories.size()];
        for (int i = 0; i < listCategories.size(); i++) {
            arrCats[i] = "" + listCategories.get(i).getName();
        }
        return arrCats;
    }

    // Gets a  List of all Statements that have no category attributed
    public ArrayList<StatementLine> getAllNullCategoryStatements() {
        NoCategorySelector selector = new NoCategorySelector();
        ArrayList<StatementLine> allNullCatStatements = new ArrayList<>();
        for (Account a : listaContas) {
            if (a.hasUncategorizedStatement()) {
                for (StatementLine s : a.getStatements()) {
                    if (selector.isSelected(s)) {
                        allNullCatStatements.add(s);
                    }
                }
            }
        }
        return allNullCatStatements;
    }


    public List<Account> getListaContas() {
        return listaContas;
    }

    // Check statements .csv files and add new accounts if necessary, or add statements to already existing accounts
    public void addfromStatements(File f) throws FileNotFoundException, ParseException {
        // Date formatter
        boolean statementLines = false;
        // Variables to store info
        long id = 0;
        String name = "", accType = "";
        Currency moeda = null;
        Account account = null;
        Scanner s = new Scanner(f);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] lineFormatted = line.replaceAll("\\s+", "").split(";");
            String identifierWord = lineFormatted[0];
            // Take first word of array as identifier word
            // Account general info
            if (identifierWord.equals("Account")) {
                id = Long.parseLong(lineFormatted[1]);
                name = lineFormatted[3];
                accType = lineFormatted[4];
                // If account number in statement doesn't exist, create new
                account = checkIfExistingAccID(id);
                if (account == null) {
                    if (accType.equals("DraftAccount")) {
                        account = new DraftAccount(id, name, moeda);
                        addAccount(account);
                    } else if (accType.equals("SavingsAccount")) {
                        account = new SavingsAccount(id, name, moeda);
                        addAccount(account);
                    }
                }
                // Start of statements line
            } else if (identifierWord.equals("Date")) {
                statementLines = true;
            } else if (statementLines && line.charAt(0) != ' ') {
                StatementLine statement = newStatementLine(line);
                // if statement doesn't exist in account, add it, otherwise do nothing
                if (!account.checkIfStatementAlreadyExists(statement)) {
                    account.addStatementLine(statement);
                }
            }
        }
        s.close();
    }


    // Print total balance for all accounts
    public double saldoTotalContas() {
        double saldoTotal = 0.0;
        for (Account a : listaContas) {
            saldoTotal += a.currentBalance();
        }
        return saldoTotal;
    }

    // Prints global position for all accounts
    public void posicaoGlobal() {
        System.out.println("Posicao Global:");
        System.out.println(SEPARATOR);
        System.out.println("Numero de Conta - Saldo");
        for (Account a : listaContas) {
            System.out.println(a.getId() + " - " + a.currentBalance());
        }
        System.out.println(SEPARATOR);
        System.out.println("Saldo Total: " + saldoTotalContas());
    }

    public void monthlyGlobalEvolution() {
        Date dateIterator = getEarliestStartDate();
        Date latest = getLatestEndDate();
        while (dateIterator.before(latest)){
            double TotalForMonth = 0;
            for (Account a : listaContas) {
                TotalForMonth = a.totalForMonth(dateIterator.getMonth().getValue(), dateIterator.getYear());
                dateIterator = Date.firstOfNextMonth(dateIterator);
            }
            System.out.println("Total for month of " + dateIterator.getMonth().name() + " " + dateIterator.getYear() + ": " + TotalForMonth);
        }

    }

    // Prints account statements to console
    public void printAccountStatements(Account a) {
        FileAccountFormat accFormat = new FileAccountFormat();
        System.out.println(accFormat.accountHeader(a));
        System.out.println(SEPARATOR);
        a.printAllStatements();
    }

    // Prints all categories to console
    public void printAllCategories() {
        System.out.println("Lista de Todas as Categorias:");
        System.out.println(SEPARATOR);
        for (Category c : listCategories) {
            System.out.println(c.toString());
        }
    }

    // Gets category from the list by name
    public Category getCategoryByName(String name) {
        for (Category c : listCategories) {
            if (name.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }

    // Print category and its tags individually to console
    public void printCategoryTags(String name) {
        Category cat = null;
        for (Category c : listCategories) {
            if (name.equals(c.getName())) {
                cat = c;
            }
        }
        System.out.println("Category:");
        System.out.println(cat.toString());
    }

    // Get date intervals for monthly position
    public Date getEarliestStartDate() {
        ArrayList<Date> listStartDates = new ArrayList<>();
        for (Account a : listaContas) {
            listStartDates.add(a.getStartDate());
        }
        Collections.sort(listStartDates);
        return listStartDates.get(0);
    }

    public Date getLatestEndDate() {
        ArrayList<Date> listEndDates = new ArrayList<>();
        for (Account a : listaContas) {
            listEndDates.add(a.getEndDate());
        }
        Collections.sort(listEndDates);
        return listEndDates.get(listEndDates.size()-1);
    }

}


