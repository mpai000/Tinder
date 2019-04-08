package com.example.tinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseLoginRegisterActivity extends AppCompatActivity {

    private Button mLogin, mRegisterHuman, mRegisterChew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_register);

        mLogin = (Button) findViewById(R.id.login);
        mRegisterHuman = (Button) findViewById(R.id.register_human);
        mRegisterChew = (Button) findViewById(R.id.register_chew);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLoginRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mRegisterHuman.setOnClickListener(new View.OnClickListener() {
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
