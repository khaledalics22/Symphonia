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
public class SeeAllPlaylistsAdapter extends RecyclerView.Adapter<SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder> {
    private ArrayList<Container> container;
    private boolean showText;

    /**
     *constructor of the adapter
     * @param data Arraylist of Container which has the data of the adapter
     * @param b if true textView2 won't be shown
     */
    public SeeAllPlaylistsAdapter(ArrayList<Container> data,boolean b) {
        container = data;
        showText=b;
    }

    /**
     *Called when RecyclerView needs a new RecyclerView.ViewHolder
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return SeeAllPlaylistsViewHolder
     */
    @NonNull
    @Override
    public SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V;
        V = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.seeall_playlists_item, parent, false);
        SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder VH = new SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder(V);
        return VH;
    }

    /**
     *Called by RecyclerView when it starts observing this Adapter.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull SeeAllPlaylistsAdapter.SeeAllPlaylistsViewHolder holder, int position) {
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


    class SeeAllPlaylistsViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private TextView textView2;

        public SeeAllPlaylistsViewHolder(@NonNull View itemView) {
            super(itemView);
            //attach views
            textView=itemView.findViewById(R.id.tv_search_all_playlists);
            imageView=itemView.findViewById(R.id.img_search_all_playlists);
            textView2=itemView.findViewById(R.id.tv_followers);

            if(!showText) textView2.setVisibility(View.GONE);//if showText=false textView2 won't be shown
            else textView2.setVisibility(View.VISIBLE);
        }
        /**
         * set text and recources in the view of the item
         * @param pos position of the item
         */
        public void makeResult(int pos) {
            Container temp = container.get(pos);
            textView.setText(temp.getCat_Name());
            imageView.setImageBitmap(temp.getImg_Res());
            if(showText) textView2.setText(temp.getCat_Name2());
        }
    }
}
