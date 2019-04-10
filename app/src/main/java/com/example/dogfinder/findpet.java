package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class findpet extends AppCompatActivity {

    Spinner location, breed, maturity, gender, size;
    Button btnviewData;
    String dogName;
    Toolbar mtoolbar;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpet);

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        mtoolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarTitle=(TextView) findViewById(R.id.title);
        toolbarTitle.setText("Find Your Chew!");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sessionID = getIntent().getExtras().getInt("sessionID");
                Intent intent = new Intent(findpet.this, MainActivity.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
            }
        });

        Spinner spinnerLocation = findViewById(R.id.spinnerLocation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Location,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);

        Spinner spinnerBreed = findViewById(R.id.spinnerBreed);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.Breed,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBreed.setAdapter(adapter1);

        Spinner spinnerMaturity = findViewById(R.id.spinnerMaturity);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Maturity,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaturity.setAdapter(adapter2);

        Spinner spinnerGender = findViewById(R.id.spinnerGender);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.Gender,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter3);

        Spinner spinnerSize = findViewById(R.id.spinnerSize);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,R.array.Size,android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapter4);

        location =(Spinner) findViewById(R.id.spinnerLocation);
        breed =(Spinner) findViewById(R.id.spinnerBreed);
        maturity =(Spinner) findViewById(R.id.spinnerMaturity);
        gender = (Spinner) findViewById(R.id.spinnerGender);
        size = (Spinner) findViewById(R.id.spinnerSize);

        btnviewData = (Button) findViewById(R.id.btnViewData);
        btnviewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int sessionID = getIntent().getExtras().getInt("sessionID");

                String dogLocation = location.getSelectedItem().toString();
                String dogBreed = breed.getSelectedItem().toString();
                String dogMaturity = maturity.getSelectedItem().toString();
                String dogGender = gender.getSelectedItem().toString();
                String dogSize = size.getSelectedItem().toString();

                Intent intent = new Intent(findpet.this, SwipeScreen.class);
                intent.putExtra("sessionID", sessionID);
                intent.putExtra("dogLocation", dogLocation);
                intent.putExtra("dogBreed", dogBreed);
                intent.putExtra("dogMaturity", dogMaturity);
                intent.putExtra("dogGender", dogGender);
                intent.putExtra("dogSize", dogSize);

                startActivity(intent);
                finish();
                return;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing to disable
    }
}



