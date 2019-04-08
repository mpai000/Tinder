package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button findpet, addpet, history;

    DatabaseHelper dogTinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dogTinder = MyApplication.getDbAdapter();

        findpet = (Button) findViewById(R.id.findpet);
        findpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                startActivity(intent);
            }
        });
    }



    public void openfindpetactivity(){
        int sessionID = getIntent().getExtras().getInt("sessionID");
        Intent intent = new Intent(this, findpet.class);
        intent.putExtra("sessionID", sessionID);
        startActivity(intent);
        finish();
        return;
    }

    public void openaddpetactivity(){
        int sessionID = getIntent().getExtras().getInt("sessionID");
        Intent intent = new Intent(this, addpet.class);
        intent.putExtra("sessionID", sessionID);
        startActivity(intent);
        finish();
        return;
    }
}
