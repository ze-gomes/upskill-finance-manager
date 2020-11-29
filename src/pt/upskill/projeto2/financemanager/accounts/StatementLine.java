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


    public StatementLine(Date date, Date valueDate, String description, double draft, double credit, double accountingBalance, double availableBalance, Category category) throws IllegalArgumentException {
        if (date == null || valueDate == null || description == null || description.equals("") || credit < 0.0 || draft > 0.0){
            throw new IllegalArgumentException();
        }
        this.date = date;
        this.valueDate = valueDate;
        this.description = description;
        this.draft = draft;
        this.credit = credit;
        this.accountingBalance = accountingBalance;
        this.availableBalance = availableBalance;
        this.category = category;
    }


    public static StatementLine newStatementLine(String line) throws FileNotFoundException, NumberFormatException, ParseException, IllegalArgumentException {
        String[] lineFormatted = line.split(";");
        // Date formatter
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(format.parse(lineFormatted[0]));
        Date valueDate = new Date(format.parse(lineFormatted[1]));
        String description = lineFormatted[2].trim();
        double draft = Double.parseDouble(lineFormatted[3]);
        double credit = Double.parseDouble(lineFormatted[4]);
        double accountingBalance = Double.parseDouble(lineFormatted[5]);
        double availableBalance = Double.parseDouble(lineFormatted[6]);
        return new StatementLine(date, valueDate, description, draft, credit, accountingBalance, availableBalance, null);
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

    @Override
    public String toString() {
        return  "date=" + date +
                ", valueDate=" + valueDate +
                ", description='" + description + '\'' +
                ", draft=" + draft +
                ", credit=" + credit +
                ", accountingBalance=" + accountingBalance +
                ", availableBalance=" + availableBalance +
                ", category=" + category +
                '}';
    }
}
