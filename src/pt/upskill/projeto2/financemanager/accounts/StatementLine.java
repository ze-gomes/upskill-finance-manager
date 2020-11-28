package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class StatementLine {
    Date date;
    Date valueDate;
    String description;
    double draft;
    double credit;
    double accountingBalance;
    double availableBalance;
    Category category;


    public StatementLine(Date date, Date valueDate, String description, double draft, double credit, double accountingBalance, double availableBalance, Category category) {
        this.date = date;
        this.valueDate = valueDate;
        this.description = description;
        this.draft = draft;
        this.credit = credit;
        this.accountingBalance = accountingBalance;
        this.availableBalance = availableBalance;
        this.category = category;
    }


    public static StatementLine newStatementLine(String[] lineFormatted) throws FileNotFoundException, NumberFormatException, ParseException {
        // Date formatter
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(format.parse(lineFormatted[0]));
        System.out.println(date.toString());
        Date valueDate = new Date(format.parse(lineFormatted[1]));
        String description = lineFormatted[2];
        double draft = Double.parseDouble(lineFormatted[3]);
        double credit = Double.parseDouble(lineFormatted[4]);
        double accountingBalance = Double.parseDouble(lineFormatted[5]);
        double availableBalance = Double.parseDouble(lineFormatted[6]);
        Category category = new Category(lineFormatted[7]);
        return new StatementLine(date, valueDate, description, draft, credit, accountingBalance, availableBalance, category);
    }

    public Date getDate() {
        return date;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public String getDescription() {
        return description;
    }

    public double getCredit() {
        return credit;
    }

    public double getDraft() {
        return draft;
    }

    public double getAccountingBalance() {
        return accountingBalance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
