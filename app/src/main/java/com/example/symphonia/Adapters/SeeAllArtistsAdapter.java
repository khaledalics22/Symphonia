package com.example.symphonia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;
import com.example.symphonia.Entities.Container;

import java.util.ArrayList;

/**
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class SeeAllArtistsAdapter extends RecyclerView.Adapter<SeeAllArtistsAdapter.SeeAllArtistsViewHolder> {

    private ArrayList<Container> container;

    /**
     *costrucor of adapter
     * @param data  Arraylist of Container which has the data of the adapter
     */
    public SeeAllArtistsAdapter(ArrayList<Container> data) {
        container = data;
    }

    /**
     *Called when RecyclerView needs a new RecyclerView.ViewHolder
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return SeeAllArtistsViewHolder
     */
    @NonNull
    @Override
    public SeeAllArtistsAdapter.SeeAllArtistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.seeall_artists_genres_profiles_item, parent, false);
        SeeAllArtistsAdapter.SeeAllArtistsViewHolder VH = new SeeAllArtistsAdapter.SeeAllArtistsViewHolder(V);
        return VH;
    }
    /**
     *Called by RecyclerView when it starts observing this Adapter.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull SeeAllArtistsViewHolder holder, int position) {
        holder.makeResult(position);
    }

    /**
     *
     * @return return number of items in recyclerview which will be the Arraylist size
     */
    @Override
    public int getItemCount() {
        return container.size();
    }


    class SeeAllArtistsViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;


        public SeeAllArtistsViewHolder(@NonNull View itemView) {
            super(itemView);
            //attach views
            textView=itemView.findViewById(R.id.tv_search_all_artists);
            imageView=itemView.findViewById(R.id.img_search_all_artists);
        }
        /**
         *set text and recources in the views of the adapter's item
         * @param pos position of the adapter item
         */
        public void makeResult(int pos) {
            Container temp = container.get(pos);
            textView.setText(temp.getCat_Name());
            imageView.setImageBitmap(temp.getImg_Res());
        }
    }
}
