package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.adapter.ConductorListAdapter;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.response.TransactionResponse;
import com.example.transporte_pay.utils.SessionManager;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConductorBookingActivity extends AppCompatActivity {
    SessionManager sessionManager;
    String token;
    Integer roleId;
    List<Booking> bookings;
    ConductorListAdapter conductorListAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_booking);
        recyclerView = findViewById(R.id.conductorlog);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        HashMap<String, Integer> ids = sessionManager.getID();
        roleId = ids.get(SessionManager.ROLE);

        conductorListAdapter = new ConductorListAdapter();

        goToBookingList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ConductorBookingActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void goToBookingList() {
        Call<TransactionResponse> callTransLog = ApiClient.getBusClient().getTransactionData( "Bearer " + token);
        callTransLog.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response.isSuccessful()){
                    bookings = response.body().getBookings();
                    conductorListAdapter.setBookingList(bookings, roleId);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(conductorListAdapter);
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                Log.e("error", "busActivity:failed code=" + t.getMessage());
            }
        });
    }
}