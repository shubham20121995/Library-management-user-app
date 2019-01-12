package com.example.shubham.department_library;

import android.app.Fragment;
import android.app.ProgressDialog;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shubham on 12/12/2016.
 */

public class navDrawer2 extends Fragment {

    ListView list;
    Adapter2 a;
    View v;
    ProgressDialog pDialog;



    ArrayList<All_books> all_books = new ArrayList<All_books>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v =inflater.inflate(R.layout.fab2,container,false);


        return v;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseHandler db = new DatabaseHandler(getActivity());
        Cursor c = db.viewAll();
        FloatingActionButton fab2 = (FloatingActionButton)getView().findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load1();
            }
        });
        if (c.getCount() > 0) {
            all_books.clear();
            TextView empty2=(TextView) getView().findViewById(R.id.empty2);
            empty2.setVisibility(View.GONE);

            if (c.moveToFirst())
                do {
                    All_books a1 = new All_books();
                    String book_id = c.getString(c.getColumnIndex("book_id"));
                    a1.setBook_id(book_id);
                    String book_name = c.getString(c.getColumnIndex("book_name"));
                    a1.setBook_name(book_name);
                    String author = c.getString(c.getColumnIndex("author"));
                    a1.setAuthor(author);
                    String publication = c.getString(c.getColumnIndex("publication"));
                    a1.setPublication(publication);
                    String edition = c.getString(c.getColumnIndex("edition"));
                    a1.setEdition(edition);
                    String quantity = c.getString(c.getColumnIndex("quantity"));
                    a1.setNo_of_copies(quantity);
                    Log.e("HELLOHELLO", a1.toString());
                    all_books.add(a1);
                    Log.e("H", all_books.toString());
                } while (c.moveToNext());

            list = (ListView) getView().findViewById(R.id.listview2);
            a = new Adapter2(getActivity(), all_books);
            list.setAdapter(a);

        } else {

           load1();
        }
    }

    public void load1()
    {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiService client = RetroClient.getApiService();
        Call<all_books_response> allBooksResponse = client.getLogin3(new all_books_request("1"));
        allBooksResponse.enqueue(new Callback<all_books_response>() {

            @Override
            public void onResponse(Call<all_books_response> call, Response<all_books_response> response) {

                if (response.body().getSuccess().equals("true")) {
                    TextView empty2=(TextView) getView().findViewById(R.id.empty2);
                    all_books.clear();
                    empty2.setVisibility(View.GONE);
                    all_books = response.body().getBooks();
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.refreshAll();
                    for (int i = 0; i < all_books.size(); i++) {
                        db.addAll(all_books.get(i));
                        Log.e("Hello",all_books.get(i).toString());
                    }

                    list = (ListView) getView().findViewById(R.id.listview2);
                    a = new Adapter2(getActivity(), all_books);
                    list.setAdapter(a);
                    pDialog.dismiss();

                } else {
                    TextView empty2=(TextView) getView().findViewById(R.id.empty2);
                    empty2.setVisibility(View.VISIBLE);
                    all_books.clear();
                    DatabaseHandler db = new DatabaseHandler(getActivity());
                    db.refreshAll();
                    for (int i = 0; i < all_books.size(); i++) {
                        db.addAll(all_books.get(i));
                        Log.e("Hello",all_books.get(i).toString());
                    }

                    list = (ListView) getView().findViewById(R.id.listview2);
                    a = new Adapter2(getActivity(), all_books);
                    list.setAdapter(a);
                    pDialog.dismiss();
                    Toast.makeText(getActivity(), "No books available", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<all_books_response> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Server down", Toast.LENGTH_LONG).show();
                Log.e("Error:", t.toString());
            }
        });
    }
}
