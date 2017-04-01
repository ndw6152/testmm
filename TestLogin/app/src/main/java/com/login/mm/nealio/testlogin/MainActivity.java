package com.login.mm.nealio.testlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String TAG = "TESTLOGIN_APP";
    CallbackManager mFBcallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFBcallbackManager = CallbackManager.Factory.create();

        initFacebookLoginButton();



    }

    private void initFacebookLoginButton() {

        // using default fb button for login
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions();
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "user_birthday"));



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



    // OnClick for the buttons
    public void onClickSignIn(View view) {

        Profile profile = Profile.getCurrentProfile();
        if (profile == null) { // not signed in

            mFBcallbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(mFBcallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.w(TAG, "FB success" +  loginResult.getAccessToken());
                    Profile profile = Profile.getCurrentProfile();

                    // Facebook Email address
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    Log.w(TAG, "Manual login response = " + response.toString());
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "email");
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

            LoginManager.getInstance().logInWithReadPermissions(
                    this,
                    Arrays.asList("user_photos", "email", "user_birthday", "public_profile")
            );

            Toast.makeText(getApplicationContext(), "Signing In", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSignOut(View view) {
        LoginManager.getInstance().logOut();
        Toast.makeText(getApplicationContext(), "Signing Out", Toast.LENGTH_SHORT).show();
        initFacebookLoginButton();
    }
}
