package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.model.BusRoutes;
import com.example.transporte_pay.data.request.GoogleSignInRequest;
import com.example.transporte_pay.data.request.LoginRequest;
import com.example.transporte_pay.data.request.RegRequest;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {



    @POST(Constants.G_LOGIN)
    Call<User> createGoggleAccount(@Body GoogleSignInRequest googleSignInRequest);

    @POST(Constants.LOGIN)
    Call<User> userLogin(@Body LoginRequest loginRequest);

    @POST(Constants.REGISTER)
    Call<User> userRegister(@Body RegRequest regRequest);
}
