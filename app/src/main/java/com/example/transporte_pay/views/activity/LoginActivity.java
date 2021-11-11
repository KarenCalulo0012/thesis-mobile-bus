package com.example.transporte_pay.views.activity;


import android.app.Activity;
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


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;

import com.example.transporte_pay.data.request.GoogleSignInRequest;
import com.example.transporte_pay.data.request.LoginRequest;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.utils.APIError;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView reg_link;
    SignInButton signInButton;
    Button loginButton;
    GoogleSignInClient mGoogleSignInClient;
    ActivityResultLauncher<Intent> activityResultLauncher;
    EditText email,password;
    String personEmail,personName, personId;
    String getName, getEmail, getGooId,status;
    Integer getRole, id;
    ProgressBar loading;
    SessionManager sessionManager;
    AlertDialogManager alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        reg_link = findViewById(R.id.register_link);
        signInButton = findViewById(R.id.signInButton);
        loginButton = findViewById(R.id.loginButton);
        loading = findViewById(R.id.progressBar);

        // Alert Dialog Manager
        alert = new AlertDialogManager();

        sessionManager = new SessionManager(this);

        reg_link.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                // user didn't entered username or password
                // Show alert asking him to enter the details
                alert.showAlertDialog(LoginActivity.this,
                        "Login failed..",
                        "Please enter username and password",
                        false);
            }else {
                gotoLogin(createRequest());
            }
        });

        configGSignIn();

        signInButton.setOnClickListener(v -> signIn());

        getActivityResultLauncher();

    }

    public LoginRequest createRequest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        return loginRequest;
    }

    private void gotoLogin(LoginRequest loginRequest) {
        loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        Call<User> loginResponseCall = ApiClient.getUserClient().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"LOGIN SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    User user = response.body();
                    String token = user.getToken();
                    String lName = user.getName();
                    String getStatus = user.getStatus();
                    Integer getId = user.getId();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getName = user.getName();
                            getEmail = user.getEmail();
                            getRole = user.getRole_id();
                            getGooId = user.getGoogle_id();
                            id = user.getId();
                            status = user.getStatus();
                            sessionManager.saveAuthToken(token);
                            sessionManager.createSession(getName, getEmail,getRole,getGooId,id,status);
                            startActivity(new Intent(LoginActivity.this,MainActivity.class)
                                    .putExtra("token", user.getToken()));
                            finish();
                        }
                    }, 300);
                } else {
                    Toast.makeText(LoginActivity.this,"LOGIN FAILED", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error", "signInResult:failed code=" +t.getMessage());
                alert.showAlertDialog(LoginActivity.this,
                        "ERROR",
                        "Login Failed, Please try again",
                        false);
                loading.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
                email.setText(null);
                password.setText(null);

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Toast.makeText(this, "Yeah", Toast.LENGTH_LONG).show();
        }
    }



    private void getActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleSignInResult(task);
                Log.d("Response COde",String.valueOf(result.getResultCode()) );
            }else if(result.getResultCode() == Activity.RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Request Cancelled "+result.getResultCode(),Toast.LENGTH_LONG).show();
                Log.e("Request Cancelled, Result Code: ",String.valueOf(result.getResultCode()) );
            }

        });
    }


    private void configGSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account != null){
                personEmail = account.getEmail();
                personName = account.getDisplayName();
                personId = account.getId();
                goToGoogleSignIn();
                //goToDash();
            }
        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            alert.showAlertDialog(LoginActivity.this,
                    "ERROR",
                    "Sign In Failed, Please try again",
                    false);
        }
    }

    private void goToGoogleSignIn() {
        GoogleSignInRequest googleSignInRequest = new GoogleSignInRequest();
        googleSignInRequest.setEmail(personEmail);
        googleSignInRequest.setName(personName);
        googleSignInRequest.setId(personId);
        googleSignInRequest.setStatus("free");

        Call<User> gooResponseCall = ApiClient.getUserClient().createGoggleAccount(googleSignInRequest);
        gooResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
//                    Log.e("CODE", "********************" + String.valueOf(response.code()));
                    User user = response.body();
                    Log.e("RESPONSE","********************" + response.body().getGoogle_id());
                    String token = user.getToken();
                    sessionManager.saveAuthToken(token);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getName = user.getName();
                            getEmail = user.getEmail();
                            getRole = user.getRole_id();
                            getGooId = user.getGoogle_id();
                            id = user.getId();
                            status = user.getStatus();

                            sessionManager.saveAuthToken(token);
                            sessionManager.createSession(getName, getEmail,getRole,getGooId,id,"free");
                            Log.e("RESPONSE",getEmail + getGooId + getName + getRole);

                            alert.showAlertDialog(LoginActivity.this,
                                    "SUCCESS",
                                    "Login Successful",
                                    true);

                            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                    .putExtra("data", user));
                        }
                    }, 700);

                }else {
                    APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                    //Toast.makeText(LoginActivity.this," " + message.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ERROR", "-----------" +message.getMessage());
                    alert.showAlertDialog(LoginActivity.this,
                            "ERROR",
                            "Login Failed, Please try again",
                            false);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("error", "signInResult:failed code=" +t.getMessage());
                alert.showAlertDialog(LoginActivity.this,
                        "ERROR",
                        "Login Failed, Please try again",
                        false);

            }
        });
    }
}