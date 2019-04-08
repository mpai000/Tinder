package com.example.dogfinder;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    int sessionID = getIntent().getExtras().getInt("sessionID");

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

        dogTinder = MyApplication.getDbAdapter();

        Cursor swipeData = dogTinder.getSwipe(sessionID,"right");

        while (swipeData.moveToNext()){
            Cursor dogData = dogTinder.getDogInfo(swipeData.getInt(1));
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
}
