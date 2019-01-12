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

public class navDrawer1 extends Fragment {

    ListView list;
    Adapter a;
    View v;
    ProgressDialog pDialog;



    ArrayList<Books> issued_books = new ArrayList<Books>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v =inflater.inflate(R.layout.fab1,container,false);


        return v;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseHandler db=new DatabaseHandler(getActivity());
        Cursor c=db.viewIssue();
        FloatingActionButton fab1 = (FloatingActionButton)getView().findViewById(R.id.fab1);
         fab1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Log.e("button","load");
             load();

        }
        });

        if(c.getCount()>0) {

            issued_books.clear();
            TextView empty=(TextView) getView().findViewById(R.id.empty);
            empty.setVisibility(View.GONE);

            if (c.moveToFirst())
                do {
                    Books a1=new Books();
                    String book_id = c.getString(c.getColumnIndex("book_id"));
                    a1.setBook_id(book_id);
                    String book_name = c.getString(c.getColumnIndex("book_name"));
                    a1.setBook_name(book_name);
                    String date_of_issue = c.getString(c.getColumnIndex("date_of_issue"));
                    a1.setDate_of_issue(date_of_issue);
                    String return_date = c.getString(c.getColumnIndex("return_date"));
                    a1.setReturn_date(return_date);
                    Log.e("HELLOHELLO",a1.toString());
                    issued_books.add(a1);
                    Log.e("H",issued_books.toString());
                } while (c.moveToNext());

            list=(ListView)getView().findViewById(R.id.listview);
            a=new Adapter(getActivity(),issued_books);
            list.setAdapter(a);


        }
       else{
            load();
        }


    }

    public void load()
    {
        Log.e("load","load");
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();


        ApiService client= RetroClient.getApiService();
        Call<books_issued_response> booksIssuedResponse=client.getLogin2(new books_issued_request(GLOBAL.regNo));
        booksIssuedResponse.enqueue(new Callback<books_issued_response>() {

            @Override
            public void onResponse(Call<books_issued_response> call, Response<books_issued_response> response) {

                if(response.body().getSuccess().equals("true"))
                {

                    TextView empty=(TextView) getView().findViewById(R.id.empty);
                    issued_books.clear();
                    empty.setVisibility(View.GONE);
                    issued_books=response.body().getBooks();
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.refreshIssue();
                    for (int i = 0; i < issued_books.size(); i++) {
                        db.addIssue(issued_books.get(i));
                        Log.e("Hello",issued_books.get(i).toString());
                    }

                    list=(ListView)getView().findViewById(R.id.listview);

                    a=new Adapter(getActivity(),issued_books);
                    list.setAdapter(a);
                    try {
                        NotificationEventReceiver.setupAlarm(getActivity().getApplicationContext());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    pDialog.dismiss();

                }

                else
                {
                    TextView empty=(TextView) getView().findViewById(R.id.empty);
                    empty.setVisibility(View.VISIBLE);
                    issued_books.clear();
                    list=(ListView)getView().findViewById(R.id.listview);

                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.refreshIssue();
                    for (int i = 0; i < issued_books.size(); i++) {
                        db.addIssue(issued_books.get(i));
                        Log.e("Hello",issued_books.get(i).toString());
                    }
                    a=new Adapter(getActivity(),issued_books);
                    list.setAdapter(a);


                    pDialog.dismiss();
                    Toast.makeText(getActivity(),"No books issued",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<books_issued_response> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity(),"Server down",Toast.LENGTH_LONG).show();
                Log.e("Error:",t.toString());
            }
        });
    }


}
/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
fab.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });*/
