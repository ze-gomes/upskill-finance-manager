package pt.upskill.projeto2.utils;

import javax.swing.*;

/**
 * @author upSkill 2020
 * <p>
 * ...
 */

public class Menu {
    private static ImageIcon bankIcon = new ImageIcon("images/bank.png");
    private static ImageIcon statementIcon = new ImageIcon("images/statement.png");



    public static boolean yesOrNoInput(String message) {
        return JOptionPane.showConfirmDialog(null, message) == JOptionPane.YES_OPTION;
    }

    public static String requestInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

    public static String requestSelection(String name, String[] options) {
        String option = ((String) JOptionPane.showInputDialog(null,
                "Escolha uma opção", name, JOptionPane.QUESTION_MESSAGE,
                bankIcon, options, options[0]));
        return option;
    }

    // Para menu de categorizar os statements
    public static String requestSelectionStatement(String name, String message,  String[] options) {
        String option = ((String) JOptionPane.showInputDialog(null,
                message, name, JOptionPane.QUESTION_MESSAGE,
                statementIcon, options, options[0]));
        return option;
    }

}
