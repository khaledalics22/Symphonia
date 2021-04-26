package com.example.symphonia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Track;
import com.example.symphonia.R;

import java.util.ArrayList;

/**
 * class tha adapt recycler view of tracks
 *
 * @author Khaled Ali
 * @version 1.0
 * @since 22-3-2020
 */
public class RvBarAdapter extends RecyclerView.Adapter<RvBarAdapter.BarHolder> {

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
    private int prevPos;

    /**
     * hold position of view that is visible  now
     */
    private int nextPos;

    /**
     * instance of interface is called when user switch to another item
     */
    private ItemInterface itemInterface;

    /**
     * non empty constructor
     *
     * @param context context of hosting activity
     * @param tracks  tracks that should be represented in recycler view
     */
    public RvBarAdapter(Context context, ArrayList<Track> tracks) {
        this.context = context;
        this.tracks = tracks;
        itemInterface = (ItemInterface) context;
    }

    /**
     * getter for tracks
     *
     * @return tracks in adapter
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }

    /**
     * setter for tracks
     *
     * @param tracks tracks should be presented
     */
    public void setTracks(ArrayList<Track> tracks) {
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
    public BarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_bar, parent, false);
        return new BarHolder(view);
    }

    /**
     * this function is called for binding holders
     *
     * @param holder   holder that would bind with data
     * @param position position of holder in recycler view
     */
    @Override
    public void onBindViewHolder(@NonNull BarHolder holder, int position) {
        holder.bind();
    }

    /**
     * @return number of items in recycler view
     */
    @Override
    public int getItemCount() {
        return (tracks!=null)?tracks.size():0;
    }

    /**
     * this function is called when a view is detached from window
     *
     * @param holder holder
     */
    @Override
    public void onViewDetachedFromWindow(@NonNull BarHolder holder) {
        super.onViewDetachedFromWindow(holder);

        // get pos of the detached view
        prevPos = holder.getAdapterPosition();

        // check if new view is visible and the other one that was visible isn't now
        if (nextPos - prevPos == 1 || prevPos - nextPos == 1) {
            itemInterface.OnItemSwitchedListener(nextPos);
        }
    }

    /**
     * this function is called when a view is attached to window
     *
     * @param holder holder
     */
    @Override
    public void onViewAttachedToWindow(@NonNull BarHolder holder) {
        super.onViewAttachedToWindow(holder);

        // get position of attached view to update to its data
        nextPos = holder.getAdapterPosition();

    }

    /**
     * this interface for listeners of recycler view
     */
    public interface ItemInterface {
        void OnItemSwitchedListener(int pos);

        void OnItemClickedListener(ArrayList<Track> tracks, int adapterPosition);
    }


    /**
     * this class create holder for views
     */
    public class BarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trackDetails;

        /**
         * non empty constructor
         *
         * @param itemView view that holds item
         */
        public BarHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            trackDetails = itemView.findViewById(R.id.tv_track_details_bar);
        }

        /**
         * this function is called when a user click on an item
         *
         * @param view view that is clicked
         */
        @Override
        public void onClick(View view) {
            itemInterface.OnItemClickedListener(tracks, getAdapterPosition());
        }

        /**
         * bind data to views
         */
        public void bind() {
            trackDetails.setSelected(true);
            trackDetails.setText(tracks.get(getAdapterPosition()).getmTitle()
                    .concat(" * ")
                    .concat((tracks.get(getAdapterPosition()).getmArtist() != null) ? tracks.get(getAdapterPosition()).getmArtist() : ""));
        }
    }
}
