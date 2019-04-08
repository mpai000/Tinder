package com.example.tinder;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;

    private ListView mlistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        mlistview= (ListView) findViewById(R.id.list_view);
        mDatabaseHelper = new DatabaseHelper(this);


//        ArrayList<String> listData = new ArrayList<>();
//        Cursor data = mDatabaseHelper.showData();
//
//        if(data.getCount()==0){
//            //alert message
//            display("Error","No data found");
//            return;
//
//        }else{
//            while(data.moveToNext()){
//                listData.add(String(data));
//                ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
//                mlistview.setAdapter(adapter);
//            }
//        }

        ViewData();
    }

    private void ViewData() {
        Cursor data = mDatabaseHelper.showData();
        if(data.getCount()==0){
            //alert message
            display("Error","No data found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (data.moveToNext()){
            buffer.append("ID: "+ data.getString(0) + "\n");
            buffer.append("First Name: " + data.getString(1)+ "\n");
            buffer.append("Last Name: " + data.getString(2)+ "\n");
            buffer.append("Email: " + data.getString(3)+ "\n");
            buffer.append("Password: " + data.getString(4)+ "\n");

            display("All Users Found: ", buffer.toString());
        }
    }

    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
