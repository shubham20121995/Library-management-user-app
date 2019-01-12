package com.example.shubham.department_library;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 12/26/2016.
 */

public class logoutRequest {

    @SerializedName("registration_no")
    private String registrationNo = "";

    @Override
    public String toString() {
        return "logoutRequest{" +
                "registrationNo='" + registrationNo + '\'' +
                '}';
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public logoutRequest(String registrationNo) {
        this.registrationNo = registrationNo;

    }


}
