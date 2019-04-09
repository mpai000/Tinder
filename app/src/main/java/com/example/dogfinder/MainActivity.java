package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    int sessionID;

    Button findpet, addpet, history;

    DatabaseHelper dogTinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
