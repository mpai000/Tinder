package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessageActivity extends AppCompatActivity {

    private TextView recipientName;
    private Button sendMsgBtn;
    private EditText messageString;

    DatabaseHelper dogTinder;

    Toolbar mtoolbar;
    TextView toolbarTitle;

    int recipientID;
    int userID;
    String nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmessage);

        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());

        recipientID = getIntent().getExtras().getInt("recipientID");
        userID = getIntent().getExtras().getInt("sessionID");

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        mtoolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarTitle=(TextView) findViewById(R.id.title);
        toolbarTitle.setText("Send Message");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendMessageActivity.this, MainActivity.class);
                intent.putExtra("sessionID", userID);
                startActivity(intent);
                finish();
            }
        });


        sendMsgBtn = (Button) findViewById(R.id.send);
        messageString = (EditText) findViewById(R.id.message);
        recipientName = (TextView) findViewById(R.id.rep_name);

        Cursor repName = dogTinder.getUserInfo(recipientID);
        if(repName.moveToNext()){
            nameString = repName.getString(4) + " " + repName.getString(5);
            recipientName.setText("TO: "+ nameString);
        }


        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sendmsg = dogTinder.sendMessage(userID, recipientID, messageString.getText().toString());
                if(sendmsg){
                    Toast.makeText(SendMessageActivity.this,"Message sent to " + nameString,Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SendMessageActivity.this, MainActivity.class);
                    intent.putExtra("sessionID", userID);
                    startActivity(intent);
                    finish();

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing to disable
    }
}
