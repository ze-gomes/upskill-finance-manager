package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.date.Date;

public class DraftAccount extends Account {

    public DraftAccount(long numConta, String name){
        super(numConta, name);
    }

    public DraftAccount(long numConta, String name, Currency moeda){
        super(numConta, name, moeda);
    }



    public DraftAccount(long numConta, String name, Currency moeda, Date startDate, Date endDate){
        super(numConta, name, moeda, startDate, endDate);
    }

    @Override
    public double getInterestRate() {
        return banksConstants.normalInterestRate();
    }

    @Override
    public void setInterestRate(double interestRate) {
        banksConstants.setNormalInterestRate(interestRate);
    }



}
