package com.login.mm.nealio.testlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {


    CallbackManager mFBcallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFBcallbackManager = CallbackManager.Factory.create();

        initFacebookLoginButton();



    }

    private void initFacebookLoginButton() {
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions();
        loginButton.setReadPermissions("email", "public_profile");

        // Callback registration
        loginButton.registerCallback(mFBcallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("regSuccess", "FB success" +  loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("regCancel", "FB error");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("regCB", "FB error" + exception.toString());
                    }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivity", "running the on activity");
        mFBcallbackManager.onActivityResult(requestCode, resultCode, data); // sending information to fb sdk
    }



    // OnClick for the buttons
    public void onClickSignIn(View view) {
    }

    public void onClickSignUp(View view) {
    }
}
