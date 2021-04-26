package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Album;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Control the items of the albums list in fragment library
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class RvListAlbumsAdapter extends RecyclerView.Adapter<RvListAlbumsAdapter.AlbumViewHolder> {

    /**
     * list of user's saved albums
     */
    private ArrayList<Album> mAlbums;
    /**
     * an object from the interface to handle the click on list items
     */
    private ListItemClickListener mOnClickListener;
    private ListItemLongClickListener mOnLongClickListener;

    /**
     * @param albums  user's saved albums
     * @param mOnClickListener click listener for the list items
     */
    public RvListAlbumsAdapter(ArrayList<Album> albums, ListItemClickListener mOnClickListener, ListItemLongClickListener mOnLongClickListener) {
        this.mAlbums = albums;
        this.mOnClickListener = mOnClickListener;
        this.mOnLongClickListener = mOnLongClickListener;
    }

    /**
     * @return the number of items in the recyclerview
     */
    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    /**
     * clear all the data in artists array
     * and create a new one
     */
    public void clear(){
        mAlbums.clear();
        mAlbums = new ArrayList<>();
    }

    /**
     * adding an arraylist to the existing one
     *
     * @param artists array to be added
     */
    public void addAll(ArrayList<Album> artists){
        this.mAlbums.addAll(artists);
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
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_album_list_item, parent, false);
        return new AlbumViewHolder(view);
    }

    /**
     * bind the view holder which has the turn to be shown
     *
     * @param holder item view holder
     * @param position its position in recyclerview
     */
    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.bind(position);
    }

    /**
     * holding the data of each item and showing them
     */
    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView albumImage;
        TextView albumName;
        TextView artistName;

        /**
         * prepare the views of the item and set the clicklistener
         *
         * @param itemView view of the recyclerview item
         */
        AlbumViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            Utils.cancelTouchAnimation(itemView);
            albumImage = itemView.findViewById(R.id.image_album);
            albumName = itemView.findViewById(R.id.text_album_name);
            artistName = itemView.findViewById(R.id.text_artist_name);

        }

        /**
         * called when the viewholder of an item is binded
         *
         * @param position item position
         */
        void bind(int position) {
            Album album = mAlbums.get(position);
            if(album.getAlbumImage() != -1)
                Picasso.get()
                        .load(album.getAlbumImage())
                        .placeholder(R.drawable.placeholder_album)
                        .into(albumImage);
            else {
                Picasso.get()
                        .load(album.getImageUrl())
                        .placeholder(R.drawable.placeholder_album)
                        .into(albumImage);
            }
            albumName.setText(album.getAlbumName());
            artistName.setText(album.getAlbumArtists().get(0).getArtistName());
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

        @Override
        public boolean onLongClick(View v) {
            mOnLongClickListener.onListItemLongClick(getAdapterPosition());
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





