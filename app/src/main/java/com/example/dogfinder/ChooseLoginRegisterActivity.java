package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChooseLoginRegisterActivity extends AppCompatActivity  {

    private Button mLogin, mRegister, mMoreInfo;
    private EditText mEmail, mPassword;
    DatabaseHelper dogTinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_register);
        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);
        mMoreInfo = (Button) findViewById(R.id.infoButton);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor checkPW = dogTinder.checkPW(mEmail.getText().toString(), mPassword.getText().toString());
                if (checkPW.getCount() == 0) {
                    Toast.makeText(ChooseLoginRegisterActivity.this, "Invalid email or password!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ChooseLoginRegisterActivity.this, "Login successful!", Toast.LENGTH_LONG).show();

                    checkPW.moveToNext();
                    int sessionID = checkPW.getInt(1);

                    Intent intent = new Intent(ChooseLoginRegisterActivity.this, MainActivity.class);
                    intent.putExtra("sessionID", sessionID);

                    startActivity(intent);
                    finish();
                    return;
                }
            }
        });


        mMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLoginRegisterActivity.this, RegisterationActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


    }
}
