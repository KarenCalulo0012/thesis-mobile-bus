package com.example.transporte_pay.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.adapter.ScheduleAdapter;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.model.Schedule;
import com.example.transporte_pay.data.request.ScheduleRequest;
import com.example.transporte_pay.data.request.TransactionRequest;
import com.example.transporte_pay.data.response.ScheduleResponse;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusActivity extends AppCompatActivity {
    int uToID, uFromID, quantity = 1, uQuantity, uScheduleID, user_id;
    String token, uDate, to, from, name,passenger_type_text,availableSeat;
    RecyclerView recyclerView;
    TextView from_tv, to_tv, quantityNo,item_list;
    EditText date,idNumber;
    ImageView plus, minus;
    SessionManager sessionManager;
    ScheduleAdapter scheduleAdapter;
    ArrayList<Schedule> schedules;
    AlertDialogManager alert;
    TextInputLayout passenger_type;
    AutoCompleteTextView drop_items;
    private ScheduleAdapter.RecyclerViewClickListener listener;
    public static final int ID_FOR_ADD_BOOKING = -1;
    public static final String DEFAULT_STATUS_FOR_ADD_BOOKING = "existing";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);


        from_tv = findViewById(R.id.locationFrom_tv);
        to_tv = findViewById(R.id.locationTo_tv);
        date = findViewById(R.id.date_et);
        plus = findViewById(R.id.plus_imgbtn);
        minus = findViewById(R.id.minus_imgbtn);
        quantityNo = findViewById(R.id.quantity_tv);
        recyclerView = findViewById(R.id.ticketList_RV);
        scheduleAdapter = new ScheduleAdapter();
        passenger_type = findViewById(R.id.drop_down);
//        passenger_type_text = "Normal";
        passenger_type_text = "Ordinary";
        drop_items     =  findViewById(R.id.drop_items);
        item_list      = findViewById(R.id.textSuperView);
        idNumber       = findViewById(R.id.idNumber_tv);
        alert = new AlertDialogManager();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);
        name = user.get(SessionManager.NAME);

        HashMap<String, Integer> ids = sessionManager.getID();
        user_id = ids.get(SessionManager.ID);

//        String[] items  ={"Normal","PWD","Senior","Student"};
        String[] items  ={"Ordinary","PWD","Senior","Student"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(BusActivity.this,R.layout.dropdown_item,items);
        drop_items.setAdapter(itemAdapter);

        drop_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                passenger_type_text = parent.getItemAtPosition(position).toString();


                if(!passenger_type_text.equals("Ordinary")){
                    idNumber.setHint(passenger_type_text+" ID NUMBER :");
                    idNumber.setVisibility(View.VISIBLE);



                }else{
                    idNumber.setVisibility(View.INVISIBLE);
                    gotoSchedule();
                }


            }
        });

        idNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() <= 7 || s.length() >= 10) {
//                    tv.setText("Invalid length, should be from 7 to 10 characters. Please check your code");
                    Toast.makeText(BusActivity.this,"Invalid length, should be from 7 to 10 characters. Please check your code",Toast.LENGTH_SHORT).show();
//                    Toast.makeText(BusActivity.this,String.valueOf(s),Toast.LENGTH_SHORT).show();
                }else{
                    if ( s.length() < 7 && s.length() > 10) {
                        gotoSchedule();
                    }
                }

            }
        });



        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            uToID = (int) b.get("toID");
            uFromID = (int) b.get("fromID");
            uDate = (String) b.get("date");
            to = (String) b.get("to");
            from = (String) b.get("from");

            to_tv.setText(to);
            from_tv.setText(from);
            date.setText(uDate);
        }

        plus.setOnClickListener(v -> {
            quantity++;
            displayQuantity();
        });

        minus.setOnClickListener(v -> {
            if (quantity == 0) {
                Toast.makeText(getApplicationContext(), "CANT DECREASE TO ZERO", Toast.LENGTH_SHORT).show();
            } else {
                quantity--;
                displayQuantity();
            }
        });

        gotoSchedule();
    }


    private void displayQuantity() {
        quantityNo.setText(String.valueOf(quantity));
    }


    private void gotoSchedule() {
        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setStarting_point_id(uFromID);
        scheduleRequest.setDestination_id(uToID);
        scheduleRequest.setSchedule_date(uDate);
        if( passenger_type_text.equals("Ordinary")){
            passenger_type_text = "Normal";
        }
        Call<ScheduleResponse> callSchedule = ApiClient.getBusClient().getSchedule(scheduleRequest, "Bearer " + token);
        callSchedule.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(@NotNull Call<ScheduleResponse> call, @NotNull Response<ScheduleResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    schedules = response.body().getSchedule();
                    setOnClickListener();
                    scheduleAdapter.setData(schedules, listener,passenger_type_text);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(scheduleAdapter);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ScheduleResponse> call, @NotNull Throwable t) {
                Log.e("error", "busActivity:failed code=" + t.getMessage());
            }
        });
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
           uScheduleID = schedules.get(position).getId();
            uQuantity = quantity;
            availableSeat = schedules.get(position).getSeatsAvailable();


        if(Integer.valueOf(availableSeat) <=0){
            alert.showAlertDialog(BusActivity.this,
                    "BOOKING STATUS",
                    "No Available Seats ",
                    false);
        }else{
//            Toast.makeText(BusActivity.this,passenger_type_text,Toast.LENGTH_SHORT).show();
            if(passenger_type_text.equals("Normal")){
                gotoConfirm();
            }else{
                if((idNumber.getText().toString().length() >= 7) && (idNumber.getText().toString().length() <=10) ){
                    gotoConfirm();
                }else{
                    alert.showAlertDialog(BusActivity.this,
                            "ID NUMBER ..",
                            "Please enter ID Number or Invalid length, should be from 7 to 10 characters. Please check your code",
                            false);
                }
            }
        }



        };
    }

    private void gotoConfirm() {



        Toast.makeText(BusActivity.this,name,Toast.LENGTH_SHORT).show();


        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setId(ID_FOR_ADD_BOOKING);
        transactionRequest.setScheduleID(uScheduleID);
        transactionRequest.setUser_status(DEFAULT_STATUS_FOR_ADD_BOOKING);
        transactionRequest.setUser_id(user_id);
        transactionRequest.setName(name);
        transactionRequest.setStarting_point_id(uFromID);
        transactionRequest.setDestination_id(uToID);
        transactionRequest.setSchedule_date(uDate);
        transactionRequest.setQuantity(quantity);
        if(!passenger_type_text.equals("Ordinary")){
            transactionRequest.setId_number(idNumber.getText().toString());
            passenger_type_text = "Normal";
        }
        transactionRequest.setPassenger_type(passenger_type_text);



        Call<Booking> transCall = ApiClient.getBusClient().getTransaction(transactionRequest, "Bearer " + token);
        transCall.enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(@NotNull Call<Booking> call, @NotNull Response<Booking> response) {

                if (response.isSuccessful()) {
//                    String getResponse = new Gson().toJson(response.body());
//                    List<Routes> routesList = new ArrayList<>();

                    alert.showAlertDialog(BusActivity.this,
                            "TRANSACTION SAVED",
                            "Transaction Successfully Saved",
                            true);

                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(BusActivity.this, MainActivity.class);
                        startActivity(intent);
                    }, 2000);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Booking> call, @NotNull Throwable t) {
                Log.e("error", "busActivity:failed code=" + t.getMessage());
            }
        });
    }
}