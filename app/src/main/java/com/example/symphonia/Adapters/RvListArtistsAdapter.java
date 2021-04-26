package com.example.symphonia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.AddArtistsActivity;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Control the items of the Artists list in fragment library
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class RvListArtistsAdapter extends RecyclerView.Adapter<RvListArtistsAdapter.ArtistViewHolder> {

    /**
     * list of the user's following artists
     */
    private ArrayList<Artist> mArtists;
    /**
     * an object from the interface to handle the click on list items
     */
    private ListItemClickListener mOnClickListener;

    private ListItemLongClickListener mOnLongClickListener;

    /**
     * constructor to get the data
     *
     * @param artists user's following artists
     * @param mOnClickListener
     */
    public RvListArtistsAdapter(ArrayList<Artist> artists, ListItemClickListener mOnClickListener, ListItemLongClickListener mOnLongClickListener) {
        this.mArtists = artists;
        this.mOnClickListener = mOnClickListener;
        this.mOnLongClickListener = mOnLongClickListener;
    }

    /**
     * @return the number of items in the recyclerview
     */
    @Override
    public int getItemCount() {
        return mArtists.size() + 1;
    }

    /**
     * returns the right view holder for each item in recyclerview
     *
     * @param parent the root view
     * @param viewType useful if there is more than one view holder
     * @return view holder for the artist item
     */
    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_artist_list_item, parent, false);
        return new ArtistViewHolder(view);
    }

    /**
     * bind the view holder which has the turn to be shown
     *
     * @param holder item view holder
     * @param position its position in recyclerview
     */
    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        holder.bind(position);
    }

    /**
     * clear all the data in artists array
     * and create a new one
     */
    public void clear(){
        mArtists.clear();
        mArtists = new ArrayList<>();
    }

    /**
     * adding an arraylist to the existing one
     *
     * @param artists array to be added
     */
    public void addAll(ArrayList<Artist> artists){
        this.mArtists.addAll(artists);
    }

    /**
     * holding the data of each item and showing them
     */
    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView artistImage;
        TextView artistName;

        /**
         * prepare the views of the item
         *
         * @param itemView view of the recyclerview item
         */
        ArtistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            Utils.cancelTouchAnimation(itemView);
            artistImage = (ImageView)itemView.findViewById(R.id.image_artist);
            artistName = (TextView) itemView.findViewById(R.id.text_artist_name);
        }

        /**
         * called when the viewholder of an item is binded
         *
         * @param position item position
         */
        void bind(int position) {
            if(position == mArtists.size()) {
                artistImage.setImageResource(R.drawable.add_image);
                artistName.setText(R.string.add_artists);
            }
            else{
                Artist artist = mArtists.get(position);
                if (artist.getImage() != -1)
                    Picasso.get().load(artist.getImage()).placeholder(R.drawable.placeholder_artist).into(artistImage);
                else
                    Picasso.get()
                            .load(artist.getImageUrl())
                            .placeholder(R.drawable.placeholder_artist)
                            .into(artistImage);

                artistName.setText(artist.getArtistName());
            }


        }

        /**
         * separate the click of the last item
         *
         * @param v clicked item
         */
        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View v) {
            if(getAdapterPosition() == mArtists.size()){

            }
            else{
                mOnLongClickListener.onListItemLongClick(getAdapterPosition());
            }
            return true;
        }
    }

    /**
     * Interface to handle click for the items
     */
    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    /**
     * Interface to handle the long click for the items
     */
    public interface ListItemLongClickListener{
        void onListItemLongClick(int clickedItemIndex);
    }

}