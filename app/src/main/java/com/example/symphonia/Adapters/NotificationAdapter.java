package com.example.symphonia.Adapters;

import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Management.Notifications.NotificationItem;
import com.example.symphonia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Activity that handles Adapter of Notification history page
 *
 * @author Hossam Alaa
 * @since 10-06-2020
 * @version 1.0
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> {

    /**
     * holds list of objects of notifications
     */
    private ArrayList<NotificationItem> mNotifications;
    /**
     * holds instance from item click listener
     */
    private OnItemClickListener mListener;
    /**
     * holds context requires this adapter
     */
    private Context mContext;

    /**
     * Constructor of Notifications Adapter
     * @param mItems holds list of items of notification
     * @param mContext holds context of page
     */
    public NotificationAdapter(ArrayList<NotificationItem> mItems, Context mContext) {
        this.mNotifications = mItems;
        this.mContext = mContext;
    }

    /**
     * interface that handles clicking on items
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    /**
     * handles item clicking
     * @param listener object of listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * function that handles initializing view holders
     * @param parent holds root view
     * @param viewType view type of the item at position in recycler view
     * @return holder of item in recycler view
     */

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //sets layout for each item in recycler
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_notification, parent, false);

        return new Holder(view, mListener);
    }

    /**
     * Updates the contents with the item at the given position
     * @param holder represent the contents of the item at the given position
     * @param position position of current item
     */
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    /**
     * holds total number of items in adapter
     * @return return size of array of item
     */
    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    /**
     * Handles an item view and metadata about its place within the RecyclerView.
     */
    public class Holder extends RecyclerView.ViewHolder {

        /**
         * holds view of image of notification
         */
        CircleImageView profile_img;
        /**
         * holds view of title text of notification
         */
        TextView text1;
        /**
         * holds view of body text of notification
         */
        TextView text2;
        /**
         * holds view of date of notification
         */
        TextView text3;

        /**
         * Constructor for Holder Class to initialize parameters
         * @param itemView view that contains current item
         */
        public Holder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            //gets text views that holds texts and image
            profile_img = itemView.findViewById(R.id.profile_img);
            text1 = itemView.findViewById(R.id.main_text);
            text2 = itemView.findViewById(R.id.description_text);
            text3 = itemView.findViewById(R.id.date_text);

            /**
             * sets listener to items
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        //gets position of clicked item
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        /**
         * Fill each item in the view with its data
         * @param position index of item in recycler view
         */
        public void bind(int position) {

            //gets each item from list, then sets it in text, image views
            NotificationItem notificationItem = mNotifications.get(position);

            //checks if image is empty, then sets default
            if(notificationItem.getImageUrl().equals(""))
                profile_img.setImageResource(notificationItem.getImageResource());
            else {
                //load image url using picasso library
                Picasso.get()
                        .load(notificationItem.getImageUrl())
                        .placeholder(R.drawable.placeholder_user)
                        .into(profile_img);
            }

            //set each text to its correspond data
            text1.setText(notificationItem.getText1());
            text2.setText(notificationItem.getText2());
            text3.setText(notificationItem.getText3());
        }

    }

}

