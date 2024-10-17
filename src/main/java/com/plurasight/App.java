package com.plurasight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        while (true) {
            ArrayList<Transaction> transactions = getTransactions();

            displayCommands();
            String option = keyboard.nextLine().toLowerCase();
            System.out.println("------------");

            switch (option) {
                case "l":
                    displayLedger(transactions, keyboard);
                    break;
                case "d":
                    addDeposit(keyboard);
                    break;
                case "p":
                    makePayment(keyboard);
                    break;
                case "x":
                    System.exit(0);
                default:
                    System.out.println("Invalid command entered. please try again.");
            }

        }

    }

    // Display commands
    public static void displayCommands() {
        System.out.print("""
                Main Menu
                
                D - Add Deposit
                P - Make Payment
                L - Ledger
                X - Exit
                
                Enter your command:""");
    }

    //Retrieve transactions from CSV
    public static ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            FileReader fr = new FileReader("src/main/resources/transactions.csv");
            // create a BufferedReader to manage input stream
            BufferedReader br = new BufferedReader(fr);
            String input;
            // read until there is no more data
            while ((input = br.readLine()) != null) {
                if (input.startsWith("date")) {
                    continue;
                }
                // date|time|description|vendor|amount ->CSV headers for reference
                String[] lineSplit = input.split(Pattern.quote("|"));
                String date = lineSplit[0];
                String time = lineSplit[1];
                String description = lineSplit[2];
                String vendor = lineSplit[3];
                double amount = Double.parseDouble(lineSplit[4]);

                transactions.add(new Transaction(date, time, description, vendor, amount));
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(transactions);
        return transactions;
    }

    //Add deposit method
    public static void addDeposit(Scanner keyboard) {
        try {
            //Format date and time
            DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm:ss");

            //Create bufWriter to write to csv file
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

            while (true) {
                //User input
                System.out.print("Please enter the amount you wish to deposit (0 to exit to main menu): ");
                double depositAmount = Double.parseDouble(keyboard.nextLine());
                if (depositAmount == 0) {
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
                if (addAnother.equalsIgnoreCase("y")) {
                    continue;

                } else if (addAnother.equalsIgnoreCase("n")) {
                    //Release file
                    bufWriter.close();
                    break;
                } else {
                    System.out.println("Invalid command entered. please try again.");
                    continue;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Make payment method
    public static void makePayment(Scanner keyboard) {
        try {
            //Format date and time
            DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm:ss");

            //Create bufWriter to write to csv file
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true));

            while (true) {
                //Obtain date and time
                LocalDateTime today = LocalDateTime.now();
                String date = today.format(formattedDate);
                String time = today.format(formattedTime);

                // date|time|description|vendor|amount ->CSV headers for reference

                System.out.print("Please enter description: ");
                String description = keyboard.nextLine();
                System.out.print("Please enter Vendor (if none, Please enter 'N/A'): ");
                String vendor = keyboard.nextLine();
                System.out.print("Please enter payment amount: ");
                double paymentAmount = -Double.parseDouble(keyboard.nextLine());

                if (vendor.equalsIgnoreCase("N/A")) {
                    vendor = "Vendor N/A";
                }

                //Format transaction entry and write to file
                String transactionEntry = String.format("\n%s|%s|%s|%s|%.2f", date, time, description, vendor, paymentAmount);
                bufWriter.write(transactionEntry);
                System.out.println("Deposit submitted!");

                //Ask user for additional payments
                System.out.println("Do you want to add another payment (Y or N)? ");
                String addAnother = keyboard.nextLine();
                if (addAnother.equalsIgnoreCase("y")) {
                    continue;

                } else if (addAnother.equalsIgnoreCase("n")) {
                    //Release file
                    bufWriter.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Display ledger screen
    public static void displayLedger(ArrayList<Transaction> transactions, Scanner keyboard) {
        while (true) {
            System.out.print("""
                    Ledger Commands
                    
                    A - All Entries
                    D - Show Deposits
                    P - Show Payments
                    R - Reports
                    H - Home Page
                    
                    Enter your command:""");
            String option = keyboard.nextLine().toLowerCase();
            System.out.println("------------");

            switch (option) {
                case "a":
                    for (Transaction transaction : transactions) {
                        transaction.listDetails();
                        System.out.println("------------");
                    }
                    break;
                case "d":
                    for (Transaction transaction : transactions) {
                        if (transaction.getDescription().contains("Deposit") || transaction.getAmount() > 0) {
                            transaction.listDetails();
                            System.out.println("------------");
                        }
                    }
                    break;
                case "p":
                    for (Transaction transaction : transactions) {
                        if (transaction.getAmount() < 0) {
                            transaction.listDetails();
                            System.out.println("------------");
                        }
                    }
                    break;
                case "r":
                    displayReport(transactions, keyboard);
                    break;
                case "h":
                    return;
                default:
                    System.out.println("Invalid command entered. please try again.");
            }
        }
    }

    //Display report screen
    public static void displayReport(ArrayList<Transaction> transactions, Scanner keyboard) {
        while (true) {
            System.out.print("""
                    Report Commands
                    
                    1 - Month To Date
                    2 - Previous Month
                    3 - Year To Date
                    4 - Previous Year
                    5 - Search by Vendor
                    0 - Back to Ledger Menu
                    
                    Choose a report:""");
            int option = Integer.parseInt(keyboard.nextLine());
            System.out.println("------------");

            switch (option) {
                case 1:
                    generateMonthToDateReport(transactions);
                    break;
                case 2, 3, 4, 5:
                    System.out.println("Feature coming soon!");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void generateMonthToDateReport(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
//        System.out.println(firstDayOfMonth);

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = LocalDate.parse(transaction.getDate());
            if (transactionDate.isAfter(firstDayOfMonth.minusDays(1)) && transactionDate.isBefore(today.minusDays(1))) {
                transaction.listDetails();
                System.out.println("------------");
            }
        }

    }

}
