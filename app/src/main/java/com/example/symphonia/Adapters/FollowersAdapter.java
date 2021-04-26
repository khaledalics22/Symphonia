package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class
FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder> {
    private ArrayList<Container> container;
    public interface ProfileFollowersItemClickListner{
        void onProfileFollowerItemlongClickListener(Container c,int p);//handle clicking on close image
        void onProfileFollowerItemClickListener(Container c);//handle clicking on close image
    }
    private ProfileFollowersItemClickListner listner;

    /**
     * constructor of the adapter
     *
     * @param data Arraylist of Container which has the data of the adapter
     */
    public FollowersAdapter(ArrayList<Container> data,ProfileFollowersItemClickListner l) {
        container = data;
        listner=l;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return SeeAllPlaylistsViewHolder
     */
    @NonNull
    @Override
    public FollowersAdapter.FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_list_item, parent, false);
        FollowersAdapter.FollowersViewHolder VH = new FollowersAdapter.FollowersViewHolder(V);
        return VH;
    }

    /**
     * Called by RecyclerView when it starts observing this Adapter.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull FollowersViewHolder holder, int position) {
        holder.makeFollowerstItem(position);
    }

    /**
     * @return return number of items in recyclerview which will be the Arraylist size
     */
    @Override
    public int getItemCount() {
        return container.size();
    }


    class FollowersViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private TextView textView;
        private RoundedImageView imageView;
        private TextView textView2;
        private ImageView done;
        private ImageView follow;

        public FollowersViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            //attach views
            textView = itemView.findViewById(R.id.tv_search_list_item);
            imageView = itemView.findViewById(R.id.img_search_list_item);
            textView2 = itemView.findViewById(R.id.tv_search_list_item_type);
            follow = itemView.findViewById(R.id.img_follow);
            done = itemView.findViewById(R.id.img_you_are_following);
            follow.setVisibility(View.VISIBLE);
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    follow.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                }
            });
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    follow.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                }
            });
        }

        /**
         * set text and recources in the view of the item
         *
         * @param pos position of the item
         */
        public void makeFollowerstItem(int pos) {
            Container temp = container.get(pos);
            textView.setText(temp.getCat_Name());
            imageView.setOval(false);
            if(!Constants.DEBUG_STATUS){
                textView2.setText(temp.getCat_Name2());
                if(!temp.getImgUrl().equals("https://thesymphonia.ddns.net/api/v1/images/users/default.png"))
                {
                    Picasso.get()
                            .load(temp.getImgUrl())
                            .fit()
                            .centerCrop()
                            .placeholder(R.drawable.placeholder_album)
                            .into(imageView);
                }
                else{
                    imageView.setImageResource(R.drawable.img_init_profile);
                }
            }
            else {
                imageView.setImageBitmap(temp.getImg_Res());
                textView2.setText(temp.getCat_Name2());
            }
        }


        /**
         * handle long click on followers item
         * @param v view which is long clicked on
         * @return boolean
         */
        @Override
        public boolean onLongClick(View v) {
            listner.onProfileFollowerItemlongClickListener(container.get(getAdapterPosition()),getAdapterPosition());
            return false;
        }

        @Override
        public void onClick(View v) {
            listner.onProfileFollowerItemClickListener(container.get(getAdapterPosition()));
        }
    }
}
