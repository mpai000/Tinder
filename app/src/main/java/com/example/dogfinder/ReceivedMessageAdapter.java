package com.example.dogfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReceivedMessageAdapter extends RecyclerView.Adapter<ReceivedMessageAdapter.ViewHolder> {


    private ArrayList<String> senderNames;
    private ArrayList<String> messages;
    private ArrayList<Integer> senderIDs;

    private Context mContext;

    int sessionID;

    public ReceivedMessageAdapter(Context mContext, ArrayList<String> senderNames, ArrayList<String> messages,  ArrayList<Integer> senderIDs) {
        this.mContext = mContext;
        this.senderNames = senderNames;
        this.messages = messages;
        this.senderIDs = senderIDs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return senderNames.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Glide.with(mContext)
                .asBitmap()
                .load("https://i.imgur.com/GBpeyEN.png")
                .into(holder.image);

        holder.imageName.setText("FROM: " + senderNames.get(position) + "\n"+ messages.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ((Activity) mContext).getIntent();
                sessionID = intent.getExtras().getInt("sessionID");

                Intent passIntent = new Intent(mContext, SendMessageActivity.class);
                passIntent.putExtra("sessionID", sessionID);
                passIntent.putExtra("recipientID", senderIDs.get(position));

                mContext.startActivity(passIntent);
                ((Activity) mContext).finish();


                Toast.makeText(mContext, "Send message to " + senderNames.get(position), Toast.LENGTH_SHORT).show();

                return;
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
