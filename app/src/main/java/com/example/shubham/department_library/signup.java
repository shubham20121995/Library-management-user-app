package com.example.shubham.department_library;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shubham on 12/2/2016.
 */

public class signup extends AppCompatActivity {
    String regno;
    String password;
    String name;
    ProgressDialog pDialog;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        TextView link_login=(TextView) findViewById(R.id.link_login);
        final EditText input_name=(EditText) findViewById(R.id.input_name);
        final EditText input_regno=(EditText) findViewById(R.id.input_regno);
        final EditText input_pass=(EditText) findViewById(R.id.input_pass);
        Button btn_signup=(Button)findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regno=input_regno.getText().toString().toUpperCase();
                password=input_pass.getText().toString();
                name=input_name.getText().toString();

                if(regno.equals(""))
                {
                    Toast.makeText(signup.this,"Enter registration number",Toast.LENGTH_LONG).show();
                }
                else if(name.equals(""))
                {
                    Toast.makeText(signup.this,"Enter name",Toast.LENGTH_LONG).show();
                }
                else if(password.equals(""))
                {
                    Toast.makeText(signup.this,"Enter password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    pDialog = new ProgressDialog(signup.this);
                    pDialog.setMessage("Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    getSignup(regno,name,password);
                }



            }
        });
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void getSignup(String regno,String name,String password)
    {
        ApiService client= RetroClient.getApiService();
        Call<signupResponse> Response=client.getLogin5(new signupRequest(regno,name,password));
        Response.enqueue(new Callback<signupResponse>() {

            @Override
            public void onResponse(Call<signupResponse> call, Response<signupResponse> response) {

                if(response.body().getSuccess().equals("1"))
                {
                    success_alert("SUCCESSFULL","Registration successfull");
                    clear_text();
                    pDialog.dismiss();



                }

                else if(response.body().getSuccess().equals("0"))
                {
                    fail_alert("UNSUCCESSFULL","Registration failed");
                    pDialog.dismiss();

                }
                else if(response.body().getSuccess().equals("3"))
                {
                    fail_alert("UNSUCCESSFULL","Enter all details");
                    pDialog.dismiss();
                }
                else
                {
                    fail_alert("UNSUCCESSFULL","Unknown error");
                    pDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<signupResponse> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(signup.this,"SERVER DOWN",Toast.LENGTH_LONG).show();
                Log.e("Error:",t.toString());
            }
        });

    }

    public void success_alert(String title, String body)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(signup.this);
        builder.setIcon(R.drawable.success);
        builder.setTitle(title);
        builder.setMessage(body)
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

    }

    public void fail_alert(String title, String body)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(signup.this);
        builder.setIcon(R.drawable.fail);
        builder.setTitle(title);
        builder.setMessage(body)
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
    }

    void clear_text()
    {
        final EditText input_name=(EditText) findViewById(R.id.input_name);
        final EditText input_regno=(EditText) findViewById(R.id.input_regno);
        final EditText input_pass=(EditText) findViewById(R.id.input_pass);
        input_name.setText("");
        input_regno.setText("");
        input_pass.setText("");

    }
}
