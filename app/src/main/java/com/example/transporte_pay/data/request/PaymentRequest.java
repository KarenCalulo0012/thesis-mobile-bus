package com.example.transporte_pay.data.request;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class PaymentRequest {
    @SerializedName("status")
    private boolean status;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("itemId")
    private String itemId;

    @SerializedName("gcash_number")
    private String gcash_number;
    @SerializedName("referenceId")
    private String referenceId;
    @SerializedName("image")
    private File image;
    @SerializedName("image_name")
    private String Title;
    @SerializedName("reason")
    private String reason;

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


    public boolean isStatus(){
        return status;
    }
    public String getRemarks(){
        return remarks;
    }



    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getGcash() {
        return gcash_number;
    }

    public void setGcash(String gcash_number) {
        this.gcash_number = gcash_number;
    }
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
