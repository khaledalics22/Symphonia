package com.example.symphonia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.R;

import java.util.ArrayList;

/**
 * Activity that handles Adapter of Premium page
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class PremiumAdapter extends RecyclerView.Adapter<PremiumAdapter.Holder> {


    /**
     * Array holds features of free account
     */
    private ArrayList<String> mFeaturesFree = new ArrayList<>();
    /**
     * Array holds features of premium account
     */
    private ArrayList<String> mFeaturesPrem = new ArrayList<>();
    /**
     * holds context that calls the adapter
     */
    private Context mContext;

    /**
     * Constructor of premium adapter that initializes parameters
     * @param mFeaturesFree Array holds features of free account
     * @param mFeaturesPrem Array holds features of premium account
     * @param mContext holds context that calls the adapter
     */
    public PremiumAdapter(ArrayList<String> mFeaturesFree, ArrayList<String> mFeaturesPrem, Context mContext) {
        this.mFeaturesFree = mFeaturesFree;
        this.mFeaturesPrem = mFeaturesPrem;
        this.mContext = mContext;
    }

    /**
     * function that handles initializing view holders
     * @param parent holds root view
     * @param viewType view type of the item at position in recycler view
     * @return holder of item in recycler view
     */

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //sets layout for each item in recycler
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_premium, parent, false);

        return new Holder(view);
    }

    /**
     * Updates the contents with the item at the given position
     * @param holder represent the contents of the item at the given position
     * @param position position of current item
     */
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    /**
     * holds total number of items in adapter
     * @return return size of array of item
     */
    @Override
    public int getItemCount() {
        return mFeaturesFree.size();
    }


    /**
     * Handles an item view and metadata about its place within the RecyclerView.
     */
    public class Holder extends RecyclerView.ViewHolder  {

        /**
         * text view which holds free feature
         */
        TextView text_view_free;
        /**
         * text view which holds premium feature
         */
        TextView text_view_premium;

        /**
         * Constructor for Holder Class to initialize parameters
         * @param itemView view that contains current item
         */
        public Holder(@NonNull View itemView) {
            super(itemView);

            //gets text views that holds text
            text_view_free = (TextView) itemView.findViewById(R.id.text_free);
            text_view_premium = (TextView) itemView.findViewById(R.id.text_prem);
        }

        /**
         * Fill each item in the view with its data
         * @param position index of item in recycler view
         */
        public void bind(int position) {
            //gets each feature from array, then sets it in text views
            String txtFree = mFeaturesFree.get(position);
            String txtPrem = mFeaturesPrem.get(position);
            text_view_free.setText(txtFree);
            text_view_premium.setText(txtPrem);
        }
    }
}

