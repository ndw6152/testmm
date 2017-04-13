package com.login.mm.nealio.testlogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by ndw6152 on 4/12/2017.
 *
 */

public class ProfileEditActivity extends AppCompatActivity {
    private String TAG = "ProfileScreen";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_profile_edit_actions, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        showToast("CLOSING");
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId())
        {
            case R.id.menu_save_profile:
                showToast("Saving");
                return true;
            case R.id.menu_discard_changes:
                showToast("Discard profile");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.com_facebook_tooltip_black_xout);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
