package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ownerProfile extends AppCompatActivity {

    DatabaseHelper dogTinder;

    Toolbar mtoolbar;
    TextView toolbarTitle;

    Button sendMsgBtn;

    String dogName;
    int sessionID;
    int dogID;
    int ownerID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerprofile);

        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());

        sessionID = getIntent().getExtras().getInt("sessionID");
        dogID = getIntent().getExtras().getInt("dogID");


        Cursor dogInfo = dogTinder.getDogInfo(dogID);
        if (dogInfo.moveToNext()){
            dogName = dogInfo.getString(6);
        }

        Cursor dogOwner = dogTinder.getOwner(dogID);
        if (dogOwner.moveToNext()){
            ownerID = dogOwner.getInt(0);

            setImage(ownerID);
        }


        sendMsgBtn = (Button) findViewById(R.id.send_msg);
        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ownerProfile.this, SendMessageActivity.class);
                intent.putExtra("sessionID", sessionID);
                intent.putExtra("recipientID", ownerID);
                startActivity(intent);
                finish();
                return;
            }
        });


        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        mtoolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarTitle = (TextView) findViewById(R.id.title);
        toolbarTitle.setText("Dog Owner Profile");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ownerProfile.this, History.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private void setImage(int userID) {
        TextView name = findViewById(R.id.user_name);
        TextView desc = findViewById(R.id.owner_description);
        String descText = "";

        Cursor ownerData = dogTinder.getUserInfo(userID);
        if (ownerData.moveToNext()) {
            descText = "Location: " + ownerData.getString(3) + "\n" +
                        "Owner of " + dogName + "\n";


            String ownerName = ownerData.getString(4) + " " + ownerData.getString(5);
            name.setText(ownerName);
            desc.setText(descText);


            ImageView image = findViewById(R.id.profile_image);
            Glide.with(this)
                    .asBitmap()
                    .load(ownerData.getString(6))
                    .into(image);
        }
    }


    @Override
    public void onBackPressed() {
        //do nothing to disable
    }
}