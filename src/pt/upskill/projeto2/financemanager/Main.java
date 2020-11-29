package pt.upskill.projeto2.financemanager;

import pt.upskill.projeto2.financemanager.gui.PersonalFinanceManagerUserInterface;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class Main {

    public static void main(String[] args) {
        String[] options = {"Posição Global", "Movimentos Conta", "Listar Categorias", "Análise", "Sair"};

        PersonalFinanceManager personalFinanceManager = new PersonalFinanceManager();
        PersonalFinanceManagerUserInterface gui = new PersonalFinanceManagerUserInterface(personalFinanceManager);
        gui.execute();
        personalFinanceManager.menu.requestSelection("Escolha uma opção", options);


    }

}
