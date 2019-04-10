package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    int sessionID;
    Toolbar mtoolbar;
    TextView toolbarTitle;

    public static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    int dogID, userID;

    DatabaseHelper dogTinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        mtoolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarTitle=(TextView) findViewById(R.id.title);
        toolbarTitle.setText("Add A Chew For Adoption");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sessionID = getIntent().getExtras().getInt("sessionID");
                Intent intent = new Intent(History.this, MainActivity.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
            }
        });


        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());
        sessionID = getIntent().getExtras().getInt("sessionID");

        Cursor swipeData = dogTinder.getSwipe(sessionID,"right");

        while (swipeData.moveToNext()){
            Cursor dogData = dogTinder.getDogInfo(swipeData.getInt(1));
            dogData.moveToNext();
            mNames.add(dogData.getString(6));
            mImageUrls.add(dogData.getString(7));
        }

        initImageBitmaps();
    }

    private void initImageBitmaps(){

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,mNames,mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {
        //do nothing to disable
    }
}
