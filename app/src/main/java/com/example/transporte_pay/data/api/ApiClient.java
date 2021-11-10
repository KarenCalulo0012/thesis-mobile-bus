package com.example.transporte_pay.data.api;

import com.example.transporte_pay.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    private static Retrofit getRetrofit(){

        if(retrofit == null){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60,TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .serializeNulls()
                    .create();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
                    .build();

        }
        return retrofit ;
    }


    public static UserClient getUserClient(){
        return getRetrofit().create(UserClient.class);
    }

    public static BusClient getBusClient(){
        return  getRetrofit().create(BusClient.class);
    }

    public static PaymentClient getPaymentClient(){return getRetrofit().create(PaymentClient.class);}
    public static ConductorClient getConductorClient(){return getRetrofit().create(ConductorClient.class);}


}

