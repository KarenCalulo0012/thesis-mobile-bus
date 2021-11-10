package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.request.LongLatRequest;
import com.example.transporte_pay.data.request.ScheduleRequest;
import com.example.transporte_pay.data.request.TransactionRequest;
import com.example.transporte_pay.data.response.RoutesResponse;
import com.example.transporte_pay.data.response.ScheduleResponse;
import com.example.transporte_pay.data.response.TransactionResponse;
import com.example.transporte_pay.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BusClient {

    @GET(Constants.BUS_ROUTES)
    Call<RoutesResponse> getRoutes(@Header("Authorization") String auth);

    @POST(Constants.BUS_ROUTES2)
    Call<ScheduleResponse> getSchedule(
            @Body ScheduleRequest scheduleRequest,
            @Header("Authorization") String auth);

    @POST(Constants.BUS_CONFIRM)
    Call<Booking> getTransaction (
            @Body TransactionRequest transactionRequest,
            @Header("Authorization") String auth);

    @GET(Constants.BUS_LOGS)
    Call<TransactionResponse> getTransactionData(
            @Header("Authorization") String auth);

    @POST(Constants.BUS_LOGS_DATA)
    Call<TransactionResponse> getTransactionDataPassenger(@Body TransactionRequest transactionRequest,
            @Header("Authorization") String auth);
    @POST(Constants.BUS_GPS)
    Call<Booking> getLongLat (
            @Body LongLatRequest longLatRequest,
            @Header("Authorization") String auth);
}
