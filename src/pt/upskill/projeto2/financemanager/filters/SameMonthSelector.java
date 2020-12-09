package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;

public class SameMonthSelector implements Selector<StatementLine>{
    private Date date;

    public SameMonthSelector(int month, int year){
        // Default to day1 since we are only interested in comparing month and year it doesn't matter here
        this.date = new Date(1, month, year);
    }

    public SameMonthSelector(Date date){
        this.date = date;
    }

    @Override
    public boolean isSelected(StatementLine stt1) {
        if (stt1.getDate().getMonth() == date.getMonth() && stt1.getDate().getYear() == date.getYear()){
            return true;
        }
        return false;
    }

}
