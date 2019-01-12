package com.example.shubham.department_library;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shubham on 12/27/2016.
 */

public class signupResponse {

    @SerializedName("success")
    private String success="";

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "signupResponse{" +
                "success='" + success + '\'' +
                '}';
    }


}
