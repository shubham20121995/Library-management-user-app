package com.example.shubham.department_library;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    static MainActivity INSTANCE;
    String regno;
    String password;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView link_signup=(TextView) findViewById(R.id.link_signup);
        Button btn_login=(Button)findViewById(R.id.btn_login);
        final EditText input_regno=(EditText)findViewById(R.id.input_regno);
        final EditText input_password=(EditText)findViewById(R.id.input_password);

        DatabaseHandler db=new DatabaseHandler(getApplicationContext());
        Cursor c=db.viewLogin();

        if(c.getCount()>0) {

            if (c.moveToFirst())
                do {

                    GLOBAL.regNo = c.getString(c.getColumnIndex("reg_no"));
                    GLOBAL.name = c.getString(c.getColumnIndex("name"));

                } while (c.moveToNext());
            Intent i=new Intent(MainActivity.this,Main2Activity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GLOBAL.regNo=input_regno.getText().toString().toUpperCase();
                 password=input_password.getText().toString();
                if(GLOBAL.regNo.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Enter registration number",Toast.LENGTH_LONG).show();
                }
                else if(password.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Enter password",Toast.LENGTH_LONG).show();
                }
                else
                {
                    pDialog = new ProgressDialog(MainActivity.this);
                    pDialog.setMessage("Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    getLoggedIN(GLOBAL.regNo,password);
                }



            }
        });

        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),signup.class);
                startActivity(i);
            }
        });


    }

    void getLoggedIN(String userName,String password)
    {
        ApiService client= RetroClient.getApiService();
        Call<LoginResponse> loginResponse=client.getLogin(new LoginRequest(userName,password));
        loginResponse.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                 if(response.body().getSuccess().equals("true"))
               {


                   DatabaseHandler db=new DatabaseHandler(getApplicationContext());
                   db.addLogin(response.body().getRegistration_no().toString(),response.body().getName().toString());
                   GLOBAL.name=response.body().getName().toString();
                   pDialog.dismiss();

                   Intent i=new Intent(MainActivity.this, Main2Activity.class);
                   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                   startActivity(i);

                }

                else
                 {
                     pDialog.dismiss();
                     Toast.makeText(MainActivity.this,"Invalid credentials",Toast.LENGTH_LONG).show();
                 }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(MainActivity.this,"SERVER DOWN",Toast.LENGTH_LONG).show();
                Log.e("Error:",t.toString());
            }
        });

    }

    public String getRegno() {
        return regno;
    }
    public static MainActivity getActivityInstance()
    {
        return INSTANCE;
    }
}
