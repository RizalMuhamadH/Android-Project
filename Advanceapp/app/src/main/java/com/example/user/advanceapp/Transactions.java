package com.example.user.advanceapp;

import java.util.List;

/**
 * Created by User on 15/06/2016.
 */
public class Transactions {
    public List<TransItem> item;

    public List<TransItem> getItem() {
        return item;
    }

    public  class TransItem{
        private String id_exp;
        private String description_exp;
        private String amount_exp;

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

        public String getAmount_exp() {
            return amount_exp;
        }

        public void setAmount_exp(String amount_exp) {
            this.amount_exp = amount_exp;
        }
    }
}
