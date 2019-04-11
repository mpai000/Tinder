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
import android.widget.Toast;

import java.util.ArrayList;

public class ReceivedMessages extends AppCompatActivity {

    int sessionID;

    Toolbar mtoolbar;
    TextView toolbarTitle;

    public static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> senderNames = new ArrayList<>();
    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<Integer> senderIDs = new ArrayList<>();

    DatabaseHelper dogTinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivedmessages);

        sessionID = getIntent().getExtras().getInt("sessionID");

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        mtoolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarTitle=(TextView) findViewById(R.id.title);
        toolbarTitle.setText("Received Messages");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceivedMessages.this, MainActivity.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
            }
        });

        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());
        sessionID = getIntent().getExtras().getInt("sessionID");

        Cursor messageData = dogTinder.getReceviedMessages(sessionID);
        while(messageData.moveToNext()){
            int senderID = messageData.getInt(1);

            messages.add(messageData.getString(3));
            senderIDs.add(senderID);

            Cursor senderName = dogTinder.getUserInfo(senderID);
            if(senderName.moveToNext()){
                senderNames.add(senderName.getString(4)+" "+senderName.getString(5));

            }
        }


        initImageBitmaps();
    }

    private void initImageBitmaps(){

        initRecyclerView();
    }

    private void initRecyclerView(){
        Intent intent = new Intent(ReceivedMessages.this, MainActivity.class);
        intent.putExtra("sessionID", sessionID);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ReceivedMessageAdapter adapter = new ReceivedMessageAdapter(this,senderNames, messages, senderIDs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {
        //do nothing to disable
    }
}
