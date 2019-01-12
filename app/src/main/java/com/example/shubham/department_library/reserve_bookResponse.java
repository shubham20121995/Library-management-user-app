package com.example.shubham.department_library;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shu on 19/02/2017.
 */
public class reserve_bookResponse {

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
        return "addbookResponse{" +
                "success='" + success + '\'' +
                '}';
    }
}
