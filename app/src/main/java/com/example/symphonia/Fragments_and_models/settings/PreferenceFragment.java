package com.example.symphonia.Fragments_and_models.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;


import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Activities.User_Interface.StartActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.facebook.login.LoginManager;

import okhttp3.internal.Util;


public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
        Preference logOut = (Preference) findPreference("log_out");
        logOut.setSummary(getString(R.string.logged_as) + Constants.currentUser.getmName());
        assert logOut != null;
        logOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Utils.resetUserData();
                getActivity().finish();

                SharedPreferences sharedPref= getContext().getSharedPreferences("LoginPref",0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();
                LoginManager.getInstance().logOut();

                Intent startActivityIntent = new Intent(getContext(), StartActivity.class);
                startActivity(startActivityIntent);
                return true;
            }
        });
    }


}
