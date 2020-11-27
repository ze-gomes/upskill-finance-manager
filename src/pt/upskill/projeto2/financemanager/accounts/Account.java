package pt.upskill.projeto2.financemanager.accounts;

import pt.upskill.projeto2.financemanager.accounts.formats.FileAccountFormat;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Account {
    Currency moeda;
    int id;
    String name;
    String additionalInfo;
    Date startDate;
    Date endDate;
    Date accountInfo;

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
        this.accountInfo = new Date();
    }

    public Account(int id, String name, Currency moeda, Date startDate, Date endDate){
        this.id = id;
        this.name = name;
        this.moeda = moeda;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accountInfo = new Date();
    }




    public static Account newAccount(File f) {
        // Date formatter
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Scanner s = new Scanner(f);
            int id;
            String name, accType = "";
            Currency moeda;
            Date startDate, endDate;
            while (s.hasNextLine()) {
                String[] lineFormatted = s.nextLine().replaceAll("\\s+", "").split(";");
                String identifierWord = lineFormatted[0];
                // Take first word of array as identifier word
                // Account general info
                if (identifierWord.equals("Account")) {
                    try {
                        id = Integer.parseInt(lineFormatted[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("num conta invalido");
                    }
                    moeda = Currency.valueOf(lineFormatted[2]);
                    name = lineFormatted[3];
                    accType = lineFormatted[4];
                    // Account Dates
                } else if (identifierWord.equals("StartDate")) {
                    startDate = format.parse(lineFormatted[1]);
                } else if (identifierWord.equals("EndDate")) {
                    endDate = format.parse(lineFormatted[1]);
                }
            }
            if (accType.equals("DraftAccount"))
                return new DraftAccount(id, name, moeda, startDate, endDate);
            else if (accType.equals("SavingsAccount"))
                return new SavingsAccount(id, name, moeda, startDate, endDate);
        } catch (
                Exception e) {
            //throw FileAccountFormat;

        }

        //Account.newAccount(new File("src/pt/upskill/projeto2/financemanager/account_info_test/1234567890987.csv"));


        public int getId () {
            return id;
        }

        public String getName () {
            return name;
        }

        public Date getEndDate () {
            return endDate;
        }

        public Date getStartDate () {
            return startDate;
        }

        public StatementLine newStatementLine (String line){

        }
    }
