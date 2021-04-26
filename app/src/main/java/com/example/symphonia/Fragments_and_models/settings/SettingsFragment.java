package com.example.symphonia.Fragments_and_models.settings;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Profile;
import com.example.symphonia.Fragments_and_models.premium.PremiumFragment;
import com.example.symphonia.Fragments_and_models.profile.FragmentProfile;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment{

    private NestedScrollView nestedScrollView;
    private float firstY;

    private ImageView userImg;
    private TextView userName;
    private String profileName;
    private Bitmap profileImg;

    private View rootView;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        nestedScrollView = rootView.findViewById(R.id.container);
        nestedScrollView.setNestedScrollingEnabled(false);

        userImg=rootView.findViewById(R.id.image_user);
        userImg.setImageResource(R.drawable.img_init_profile);
        userName = rootView.findViewById(R.id.text_user_name);
        userName.setText(Constants.currentUser.getmName());
        profileName = Constants.currentUser.getmName();

        AppBarLayout appBarLayout = rootView.findViewById(R.id.app_bar);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        params.setBehavior(new AppBarLayout.Behavior());
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        assert behavior != null;
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });

        final FragmentContainerView settingsFragment = rootView.findViewById(R.id.settings);
        settingsFragment.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.settings, new PreferenceFragment())
                .commit();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                settingsFragment.setVisibility(View.VISIBLE);
                nestedScrollView.scrollTo(0,0);
            }
        }, 1);

        final Button premiumButton = rootView.findViewById(R.id.button_go_premium);

        ConstraintLayout profileLayout = rootView.findViewById(R.id.layout_profile);
        userName = rootView.findViewById(R.id.text_user_name);

        final TextView viewProfile = rootView.findViewById(R.id.text_view_profile);

        RestApi restApi=new RestApi();
        restApi.getCurrentUserProfile(getContext(),this,null);
        restApi.getCurrentUserProfile(getContext(),this,null);


        premiumButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.startTouchAnimation(v, 0.95f, 0.5f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(v);
                        if((currentY >= 0) && (currentY <= v.getHeight())){
                            v.performClick();
                        }
                        return true;
                }
                return false;
            }
        });

        profileLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstY = currentY;
                        Utils.startTouchAnimation(userName, 1f, 0.5f);
                        Utils.startTouchAnimation(viewProfile, 1f, 0.5f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        Utils.cancelTouchAnimation(userName);
                        Utils.cancelTouchAnimation(viewProfile);
                        if((currentY >= 0) && (currentY <= v.getHeight())){
                            v.performClick();
                        }
                        return true;
                }
                return false;
            }

        });

        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float currentY = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstY = currentY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(Math.abs(currentY - firstY) > 3){
                            Utils.cancelTouchAnimation(premiumButton);
                            Utils.cancelTouchAnimation(userName);
                            Utils.cancelTouchAnimation(viewProfile);
                        }
                }
                return false;
            }
        });


        premiumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(
                        R.id.nav_host_fragment, new PremiumFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileImg==null) profileImg=Utils.convertToBitmap(R.drawable.img_init_profile);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new FragmentProfile(new Container(profileName,profileImg)))
                        .addToBackStack(null)
                        .commit();

            }
        });

        
        ImageView backIcon = rootView.findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }

    public void updateUiProfile(Profile profile){
        if(profile.getImgUrl().contains("facebook")) {
            Glide.with(getContext()).load(profile.getImgUrl()).into(userImg);
            BitmapDrawable drawable = (BitmapDrawable) userImg.getDrawable();
            profileImg = drawable.getBitmap();
        }
        else {
            //if (!profile.getImgUrl().contains("default")) {
                Picasso.get()
                        .load(profile.getImgUrl())
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.img_init_profile)
                        .into(userImg, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                BitmapDrawable drawable = (BitmapDrawable) userImg.getDrawable();
                                profileImg = drawable.getBitmap();
                            }

                            @Override
                            public void onError(Exception e) {

                            }

                        });
        }
                BitmapDrawable drawable = (BitmapDrawable) userImg.getDrawable();
                profileImg = drawable.getBitmap();
            //}
        profileName=profile.getCat_Name();
        Constants.currentUser.setmName(profileName);
        userName.setText(profileName);
        //userImg.setImageBitmap(profileImg);
    }

}
