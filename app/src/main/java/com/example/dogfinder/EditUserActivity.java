package com.example.dogfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditUserActivity extends AppCompatActivity {

    private static final String TAG ="EditDataActivity";
    private Button mSave, mDelete;
    private EditText edit_item;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        mSave= (Button) findViewById(R.id.save);
        mDelete = (Button) findViewById(R.id.delete);
        edit_item = (EditText) findViewById(R.id.text);
        mDatabaseHelper = MyApplication.getDbAdapter().getInstance(getApplicationContext());

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedName = receivedIntent.getStringExtra("name");

        edit_item.setText(selectedName);




    }
}
