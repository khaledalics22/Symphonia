package com.example.symphonia.Fragments_and_models.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.R;
import com.example.symphonia.Adapters.LibraryPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

/**
 * root fragment for the library
 * that has many subfragment for each object in the user library
 * it controls all these fragments using pagers
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class LibraryFragment extends Fragment {


    /**
     * controls the showing and transitions between tab fragments
     *
     * @param inflater to inflate the layout of the fragment
     * @param container the ViewGroup that the fragment attached to
     * @param savedInstanceState saved data from previous calls
     * @return fragment view to be shown
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_your_library, container, false);
        LibraryPagerAdapter sectionsPagerAdapter = new LibraryPagerAdapter(getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        final TextView title = root.findViewById(R.id.title);
        AppBarLayout appBarLayout = root.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                title.setAlpha((float)(1 + 0.006667 * i));
            }
        });


        return root;
    }


    /**
     * called when the fragment is shown
     */
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setRoot(false);
    }


    /**
     * called when the user leave the framgnet
     */
    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).setRoot(true);
    }
}