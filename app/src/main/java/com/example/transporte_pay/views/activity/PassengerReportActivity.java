package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.adapter.BookingAdapter;
import com.example.transporte_pay.adapter.ConductorListAdapter;
import com.example.transporte_pay.adapter.PassengerReportAdapter;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.request.TransactionRequest;
import com.example.transporte_pay.data.response.TransactionResponse;
import com.example.transporte_pay.utils.SessionManager;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerReport extends AppCompatActivity {
    DatePicker datePickerFrom;
    DatePicker datePickerTo;
    Button search;
    SessionManager sessionManager;
    String token;
    Integer id;
    String uFrom;
    String uTo;
    String uDate;
    String dayPadded;
    String monthPadded;
    String dayPadded1;
    String monthPadded1;
    int uFromID, uToID;
    Integer roleId;
    List<Booking> bookings;
    PassengerReportAdapter passengerReportAdapter;
    ConductorListAdapter conductorListAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_report);
        recyclerView = findViewById(R.id.passengerTrans_rv);

        datePickerFrom = findViewById(R.id.editTextDateFrom);
        datePickerTo = findViewById(R.id.editTextDateTo);
        search      = (Button)findViewById(R.id.search_btn);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        HashMap<String, Integer> userDetail = sessionManager.getID();
        id = userDetail.get(SessionManager.ID);
        roleId = userDetail.get(SessionManager.ROLE);
        passengerReportAdapter = new PassengerReportAdapter();
        conductorListAdapter   = new ConductorListAdapter();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePickerFrom.getDayOfMonth();
                int month = (datePickerFrom.getMonth() + 1);
                int year = datePickerFrom.getYear();
                int day1 = datePickerTo.getDayOfMonth();
                int month1 = (datePickerTo.getMonth() + 1);
                int year1 = datePickerTo.getYear();

                monthPadded = String.format("%02d",month );
                dayPadded = String.format("%02d",day );



                uFrom = year + "-" + monthPadded + "-" + dayPadded;
                Log.e("DATE", "********** DATE: " + uFrom);

                monthPadded1 = String.format("%02d",month1 );
                dayPadded1 = String.format("%02d",day1 );

                uTo = year1 + "-" + monthPadded1 + "-" + dayPadded1;
                Log.e("DATE", "********** DATE: " + uTo);


                goToBookingList();

            }
        });
        int day3 = datePickerFrom.getDayOfMonth();
        int month3 = (datePickerFrom.getMonth() + 1);
        int year3 = datePickerFrom.getYear();
        int day4 = datePickerTo.getDayOfMonth();
        int month4 = (datePickerTo.getMonth() + 1);
        int year4 = datePickerTo.getYear();

        monthPadded = String.format("%02d",month3 );
        dayPadded = String.format("%02d",day3 );



        uFrom = year3 + "-" + monthPadded + "-" + dayPadded;
        Log.e("DATE", "********** DATE: " + uFrom);

        monthPadded1 = String.format("%02d",month4 );
        dayPadded1 = String.format("%02d",day4 );

        uTo = year4 + "-" + monthPadded1 + "-" + dayPadded1;
        Log.e("DATE", "********** DATE: " + uTo);
        goToBookingList();
    }

//    private void goToBookingList() {
//        TransactionRequest transactionRequest = new TransactionRequest();
//        transactionRequest.setFrom(uFrom);
//        transactionRequest.setTo(uTo);
//       transactionRequest.setUser_id(id);
//        Call<TransactionResponse> callTransLog = ApiClient.getBusClient().getTransactionDataPassenger(transactionRequest, "Bearer " + token);
//        callTransLog.enqueue(new Callback<TransactionResponse>() {
//            @Override
//            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
//                if (response.isSuccessful()){
//                    bookings = response.body().getBookings();
//                    passengerReportAdapter.setBookingList(bookings, roleId,uFrom,uTo);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    recyclerView.setAdapter(passengerReportAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TransactionResponse> call, Throwable t) {
//                Log.e("error", "busActivity:failed code=" + t.getMessage());
//            }
//        });
//    }
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