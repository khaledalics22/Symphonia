package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Album;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RvTracksPreviewAdapter extends RecyclerView.Adapter<RvTracksPreviewAdapter.TrackPreviewViewHolder> {

    /**
     * list of user's saved albums
     */
    private ArrayList<Track> mTracks;
    /**
     * an object from the interface to handle the click on list items
     */
    private ListItemClickListener mOnClickListener;

    private ListItemLongClickListener mOnLongClickListener;

    private LikeClickListener mOnLikeClickListener;



    public RvTracksPreviewAdapter(ArrayList<Track> mTracks, ListItemClickListener mOnClickListener, ListItemLongClickListener mOnLongClickListener, LikeClickListener mOnLikeClickListener) {
        this.mTracks = mTracks;
        this.mOnClickListener = mOnClickListener;
        this.mOnLongClickListener = mOnLongClickListener;
        this.mOnLikeClickListener = mOnLikeClickListener;
    }

    /**
     * @return the number of items in the recyclerview
     */
    @Override
    public int getItemCount() {
        return mTracks.size();
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
    public TrackPreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_track_preview_item, parent, false);
        return new TrackPreviewViewHolder(view);
    }

    /**
     * bind the view holder which has the turn to be shown
     *
     * @param holder item view holder
     * @param position its position in recyclerview
     */
    @Override
    public void onBindViewHolder(TrackPreviewViewHolder holder, int position) {
        holder.bind(position);
    }

    /**
     * clear all the data in tracks array
     * and create a new one
     */
    public void clear(){
        mTracks.clear();
        mTracks = new ArrayList<>();
    }

    /**
     * adding an arraylist to the existing one
     *
     * @param tracks array to be added
     */
    public void addAll(ArrayList<Track> tracks){
        this.mTracks.addAll(tracks);
    }


    /**
     * holding the data of each item and showing them
     */
    public class TrackPreviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView trackImage;
        TextView trackName;
        TextView artistName;
        ImageView likeImage;

        /**
         * prepare the views of the item and set the clicklistener
         *
         * @param itemView view of the recyclerview item
         */
        TrackPreviewViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            Utils.cancelTouchAnimation(itemView);
            trackImage = itemView.findViewById(R.id.image_track);
            trackName = itemView.findViewById(R.id.text_track_name);
            artistName = itemView.findViewById(R.id.text_artist_name);
            likeImage = itemView.findViewById(R.id.image_like);
            likeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnLikeClickListener.onExtraLikeClick(getAdapterPosition());
                }
            });
        }

        /**
         * called when the viewholder of an item is binded
         *
         * @param position item position
         */
        void bind(int position) {
            Track track = mTracks.get(position);
            if (Constants.DEBUG_STATUS)
                Picasso.get().load(track.getmImageResources()).placeholder(R.drawable.placeholder_playlist).into(trackImage);
            else
                Picasso.get()
                        .load(track.getImageUrl())
                        .placeholder(R.drawable.placeholder_playlist)
                        .into(trackImage);
            trackName.setText(track.getmTitle());
            artistName.setText(track.getmArtist());
        }

        /**
         * calls the interface object of the list item click
         *
         * @param v clicked item
         */
        @Override
        public void onClick(View v) {
            mOnClickListener.onTrackPreviewClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mOnLongClickListener.onTrackPreviewLongClick(getAdapterPosition());
            return true;
        }
    }

    /**
     * Interface to handle click for the items
     */
    public interface ListItemClickListener{
        void onTrackPreviewClick(int clickedItemIndex);
    }

    public interface LikeClickListener{
        void onExtraLikeClick(int clickedItemIndex);
    }

    /**
     * Interface to handle the long click for the items
     */
    public interface ListItemLongClickListener{
        void onTrackPreviewLongClick(int clickedItemIndex);
    }

}






