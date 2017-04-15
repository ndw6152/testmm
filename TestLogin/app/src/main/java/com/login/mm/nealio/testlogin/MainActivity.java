package com.login.mm.nealio.testlogin;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainScreen";
    private CallbackManager mFBcallbackManager;

    private void initFacebookLoginButton() {

        // using default fb button for login
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_login);
        loginButton.setReadPermissions();
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "user_birthday", "user_likes"));

        // Callback registration
        loginButton.registerCallback(mFBcallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "FB success" +  loginResult.getAccessToken());
                        Profile profile = Profile.getCurrentProfile();

                        // Facebook Email address
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.d(TAG, "LoginActivity Response " + response.toString());

                                        try {
                                            String name = object.getString("name");
                                            Log.d(TAG, "Name = " + name);
                                            String gender = object.getString("gender");
                                            Log.d(TAG, "Gender = " + gender);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, gender, work");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "on Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e(TAG, "FB error" + exception.toString());
                    }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "running the on activity");
        mFBcallbackManager.onActivityResult(requestCode, resultCode, data); // sending information to fb sdk
    }

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.nav_profile:
                intent = new Intent(this, ProfileEditActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_history:
                Toast.makeText(getApplicationContext(), "History Icon", Toast.LENGTH_SHORT).show();
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
                break;
            case R.id.nav_legal_info:
                break;
            case R.id.nav_help:
                break;
            case R.id.nav_settings:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFBcallbackManager = CallbackManager.Factory.create();
        initFacebookLoginButton();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
}
