package com.example.shubham.department_library;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shubham on 12/12/2016.
 */

public class Adapter2 extends BaseAdapter {

    Context context;
    ProgressDialog pDialog;
    ArrayList all_books;
    int year,month,day,hour;
    String s3;
    Adapter2(Context context,ArrayList<All_books> all_books)
    {

        this.context=context;
        this.all_books=all_books;

    }





    @Override
    public int getCount() {
        return all_books.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holderid h=new holderid();

        LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflate.inflate(R.layout.adapter_2,null);

        h.book_name2=(TextView)v.findViewById(R.id.book_name2);
        h.author=(TextView)v.findViewById(R.id.author);
        h.publication=(TextView)v.findViewById(R.id.publication);
        h.edition=(TextView)v.findViewById(R.id.edition);
        h.no_of_copies=(TextView)v.findViewById(R.id.no_of_copies);
        Button res = (Button) v.findViewById(R.id.res);

        All_books s=new All_books();
        s= (All_books) all_books.get(position);
        final String ss = (String) s.getBook_id();
        res.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH)+1;
                day = c.get(Calendar.DAY_OF_MONTH);
                hour=c.get(Calendar.HOUR_OF_DAY);
                if(hour<15)
                {
                    s3=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
                }
                else
                {
                    c.add(Calendar.DATE, 1);
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH)+1;
                    day = c.get(Calendar.DAY_OF_MONTH);
                    hour=c.get(Calendar.HOUR);
                    s3=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
                }
                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();


                ApiService client= RetroClient.getApiService();
                Call<reserve_bookResponse> reserve_bookresponse=client.getLogin6(new reserve_bookRequest(GLOBAL.regNo,ss,s3));
                reserve_bookresponse.enqueue(new Callback<reserve_bookResponse>() {

                    @Override
                    public void onResponse(Call<reserve_bookResponse> call, Response<reserve_bookResponse> response) {

                        if (response.body().getSuccess().equals("1")) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setIcon(R.drawable.success);
                            builder.setTitle("Book reserved");

                            builder.setMessage("Collect your book by "+s3)
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }

                                    });

                            // create alert dialog
                            AlertDialog alertDialog = builder.create();

                            // show it
                            alertDialog.show();



                            pDialog.dismiss();

                        }
                        else if(response.body().getSuccess().equals("4"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setIcon(R.drawable.fail);
                            builder.setTitle("Book not reserved");

                            builder.setMessage("Book already reserved")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }

                                    });

                            // create alert dialog
                            AlertDialog alertDialog = builder.create();

                            // show it
                            alertDialog.show();
                            pDialog.dismiss();
                        } else if(response.body().getSuccess().equals("5"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setIcon(R.drawable.fail);
                            builder.setTitle("Book not reserved");

                            builder.setMessage("Book already issued")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }

                                    });

                            // create alert dialog
                            AlertDialog alertDialog = builder.create();

                            // show it
                            alertDialog.show();
                            pDialog.dismiss();
                        }
                        else {


                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setIcon(R.drawable.fail);
                            builder.setTitle("Book not reserved");

                            builder.setMessage("Book not available")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }

                                    });

                            // create alert dialog
                            AlertDialog alertDialog = builder.create();

                            // show it
                            alertDialog.show();
                            pDialog.dismiss();

                        }

                    }

                    @Override
                    public void onFailure(Call<reserve_bookResponse> call, Throwable t) {
                        pDialog.dismiss();
                        Toast.makeText(context, "Server down", Toast.LENGTH_LONG).show();
                        Log.e("Error:", t.toString());
                    }
                });
            }
        });

        String s2 = "<b>" + "BOOK NAME : " + "</b> " + (String) s.getBook_name();
        h.book_name2.setText(Html.fromHtml(s2));
        String s3 = "<b>" + "AUTHOR : " + "</b> " + (String) s.getAuthor();
        h.author.setText(Html.fromHtml(s3));
        String s4 = "<b>" + "PUBLICATION : " + "</b> " + (String) s.getPublication();
        h.publication.setText(Html.fromHtml(s4));
        String s5 = "<b>" + "EDITION : " + "</b> " + (String) s.getEdition();
        h.edition.setText(Html.fromHtml(s5));
        String s6 = "<b>" + "QUANTITY : " + "</b> " + (String) s.getNo_of_copies();
        h.no_of_copies.setText(Html.fromHtml(s6));

        return v;
    }

    class holderid{

        TextView book_name2,author,publication,edition,no_of_copies;

    }
}
