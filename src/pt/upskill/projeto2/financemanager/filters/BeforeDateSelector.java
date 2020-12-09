package pt.upskill.projeto2.financemanager.filters;

import pt.upskill.projeto2.financemanager.accounts.StatementLine;
import pt.upskill.projeto2.financemanager.date.Date;


public class BeforeDateSelector  implements Selector<StatementLine>{
    private Date date;


    public BeforeDateSelector(Date date){
        this.date = date;
    }

    @Override
    public boolean isSelected(StatementLine stt1) {
        if (stt1.getDate().compareTo(date) < 0){
            return true;
        }
        return false;
    }

    public boolean isSelected(Date date) {
        if (date.compareTo(this.date) < 0){
            return true;
        }
        return false;
    }
}
