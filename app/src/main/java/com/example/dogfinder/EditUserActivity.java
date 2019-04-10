package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditUserActivity extends AppCompatActivity {

    private static final String TAG ="EditDataActivity";
    private Button mSave, mCheck;
    private EditText edit_email, edit_pass, edit_fn, edit_ln, edit_link, edit_loc;

    DatabaseHelper mDatabaseHelper;

    private String selectedfName;
    private String selectedlName;
    private String selectedemail;
    private String selectedpass;
    private String selectedlink;
    private String selectedloc;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        mSave = (Button) findViewById(R.id.save);
        mCheck = (Button) findViewById(R.id.check);
        edit_email = (EditText) findViewById(R.id.email);
        edit_pass= (EditText) findViewById(R.id.password);
        edit_fn= (EditText) findViewById(R.id.fname);
        edit_ln= (EditText) findViewById(R.id.lname);
        edit_link= (EditText) findViewById(R.id.link);
        edit_loc= (EditText) findViewById(R.id.location);

        mDatabaseHelper = MyApplication.getDbAdapter().getInstance(getApplicationContext());

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getExtras().getInt("sessionID");

        Cursor userData = mDatabaseHelper.getUserInfo(selectedID);

        if(userData.moveToNext()){
            selectedemail = userData.getString(1);
            selectedpass = userData.getString(2);
            selectedloc = userData.getString(3);
            selectedfName = userData.getString(4);
            selectedlName = userData.getString(5);
            selectedlink = userData.getString(6);
        }

        edit_fn.setText(selectedfName);
        edit_ln.setText(selectedlName);
        edit_email.setText(selectedemail);
        edit_pass.setText(selectedpass);
        edit_link.setText(selectedlink);
        edit_loc.setText(selectedloc);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item1 = edit_email.getText().toString();
                String item2 = edit_pass.getText().toString();
                String item4 = edit_fn.getText().toString();
                String item5 = edit_ln.getText().toString();
                String item6 = edit_link.getText().toString();
                String item3 = edit_loc.getText().toString();

                mDatabaseHelper.updateUser(item1, item2, item3, item4, item5, item6, selectedID);

            }
        });

        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor data = mDatabaseHelper.getUserInfo(selectedID);
                String dataName;

                if (data.moveToNext()) {
                    dataName = data.getString(4);
                    Toast.makeText(EditUserActivity.this, "VALUE IS" + String.valueOf(dataName), Toast.LENGTH_LONG).show();
//                    Intent editUser = new Intent(EditUserActivity.this, EditUserActivity.class);
//                    editUser.putExtra("sessionID", selectedID);
//                    editUser.putExtra("name", dataName);
//                    startActivity(editUser);
//                    Intent intent = new Intent(EditUserActivity.this, MainActivity.class);
//                    intent.putExtra("sessionID", selectedID);
//                    startActivity(intent);
//                    finish();
                }
            }
        });
    }








    private void toastMessage(String message){
        Toast.makeText(EditUserActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        //do nothing to disable
    }
}

