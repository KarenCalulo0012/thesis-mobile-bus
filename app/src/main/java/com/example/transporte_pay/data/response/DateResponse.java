package com.example.transporte_pay.data.response;

import com.example.transporte_pay.data.model.Date;
import com.example.transporte_pay.data.model.Schedules;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DateResponse {
    @SerializedName("dates")
    ArrayList<Date> dates;

    public ArrayList<Date> getDates() {
        return dates;
    }
    public void setSchedule(ArrayList<Date> dates) {
        this.dates = dates;
    }
}
