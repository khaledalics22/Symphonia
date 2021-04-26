package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.example.symphonia.Service.ServiceController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Control the items of the Artists grid in AddArtistsActivity
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class RvGridArtistsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Final variable for the viewHolder type
     */
    private static final int ARTIST_HOLDER = 0;
    /**
     * Final variable for the viewHolder type
     */
    private static final int MORE_FOR_YOU_HOLDER = 1;
    /**
     * the list of the recommended artists to be shown to the user
     */
    private ArrayList<Artist> mArtists;

    /**
     * Array of clicked artists
     */
    private ArrayList<String> mClickedArtists;

    /**
     * an object from the interface to handle the click on grid items
     */
    private ListItemClickListener mOnClickListener;

    /**
     * Constructor to take the data and get the service controller
     *
     * @param artists          recommended artists
     * @param mOnClickListener click listener for the grid items
     */
    public RvGridArtistsAdapter(ArrayList<Artist> artists, ArrayList<String> mClickedArtists, ListItemClickListener mOnClickListener) {
        this.mArtists = artists;
        this.mClickedArtists = mClickedArtists;
        this.mOnClickListener = mOnClickListener;
    }

    /**
     * get the number of items in the artists array
     *
     * @return items number
     */
    @Override
    public int getItemCount() {
        return mArtists.size() + 1;
    }

    /**
     * Get the type of the viewHolder
     * for the current binded item
     *
     * @param position current binded item position
     * @return the type of the viewHolder for the current item
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return MORE_FOR_YOU_HOLDER;
        else
            return ARTIST_HOLDER;
    }

    /**
     * returns the right view holder for each item in recyclerview
     *
     * @param parent   the root view
     * @param viewType useful if there is more than one view holder
     * @return view holder for the artist item
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ARTIST_HOLDER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_artist_add, parent, false);
            return new ArtistViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_more_for_you_item, parent, false);
            return new MoreForYouHolder(view);
        }

    }

    /**
     * bind the view holder which has the turn to be shown
     *
     * @param holder   item view holder
     * @param position its position in recyclerview
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ARTIST_HOLDER) {
            ArtistViewHolder viewHolder = (ArtistViewHolder) holder;
            Artist artist = mArtists.get(position);
            if (artist.getImage() != -1)
                Picasso.get().load(artist.getImage()).placeholder(R.drawable.placeholder_artist).into(viewHolder.artistImage);
            else
                Picasso.get()
                        .load(artist.getImageUrl())
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_artist)
                        .into(viewHolder.artistImage);

            viewHolder.artistName.setText(artist.getArtistName());
            if (mClickedArtists.contains(artist.getId()))
                viewHolder.checkImage.setVisibility(View.VISIBLE);
            else
                viewHolder.checkImage.setVisibility(View.GONE);
        }
    }


    /**
     * clear all the data in artists array
     * and create a new one
     */
    public void clear() {
        mArtists.clear();
        mArtists = new ArrayList<>();
    }

    /**
     * adding an arraylist to the existing one
     *
     * @param artists array to be added
     */
    public void addAll(ArrayList<Artist> artists) {
        this.mArtists.addAll(artists);
    }

    /**
     * holding the data of each item and showing them
     */
    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView artistImage;
        TextView artistName;
        View checkImage;

        /**
         * prepare the views of the item
         *
         * @param itemView view of the recyclerview item
         */
        ArtistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            artistImage = itemView.findViewById(R.id.image_artist);
            artistName = itemView.findViewById(R.id.text_artist_name);
            checkImage = itemView.findViewById(R.id.image_check);
        }

        /**
         * calls the interface object of the list item click
         *
         * @param v clicked item
         */
        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(v, getAdapterPosition());
        }
    }

    /**
     * View holder to hold MoreForYou item data
     */
    public class MoreForYouHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        /**
         * prepare the views of the item
         *
         * @param itemView view of the recyclerview item
         */
        MoreForYouHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        /**
         * calls the interface object of the list item click
         *
         * @param v clicked item
         */
        @Override
        public void onClick(View v) {
            mOnClickListener.onMoreForeYouClick(getAdapterPosition());
        }
    }


    /**
     * interface to handle click for the items
     */
    public interface ListItemClickListener {
        void onListItemClick(View v, int clickedItemIndex);
        void onMoreForeYouClick(int clickedItemIndex);
    }
}