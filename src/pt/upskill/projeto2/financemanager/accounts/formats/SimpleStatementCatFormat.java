package pt.upskill.projeto2.financemanager.accounts.formats;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.categories.Category;

import java.util.Locale;

public class SimpleStatementCatFormat implements StatementLineFormat {
    @Override
    public String fields() {
        return "Date \tDescription \tDraft \tCredit \tCategory";
    }

    @Override
    public String format(StatementLine objectToFormat) {
        String date = objectToFormat.getDate().toString();
        String description = objectToFormat.getDescription();
        double draft = objectToFormat.getDraft();
        double credit = objectToFormat.getCredit();
        String cat = "";
        if (objectToFormat.getCategory() != null){
            cat = objectToFormat.getCategory().getName();
        }
        // Locale.US é utilizado para transformar os decimais dos doubles de 2,0 para 2.0
        return String.format(Locale.US,"%s \t%s \t%.1f \t%.1f \t%s",date, description, draft, credit, cat) ;
    }
}