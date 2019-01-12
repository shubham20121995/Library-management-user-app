package com.example.shubham.department_library;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog pDialog;
    private Boolean exit = false;

    private CharSequence mTitle;
    FragmentManager fragmentmanager=getFragmentManager();

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);

            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);


        TextView header_name=(TextView)findViewById(R.id.header_name);
        TextView header_regno=(TextView)findViewById(R.id.header_regno);
        header_name.setText("Name : "+GLOBAL.name);
        header_regno.setText("Registration number : "+GLOBAL.regNo);
        setmTitle("BOOKS ISSUED");
        fragmentmanager.beginTransaction().replace(R.id.content_main2,new navDrawer1()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);

        return true;
    }

    public void setmTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {

            setmTitle("BOOKS ISSUED");
            fragmentmanager.beginTransaction().replace(R.id.content_main2,new navDrawer1()).commit();

        } else if (id == R.id.nav_gallery) {


            setmTitle("AVAILABLE BOOKS");
            fragmentmanager.beginTransaction().replace(R.id.content_main2,new navDrawer2()).commit();
        }

        else if (id == R.id.qr) {


            setmTitle("QR CODE");
            fragmentmanager.beginTransaction().replace(R.id.content_main2,new qrcode()).commit();
        }
        else if (id == R.id.res) {


            setmTitle("RESERVED BOOKS");
            fragmentmanager.beginTransaction().replace(R.id.content_main2,new navDrawer3()).commit();
        }

        else if (id == R.id.logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            //Uncomment the below code to Set the message and title from the strings.xml file
            //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

            //Setting message manually and performing action on button click
            builder.setMessage("Are you sure you want to log out ?")
                    .setCancelable(false)
                    .setTitle("LOGOUT")
                    .setIcon(R.drawable.logout)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            pDialog = new ProgressDialog(Main2Activity.this);
                            pDialog.setMessage("Please wait...");
                            pDialog.setCancelable(false);
                            pDialog.show();


                            ApiService client= RetroClient.getApiService();
                            Call<logoutResponse> Response=client.getLogin4(new logoutRequest(GLOBAL.regNo));
                            Response.enqueue(new Callback<logoutResponse>() {

                                @Override
                                public void onResponse(Call<logoutResponse> call, Response<logoutResponse> response) {

                                    if(response.body().getSuccess().equals("1"))
                                    {
                                        NotificationEventReceiver.cancelAlarm(getApplicationContext());
                                        DatabaseHandler db=new DatabaseHandler(getApplicationContext());
                                        db.refreshIssue();
                                        db.deleteLogin();
                                        db.refreshAll();
                                        pDialog.dismiss();
                                        Intent i=new Intent(Main2Activity.this,MainActivity.class);

                                        startActivity(i);


                                    }

                                    else
                                    {



                                        pDialog.dismiss();
                                        Toast.makeText(Main2Activity.this,"Not able to logout",Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<logoutResponse> call, Throwable t) {
                                    pDialog.dismiss();
                                    Toast.makeText(Main2Activity.this,"Server down",Toast.LENGTH_LONG).show();
                                    Log.e("Error:",t.toString());
                                }
                            });

                        }

                    })

                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = builder.create();

            // show it
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
