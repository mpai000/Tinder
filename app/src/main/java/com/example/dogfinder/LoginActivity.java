package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button mLogin;
    private EditText mEmail, mPassword;

    DatabaseHelper dogTinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());

        mLogin= (Button) findViewById(R.id.login);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Cursor checkPW = dogTinder.checkPW(mEmail.getText().toString(), mPassword.getText().toString());
                if(checkPW.getCount() == 0){
                    Toast.makeText(LoginActivity.this,"Invalid email or password!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this,"Login successful!",Toast.LENGTH_LONG).show();

                    checkPW.moveToNext();
                    int sessionID = checkPW.getInt(1);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("sessionID", sessionID);

                    startActivity(intent);
                    finish();
                    return;
                }
            }
        });
    }
}
