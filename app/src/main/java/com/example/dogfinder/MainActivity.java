package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int sessionID;
    Button findpet, addpet, history;
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
        toolbarTitle.setText("");
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
}
