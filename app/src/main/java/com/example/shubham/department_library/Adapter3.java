package com.example.shubham.department_library;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shu on 19/02/2017.
 */
public class Adapter3 extends BaseAdapter {

    Context context;
    ArrayList all_books;
    Adapter3(Context context, ArrayList<res_books> all_books)
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
        View v=inflate.inflate(R.layout.adapter_3,null);
        h.res_book_id=(TextView)v.findViewById(R.id.res_book_id);
        h.res_name=(TextView)v.findViewById(R.id.res_name);
        h.res_date=(TextView)v.findViewById(R.id.res_date);



        res_books s=new res_books();
        s= (res_books) all_books.get(position);
        String s1 = "<b>" + "BOOK ID :" + "</b> " + (String) s.getBook_id();
        h.res_book_id.setText(Html.fromHtml(s1));
        String s2 = "<b>" + "BOOK NAME : " + "</b> " + (String) s.getBook_name();
        h.res_name.setText(Html.fromHtml(s2));

        String s4 = "<b>" + "RESERVED DATE : " + "</b> " + (String) s.getReserved_date();
        h.res_date.setText(Html.fromHtml(s4));

        return v;
    }

    class holderid{

        TextView res_book_id,res_name,res_date;

    }
}

