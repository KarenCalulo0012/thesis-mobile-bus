package com.example.transporte_pay.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Date {

    @Expose
    private String date;
//    @SerializedName("date")
    @Expose
    private String month;
//    @SerializedName("month")
    @Expose
    private String year;
//    @SerializedName("year")



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.date = year;
    }
}
