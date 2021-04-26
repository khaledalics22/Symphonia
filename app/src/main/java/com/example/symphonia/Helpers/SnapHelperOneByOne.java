package com.example.symphonia.Helpers;

import android.view.View;

import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * this class for adapting recyclerview scrolling
 *
 * @author Khaled Ali
 * @since 22-3-2020
 * @version 1.0
 */
public class SnapHelperOneByOne extends LinearSnapHelper {

    /**
     * this function finds the most appearing item in recyclerview to scroll to
     * @param layoutManager  layout manager of recycler view
     * @param velocityX      velocity of motion in x axis
     * @param velocityY      velocity of motion in y axis
     * @return
     */
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {

        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        final View currentView = findSnapView(layoutManager);

        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        final int currentPosition = layoutManager.getPosition(currentView);

        return currentPosition;
    }
}


