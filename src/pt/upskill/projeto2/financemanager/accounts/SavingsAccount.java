package pt.upskill.projeto2.financemanager.accounts;


import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;

public class SavingsAccount extends Account {
    public static Category savingsCategory;

    public SavingsAccount(long numConta, String name){
        super(numConta, name);
    }

    public SavingsAccount(long numConta, String name, Currency moeda){
        super(numConta, name, moeda);
    }

    public SavingsAccount(long numConta, String name, Currency moeda, Date startDate, Date endDate){
        super(numConta, name, moeda, startDate, endDate);
    }

    @Override
    public double getInterestRate() {
        return banksConstants.savingsInterestRate();
    }

    @Override
    public void setInterestRate(double interestRate) {
        banksConstants.setSavingsInterestRate(interestRate);
    }

}
