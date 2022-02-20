package com.example.transporte_pay.data.model;

public class Schedules {
    private int id;
    private int bus_id;
    private int driver_id;
    private int conductor_id;
    private int starting_point_id;
    private int destination_id;
    private int fare;
    private int capacity;
    private String schedule_date;
    private String time_departure;
    private String time_arrival;
    private String status;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String starting_point;
    private String plate_number;
    private String type;
    private String drivername;
    private String conductorname;
    private String destination;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBusId() {
        return bus_id;
    }

    public void setBusId(int bus_id) {
        this.bus_id =  bus_id;
    }

    public int getDriverId() {
        return driver_id;
    }

    public void setDriverId(int driver_id) {
        this.driver_id = driver_id;
    }

    public int getConductorId() {
        return conductor_id;
    }

    public void setConductorId(int conductor_id) {
        this.conductor_id = conductor_id;
    }

    public int getStartingPointId() {
        return id;
    }

    public void setStartingPointId(int starting_point_id) {
        this.starting_point_id = starting_point_id;
    }
    public int getDestinationId() {
        return destination_id;
    }

    public void setDestinationId(int destination_id) {
        this.destination_id = destination_id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public String getScheduleDate() {
        return schedule_date;
    }

    public void setScheduleDate(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getTimeDeparture() {
        return time_departure;
    }

    public void setTimeDeparture(String time_departure) {
        this.time_departure = time_departure;
    }

    public String getTimeArrival() {
        return time_arrival;
    }

    public void setTimeArrival(String time_arrival) {
        this.time_arrival = time_arrival;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeletedAt() {
        return deleted_at;
    }

    public void setDeletedAt(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getStartingPoint() {
        return starting_point;
    }

    public void setStartingPoint(String starting_point) {
        this.starting_point = starting_point;
    }

    public String getPlateNumber() {
        return plate_number;
    }

    public void setPlateNumber(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriverName() {
        return drivername;
    }

    public void setDriverName(String drivername) {
        this.drivername = drivername;
    }

    public String getConductorName() {
        return conductorname;
    }

    public void setConductorName(String conductorname) {
        this.conductorname = conductorname;
    }


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}