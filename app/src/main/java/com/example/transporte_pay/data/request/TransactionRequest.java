package com.example.transporte_pay.data.request;

import com.google.gson.annotations.SerializedName;

public class TransactionRequest {
    private int id;
    @SerializedName("schedule_id")
    private int scheduleID;
    private String user_status;
    private int user_id;
    private String name;
    private int starting_point_id;
    private int destination_id;
    private String schedule_date;
    private int quantity;
    private String from;
    private String to;

    public int getScheduleID() {
        return scheduleID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStarting_point_id() {
        return starting_point_id;
    }

    public void setStarting_point_id(int starting_point_id) {
        this.starting_point_id = starting_point_id;
    }

    public int getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(int destination_id) {
        this.destination_id = destination_id;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
