package com.example.shubham.department_library;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shu on 19/02/2017.
 */
public class reserve_bookRequest {

    @SerializedName("registration_no")
    private String registration_no = "";
    @SerializedName("book_id")
    private String book_id = "";
    @SerializedName("reserve_date")
    private String reserve_date = "";

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(String reserve_date) {
        this.reserve_date = reserve_date;
    }

    @Override
    public String toString() {
        return "reserve_bookRequest{" +
                "book_id='" + book_id + '\'' +
                ", registration_no='" + registration_no + '\'' +
                ", reserve_date='" + reserve_date + '\'' +
                '}';
    }





    public reserve_bookRequest(String registration_no, String book_id, String reserve_date ) {
        this.registration_no = registration_no;
        this.book_id = book_id;
        this.reserve_date = reserve_date;


    }
}
