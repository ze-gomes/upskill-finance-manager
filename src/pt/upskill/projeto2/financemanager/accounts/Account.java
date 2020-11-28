package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.exceptions.BadFormatException;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pt.upskill.projeto2.financemanager.accounts.StatementLine.newStatementLine;
import static pt.upskill.projeto2.financemanager.date.Date.intToMonth;

public abstract class Account {
    Currency moeda;
    long id;
    String name;
    String additionalInfo = "";
    Date startDate;
    Date endDate;
    Date accountInfo;
    ArrayList<StatementLine> statements;
    BanksConstants banksConstants;

    public Account(long id, String name) {
        this.id = id;
        this.name = name;
        this.accountInfo = new Date();
        this.statements = new ArrayList<>();
        this.banksConstants = new BanksConstants();
    }

    public Account(long id, String name, Currency moeda) {
        this.id = id;
        this.name = name;
        this.moeda = moeda;
        this.accountInfo = new Date();
        this.statements = new ArrayList<>();
        this.banksConstants = new BanksConstants();
    }

    public Account(long id, String name, Currency moeda, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.moeda = moeda;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accountInfo = new Date();
        this.statements = new ArrayList<>();
        this.banksConstants = new BanksConstants();
    }

    public static Account newAccount(File f) throws FileNotFoundException, BadFormatException, ParseException, UnknownAccountException {
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
            String[] lineFormatted = s.nextLine().replaceAll("\\s+", "").split(";");
            String identifierWord = lineFormatted[0];
            // Take first word of array as identifier word
            // Account general info
            if (identifierWord.equals("Account")) {
                id = Long.parseLong(lineFormatted[1]);
                moeda = Currency.valueOf(lineFormatted[2]);
                name = lineFormatted[3];
                accType = lineFormatted[4];
                if (accType.equals("DraftAccount"))
                    account = new DraftAccount(id, name, moeda);
                else if (accType.equals("SavingsAccount"))
                    account =  new SavingsAccount(id, name, moeda);
                // Account Dates
            } else if (identifierWord.equals("StartDate")) {
                account.startDate = new Date(format.parse(lineFormatted[1]));
            } else if (identifierWord.equals("EndDate")) {
                account.endDate = new Date(format.parse(lineFormatted[1]));
            } else if (identifierWord.equals("Date")) {
                statementLines = true;
                break;
            } else if (statementLines) {
                StatementLine statement = newStatementLine(lineFormatted);
                account.addStatementLine(statement);
            }
        }
        return account;
    }


    public String additionalInfo() {
        return additionalInfo;
    }

    public double currentBalance() {
        double balance = getStartingBalance();
        for (StatementLine statement: statements) {
            balance += statement.getDraft();
            balance += statement.getCredit();
        }
        return balance;
    }


    // Find the starting balance of an account from a list of statements
    public double getStartingBalance() {
        for (StatementLine statement: statements) {
            if (statement.getDate().equals(this.startDate)){
                return statement.getAvailableBalance();
            }
        }
        return 0.0;
    }


    public void addStatementLine(StatementLine statement) {
        //currentBalance += statement.getDraft();
        //currentBalance += statement.getCredit();
        statements.add(statement);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String other) {
        this.name = other;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public double estimatedAverageBalance() {
        return 0;
    }

    public abstract double getInterestRate();

    public abstract void setInterestRate(double interestRate);

    public int totalDraftsForCategorySince(Category category, Date date) {
        return 0;
    }

    public double totalForMonth(int month, int year) {
        double totalMonth = 0.0;
        for (StatementLine statement: statements) {
            if (statement.getDate().getYear() == year && statement.getDate().getMonth() ==  intToMonth(month)){
                totalMonth += statement.getDraft();
                totalMonth += statement.getCredit();
            }
        }
        return totalMonth;
    }

    public void autoCategorizeStatements(List<Category> categories) {
    }

    public void removeStatementLinesBefore(Date date) {
    }

}


//String description, double draft, double credit, double accountingBalance, double availableBalance, Category category
//Date ;Value Date ;Description ;Draft ;Credit ;Accounting balance ;Available balance
//31-10-2013 ;31-10-2013 ;SUMMARY ;0.0 ;200.0 ;2600.0 ;2600.0

//(new pt.upskill.projeto2.financemanager.date.Date(1, 1, 2014), new pt.upskill.projeto2.financemanager.date.Date(1, 1, 2014), "description", 0.0, 22, 1520, 1542, null));

