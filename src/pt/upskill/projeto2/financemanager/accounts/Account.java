package pt.upskill.projeto2.financemanager.accounts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Account {

    public static Account newAccount(File f){
        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()){
                String line = s.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    public StatementLine newStatementLine(String line){

    }
}
