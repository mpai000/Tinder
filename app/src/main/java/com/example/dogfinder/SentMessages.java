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

public class SentMessages extends AppCompatActivity {

    int sessionID;

    Toolbar mtoolbar;
    TextView toolbarTitle;

    public static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> recipients = new ArrayList<>();
    private ArrayList<String> messages = new ArrayList<>();


    int dogID, userID;

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
        toolbarTitle.setText("Sent Messages");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SentMessages.this, MainActivity.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
            }
        });

        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());
        sessionID = getIntent().getExtras().getInt("sessionID");

        Cursor messageData = dogTinder.getSentMessages(sessionID);
        while(messageData.moveToNext()){
            messages.add(messageData.getString(3));

            Cursor recipientName = dogTinder.getUserInfo(Integer.parseInt(messageData.getString(1)));
            if(recipientName.moveToNext()){
                recipients.add(recipientName.getString(4)+" "+recipientName.getString(5));
            }
        }


        initImageBitmaps();
    }

    private void initImageBitmaps(){

        initRecyclerView();
    }

    private void initRecyclerView(){
        Intent intent = new Intent(SentMessages.this, MainActivity.class);
        intent.putExtra("sessionID", sessionID);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ReceivedMessageAdapter adapter = new ReceivedMessageAdapter(this,recipients, messages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {
        //do nothing to disable
    }
}
