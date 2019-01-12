package com.example.shubham.department_library;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 12/8/2016.
 */

public class LoginResponse {

    @SerializedName("success")
    private String success="";
    @SerializedName("name")
    private String name="";
    @SerializedName("registration_no")
    private String registration_no="";

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success='" + success + '\'' +
                ", name='" + name + '\'' +
                ", registration_no='" + registration_no + '\'' +
                '}';
    }
}
