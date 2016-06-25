package com.example.user.advanceapp;

/**
 * Created by User on 15/06/2016.
 */
public class Transaction {
    private String id_exp;
    private String description_exp;
    private  String amount_expenses;
    public Transaction(String id_exp,String description_exp, String amount_expenses){
        this.id_exp = id_exp;
        this.description_exp = description_exp;
        this.amount_expenses = amount_expenses;
    }

    public String getId_exp() {
        return id_exp;
    }

    public void setId_exp(String id_exp) {
        this.id_exp = id_exp;
    }

    public String getDescription_exp() {
        return description_exp;
    }

    public void setDescription_exp(String description_exp) {
        this.description_exp = description_exp;
    }

    public String getAmount_expenses() {
        return amount_expenses;
    }

    public void setAmount_expenses(String amount_expenses) {
        this.amount_expenses = amount_expenses;
    }
}
