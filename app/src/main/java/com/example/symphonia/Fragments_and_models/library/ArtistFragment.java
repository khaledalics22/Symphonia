package com.example.symphonia.Fragments_and_models.library;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.google.android.material.appbar.AppBarLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {

    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_artist, container, false);

        ImageView backIcon = rootView.findViewById(R.id.arrow_back);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        final TextView artistName = rootView.findViewById(R.id.text_artist_name);
        final ImageView artistImage = rootView.findViewById(R.id.image_artist);
        final TextView title = rootView.findViewById(R.id.title);
        title.setAlpha(0);

        AppBarLayout appBarLayout = rootView.findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
/*                if(verticalOffset == 0){
                    CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) floatingButton.getLayoutParams();
                    p.setAnchorId(R.id.toolbar);
                    floatingButton.setLayoutParams(p);
                }*/
                Log.i("TAG", "offset: " + verticalOffset);
                int halfRange = appBarLayout.getTotalScrollRange()/2;
                Utils.startTouchAnimation(artistName,1 + (float)verticalOffset/(halfRange*24),  1 + (float)verticalOffset/(halfRange*4));
                Utils.startTouchAnimation(artistImage,1 + (float)verticalOffset/(halfRange*128),  1 + (float)verticalOffset/(halfRange*4));
                if(Math.abs(verticalOffset) > halfRange){
                    Log.i("TAG", "alpha: " + -(verticalOffset/halfRange+1));
                    title.setAlpha((float)(-((float)verticalOffset/halfRange+1)));
                }
            }
        });


        return rootView;
    }
}
