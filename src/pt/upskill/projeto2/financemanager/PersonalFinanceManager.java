package pt.upskill.projeto2.financemanager;

import pt.upskill.projeto2.financemanager.accounts.*;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.exceptions.BadFormatException;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;
import pt.upskill.projeto2.utils.Menu;
import pt.upskill.projeto2.financemanager.gui.PersonalFinanceManagerUserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import static pt.upskill.projeto2.financemanager.accounts.StatementLine.newStatementLine;


public class PersonalFinanceManager {
    private ArrayList<Account> listaContas = new ArrayList<>();

    public void criarContasFicheiros() throws FileNotFoundException, BadFormatException, ParseException, UnknownAccountException {
        File[] listFiles = new File("src/pt/upskill/projeto2/financemanager/account_info_test/").listFiles();
        for (File f : listFiles) {
            listaContas.add(Account.newAccount(f));
        }
    }

    // Check if there is account with accID already added to the list
    public Account checkIfExistingAccID(long accId) {
        for (Account a : listaContas) {
            if (a.getId() == accId) {
                return a;
            }
        }
        return null;
    }

    public Account addfromStatements(File f) throws FileNotFoundException, BadFormatException, ParseException, UnknownAccountException {
        // Date formatter
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
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
                moeda = Currency.valueOf(lineFormatted[2]);
                name = lineFormatted[3];
                accType = lineFormatted[4];
                // If account number in statement doesn't exist, create new
                if (checkIfExistingAccID(id) == null) {
                    if (accType.equals("DraftAccount"))
                        account = new DraftAccount(id, name, moeda);
                    else if (accType.equals("SavingsAccount"))
                        account = new SavingsAccount(id, name, moeda);
                } else { // Otherwise edit existing acc
                    account = checkIfExistingAccID(id);
                }
                // Account Dates
            } else if (identifierWord.equals("StartingDate")) {
                account.setStartDate(new Date(format.parse(lineFormatted[1])));
            } else if (identifierWord.equals("EndingDate")) {
                account.setEndDate(new Date(format.parse(lineFormatted[1])));
            } else if (identifierWord.equals("Date")) {
                statementLines = true;
            } else if (statementLines) {
                StatementLine statement = newStatementLine(line);
                account.addStatementLine(statement);
            }
        }
        return account;
    }

    public void execute() {

    }


}
