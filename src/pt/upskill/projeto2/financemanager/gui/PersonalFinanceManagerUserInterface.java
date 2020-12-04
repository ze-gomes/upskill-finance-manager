package pt.upskill.projeto2.financemanager.gui;

import pt.upskill.projeto2.financemanager.PersonalFinanceManager;
import pt.upskill.projeto2.utils.Menu;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class PersonalFinanceManagerUserInterface {

    public static final String SEPARATOR = "-----------------------------------------------------------";
    private static final String OPT_GLOBAL_POSITION = "Posição Global";
    private static final String OPT_ACCOUNT_STATEMENT = "Movimentos Conta";
    private static final String OPT_LIST_CATEGORIES = "Listar categorias";
    private static final String OPT_ANALISE = "Análise";
    private static final String OPT_EXIT = "Sair";
    private static final String OPT_MONTHLY_SUMMARY = "Evolução global por mês";
    private static final String OPT_PREDICTION_PER_CATEGORY = "Previsão gastos totais do mês por categoria";
    private static final String OPT_ANUAL_INTEREST = "Previsão juros anuais";
    private static final String[] OPTIONS_ANALYSIS = {OPT_MONTHLY_SUMMARY, OPT_PREDICTION_PER_CATEGORY, OPT_ANUAL_INTEREST};
    private static final String[] OPTIONS = {OPT_GLOBAL_POSITION,
            OPT_ACCOUNT_STATEMENT, OPT_LIST_CATEGORIES, OPT_ANALISE, OPT_EXIT};
    private PersonalFinanceManager personalFinanceManager;
    private Menu mainMenu;

    public PersonalFinanceManagerUserInterface(PersonalFinanceManager personalFinanceManager) {
        this.personalFinanceManager = personalFinanceManager;
        this.mainMenu = new Menu();

    }

    public void execute() {
        String option = mainMenu.requestSelection("Escolha uma opção", OPTIONS);
        if (option != null) {
            optionSelected(option);
        }
    }


    public void optionSelected(String option) {
        switch (option) {
            case (OPT_GLOBAL_POSITION):
                System.out.println(SEPARATOR);
                personalFinanceManager.posicaoGlobal();
                execute();
                break;
            case (OPT_ACCOUNT_STATEMENT):
                String accId = mainMenu.requestSelection("Escolha uma conta para consultar os extractos", personalFinanceManager.getArrayIds());
                if (accId != null) {
                    long accIdSelected = Long.parseLong(accId);
                    System.out.println(SEPARATOR);
                    personalFinanceManager.printAccountStatements(personalFinanceManager.checkIfExistingAccID(accIdSelected));
                }
                execute();
                break;
            case (OPT_LIST_CATEGORIES):
                System.out.println(SEPARATOR);
                String cat = mainMenu.requestSelection("Selecione a categoria que quer consultar", personalFinanceManager.getArrayCategories());
                personalFinanceManager.printCategoryTags(cat);
                execute();
                break;
            case (OPT_ANALISE):
                System.out.println(SEPARATOR);
                String selection = mainMenu.requestSelection("Escolha uma das seguintes opções de análise:", OPTIONS_ANALYSIS);
                if (selection != null) {
                }
                execute();
                break;
            case (OPT_EXIT):
                System.out.println("A terminar sessão...");
                break;
        }
    }


}
