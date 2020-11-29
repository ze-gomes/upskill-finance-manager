package pt.upskill.projeto2.financemanager.accounts;


import pt.upskill.projeto2.financemanager.categories.Category;
import pt.upskill.projeto2.financemanager.date.Date;

import java.util.List;

public class SavingsAccount extends Account {
    public static Category savingsCategory = new Category("SAVINGS");

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
        return getBanksConstants().savingsInterestRate();
    }

    @Override
    public void setInterestRate(double interestRate) {
        getBanksConstants().setSavingsInterestRate(interestRate);
    }

    @Override
    public void autoCategorizeStatements(List<Category> categories) {
        super.autoCategorizeStatements(categories);
    }

    @Override
    public void addStatementLine(StatementLine statement) {
        statement.setCategory(savingsCategory);
        super.addStatementLine(statement);
    }
}
