package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Playlist;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvListPlaylistsAdapter extends RecyclerView.Adapter<RvListPlaylistsAdapter.PlaylistViewHolder> {


    private ArrayList<Playlist> mPlaylists;
    /**
     * an object from the interface to handle the click on list items
     */
    private ListItemClickListener mOnClickListener;
    private ListItemLongClickListener mOnLongClickListener;

    /**
     * @param playlists
     * @param mOnClickListener click listener for the list items
     */
    public RvListPlaylistsAdapter(ArrayList<Playlist> playlists, ListItemClickListener mOnClickListener, ListItemLongClickListener mOnLongClickListener) {
        this.mPlaylists = playlists;
        this.mOnClickListener = mOnClickListener;
        this.mOnLongClickListener = mOnLongClickListener;
    }

    /**
     * @return the number of items in the recyclerview
     */
    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    /**
     * clear all the data in artists array
     * and create a new one
     */
    public void clear() {
        mPlaylists.clear();
        mPlaylists = new ArrayList<>();
    }

    /**
     * adding an arraylist to the existing one
     *
     * @param artists array to be added
     */
    public void addAll(ArrayList<Playlist> artists) {
        this.mPlaylists.addAll(artists);
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
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_playlist_list_item, parent, false);
        return new PlaylistViewHolder(view);
    }

    /**
     * bind the view holder which has the turn to be shown
     *
     * @param holder   item view holder
     * @param position its position in recyclerview
     */
    @Override
    public void onBindViewHolder(PlaylistViewHolder holder, int position) {
        holder.bind(position);
    }

    /**
     * holding the data of each item and showing them
     */
    public class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView playlistImage;
        TextView playlistName;
        TextView ownerName;

        /**
         * prepare the views of the item and set the clicklistener
         *
         * @param itemView view of the recyclerview item
         */
        PlaylistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            Utils.cancelTouchAnimation(itemView);
            playlistImage = itemView.findViewById(R.id.image_playlist);
            playlistName = itemView.findViewById(R.id.text_playlist_name);
            ownerName = itemView.findViewById(R.id.text_owner_name);
        }

        /**
         * called when the viewholder of an item is binded
         *
         * @param position item position
         */
        void bind(int position) {
            Playlist playlist = mPlaylists.get(position);

            if (playlist.getmPlaylistImage() != null)
                playlistImage.setImageBitmap(playlist.getmPlaylistImage());
            else if(playlist.getImageUrl() != null) {
                Picasso.get()
                        .load(playlist.getImageUrl())
                        .placeholder(R.drawable.placeholder_playlist)
                        .into(playlistImage);
            }
            playlistName.setText(playlist.getmPlaylistTitle());
            ownerName.setText(String.format("by %s", playlist.getOwnerName()));
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
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    /**
     * Interface to handle the long click for the items
     */
    public interface ListItemLongClickListener {
        void onListItemLongClick(int clickedItemIndex);
    }
}