package com.example.myapplication.Model;

import java.util.Date;

public class Reciept {
    String recieptId;

    public Reciept(String recieptId) {
        this.recieptId = recieptId;
    }

    String plazaId;
    int fee;
    Date date;

    public String getRecieptId() {
        return recieptId;
    }

    public void setRecieptId(String recieptId) {
        this.recieptId = recieptId;
    }

    public String getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(String plazaId) {
        this.plazaId = plazaId;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
