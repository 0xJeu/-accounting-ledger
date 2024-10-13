package com.plurasight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) throws IOException {
        Scanner keyboard = new Scanner(System.in);

        while(true){
            ArrayList<Transaction> transactions = getTransactions();

            displayCommands();
            String option = keyboard.nextLine().toLowerCase();
            System.out.println("------------");

            switch (option){
                case "l":
                    displayLedger(transactions, keyboard);
                    break;
                case "d":
                    addDeposit(keyboard);
                    break;
                case "x":
                    System.exit(0);
                default:
                    System.out.println("Invalid command entered. please try again.");
            }

        }



    }
    public static ArrayList<Transaction> getTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();
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
                // date|time|description|vendor|amount ->CSV headers for reference
                String[] lineSplit = input.split(Pattern.quote("|"));
                String date= lineSplit[0];
                String time = lineSplit[1];
                String description = lineSplit[2];
                String vendor = lineSplit[3];
                double amount = Double.parseDouble(lineSplit[4]);

                transactions.add(new Transaction(date,time,description,vendor,amount));
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(transactions);
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
    public static void addDeposit(Scanner keyboard) {
        try {
            //Format date and time
            DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm:ss");

            //Create bufWriter to write to csv file
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",true));

            while (true){
                //User input
                System.out.print("Please enter the amount you wish to deposit (0 to exit to main menu): ");
                double depositAmount = Double.parseDouble(keyboard.nextLine());
                if (depositAmount == 0){
                    break;
                }

                //Obtain date and time
                LocalDateTime today = LocalDateTime.now();
                String date = today.format(formattedDate);
                String time = today.format(formattedTime);

                //Format transaction entry and write to file
                String transactionEntry = String.format("\n%s|%s|%s|%s|%.2f", date, time, "User Deposit", "Vendor N/A", depositAmount);
                bufWriter.write(transactionEntry);
                System.out.println("Deposit submitted!");

                //Ask user for additional deposits
                System.out.println("Do you want to add another deposit (Y or N)? ");
                String addAnother = keyboard.nextLine();
                if(addAnother.equalsIgnoreCase("y")){
                    continue;

                } else if(addAnother.equalsIgnoreCase("n")) {
                    //Release file
                    bufWriter.close();
                    break;
                }
                else {
                    System.out.println("Invalid command entered. please try again.");
                    continue;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //Display ledger screen
    public static void displayLedger(ArrayList<Transaction>transactions, Scanner keyboard){
        while (true){
            System.out.print("""
                Ledger Commands
                
                A - All Entries
                D - Show Deposits
                P - Show Payments
                H - Home Page
                
                Enter your command:""");
            String option = keyboard.nextLine().toLowerCase();
            System.out.println("------------");

            switch (option){
                case "a":
                    for (Transaction transaction : transactions){
                        transaction.listDetails();
                        System.out.println("------------");
                    }
                    break;
                case "d":
                    for (Transaction transaction: transactions){
                        if (transaction.getDescription().contains("Deposit") || transaction.getAmount() > 0){
                            transaction.listDetails();
                            System.out.println("------------");
                        }
                    }
                    break;
                case "p":
                    for (Transaction transaction: transactions){
                        if (transaction.getAmount() < 0){
                            transaction.listDetails();
                            System.out.println("------------");
                        }
                    }
                    System.out.println("Feature coming soon!\n");
                    break;
                case "h":
                    return;
                default:
                    System.out.println("Invalid command entered. please try again.");
            }
        }
    }
}
