package com.login.mm.nealio.testlogin;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.login.mm.nealio.testlogin.user.Job;

import java.util.ArrayList;

/**
 * Created by ndw6152 on 5/14/2017.
 */

public class JobFormActivity extends AppCompatActivity {
    private String TAG = "New Job Form";
    private final int CHOOSING_IMAGE_FROM_GALLERY = 1000;

    private Job mJob;



    private ImageView createImageViewsWhenChosen(Uri imageUri) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(layoutParams);
        imgView.setImageURI(null);
        imgView.setImageURI(imageUri);
        imgView.setClickable(true);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), v.getWindowId()+"", Toast.LENGTH_SHORT).show();
            }
        });
        return imgView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case CHOOSING_IMAGE_FROM_GALLERY:
                    // When an Image is picked
                    // Get the Image from data
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    ArrayList<String> imagesEncodedList = new ArrayList<String>();

                    LinearLayout linearLayoutImages = (LinearLayout)findViewById(R.id.ll_images_from_gallery);

                    if(data.getData()!= null){
                        Uri imageUri = data.getData();
                        ImageView imgView = createImageViewsWhenChosen(imageUri);
                        linearLayoutImages.addView(imgView);

                    }
                    else {
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();
                            ArrayList<Uri> mArrayUri = new ArrayList<Uri>();

                            for (int i = 0; i < mClipData.getItemCount(); i++) {
                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri imageUri = item.getUri();

                                ImageView imgView = createImageViewsWhenChosen(imageUri);
                                linearLayoutImages.addView(imgView);

                            }
                            Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                        }
                    }
            }
        }
    }

    public void ChoosePicturesFromGalleryOnClick(View view) {
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imageIntent, "Select Picture"), CHOOSING_IMAGE_FROM_GALLERY);
        LinearLayout imageLayout = (LinearLayout)findViewById(R.id.ll_images_from_gallery);
        if((imageLayout).getChildCount() > 0)
            imageLayout.removeAllViews();

    }

    public void submitJobOnClick(View view) {


    }

    public void cancelJobOnClick(View view) {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job_form);

        mJob = new Job();

    }



}
