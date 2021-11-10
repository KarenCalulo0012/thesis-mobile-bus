package com.example.transporte_pay.data.request;

import com.google.gson.annotations.SerializedName;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PaymentRequestForImage {
//    @SerializedName("status")
//    private boolean status;
//    @SerializedName("remarks")
//    private String remarks;
//    @SerializedName("itemId")
//    private String itemId;

//    @SerializedName("gcash_number")
//    private String gcash_number;
  //  @SerializedName("referenceId")//
//    private String referenceId;
  //  @SerializedName("image")
//    private File image;


    private RequestBody itemId;
    private RequestBody referenceId;
    private MultipartBody.Part image;

//    @SerializedName("image")
//    private String Image;
//
//
//    @SerializedName("response")
//    private String Response;
//
//    public String getResponse() {
//        return Response;
//    }

//
//    public boolean isStatus(){
//        return status;
//    }
//    public String getRemarks(){
//        return remarks;
//    }



    public RequestBody getItemId() {
        return itemId;
    }

    public void setItemId(RequestBody itemId) {
        this.itemId = itemId;
    }

//    public String getGcash() {
//        return gcash_number;
//    }
//
//    public void setGcash(String gcash_number) {
//        this.gcash_number = gcash_number;
//    }
    public RequestBody getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(RequestBody referenceId) {
        this.referenceId = referenceId;
    }

    public MultipartBody.Part getImage() {
        return image;
    }

    public void setImage(MultipartBody.Part image) {
        this.image = image;
    }

}
