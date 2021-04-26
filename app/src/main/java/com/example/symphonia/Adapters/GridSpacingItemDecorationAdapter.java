package com.example.symphonia.Adapters;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Item decoration adapter attached to recyclerview
 * to control the spacing between the items of the grid
 * @author islamahmed1092
 * @version 1.0
 */
public class GridSpacingItemDecorationAdapter extends RecyclerView.ItemDecoration {

    /**
     * no of items in each row
     */
    private int spanCount;
    /**
     * spacing in pixels
     */
    private int spacing;
    /**
     * determine whether or not to include the edges of the recyclerview
     */
    private boolean includeEdge;


    /**
     * constructor for the decorator
     *
     * @param spanCount no of items in each row
     * @param spacing spacing in pixels
     * @param includeEdge include edge or not
     */
    public GridSpacingItemDecorationAdapter(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }


    /**
     * set the spacing between items and each others
     *
     * @param outRect controls the spacing around each item
     * @param view the item of the recyclerview
     * @param parent the recyclerview
     * @param state the state of the recyclerview
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }

    }
}