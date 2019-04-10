package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterationActivity extends AppCompatActivity {

    private Button mRegister;
    private EditText mFirstName, mLastName, mEmail, mPassword;
    private Spinner mLocation;

    Toolbar mtoolbar;
    TextView toolbarTitle;
    // In any activity just pass the context and use the singleton method
    DatabaseHelper dogTinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());

        mRegister= (Button) findViewById(R.id.register);
        mEmail= (EditText) findViewById(R.id.email);
        mFirstName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastname);
        mPassword = (EditText) findViewById(R.id.password);
        mLocation = (Spinner) findViewById(R.id.spinnerLocation);


        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        mtoolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarTitle=(TextView) findViewById(R.id.title);
        toolbarTitle.setText("");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterationActivity.this, ChooseLoginRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Spinner spinnerLocation = findViewById(R.id.spinnerLocation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Location,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);

        Toast.makeText(RegisterationActivity.this,"1",Toast.LENGTH_LONG).show();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor checkExist = dogTinder.checkExist(mEmail.getText().toString());
                String temail= mEmail.getText().toString();
                String tpass = mPassword.getText().toString();
                String tfname = mFirstName.getText().toString();
                String tlname= mLastName.getText().toString();


                if(temail.matches("")| tpass.matches("")|| tfname.matches("")|| tlname.matches(""))
                {
                    Toast.makeText(RegisterationActivity.this,"All Fields must be filled to register",Toast.LENGTH_LONG).show();

                }
                else{
                    if(checkExist.getCount() == 0){
                        String userLocation = mLocation.getSelectedItem().toString();

                        boolean insertData = dogTinder.addUserData(mEmail.getText().toString(), mPassword.getText().toString(), userLocation, mFirstName.getText().toString(), mLastName.getText().toString());

                        if(insertData == true){
                            Toast.makeText(RegisterationActivity.this,"Data succesfully inserted!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterationActivity.this, ChooseLoginRegisterActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            Toast.makeText(RegisterationActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();

                        }
                    }

                }


            }
        });
    }
}
