package com.example.transporte_pay.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("bus_id")
    @Expose
    private Integer busId;
    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("conductor_id")
    @Expose
    private Integer conductorId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("starting_point_id")
    @Expose
    private Integer startingPointId;
    @SerializedName("destination_id")
    @Expose
    private Integer destinationId;
    @SerializedName("fare")
    @Expose
    private Integer fare;
    @SerializedName("schedule_date")
    @Expose
    private String scheduleDate;
    @SerializedName("time_departure")
    @Expose
    private String timeDeparture;
    @SerializedName("time_arrival")
    @Expose
    private String timeArrival;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("bus")
    @Expose
    private Bus bus;
    @SerializedName("starting_point")
    @Expose
    private StartingPoint startingPoint;
    @SerializedName("destination")
    @Expose
    private Destination destination;
    @SerializedName("status")
    @Expose
    private String sStatus;
    @SerializedName("seats_available")
    @Expose
    private String seats_available;

    public StartingPoint getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(StartingPoint startingPoint) {
        this.startingPoint = startingPoint;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getConductorId() {
        return conductorId;
    }

    public void setConductorId(Integer conductorId) {
        this.conductorId = conductorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStartingPointId() {
        return startingPointId;
    }

    public void setStartingPointId(Integer startingPointId) {
        this.startingPointId = startingPointId;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(String timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public String getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(String timeArrival) {
        this.timeArrival = timeArrival;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setsStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public String getsStatus() {
        return sStatus;
    }
    public void setSeatsAvailable(String seats_available) {
        this.seats_available = seats_available;
    }

    public String getSeatsAvailable() {
        return seats_available;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
}
