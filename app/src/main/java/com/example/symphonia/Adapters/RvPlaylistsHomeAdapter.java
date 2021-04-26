package com.example.symphonia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
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
public class RvPlaylistsHomeAdapter extends RecyclerView.Adapter<RvPlaylistsHomeAdapter.Holder> {

    /**
     * this instance of interface is called when user click on an item
     */
    private OnPlaylistClicked onPlaylistClicked;
    /**
     * playlist that would be represented
     */
    private ArrayList<com.example.symphonia.Entities.Context> mPlaylists;
    /**
     * context of hosting activity
     */
    private Context context;

    /**
     * non empty constructor
     *
     * @param context context of hosting activity
     * @param list    arrayList of playlists that would be represented
     */
    public RvPlaylistsHomeAdapter(Context context, ArrayList<com.example.symphonia.Entities.Context> list) {
        this.mPlaylists = list;
        this.context = context;
        onPlaylistClicked = (OnPlaylistClicked) context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_playlist_home, parent, false);
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
        holder.bind(position);
    }

    /**
     * @return number of items in recycler view
     */
    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    /**
     * this interface for listeners of recycler view
     */
    public interface OnPlaylistClicked {
        void OnPlaylistClickedListener(com.example.symphonia.Entities.Context playlist);
    }

    /**
     * this class create holder for views
     */
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivPlaylistImage;
        private TextView tvPlaylistTitle;
        private TextView tvPlaylistDescription;

        /**
         * non empty constructor
         *
         * @param itemView view that holds item
         */
        public Holder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            ivPlaylistImage = itemView.findViewById(R.id.iv_playlist_image_home);
            tvPlaylistTitle = itemView.findViewById(R.id.tv_playlist_title_home);
            tvPlaylistDescription = itemView.findViewById(R.id.tv_playlist_description_home);

        }

        /**
         * bind data to views
         */
        private void bind(int pos) {
            if (mPlaylists.get(pos).getmContextImage() == null) {
                ivPlaylistImage.setImageResource(R.drawable.no_image);
            }

            if (!Constants.DEBUG_STATUS)
                Picasso.get()
                        .load(mPlaylists.get(pos).getImageUrl())
                        .fit()
                        .centerCrop()
                        .into(ivPlaylistImage);
            else {
                ivPlaylistImage.setImageBitmap(mPlaylists.get(pos).getmContextImage());
            }

            if (mPlaylists.get(pos).getmContextTitle() == null) {
                tvPlaylistTitle.setVisibility(View.GONE);
            }

            tvPlaylistTitle.setText(mPlaylists.get(pos).getmContextTitle());
            if (mPlaylists.get(pos).getmContextDescription() == null) {
                tvPlaylistDescription.setVisibility(View.GONE);
            } else {
                tvPlaylistDescription.setText(mPlaylists.get(pos).getmContextDescription());
            }
        }

        /**
         * this function is called when user click on item
         *z
         * @param view takes view that is clicked
         */
        @Override
        public void onClick(View view) {
            onPlaylistClicked.OnPlaylistClickedListener(
                    mPlaylists.get(getAdapterPosition()));
        }
    }
}
