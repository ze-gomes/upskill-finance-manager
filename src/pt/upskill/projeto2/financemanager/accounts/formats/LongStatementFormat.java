package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;

import java.util.Locale;

public class LongStatementFormat implements StatementLineFormat {
    @Override
    public String fields() {
        return "Date \tValue Date \tDescription \tDraft \tCredit \tAccounting balance \tAvailable balance ";
    }

    // Needed for FileAccountFormat correct display (Using ;)
    public String fieldsForAccount(){
        return fields().replaceAll("\t", ";").trim();
    }

    @Override
    public String format(StatementLine objectToFormat) {
        String date = objectToFormat.getDate().toString();
        String dateValue = objectToFormat.getValueDate().toString();
        String description = objectToFormat.getDescription();
        double draft = objectToFormat.getDraft();
        double credit = objectToFormat.getCredit();
        double accBalance = objectToFormat.getAccountingBalance();
        double availBalance = objectToFormat.getAvailableBalance();
        // Locale.US éutilizado para transformar os decimais dos doubles de 2,0 para 2.0
        return String.format(Locale.US,"%s \t%s \t%s \t%.1f \t%.1f \t%.1f \t%.1f",date, dateValue, description, draft, credit, accBalance, availBalance) ;
    }

    // Needed for FileAccountFormat correct display (Using ;)
    public String formatForAcc(String statementformatted) {
        return statementformatted.trim().replaceAll("\t", ";");
    }
}
