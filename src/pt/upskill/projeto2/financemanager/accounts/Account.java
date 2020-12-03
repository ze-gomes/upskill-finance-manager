package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.accounts.formats.SimpleStatementFormat;
import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;
import pt.upskill.projeto2.financemanager.exceptions.BadFormatException;
import pt.upskill.projeto2.financemanager.exceptions.UnknownAccountException;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
            } else if (statementLines && !line.equals("")) {
                StatementLine statement = newStatementLine(line);
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
        for (int i=0; i<statements.size(); i++){
            if (i==0) {
                balance = statements.get(i).getAvailableBalance();
                continue;
            }
            balance += statements.get(i).getDraft();
            balance += statements.get(i).getCredit();
        }
        return balance;
    }



    public BanksConstants getBanksConstants() {
        return banksConstants;
    }

    public ArrayList<StatementLine> getStatements(){
        return statements;
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

    public double estimatedAverageBalance() {
        double balance = 0.0;
        for (int i=0; i<statements.size(); i++){
            if (i==0) {
                balance = statements.get(i).getAvailableBalance();
                continue;
            }
            balance += statements.get(i).getDraft();
            balance += statements.get(i).getCredit();
        }
        return balance;
    }

    public Currency getMoeda() {
        return moeda;
    }

    public abstract double getInterestRate();

    public abstract void setInterestRate(double interestRate);

    public double totalDraftsForCategorySince(Category category, Date date) {
        double totalDrafts = 0.0;
        for ( StatementLine statement : statements) {
            // Null Category, doesn't matter
            if (statement.getCategory() == null){
                continue;
            }
            if (statement.getDate().compareTo(date) > 0 && statement.getCategory().getName().equals(category.getName())){
                totalDrafts += statement.getDraft();
            }
        }
        return totalDrafts;
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
        for (StatementLine statement : statements) {
            for (Category cat: categories) {
                if ( cat.hasTag(statement.getDescription())){
                    statement.setCategory(cat);
                }
            }
        }
    }

    public void removeStatementLinesBefore(Date date) {
        // Need to use a iterator to remove from list concurrently
        ListIterator<StatementLine> statementsIterator = statements.listIterator();
        while(statementsIterator.hasNext()){
            if(statementsIterator.next().getDate().compareTo(date) < 0){
                statementsIterator.remove();
            }
        }
    }

    public void sortStatementLines(){
        Collections.sort(statements);
    }


    // Check if a certain statement is already saved on the account
    public boolean checkIfStatementAlreadyExists(StatementLine toBeChecked){
        for (StatementLine s: statements) {
             if (s.equals(toBeChecked)){
                 return true;
             }
        }
        // No equal statement found already in account
        return false;
    }

    // Prints all statements in the console using SimpleStatementFormat
    public void printAllStatements(){
        SimpleStatementFormat statementFormat = new SimpleStatementFormat();
        for (StatementLine s: statements) {
            System.out.println(statementFormat.format(s));
        }
    }

}


