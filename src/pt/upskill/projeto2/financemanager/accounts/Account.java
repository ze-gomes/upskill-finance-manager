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
    private Currency moeda;
    private long id;
    private String name;
    private String additionalInfo = "";
    private Date startDate;
    private Date endDate;
    private Date accountInfo = new Date();
    private ArrayList<StatementLine> statements = new ArrayList<StatementLine>();
    private BanksConstants banksConstants = new BanksConstants();


    public Account(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Account(long id, String name, Currency moeda) {
        this.id = id;
        this.name = name;
        this.moeda = moeda;
    }

    public Account(long id, String name, Currency moeda, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.moeda = moeda;
        this.startDate = startDate;
        this.endDate = endDate;
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
        double balance = 0.0;
        System.out.println(statements.toString());
        for (int i=0; i<statements.size(); i++){
            if (i==0) {
                balance = statements.get(i).getAvailableBalance();
                continue;
            }
            balance += statements.get(i).getDraft();
            balance += statements.get(i).getCredit();
            System.out.println("Balance at " + i + "  " + balance);
        }
        return balance;
    }


    // Find the starting balance of an account from a list of statements
    public double getStartingBalance() {
        return statements.get(0).getAvailableBalance();
    }


    public BanksConstants getBanksConstants() {
        return banksConstants;
    }

    public void addStatementLine(StatementLine statement) {
        if (statements.size()==0){
            setStartDate(statement.getDate());
            setEndDate(statement.getDate());
        }
        statements.add(statement);
        if (statement.getDate().compareTo(getStartDate()) < 0){
            setStartDate(statement.getDate());
        } else if (statement.getDate().compareTo(getEndDate()) > 0) {
            setEndDate(statement.getDate());
        }
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public ArrayList<StatementLine> getStatements() {
        return statements;
    }

    public double estimatedAverageBalance() {
        double balance = 0.0;
        System.out.println(statements.toString());
        for (int i=0; i<statements.size(); i++){
            if (i==0) {
                balance = statements.get(i).getAvailableBalance();
                continue;
            }
            balance += statements.get(i).getDraft();
            balance += statements.get(i).getCredit();
            System.out.println("Balance at " + i + "  " + balance);
        }
        return balance;
    }

    public abstract double getInterestRate();

    public abstract void setInterestRate(double interestRate);

    public int totalDraftsForCategorySince(Category category, Date date) {
        // TODO
        return 0;
    }

    public double totalForMonth(int month, int year) {
        double totalMonth = 0.0;
        for (StatementLine statement: statements) {
            if (statement.getDate().getYear() == year && statement.getDate().getMonth() ==  intToMonth(month)){
                totalMonth += statement.getDraft();
            }
        }
        return totalMonth;
    }

    public void autoCategorizeStatements(List<Category> categories) {
        //TODO
    }

    public void removeStatementLinesBefore(Date date) {
        // TODO
    }


    public static void main(String[] args) {
        try {
            File f = new File("src/pt/upskill/projeto2/financemanager/account_info_test/1234567890989.csv");
            System.out.println(f.exists());
            Account acc = Account.newAccount(f);
            System.out.println(acc.statements.size());
        } catch (Exception e){
        }
    }
}


