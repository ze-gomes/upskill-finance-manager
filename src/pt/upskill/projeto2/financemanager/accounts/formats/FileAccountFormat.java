package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.financemanager.accounts.SavingsAccount;
import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

public class FileAccountFormat implements Format {
    @Override
    public String format(Object objectToFormat) {
        // Account formatting
        Account acc = (Account) objectToFormat;
        String accType;
        if (acc instanceof SavingsAccount) {
            accType = "SavingsAccount";
        } else {
            accType = "DraftAccount";
        }
        String startDate = acc.getStartDate().toString();
        String endDate = acc.getEndDate().toString();
        String nl = System.getProperty("line.separator");
        String accountFormatHeader = "Account Info - " + new Date().toString() + nl
                + "Account  ;" + acc.getId() +" ; " + acc.getMoeda() + "  ;" + acc.getName() + " ;" + accType + " ;" + nl
                + "Start Date ;" + startDate + nl
                + "End Date ;" + endDate + nl;
        // Statements Formatting
        // Header
        LongStatementFormat longStatementFormat = new LongStatementFormat();
        String statementFormatHeader = longStatementFormat.fieldsForAccount() + nl;
        // Statements
        String allStatementsFormat = "";
        for (StatementLine statement: acc.getStatements()) {
            allStatementsFormat += longStatementFormat.formatForAcc(longStatementFormat.format(statement)) +nl;
        }
        return accountFormatHeader + statementFormatHeader + allStatementsFormat;
    }

    public String accountHeader(Object objectToFormat){
        // Account Header formatting
        Account acc = (Account) objectToFormat;
        String accType;
        if (acc instanceof SavingsAccount) {
            accType = "SavingsAccount";
        } else {
            accType = "DraftAccount";
        }
        String nl = System.getProperty("line.separator");
        return "Account Info - " + new Date().toString() + nl
                + "Account  ;" + acc.getId() +" ; " + acc.getMoeda() + "  ;" + acc.getName() + " ;" + accType + " ;" + nl;
    }
}
