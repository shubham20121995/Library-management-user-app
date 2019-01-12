package com.example.shubham.department_library;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 12/8/2016.
 */

public class LoginRequest {
    @SerializedName("registration_no")
    private String registrationNo = "";
    @SerializedName("password")
    private String password = "";

    public LoginRequest(String registrationNo, String password) {
        this.registrationNo = registrationNo;
        this.password = password;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "registrationNo='" + registrationNo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
