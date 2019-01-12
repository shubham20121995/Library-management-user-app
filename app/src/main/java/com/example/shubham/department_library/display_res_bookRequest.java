package com.example.shubham.department_library;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shu on 19/02/2017.
 */
public class display_res_bookRequest {

    @SerializedName("registration_no")
    private String registration_no = "";

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }



    public display_res_bookRequest(String registration_no) {
        this.registration_no = registration_no;

    }
}
