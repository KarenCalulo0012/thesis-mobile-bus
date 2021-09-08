package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.transporte_pay.R;
import com.example.transporte_pay.adapter.BookingAdapter;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.response.TransactionResponse;
import com.example.transporte_pay.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelLogsActivity extends AppCompatActivity {
    SessionManager sessionManager;
    String token;
    List<Booking> bookings;
    BookingAdapter bookingAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_logs);

        recyclerView = findViewById(R.id.transLog_rv);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

       bookingAdapter = new BookingAdapter(getApplicationContext());

        goToBookingList();

    }

    private void goToBookingList() {
        Call<TransactionResponse> callTransLog = ApiClient.getBusClient().getTransactionData( "Bearer " + token);
        callTransLog.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response.isSuccessful()){
                    bookings = response.body().getBookings();
                    bookingAdapter.setBookingList(bookings);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(bookingAdapter);
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                Log.e("error", "busActivity:failed code=" + t.getMessage());
            }
        });
    }
}