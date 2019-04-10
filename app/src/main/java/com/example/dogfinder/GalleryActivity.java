package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";

    DatabaseHelper dogTinder;

    Toolbar mtoolbar;
    TextView toolbarTitle;

    Button ownerProfile;

    int sessionID;
    int dogID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());

        sessionID = getIntent().getExtras().getInt("sessionID");
        dogID = getIntent().getExtras().getInt("dogID");

        setImage(dogID, sessionID);

        ownerProfile= (Button) findViewById(R.id.view_owner);
        ownerProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GalleryActivity.this, ownerProfile.class);
                    intent.putExtra("sessionID", sessionID);
                    intent.putExtra("dogID", dogID);
                    startActivity(intent);
                    finish();
                    return;
                }
            });


        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        mtoolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarTitle=(TextView) findViewById(R.id.title);
        toolbarTitle.setText("To Be or Not to Be");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GalleryActivity.this, History.class);
                    intent.putExtra("sessionID", sessionID);
                    startActivity(intent);
                    finish();
                    return;
            }
        });
    }

    private void setImage(int dogID, int sessionID){
        TextView name = findViewById(R.id.image_description);
        TextView desc = findViewById(R.id.dog_description);
        String descText = "";

        Cursor dogData = dogTinder.getDogInfo(dogID);
        if (dogData.moveToNext()){
            descText = ("Location: " + dogData.getString(1)+ "\n" +
                    "Breed: " + dogData.getString(2) + "\n" +
                    "Maturity: " + dogData.getString(3) + "\n" +
                    "Gender: " + dogData.getString(4) + "\n" +
                    "Size: " + dogData.getString(5) + "\n");


            name.setText(dogData.getString(6));
            desc.setText(descText);


            ImageView image = findViewById(R.id.image);
            Glide.with(this)
                    .asBitmap()
                    .load(dogData.getString(7))
                    .into(image);
        }
    }



    @Override
    public void onBackPressed() {
        //do nothing to disable
    }
}