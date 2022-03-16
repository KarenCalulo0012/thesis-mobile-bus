package com.example.transporte_pay.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("schedule_id")
    @Expose
    private Integer scheduleId;
    @SerializedName("bus_id")
    @Expose
    private Integer busId;
    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("conductor_id")
    @Expose
    private Integer conductorId;
    @SerializedName("fare_amount")
    @Expose
    private Integer fareAmount;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("bus")
    @Expose
    private Bus bus;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("schedule")
    @Expose
    private Schedule schedule;
    @SerializedName("gcash_number")
    @Expose
    private String gcashNumber;
    @SerializedName("long")
    @Expose
    private String longitude;
    @SerializedName("lat")
    @Expose
    private String latitude;
    @Expose
    private String passenger_type;
    @SerializedName("passenger_type")


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
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

    public Integer getFareAmount() {
        return fareAmount;
    }

    public void setFareAmount(Integer fareAmount) {
        this.fareAmount = fareAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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
    public String getPassenger_type() {
        return passenger_type;
    }

    public void setPassenger_type(String passenger_type) {
        this.passenger_type = passenger_type;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setGcashNumber(String gcashNumber) {
        this.gcashNumber = gcashNumber;
    }

    public String getGcashNumber() {
        return gcashNumber;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

}
