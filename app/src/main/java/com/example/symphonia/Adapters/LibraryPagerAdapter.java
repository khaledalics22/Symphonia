package com.example.symphonia.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.symphonia.Fragments_and_models.library.LibraryAlbumsFragment;
import com.example.symphonia.Fragments_and_models.library.LibraryArtistsFragment;
import com.example.symphonia.Fragments_and_models.library.LibraryPlaylistsFragment;
import com.example.symphonia.R;

/**
 * view pager adapter to change the fragments
 * of the library in scrolling
 *
 * @author islamahmed1092
 * @version 1.0
 */
public class LibraryPagerAdapter extends FragmentPagerAdapter {

    /**
     * titles of each tab in the tablayout
     */
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.library_tab_playlists, R.string.library_tab_artists, R.string.library_tab_albums};
    /**
     * context to get the string of the titles from resources
     */
    private final Context mContext;

    /**
     * constructor of the pager adapter
     *
     * @param context the context
     * @param fm fragment manager
     */
    public LibraryPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    /**
     * @param position position of the fragment that will be shown
     * @return fragment object
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LibraryPlaylistsFragment();
        } else if (position == 1) {
            return new LibraryArtistsFragment();
        } else if (position == 2) {
            return new LibraryAlbumsFragment();
        } else
            return new LibraryPlaylistsFragment();
    }

    /**
     * @param position position of the fragment that will be shown
     * @return tab title
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    /**
     * @return the number of fragments in the pager
     */
    @Override
    public int getCount() {
        return 3;
    }
}
