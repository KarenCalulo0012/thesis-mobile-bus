package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.request.RegRequest;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView loginLink;
    Button registerButton;
    EditText name, email, password, c_pass,phoneNumber;
    ProgressBar loading;
    AlertDialogManager alert;
    String getName, getEmail, getGooId,status;
    Integer getRole, id;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginLink = findViewById(R.id.changePass_link);
        registerButton = findViewById(R.id.reg_btn);
        name = findViewById(R.id.prof_name);
        email = findViewById(R.id.prof_email);
        password = findViewById(R.id.reg_password);
        c_pass = findViewById(R.id.reg_confirm_password);
        loading = findViewById(R.id.progressBar_update);
        phoneNumber   = findViewById(R.id.prof_number);

        sessionManager = new SessionManager(this);
        alert = new AlertDialogManager();

        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(name.getText().toString()) ||
                 TextUtils.isEmpty(email.getText().toString()) ||
                 TextUtils.isEmpty(phoneNumber.getText().toString()) ||
                 TextUtils.isEmpty(password.getText().toString()) ||
                 TextUtils.isEmpty(c_pass.getText().toString()))
            {
                Toast.makeText(RegisterActivity.this, "Email/Password is Required", Toast.LENGTH_LONG).show();
                alert.showAlertDialog(RegisterActivity.this,
                        "FAILED",
                        "Name/Email/Phone Number /Password is Required, Don't leave it blank.",
                        false);
            }else if (!TextUtils.equals(password.getText().toString(), c_pass.getText().toString())){
                alert.showAlertDialog(RegisterActivity.this,
                        "FAILED",
                        "Password did not match, Please Try Again",
                        false);
            }else if(name.length()<8){
                alert.showAlertDialog(RegisterActivity.this,
                        "FAILED",
                        "Please Provide your Full Name",
                        false);
            }else if(phoneNumber.length() !=11){
                alert.showAlertDialog(RegisterActivity.this,
                        "FAILED",
                        "Only 11 digit Phone Number is allowed",
                        false);
            }
            else
            {
                gotoRegister();

            }

        });
    }

    private void gotoRegister() {
        RegRequest regRequest = new RegRequest();
        regRequest.setName(capitalize(name.getText().toString()));
        regRequest.setEmail(email.getText().toString());
        regRequest.setPhone(phoneNumber.getText().toString());
        regRequest.setPassword(password.getText().toString());
        regRequest.setPassword_confirmation(c_pass.getText().toString());

        loading.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.GONE);

        Call<User> registerResponseCall = ApiClient.getUserClient().userRegister(regRequest);
        registerResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (response.isSuccessful()){
                    loading.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this,"Account Registered", Toast.LENGTH_LONG).show();
                    alert.showAlertDialog(RegisterActivity.this,
                            "SUCCESS",
                            "Account Registered",
                            true);
                    User user = response.body();
                    assert user != null;
                    String token = user.getToken();
                    new Handler().postDelayed(() -> {
                        getName = user.getName();
                        getEmail = user.getEmail();
                        getRole = user.getRole_id();
                        getGooId = user.getGoogle_id();
                        id = user.getId();
                        status = user.getStatus();
                        SessionManager.saveAuthToken(token);
                        sessionManager.createSession(getName, getEmail,getRole,getGooId,id,status);
                        startActivity(new Intent(RegisterActivity.this,
                                MainActivity.class).
                                putExtra("data", user.getToken()));
                    }, 300);
                    loading.setVisibility(View.GONE);
                    registerButton.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(RegisterActivity.this,"LOGIN FAILED", Toast.LENGTH_LONG).show();
                    failedAlert("Error");
                }
            }
            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                Log.w("error", "signInResult:failed code=" +t.getMessage());
                failedAlert(t.getMessage().toString());
                Log.e("error", "signInResult:failed code=" +t.getMessage());
                loading.setVisibility(View.GONE);
                registerButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void failedAlert(String errorMessage) {
        alert.showAlertDialog(RegisterActivity.this,
                "REGISTER FAILED",
                errorMessage,
                false);
//        "Email is already taken, Please try something new",

    }

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, Objects.requireNonNull(capMatcher.group(1)).toUpperCase() + Objects.requireNonNull(capMatcher.group(2)).toLowerCase());
        }
        return capMatcher.appendTail(capBuffer).toString();
    }
}