package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;

public class NoCategorySelector {


    public boolean isSelected(StatementLine stt1) {
        if (stt1.getCategory() == null) {
            return true;
        }
        return false;
    }
}
