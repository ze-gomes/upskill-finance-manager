package pt.upskill.projeto2.financemanager.accounts;

import java.util.Date;

public class SavingsAccount extends Account {

    public SavingsAccount(int numConta, String name){
        super(numConta, name);
    }

    public SavingsAccount(int numConta, String name, Currency moeda, Date startDate, Date endDate){
        super(numConta, name, moeda, startDate, endDate);
    }
}
