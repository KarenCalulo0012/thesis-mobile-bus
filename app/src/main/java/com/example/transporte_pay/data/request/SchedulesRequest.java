package com.example.transporte_pay.data.request;

public class SchedulesRequest {
    private int starting_point_id;
    private int destination_id;
    private String schedule_date;


    public int getStarting_point_id() {
        return starting_point_id;
    }

    public int getDestination_id() {
        return destination_id;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setStarting_point_id(int starting_point_id) {
        this.starting_point_id = starting_point_id;
    }
    public void setDestination_id(int destination_id) {
        this.destination_id = destination_id;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }
}
