package com.example.transporte_pay.data.request;

public class UpdateUserPass {

    private String old_password;
    private String new_password;
    private String new_password_confirmation;

    //GETTER SETTER
    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getNew_password_confirmation() {
        return new_password_confirmation;
    }

    public void setNew_password_confirmation(String new_password_confirmation) {
        this.new_password_confirmation = new_password_confirmation;
    }
}
