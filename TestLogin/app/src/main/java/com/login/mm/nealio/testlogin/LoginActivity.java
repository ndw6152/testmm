package com.login.mm.nealio.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by ndw6152 on 4/9/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginScreen";
    private CallbackManager mFBcallbackManager;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "running the on activity");
        mFBcallbackManager.onActivityResult(requestCode, resultCode, data); // sending information to fb sdk
    }


    private void initFacebookLoginButton() {

        // using default fb button for login
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
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


                onSuccessLaunchMainScreen();

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

    public void onSuccessLaunchMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFBcallbackManager = CallbackManager.Factory.create();
        initFacebookLoginButton();

    }
}
