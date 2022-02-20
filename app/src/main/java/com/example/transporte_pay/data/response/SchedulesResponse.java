package com.example.transporte_pay.data.response;

import com.example.transporte_pay.data.model.Schedule;
import com.example.transporte_pay.data.model.Schedules;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SchedulesResponse {
    @SerializedName("schedule")
    ArrayList<Schedules> mSchedules;

    public ArrayList<Schedules> getSchedule() {
        return mSchedules;
    }
    public void setSchedule(ArrayList<Schedules> mSchedules) {
        this.mSchedules = mSchedules;
    }
}
