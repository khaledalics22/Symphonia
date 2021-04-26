package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;

import java.util.ArrayList;

public class SeeAllProfilesAdapter extends RecyclerView.Adapter<SeeAllProfilesAdapter.SeeAllProfilesViewHolder> {
    private ArrayList<Container> data;
    public interface ProfileItemClickListner{
        void onProfileItemClickListener(Container c);//handle clicking on close image
    }
    private ProfileItemClickListner ProfielItemClickListner;
    /**
     *costrucor of adapter
     * @param d  Arraylist of Container which has the data of the adapter
     */
    public SeeAllProfilesAdapter(ArrayList<Container> d,ProfileItemClickListner listner) {
        data = d;
        ProfielItemClickListner=listner;
    }

    /**
     *Called when RecyclerView needs a new RecyclerView.ViewHolder
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return SeeAllArtistsViewHolder
     */
    @NonNull
    @Override
    public SeeAllProfilesAdapter.SeeAllProfilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.seeall_artists_genres_profiles_item, parent, false);
        SeeAllProfilesAdapter.SeeAllProfilesViewHolder VH = new SeeAllProfilesAdapter.SeeAllProfilesViewHolder(V);
        return VH;
    }
    /**
     *Called by RecyclerView when it starts observing this Adapter.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull SeeAllProfilesAdapter.SeeAllProfilesViewHolder holder, int position) {
        holder.makeProfileItem(position);
    }

    /**
     *
     * @return return number of items in recyclerview which will be the Arraylist size
     */
    @Override
    public int getItemCount() {
        return data.size();
    }


    class SeeAllProfilesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView imageView;


        public SeeAllProfilesViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            //attach views
            textView=itemView.findViewById(R.id.tv_search_all_artists);
            imageView=itemView.findViewById(R.id.img_search_all_artists);
        }
        /**
         *set text and recources in the views of the adapter's item
         * @param pos position of the adapter item
         */
        public void makeProfileItem(int pos) {
            Container temp = data.get(pos);
            textView.setText(temp.getCat_Name());
            imageView.setImageBitmap(temp.getImg_Res());
        }

        @Override
        public void onClick(View v) {
            ProfielItemClickListner.onProfileItemClickListener(data.get(getAdapterPosition()));
        }
    }
}
