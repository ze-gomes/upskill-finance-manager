package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;


public class StatementLine implements Comparable<StatementLine> {
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
            System.out.println("Argumento ilegal");
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


    public static StatementLine newStatementLine(String line) throws ParseException {
        String[] lineFormatted = line.split(";");
        // Date formatter
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(format.parse(lineFormatted[0]));
        Date valueDate = new Date(format.parse(lineFormatted[1]));
        String description = lineFormatted[2].trim();
        // Tests if value is valid double and convert otherwise
        double draft = convertToDouble(lineFormatted[3]);
        double credit = convertToDouble(lineFormatted[4]);
        double accountingBalance = Double.parseDouble(lineFormatted[5]);
        double availableBalance = Double.parseDouble(lineFormatted[6]);
        return new StatementLine(date, valueDate, description, draft, credit, accountingBalance, availableBalance, null);
    }

    public static double convertToDouble(String s){
        // First case, if value is not provided
        if (s == null || s.equals("")){
            return 0.0;
            // if statement value is double format
        } else if (isDouble(s)){
            return Double.parseDouble(s);
            // if string is int Format
        } else {
            return Double.valueOf(s);
        }
    }


    // Test if value is double or not
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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


    // Compara statements por data
    @Override
    public int compareTo(StatementLine s) {
        return getDate().compareTo(s.getDate());
    }


    // Verifica se 2 statements sao iguais
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatementLine)) return false;
        StatementLine that = (StatementLine) o;
        return Double.compare(that.getDraft(), getDraft()) == 0 &&
                Double.compare(that.getCredit(), getCredit()) == 0 &&
                Double.compare(that.getAccountingBalance(), getAccountingBalance()) == 0 &&
                Double.compare(that.getAvailableBalance(), getAvailableBalance()) == 0 &&
                getDate().equals(that.getDate()) &&
                getValueDate().equals(that.getValueDate()) &&
                getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getValueDate(), getDescription(), getDraft(), getCredit(), getAccountingBalance(), getAvailableBalance());
    }
}
