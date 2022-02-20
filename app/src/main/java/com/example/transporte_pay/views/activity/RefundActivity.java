package com.example.transporte_pay.views.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.request.PaymentRequest;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefundActivity extends AppCompatActivity {

    EditText fullName,amountPaid,reason;
    Button save,back;
    String token,amount_paid,full_name,reason_refund,booking_id;
    SessionManager sessionManager;
    AlertDialogManager alert;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        fullName        = findViewById(R.id.fullname);
        amountPaid      = findViewById(R.id.amountPaid);
        reason          = findViewById(R.id.reason);
        save            = findViewById(R.id.save);
        back            = findViewById(R.id.back);
        alert = new AlertDialogManager();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            full_name = (String) extras.getString("fullname");
            booking_id = (String)extras.getString("booking_id");
            amount_paid = (String) extras.getString("amount");
        }

        fullName.setEnabled(false);
        amountPaid.setEnabled(false);

        amountPaid.setText(amount_paid);
        fullName.setText(full_name);




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reason_refund = reason.getText().toString();

                if(reason_refund.isEmpty()){
                    // user didn't entered reason of fund
                    alert.showAlertDialog(RefundActivity.this,
                            "Refund failed..",
                            "Please enter reason for refund",
                            false);
                }else{
                    refundBooking();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(),BookingActivity.class);
                startActivity(backIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(getApplicationContext(),BookingActivity.class);
        startActivity(backIntent);
    }


    private void refundBooking(){

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setItemId(booking_id);
        paymentRequest.setReason(reason_refund);
        Call<PaymentRequest> paymentCall = ApiClient.getPaymentClient().paymentRefund(paymentRequest,"Bearer"+ token);
        paymentCall.enqueue(new Callback<PaymentRequest>() {
            @Override
            public void onResponse(retrofit2.Call<PaymentRequest> call, Response<PaymentRequest> response) {

                if(response.body().isStatus()){
                    Toast.makeText(RefundActivity.this,response.body().getRemarks(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RefundActivity.this,TravelLogsActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RefundActivity.this,response.body().getRemarks(),Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<PaymentRequest> call, Throwable t) {

                Toast.makeText(RefundActivity.this,"Network Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
