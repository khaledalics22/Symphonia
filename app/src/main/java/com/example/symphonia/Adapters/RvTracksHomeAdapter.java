package com.example.symphonia.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Track;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * class tha adapt recycler view of tracks
 *
 * @author Khaled Ali
 * @version 1.0
 * @since 22-3-2020
 */
public class RvTracksHomeAdapter extends RecyclerView.Adapter<RvTracksHomeAdapter.Holder> {

    /**
     * position of previous clicked item
     */
    private int prev = -1;
    /**
     * tracks that would be represented
     */
    private ArrayList<Track> mTracks;

    /**
     * context of hosting activity
     */
    private Context context;

    /**
     * this listener is called when user click on an item
     */
    private OnTrackClicked onTrackClicked;

    public void changeTracks(ArrayList<Track> tracks) {
        mTracks = tracks;
    }

    /**
     * non empty constructor
     *
     * @param context context of hosting activity
     * @param mTracks tracks that should be represented in recycler view
     */
    public RvTracksHomeAdapter(Context context, ArrayList<Track> mTracks) {
        this.mTracks = mTracks;
        this.context = context;
        onTrackClicked = (OnTrackClicked) context;
    }

    public void selectPlaying(String id) {
        for (int i = 0; i < mTracks.size(); i++) {
            if (mTracks.get(i).getId().matches(Utils.currTrack.getId())) {
                mTracks.get(i).isPlaying(true);
            } else mTracks.get(i).isPlaying(false);
        }
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
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_track, parent, false);
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
        return mTracks.size();
    }

    public void selectHidden(int pos, boolean isHidden) {
        mTracks.get(pos).setHidden(isHidden);
    }

    public void selectLiked(String id, boolean isLiked) {
        int pos = Utils.getPosInPlaying(id);
        mTracks.get(pos).setLiked(isLiked);
    }

    /**
     * this interface for listeners of recycler view
     */
    public interface OnTrackClicked {
        void OnTrackClickedListener(ArrayList<Track> tracks, int pos, int prev);

        void showTrackSettingFragment(Track track);

        void OnLikeClickedListener(boolean selected, int pos);

    }

    /**
     * this class create holder for views
     */
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivLike;
        private ImageView ivHide;
        private ImageView ivSettings;
        private ImageView ivTrackImage;
        private TextView tvTrackTitle;
        private TextView tvTrackDescription;

        /**
         * non empty constructor
         *
         * @param itemView view that holds item
         */
        public Holder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivHide = itemView.findViewById(R.id.iv_hide_track_item);
            ivLike = itemView.findViewById(R.id.iv_like_track_item);
            ivSettings = itemView.findViewById(R.id.iv_track_settings_item);
            ivTrackImage = itemView.findViewById(R.id.iv_track_image_item);
            tvTrackTitle = itemView.findViewById(R.id.tv_track_title_item);
            tvTrackDescription = itemView.findViewById(R.id.tv_track_description_item);
            setListeners();


        }

        /**
         * sets listener to views in Holder
         */
        private void setListeners() {
            ivHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.playingContext != null && Utils.displayedContext.getId().matches(Utils.playingContext.getId())) {
                        if (ivHide.isSelected()) {
                            ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
                            Toast.makeText(context, R.string.return_to_playing_playlist, Toast.LENGTH_SHORT).show();
                            Utils.playingContext.getTracks().get(getAdapterPosition()).setHidden(false);
                            if (!Utils.playingContext.getTracks().get(getAdapterPosition()).getId().matches(Utils.currTrack.getId()))
                                tvTrackTitle.setTextColor(context.getResources().getColor(R.color.white));
                            tvTrackDescription.setTextColor(context.getResources().getColor(R.color.white));
                            ivHide.setSelected(false);
                        } else {
                            Utils.playingContext.getTracks().get(getAdapterPosition()).setHidden(true);
                            if (!Utils.playingContext.getTracks().get(getAdapterPosition()).getId().matches(Utils.currTrack.getId())) {
                                tvTrackTitle.setTextColor(context.getResources().getColor(R.color.light_gray));
                                tvTrackDescription.setTextColor(context.getResources().getColor(R.color.light_gray));
                            }
                            ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_red_24dp);
                            Toast.makeText(context, R.string.remove_from_playing_list, Toast.LENGTH_SHORT).show();
                            ivHide.setSelected(true);
                        }
                    } else
                        Toast.makeText(context.getApplicationContext(), "this playlist is'nt playping", Toast.LENGTH_LONG).show();
                }
            });
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTrackClicked.OnLikeClickedListener(!ivLike.isSelected(), getAdapterPosition());
                    Utils.displayedContext.getTracks().get(getAdapterPosition()).setLiked(!ivLike.isSelected());
                    if (ivLike.isSelected()) {
                        ivLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        Toast.makeText(context, R.string.remove_from_liked_playlist, Toast.LENGTH_SHORT).show();
                        ivLike.setSelected(false);
                        ServiceController.getInstance().removeFromSaved(context.getApplicationContext(),
                                Utils.displayedContext.getTracks().get(getAdapterPosition()).getId());
                        mTracks.get(getAdapterPosition()).setLiked(false);
                    } else {
                        ivLike.setImageResource(R.drawable.ic_favorite_black_24dp);
                        Toast.makeText(context, R.string.add_to_like_playlist, Toast.LENGTH_SHORT).show();
                        ivLike.setSelected(true);
                        ServiceController.getInstance().saveTrack(context.getApplicationContext(),
                                Utils.displayedContext.getTracks().get(getAdapterPosition()).getId());
                        mTracks.get(getAdapterPosition()).setLiked(true);
                    }
                }
            });
            ivSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "open settings ", Toast.LENGTH_SHORT).show();
                    onTrackClicked.showTrackSettingFragment(mTracks.get(getAdapterPosition()));

                }
            });

        }

        /**
         * bind data to views
         */
        public void bind(int pos) {
            if (!Constants.DEBUG_STATUS)
                if (!mTracks.get(pos).getImageUrl().matches("")) {
                    Target target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            ivTrackImage.setImageBitmap(bitmap);
                            Utils.displayedContext.getTracks().get(pos).setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };
                    Picasso.get()
                            .load(mTracks.get(pos).getImageUrl())
                            .into(target);
                } else {
                    ivTrackImage.setImageResource(R.drawable.no_image);
                    mTracks.get(pos).setImageResources(R.drawable.no_image);
                }
            else
                ivTrackImage.setImageResource(mTracks.get(pos).getmImageResources());
            tvTrackTitle.setText(mTracks.get(pos).getmTitle());
            tvTrackDescription.setText(mTracks.get(pos).getmArtist());
            Track track = mTracks.get(getAdapterPosition());
            if (track.isLiked()) {
                ivLike.setImageResource(R.drawable.ic_favorite_black_24dp);
                ivLike.setSelected(true);
            } else {
                ivLike.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                ivLike.setSelected(false);
            }
            if (Utils.playingContext != null
                    && Utils.displayedContext.getId().matches(Utils.playingContext.getId())
                    && Utils.playingContext.getTracks().get(getAdapterPosition()).getId().matches(track.getId()) &&
                    Utils.playingContext.getTracks().get(getAdapterPosition()).isHidden()) {
                tvTrackTitle.setTextColor(context.getResources().getColor(R.color.light_gray));
                ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_red_24dp);
                tvTrackDescription.setTextColor(context.getResources().getColor(R.color.light_gray));
                ivHide.setSelected(true);
            } else {
                ivHide.setImageResource(R.drawable.ic_do_not_disturb_on_black_24dp);
                tvTrackTitle.setTextColor(context.getResources().getColor(R.color.white));
                tvTrackDescription.setTextColor(context.getResources().getColor(R.color.white));
                ivHide.setSelected(false);

            }
            if (Utils.currTrack != null && mTracks.get(pos).getId().matches(Utils.currTrack.getId())) {//.isPlaying()){
                tvTrackDescription.setTextColor(context.getColor(R.color.colorGreen));
                tvTrackTitle.setTextColor(context.getColor(R.color.colorGreen));
            }
            if (track.isLocked() && !Constants.currentUser.isPremuim()) {
                tvTrackTitle.setTextColor(context.getResources().getColor(R.color.light_gray));
                tvTrackDescription.setTextColor(context.getResources().getColor(R.color.light_gray));
            }
        }

        /**
         * this function is called when user click on item
         *
         * @param view takes view that is clicked
         */
        @Override
        public void onClick(View view) {
            onTrackClicked.OnTrackClickedListener(mTracks,
                    getAdapterPosition(), prev);
            prev = getAdapterPosition();

        }
    }

}
