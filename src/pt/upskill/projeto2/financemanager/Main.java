package pt.upskill.projeto2.financemanager;

import pt.upskill.projeto2.financemanager.gui.PersonalFinanceManagerUserInterface;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class Main {

    public static void main(String[] args) {

        PersonalFinanceManager personalFinanceManager = new PersonalFinanceManager();
        PersonalFinanceManagerUserInterface gui = new PersonalFinanceManagerUserInterface(personalFinanceManager);
        gui.execute();

        // TODO Global Evolution month - menu
        // TODO Expected cost by category - menu
        // TODO interest for the year - menu
        // TODO Use Selectors with filters
        // TODO Month and date cannot be modified
        // TODO Exception handling
        // TODO save data
        // TODO fix auto categories
        // TODO use format for showing info
        // TODO MEnu error tests
        // TODO Deve verificar se os movimentos est ̃ao coerentes (se o levantamento ou dep ́osito e o saldoap ́os o movimento est ̃ao de acordo com a informa ̧c ̃ao anterior).



    }

}
