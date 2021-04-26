package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Entities.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Control the items of the Artists search list in ArtistsSearchActivity
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class RvListArtistSearchAdapter extends RecyclerView.Adapter<RvListArtistSearchAdapter.ArtistViewHolder> {

    /**
     * the result list of the search
     */
    private ArrayList<Artist> mArtists;
    /**
     * an object from the interface to handle the click on list items
     */
    private ListItemClickListener mOnClickListener;

    /**
     * @param artists search result
     * @param mOnClickListener click listener for the list items
     */
    public RvListArtistSearchAdapter(ArrayList<Artist> artists, ListItemClickListener mOnClickListener) {
        this.mArtists = artists;
        this.mOnClickListener = mOnClickListener;
    }

    /**
     * @return the number of items in the recyclerview
     */
    @Override
    public int getItemCount() {
        return mArtists.size();
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_artist_search_list_item, parent, false);
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
    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView artistImage;
        TextView artistName;

        /**
         * prepare the views of the item and set the clicklistener
         *
         * @param itemView view of the recyclerview item
         */
        ArtistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            artistImage = (ImageView)itemView.findViewById(R.id.image_artist);
            artistName = (TextView) itemView.findViewById(R.id.text_artist_name);
        }

        /**
         * called when the viewholder of an item is binded
         *
         * @param position item position
         */
        void bind(int position) {
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

        /**
         * calls the interface object of the list item click
         *
         * @param v clicked item
         */
        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }

    /**
     * interface to handle click for the items
     */
    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

}