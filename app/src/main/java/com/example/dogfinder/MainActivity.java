package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int sessionID;
    Button findpet, addpet, history, mprofile, sendmsg, receivemsg;
    DatabaseHelper dogTinder;
    Toolbar mtoolbar;
    TextView toolbarTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        mtoolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarTitle=(TextView) findViewById(R.id.title);
        toolbarTitle.setText("Menu");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseLoginRegisterActivity.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
            }
        });


        sessionID = getIntent().getExtras().getInt("sessionID");
        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());

        findpet = (Button) findViewById(R.id.findpet);
        findpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfindpetactivity();
            }
        });

        addpet = (Button) findViewById(R.id.addpet);
        addpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openaddpetactivity();
            }
        });

        history = (Button) findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, History.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
                return;
            }
        });


        mprofile = (Button) findViewById(R.id.profile);
        mprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = dogTinder.getUserInfo(sessionID);
                String dataName;
                String dataLname;
                String dataEmail;
                String dataPassword;
                String dataLocation;
                String datalink;

                if(data.moveToNext()){
                    dataEmail=data.getString(1);
                    dataPassword=data.getString(2);
                    dataLocation= data.getString(3);
                    dataLname=data.getString(5);
                    dataName= data.getString(4);
                    datalink=data.getString(6);

                    //Toast.makeText(MainActivity.this,"VALUE IS" + dataName,Toast.LENGTH_LONG).show();
                    Intent editUser = new Intent(MainActivity.this, EditUserActivity.class);
                    editUser.putExtra("sessionID", sessionID);
                    startActivity(editUser);
                }
                else{
                    Toast.makeText(MainActivity.this,"no id",Toast.LENGTH_LONG).show();
                }


            }
        });

        sendmsg = (Button) findViewById(R.id.sent_msg);
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SentMessages.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
                return;
            }
        });

        receivemsg = (Button) findViewById(R.id.recev_msg);
        receivemsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReceivedMessages.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
                return;
            }
        });
}


    public void openfindpetactivity(){
        Intent intent = new Intent(this, findpet.class);
        intent.putExtra("sessionID", sessionID);
        startActivity(intent);
        finish();
        return;
    }

    public void openaddpetactivity(){
        Intent intent = new Intent(this, addpet.class);
        intent.putExtra("sessionID", sessionID);
        startActivity(intent);
        finish();
        return;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater mMenuInflater = getMenuInflater();
//        mMenuInflater.inflate(R.menu.my_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public void onBackPressed() {
        //do nothing to disable
    }
}
