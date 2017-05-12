package com.login.mm.nealio.testlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.login.mm.nealio.testlogin.Authorization.RestClientActivity;
import com.login.mm.nealio.testlogin.User.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FROM_PROFILE_EDIT_SCREEN = 911;
    private String TAG = "MainScreen";
    private User mCustomer;


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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (FROM_PROFILE_EDIT_SCREEN):
                if (resultCode == Activity.RESULT_OK) {
                    Bundle b = data.getExtras();
                    TextView nameView = (TextView) findViewById(R.id.main_first_text);
                    nameView.setText(b.getString("key1"));
                    mCustomer.setName(b.getString("key1"));
                }
                break;
        }
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

        mCustomer = new User("TEST_customer1");
    }
}
