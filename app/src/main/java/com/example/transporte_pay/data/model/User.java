package com.example.transporte_pay.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    private int id;

    private String name;

    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }
}
