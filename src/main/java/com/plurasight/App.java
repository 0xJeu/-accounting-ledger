package com.plurasight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        HashMap<String, Transaction> transactions = getTransactions();

        for (Transaction transaction : transactions.values()){
            transaction.listDetails();
            System.out.println("------------");
        }

    }
    public static HashMap<String,Transaction> getTransactions(){
        HashMap<String, Transaction> transactions = new HashMap<>();
        try {
            FileReader fr = new FileReader("src/main/resources/transactions.csv");
            // create a BufferedReader to manage input stream
            BufferedReader br = new BufferedReader(fr);
            String input;
            // read until there is no more data
            while ((input = br.readLine()) != null) {
                if (input.startsWith("date")){
                    continue;
                }
                // date|time|description|vendor|amount
                String[] lineSplit = input.split(Pattern.quote("|"));
                String date= lineSplit[0];

                String time = lineSplit[1];
                String description = lineSplit[2];
                String vendor = lineSplit[3];
                double amount = Double.parseDouble(lineSplit[4]);

                transactions.put(vendor,new Transaction(date,time,description,vendor,amount));
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Display commands
    public static void displayCommands(){
        System.out.print("""
                What do you want to do?
                
                D - Add Deposit
                P - Make Payment
                L - Ledger
                X - Exit
                
                Enter your command:""");

    }

    //Add deposit method
    public static void addDeposit(Transaction transaction, Scanner keyboard){

    }


}
