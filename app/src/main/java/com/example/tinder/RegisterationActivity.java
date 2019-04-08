package com.example.tinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterationActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;


    private Button mRegister;
    private EditText mFirstName, mLastName, mEmail, mPassword;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        mDatabaseHelper = new DatabaseHelper(this);

        mRegister= (Button) findViewById(R.id.register);
        mEmail= (EditText) findViewById(R.id.email);
        mFirstName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastname);
        mPassword = (EditText) findViewById(R.id.password);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        AddData();
    }

    public void AddData(){
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = mFirstName.getText().toString();
                String lname = mLastName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                boolean insertData = mDatabaseHelper.addData(fname, lname, email, password);

                if(insertData){
                    toastMessage("Data successfully added");
                }
                else{
                    toastMessage("error adding");
                }
                Intent intent = new Intent(RegisterationActivity.this, ListDataActivity.class);
                startActivity(intent);
                finish();
            }


        });

    }

//    public void ViewData(){
//        mShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Cursor data = mDatabaseHelper.showData();
//
//                if
//            }
//        });
//    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
