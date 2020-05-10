package com.example.bmihome;

import java.util.Date;

public class BMI {
    private String id;
    private String email;
    private Double bmi;
    private String date;

    public BMI() {
    }

    public BMI(String id, String email, Double bmi, String date) {
        this.id = id;
        this.email = email;
        this.bmi = bmi;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
