package pt.upskill.projeto2.financemanager.gui;

import pt.upskill.projeto2.financemanager.PersonalFinanceManager;
import pt.upskill.projeto2.financemanager.accounts.Account;
import pt.upskill.projeto2.utils.Menu;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class PersonalFinanceManagerUserInterface {

    public PersonalFinanceManagerUserInterface(PersonalFinanceManager personalFinanceManager) {
        this.personalFinanceManager = personalFinanceManager;
        this.menu = new Menu();

    }

    private static final String OPT_GLOBAL_POSITION = "Posição Global";
    private static final String OPT_ACCOUNT_STATEMENT = "Movimentos Conta";
    private static final String OPT_LIST_CATEGORIES = "Listar categorias";
    private static final String OPT_ANALISE = "Análise";
    private static final String OPT_EXIT = "Sair";

    private static final String OPT_MONTHLY_SUMMARY = "Evoluçãoo global por mês";
    private static final String OPT_PREDICTION_PER_CATEGORY = "Previsão gastos totais do mês por categoria";
    private static final String OPT_ANUAL_INTEREST = "Previsão juros anuais";

    private static final String[] OPTIONS_ANALYSIS = {OPT_MONTHLY_SUMMARY, OPT_PREDICTION_PER_CATEGORY, OPT_ANUAL_INTEREST};
    private static final String[] OPTIONS = {OPT_GLOBAL_POSITION,
            OPT_ACCOUNT_STATEMENT, OPT_LIST_CATEGORIES, OPT_ANALISE, OPT_EXIT};

    private static final String SEPARATOR = "-----------------------------------------------------------";

    private PersonalFinanceManager personalFinanceManager;
    private Menu menu;


    public void execute() {
        String option = menu.requestSelection("Escolha uma opção", OPTIONS);
        if (option!=null){
            optionSelected(option);
        }
    }


    public void optionSelected(String option) {
        switch (option) {
            case (OPT_GLOBAL_POSITION):
                System.out.println("Posicao Global:");
                System.out.println("Numero de Conta - Saldo");
                personalFinanceManager.imprimirSaldoContas();
                System.out.println("Saldo Total: " + personalFinanceManager.saldoTotalContas());
                execute();
            case (OPT_ACCOUNT_STATEMENT):
            case (OPT_EXIT):
                System.out.println(SEPARATOR);
                System.out.println("A terminar sessão");
                break;
        }
    }


}
