package com.login.mm.nealio.testlogin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.login.mm.nealio.testlogin.authorization.RestClientActivity;
import com.login.mm.nealio.testlogin.user.Job;
import com.login.mm.nealio.testlogin.user.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainScreen";
    private User mCustomer;

    private static final int FROM_PROFILE_EDIT_SCREEN = 123;
    private static final int FROM_NEW_JOB_SCREEN = 124;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }


    public void handleOption1() {
        Toast.makeText(getApplicationContext(), "Stuff", Toast.LENGTH_SHORT).show();
    }

    public void handleOption2() {
        Toast.makeText(getApplicationContext(), "Action chosen", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId())
        {
            case R.id.menu_stuff:
                handleOption1();
                return true;
            case R.id.menu_action:
                handleOption2();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.nav_profile:
                intent = new Intent(this, ProfileEditActivity.class);

                Bundle b = new Bundle();
                b.putString("key1", mCustomer.getName());
                intent.putExtras(b);
                startActivityForResult(intent, FROM_PROFILE_EDIT_SCREEN);  // starting the intent with special id that will be called back
                break;
            case R.id.nav_history:
                intent = new Intent(this, RestClientActivity.class);
                startActivityForResult(intent, FROM_PROFILE_EDIT_SCREEN);

                break;
            case R.id.nav_payment:

                break;
            case R.id.nav_sign_up_mech:
                intent = new Intent(this, MechEditActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_sign_out:
                LoginManager.getInstance().logOut();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_legal_info:
                Toast.makeText(getApplicationContext(), "Legal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_help:
                Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showJobInformation(Job job) {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_main_job_dialog);
        dialog.setTitle("Job Information");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.textView_dialog_job_summary);
        text.setText(job.getSummary());
        TextView text1 = (TextView) dialog.findViewById(R.id.textView_dialog_job_description);
        text1.setText("Description: \n" + job.getDescription());
        TextView text2 = (TextView) dialog.findViewById(R.id.textView_dialog_onSiteDiagnostic);
        text2.setText("On Site Diagnostic = " + job.isOnSiteDiagnostic());
        TextView text3 = (TextView) dialog.findViewById(R.id.textView_dialog_carInWorkingCondition);
        text3.setText("Car in working condition = " + job.isCarInWorkingCondition());
        TextView text4 = (TextView) dialog.findViewById(R.id.textView_dialog_repairCanBeDoneOnSite);
        text4.setText("Repair can be done on-site = " + job.isRepairDoneOnSite());
        TextView text5 = (TextView) dialog.findViewById(R.id.textView_dialog_carPickUpDropOff);
        text5.setText("Car pick up and drop off = " + job.isCarPickUpAndDropOff());

        TextView text6 = (TextView) dialog.findViewById(R.id.textView_dialog_parkingAvailable);
        text6.setText("Parking available on-site = " + job.isParkingAvailable());

        dialog.show();
    }



    public void displayJobsOnMainHomeScreen(final Job job) {
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tl_main_jobs_table);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
/* Create a Button to be the row-content. */
        TextView textView = new TextView(this);
        textView.setText(job.getSummary());
        textView.setTextSize(32);
        tableRow.addView(textView);

        Button statusButton = new Button(this);
        statusButton.setText("status");

        statusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                showJobInformation(job);
            }
        });

        tableRow.addView(statusButton, 1);
        tableLayout.addView(tableRow);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case (FROM_PROFILE_EDIT_SCREEN):
                    Bundle b = data.getExtras();
                    break;
                case (FROM_NEW_JOB_SCREEN):
                    String jsonMyObject = "";
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        jsonMyObject = extras.getString("newJob");
                    }
                    Job newJob = new Gson().fromJson(jsonMyObject, Job.class);
                    newJob.getSummary();
                    displayJobsOnMainHomeScreen(newJob);
                    break;
            }
        }
    }


    public void createNewJobOnClick(View view) {
        Intent intent;
        intent = new Intent(this, JobFormActivity.class);
        startActivityForResult(intent, FROM_NEW_JOB_SCREEN);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        String jwtoken = getIntent().getExtras().getString("token");

        Log.i(TAG, jwtoken);

        mCustomer = new User("TEST_customer1");
    }


}
