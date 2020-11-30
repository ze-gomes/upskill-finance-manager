package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;

import java.util.Locale;

public class SimpleStatementFormat implements StatementLineFormat {
    @Override
    public String fields() {
        return "Date \tDescription \tDraft \tCredit \tAvailable balance ";
    }

    @Override
    public String format(StatementLine objectToFormat) {
        String date = objectToFormat.getDate().toString();
        String description = objectToFormat.getDescription();
        double draft = objectToFormat.getDraft();
        double credit = objectToFormat.getCredit();
        double availBalance = objectToFormat.getAvailableBalance();
        // Locale.US éutilizado para transformar os decimais dos doubles de 2,0 para 2.0
        return String.format(Locale.US,"%s \t%s \t%.1f \t%.1f \t%.1f",date, description, draft, credit, availBalance) ;
    }
}
