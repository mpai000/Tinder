package com.example.dogfinder;

import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;



public class SwipeScreen extends AppCompatActivity {

    int sessionID;

    private ArrayList<DogData> dogs;
  //  private ArrayAdapter<DogData> arrayAdapter;

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private int i;

    DogData toBeRemoved;
    DatabaseHelper dogTinder;

    Toolbar mtoolbar;
    TextView toolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);


        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        mtoolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarTitle=(TextView) findViewById(R.id.title);
        toolbarTitle.setText("");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sessionID = getIntent().getExtras().getInt("sessionID");
                Intent intent = new Intent(SwipeScreen.this, MainActivity.class);
                intent.putExtra("sessionID", sessionID);
                startActivity(intent);
                finish();
            }
        });

        sessionID = getIntent().getExtras().getInt("sessionID");
        dogTinder = MyApplication.getDbAdapter().getInstance(getApplicationContext());



        dogs = new ArrayList<>();
        searchData();

        //arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.description, dogs );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        myAppAdapter = new MyAppAdapter(dogs, SwipeScreen.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                toBeRemoved = dogs.get(0);
                dogs.remove(0);
                //arrayAdapter.notifyDataSetChanged();
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                String id = toBeRemoved.getDogID();

                boolean insertData = dogTinder.swipe(sessionID,Integer.parseInt(id),"left");
                if(insertData == true){
                    Toast.makeText(SwipeScreen.this, id +" left", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SwipeScreen.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                String id = toBeRemoved.getDogID();

                boolean insertData = dogTinder.swipe(sessionID,Integer.parseInt(id),"right");
                if(insertData == true){
                    Toast.makeText(SwipeScreen.this, id +" right", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SwipeScreen.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
               // dogs.add("XML ".concat(String.valueOf(i)));
                //arrayAdapter.notifyDataSetChanged();
                myAppAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(SwipeScreen.this, "click", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void searchData(){

        String dogLocation = getIntent().getExtras().getString("dogLocation");
        String dogBreed = getIntent().getExtras().getString("dogBreed");
        String dogMaturity = getIntent().getExtras().getString("dogMaturity");
        String dogGender = getIntent().getExtras().getString("dogGender");
        String dogSize = getIntent().getExtras().getString("dogSize");

        Cursor data = dogTinder.searchDogData(dogLocation,dogBreed,dogMaturity,dogGender,dogSize);
        if(data.getCount()==0){
            display("Error","No data found");
            return;
        }
        StringBuffer buffer = new StringBuffer();


        while (data.moveToNext()){

            buffer.append("ID: " +data.getString(0)+ "\n");
            buffer.append("Name: " +data.getString(6)+ "\n");
            buffer.append("Location: " +data.getString(1)+ "\n");
            buffer.append("Breed: " +data.getString(2)+ "\n");
            buffer.append("Maturity: " +data.getString(3)+ "\n");
            buffer.append("Gender: " +data.getString(4)+ "\n");
            buffer.append("Size: " +data.getString(5)+ "\n");

            dogs.add(new DogData(data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6),
                    data.getString(7)));

        }

        display("All Chews Found: ", buffer.toString());
    }

    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public static class ViewHolder {
        public TextView DataText;
        public ImageView cardImage;
    }

    public class MyAppAdapter extends BaseAdapter {

        public ArrayList<DogData> list;
        public Context context;

        private MyAppAdapter(ArrayList<DogData> apps, Context context) {
            this.list = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.description);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText("ID: " + list.get(position).getDogID() + "\n" +
                                        "Name: " + list.get(position).getDogName() + "\n" +
                                        "Location: " + list.get(position).getDogLocation() + "\n" +
                                        "Breed: " + list.get(position).getDogBreed() + "\n" +
                                        "Maturity: " + list.get(position).getDogMaturity() + "\n" +
                                        "Gender: " + list.get(position).getDogSize() + "\n" +
                                        "Size: " + list.get(position).getDogGender() + "\n");


            Glide.with(SwipeScreen.this).load(list.get(position).getDogPictureLink()).into(viewHolder.cardImage);

            return rowView;
        }
    }

    public void goBack(){
        Intent intent = new Intent(SwipeScreen.this, MainActivity.class);
        intent.putExtra("sessionID", sessionID);
        startActivity(intent);
        finish();
        return;
    }
}
