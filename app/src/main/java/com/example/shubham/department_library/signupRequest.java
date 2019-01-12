package com.example.shubham.department_library;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 12/27/2016.
 */

public class signupRequest {

    @SerializedName("registration_no")
    private String registrationNo = "";
    @SerializedName("name")
    private String name = "";
    @SerializedName("password")
    private String password = "";

    @Override
    public String toString() {
        return "signupRequest{" +
                "registrationNo='" + registrationNo + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public signupRequest(String registrationNo, String name, String password) {
        this.registrationNo = registrationNo;
        this.name = name;
        this.password = password;
    }
}
