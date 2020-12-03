package pt.upskill.projeto2.financemanager;

import pt.upskill.projeto2.financemanager.accounts.*;
import pt.upskill.projeto2.financemanager.accounts.formats.FileAccountFormat;
import pt.upskill.projeto2.financemanager.exceptions.BadFormatException;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;
import pt.upskill.projeto2.financemanager.filters.AccountIdSelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pt.upskill.projeto2.financemanager.accounts.StatementLine.newStatementLine;
import static pt.upskill.projeto2.financemanager.gui.PersonalFinanceManagerUserInterface.SEPARATOR;


public class PersonalFinanceManager {
    private List<Account> listaContas = new ArrayList<Account>();

    public PersonalFinanceManager() {
        try {
            criarContasFicheiros();
            lerFicheirosStatements();
        } catch (Exception e) {

        }
    }

    // Creates all accounts from the .csv files in /account_info_test
    public void criarContasFicheiros() throws FileNotFoundException, BadFormatException, ParseException, UnknownAccountException {
        // Obtem lista de ficheiros de contas mas filtra antes para só carregar os .csv
        File[] listFiles = new File("account_info/").listFiles((d, name) -> name.endsWith(".csv"));
        for (File f : listFiles) {
            listaContas.add(Account.newAccount(f));
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
                        listaContas.add(account);
                    } else if (accType.equals("SavingsAccount")) {
                        account = new SavingsAccount(id, name, moeda);
                        listaContas.add(account);
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
        account.sortStatementLines();
    }


    public double saldoTotalContas() {
        double saldoTotal = 0.0;
        for (Account a : listaContas) {
            saldoTotal += a.currentBalance();
        }
        return saldoTotal;
    }

    public void posicaoGlobal(){
        System.out.println("Posicao Global:");
        System.out.println(SEPARATOR);
        System.out.println("Numero de Conta - Saldo");
        for (Account a: listaContas) {
            System.out.println(a.getId() + " - " + a.currentBalance());
        }
        System.out.println(SEPARATOR);
        System.out.println("Saldo Total: " + saldoTotalContas());
    }

    public void printAccountStatements(Account a) {
        FileAccountFormat accFormat = new FileAccountFormat();
        System.out.println(accFormat.accountHeader(a));
        a.printAllStatements();
    }
}


