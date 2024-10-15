package com.plurasight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction implements Comparable<Transaction> {
    private LocalDateTime dateTime;
    private String description, vendor;
    private double amount;

    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.dateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public void listDetails(){
        System.out.printf("""
                Date: %s
                Time: %s
                Description: %s
                Vendor: %s
                Amount: %.2f
                """, getDate(),getTime(), getDescription(), getVendor(), getAmount());
    }

    //Getters
    public String getDate() {
        return dateTime.toLocalDate().toString();
    }

    public String getTime() {
        return dateTime.toLocalTime().toString();
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    //Setters

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(Transaction o) {
        return this.dateTime.compareTo(o.dateTime);
    }
}
