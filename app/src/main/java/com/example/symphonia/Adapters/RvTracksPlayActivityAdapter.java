package com.example.symphonia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * class tha adapt recycler view of tracks
 *
 * @author Khaled Ali
 * @version 1.0
 * @since 22-3-2020
 */
public class RvTracksPlayActivityAdapter extends RecyclerView.Adapter<RvTracksPlayActivityAdapter.Holder> {

    /**
     * context of hosting activity
     */
    private Context context;

    /**
     * tracks that would be represented
     */
    private ArrayList<Track> tracks;

    /**
     * hold position of view that has been recycled
     */
    private int prevPos = -1;

    /**
     * hold position of view that is visible  now
     */
    private int nextPos = -1;

    /**
     * this listener is called when user switch to another item
     */
    private OnItemSwitched onItemSwitched;

    /**
     * non empty constructor
     *
     * @param context context of hosting activity
     * @param tracks  tracks that should be represented in recycler view
     */
    public RvTracksPlayActivityAdapter(Context context, ArrayList<Track> tracks) {
        this.context = context;
        onItemSwitched = (OnItemSwitched) context;
        this.tracks = tracks;
    }

    /**
     * this function inflates the view the will hold each track information
     *
     * @param parent   viewGroup that hold each viewItem
     * @param viewType type of view
     * @return Holder that holds each view
     */
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rv_item_playlist_play_activty, parent, false);
        return new Holder(view);
    }

    /**
     * this function is called for binding holders
     *
     * @param holder   holder that would bind with data
     * @param position position of holder in recycler view
     */
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind();
    }

    /**
     * this function is called when a view is detached from window
     *
     * @param holder holder
     */
    @Override
    public void onViewDetachedFromWindow(@NonNull Holder holder) {
        super.onViewDetachedFromWindow(holder);

        // get pos of the detached view
        prevPos = holder.getAdapterPosition();

        // check if new view is visible and the other one that was visible isn't now
        if (nextPos - prevPos == 1 || prevPos - nextPos == 1) {
            onItemSwitched.OnItemSwitchedListener(nextPos);
        }

    }

    /**
     * this function is called when a view is attached to window
     *
     * @param holder holder
     */
    @Override
    public void onViewAttachedToWindow(@NonNull Holder holder) {
        super.onViewAttachedToWindow(holder);

        // get position of attached view to update to its data
        nextPos = holder.getAdapterPosition();

    }

    /**
     * @return number of items in recycler view
     */
    @Override
    public int getItemCount() {
        return tracks.size();
    }

    /**
     * this interface for listeners of recycler view
     */
    public interface OnItemSwitched {
        void OnItemSwitchedListener(int pos);
    }

    /**
     * this class create holder for views
     */
    public class Holder extends RecyclerView.ViewHolder {

        /**
         * track image
         */
        ImageView trackImage;


        /**
         * non empty constructor
         *
         * @param itemView view that holds item
         */
        public Holder(@NonNull View itemView) {
            super(itemView);

            trackImage = itemView.findViewById(R.id.iv_track_image_item_play_activity);
        }

        /**
         * bind data to views
         */
        private void bind() {
            Track track = tracks.get(getAdapterPosition());
            if (!Constants.DEBUG_STATUS)
                Picasso.get()
                        .load(tracks.get(getAdapterPosition()).getImageUrl())
                        .fit()
                        .centerCrop()
                        .into(trackImage);
            else
                trackImage.setImageResource(track.getmImageResources());
        }
    }
}
