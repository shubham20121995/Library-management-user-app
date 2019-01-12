package com.example.shubham.department_library;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shubham on 12/12/2016.
 */

public class navDrawer3 extends Fragment {

    ListView list;
    Adapter3 a;
    View v;
    ProgressDialog pDialog;



    ArrayList<res_books> res = new ArrayList<res_books>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v =inflater.inflate(R.layout.fab3,container,false);


        return v;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab3 = (FloatingActionButton)getView().findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load1();
            }
        });
       load1();

    }

    public void load1()
    {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiService client = RetroClient.getApiService();
        Call<display_res_bookResponse> display_res_bookresponse = client.getLogin7(new display_res_bookRequest(GLOBAL.regNo));
        display_res_bookresponse.enqueue(new Callback<display_res_bookResponse>() {

            @Override
            public void onResponse(Call<display_res_bookResponse> call, Response<display_res_bookResponse> response) {

                if (response.body().getSuccess().equals("true")) {

                    res.clear();

                    res = response.body().getBooks();

                    list = (ListView)getView().findViewById(R.id.listview3);
                    a = new Adapter3(getActivity(), res);
                    list.setAdapter(a);
                    pDialog.dismiss();

                } else {


                    pDialog.dismiss();
                    Toast.makeText(getActivity(), "No books reserved", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<display_res_bookResponse> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Server down", Toast.LENGTH_LONG).show();
                Log.e("Error:", t.toString());
            }
        });
    }


}

