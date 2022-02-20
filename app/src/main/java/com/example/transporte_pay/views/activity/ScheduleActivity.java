package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.adapter.BookingAdapter;
import com.example.transporte_pay.adapter.DateAdapter;
import com.example.transporte_pay.adapter.ScheduleAdapter;
import com.example.transporte_pay.adapter.ScheduleDataAdpater;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.model.Date;
import com.example.transporte_pay.data.model.Schedule;
import com.example.transporte_pay.data.model.Schedules;
import com.example.transporte_pay.data.request.DateRequest;
import com.example.transporte_pay.data.request.ScheduleRequest;
import com.example.transporte_pay.data.request.SchedulesRequest;
import com.example.transporte_pay.data.response.DateResponse;
import com.example.transporte_pay.data.response.ScheduleResponse;
import com.example.transporte_pay.data.response.SchedulesResponse;
import com.example.transporte_pay.data.response.TransactionResponse;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {
    int uToID, uFromID, quantity = 1, uQuantity, uScheduleID, user_id;
    String token, uDate, to, from, name;
    RecyclerView recyclerView,dateRecyclerView;
    TextView item_list;
    SessionManager sessionManager;
    ScheduleDataAdpater scheduleDataAdpater;
    DateAdapter dateAdapter;
    ArrayList<Schedules> schedules;
    ArrayList<Date> dates;
    AlertDialogManager alert;

    private ScheduleDataAdpater.RecyclerViewClickListener listener;

    private DateAdapter.RecyclerViewClickListener dlistener;
    public static final int ID_FOR_ADD_BOOKING = -1;
    public static final String DEFAULT_STATUS_FOR_ADD_BOOKING = "existing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        recyclerView = findViewById(R.id.ticketList_RV);
        scheduleDataAdpater = new ScheduleDataAdpater();

        dateRecyclerView = findViewById(R.id.scList_RV);
        dateAdapter    =new DateAdapter();

        item_list      = findViewById(R.id.textSuperView);
        alert = new AlertDialogManager();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);
        name = user.get(SessionManager.NAME);

        HashMap<String, Integer> ids = sessionManager.getID();
        user_id = ids.get(SessionManager.ID);

     //   gotoDate();
        gotoSchedule();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(ScheduleActivity.this,MainActivity.class);
        startActivity(backIntent);
    }
    private void gotoSchedule() {
        SchedulesRequest schedulesRequest = new SchedulesRequest();
        schedulesRequest.setStarting_point_id(1);
        schedulesRequest.setDestination_id(2);
        schedulesRequest.setSchedule_date("2022-02-28");

        Call<SchedulesResponse> callSchedule = ApiClient.getBusClient().getScheduleData(schedulesRequest, "Bearer " + token);
        callSchedule.enqueue(new Callback<SchedulesResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchedulesResponse> call, @NotNull Response<SchedulesResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    schedules = response.body().getSchedule();
                    setOnClickListener();
                    scheduleDataAdpater.setData(schedules, listener);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(scheduleDataAdpater);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SchedulesResponse> call, @NotNull Throwable t) {
                Log.e("error", "scheduleActivity:failed code=" + t.getMessage());
            }
        });
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            alert.showAlertDialog(ScheduleActivity.this,
                    "Schedule Alert..",
                    "Please go to book a seat section",
                    false);


        };
    }

    private void gotoDate() {
        DateRequest dateRequest = new DateRequest();
        dateRequest.setDate("1");
        dateRequest.setMonth("2");
        dateRequest.setYear("2022");

        Call<DateResponse> callDate = ApiClient.getBusClient().getDateData(dateRequest, "Bearer " + token);
        callDate.enqueue(new Callback<DateResponse>() {
            @Override
            public void onResponse(@NotNull Call<DateResponse> call, @NotNull Response<DateResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    dates = response.body().getDates();
                    setOnClickListenerDate();
                    dateAdapter.setData(dates, dlistener);
                    dateRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    dateRecyclerView.setAdapter(dateAdapter);
                }
            }

            @Override
            public void onFailure(@NotNull Call<DateResponse> call, @NotNull Throwable t) {
                Log.e("error", "scheduleActivity:failed code=" + t.getMessage());
            }
        });
    }

    private void setOnClickListenerDate() {
        dlistener = (v, position) -> {
            alert.showAlertDialog(ScheduleActivity.this,
                    "Schedule Alert..",
                    "Please go to book a seat section",
                    false);


        };
    }


}
