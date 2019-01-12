package com.example.shubham.department_library;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by shubham on 12/8/2016.
 */

public interface ApiService {


    @POST("/library/login.php")
    Call<LoginResponse> getLogin(@Body LoginRequest loginRequest);

    @POST("/library/books_issued.php")
    Call<books_issued_response> getLogin2(@Body books_issued_request booksIssuedRequest);

    @POST("/library/all_books.php")
    Call<all_books_response> getLogin3(@Body all_books_request allBooksRequestRequest);

    @POST("/library/logout.php")
    Call<logoutResponse> getLogin4(@Body logoutRequest logoutrequest);

    @POST("/library/signup.php")
    Call<signupResponse> getLogin5(@Body signupRequest signuprequest);

    @POST("/library/reserve_book.php")
    Call<reserve_bookResponse> getLogin6(@Body reserve_bookRequest reserve_bookrequest);

    @POST("/library/display_reserved_books(2).php")
    Call<display_res_bookResponse> getLogin7(@Body display_res_bookRequest display_res_bookrequest);

}
