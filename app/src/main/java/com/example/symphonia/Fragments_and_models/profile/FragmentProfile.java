package com.example.symphonia.Fragments_and_models.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.symphonia.Activities.User_Interface.EditProfileActivity;
import com.example.symphonia.Adapters.ProfilePlaylistsAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.Entities.Profile;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * FragmentProfile to show the fragment_profile layout
 *  * @author Mahmoud Amr Nabil
 *  * @version 1.0
 */
public class FragmentProfile extends Fragment implements ProfilePlaylistsAdapter.ProfileplaylistItemClickListner {
    private ServiceController controller;
    private Container profile;
    private LinearLayout animatedLayout;
    private View background;
    private TextView profileName;
    private TextView backgroundProfileName;
    private AppBarLayout appBarLayout;
    private ImageView profileImage;
    private RecyclerView recyclerView;
    private Button seeAll;
    private LinearLayout playlistsLayout;
    private LinearLayout followingLayout;
    private LinearLayout followersLayout;
    private Button followButton;
    private ImageView backImg;
    private ImageView options;
    private double x;
    private double y;
    private TextView playlistsNum;
    private TextView followersNum;
    private TextView followingNum;
    private LinearLayout artist_part;
    private LinearLayout graphs;
    private LinearLayout albums;
    private LinearLayout singles;

    private boolean checkIfUserProfile=true;
    private String id=null;


    private View.OnClickListener showOptions=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BottomSheetDialogProfile bottomSheet = new BottomSheetDialogProfile(profile,1);
            assert getParentFragmentManager() != null;
            bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());
        }
    };

    private View.OnClickListener toPlaylists=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment,new ProfilePlaylistsFragment(id));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    };

    private View.OnClickListener toFollowers=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment,new ProfileFollowersFragment("followers",id));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    };

    private View.OnClickListener toFollowing=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment,new ProfileFollowersFragment("Following",id));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    };

    private final View.OnClickListener back = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager().popBackStack();
        }
    };

    private View.OnClickListener follow=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            float density = getResources().getDisplayMetrics().density;
            int[] loc={0,0};
            followButton.getLocationOnScreen(loc);
            int i= (int) (loc[0]/density);
            int j= (int) (loc[1]/density);
            if (y <=j+30&&y>=j && x >=i&&x<=i+110) {
                String s=(String) followButton.getText();
                if(s.equals(getResources().getString(R.string.follow)))
                    followButton.setText(getResources().getString(R.string.Following));
                else if(s.equals(getResources().getString(R.string.Following))) followButton.setText(getResources().getString(R.string.follow));
                else if(s.equals(getContext().getResources().getString(R.string.Edit_Profile))){
                   /* EditProfileFragment bottomSheet = new EditProfileFragment(profile);
                    assert getParentFragmentManager() != null;
                    bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());*/
                    Intent editProfileActivity = new Intent(getContext(), EditProfileActivity.class);
                    Bundle b = new Bundle();
                    b.putString("name", profile.getCat_Name());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    profile.getImg_Res().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] bytes = stream.toByteArray();
                    b.putByteArray("bitmap",bytes);
                    editProfileActivity.putExtras(b);
                    startActivityForResult(editProfileActivity,111);
                }
            }
        }
    };

    private View.OnTouchListener t=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                float density = getResources().getDisplayMetrics().density;
                x = event.getRawX();
                x/=density;
                y = event.getRawY();
                y/=density;
            }
            return false;
        }
    };



    public FragmentProfile(String i){
        id=i;
        checkIfUserProfile=false;
    }

    public FragmentProfile(Container c){
        profile=c;
        //id="5e812a3454660672fd699880";
        //checkIfUserProfile=false;
    }
    public FragmentProfile(Container c,boolean b){
        profile=c;
        checkIfUserProfile=b;
    }

    private View.OnClickListener showGraphs=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };



    private View.OnClickListener showAlbums=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment,new ArtistAlbums());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    };

    private View.OnClickListener showSingles=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment,new ProfileFollowersFragment("singles",id));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        //get instance of Service controller
        controller=ServiceController.getInstance();
        //attach views
        artist_part=root.findViewById(R.id.rest_of_bar);
        boolean userType = Constants.currentUser.isListenerType();
        if(!userType){
            artist_part.setVisibility(View.VISIBLE);
        }
        background=root.findViewById(R.id.profile_background);
        profileName=root.findViewById(R.id.tv_profile_name_animated);
        backgroundProfileName=root.findViewById(R.id.tv_profile_background);
        appBarLayout=root.findViewById(R.id.app_bar_profile);
        animatedLayout=root.findViewById(R.id.profile_animated_layout);
        profileImage=root.findViewById(R.id.profile_image);
        recyclerView=root.findViewById(R.id.rv_profile_playlists);
        seeAll=root.findViewById(R.id.btn_see_all_profile_playlists);
        playlistsLayout=root.findViewById(R.id.playlists_count);
        followersLayout=root.findViewById(R.id.followers_count);
        followingLayout=root.findViewById(R.id.following_count);
        backImg=root.findViewById(R.id.img_back_profile);
        followButton=root.findViewById(R.id.btn_profile_follow);
        options=root.findViewById(R.id.profile_options_menu);
        playlistsNum=root.findViewById(R.id.number_of_playlists);
        followersNum=root.findViewById(R.id.number_of_followers);
        followingNum=root.findViewById(R.id.number_of_following);
        graphs=root.findViewById(R.id.graphs);
        albums=root.findViewById(R.id.albums);
        singles=root.findViewById(R.id.singles);

        //handle clicks
        options.setOnClickListener(showOptions);
        appBarLayout.setOnClickListener(follow);
        appBarLayout.setOnTouchListener(t);

        if(!checkIfUserProfile)  followButton.setText(getContext().getResources().getString(R.string.follow));

        if(checkIfUserProfile) {
            profileImage.setImageBitmap(profile.getImg_Res());
            profileName.setText(profile.getCat_Name());
            backgroundProfileName.setText(profile.getCat_Name());
        }

        //followButton.setOnClickListener(follow);
        backImg.setOnClickListener(back);
        playlistsLayout.setOnClickListener(toPlaylists);
        seeAll.setOnClickListener(toPlaylists);
        followersLayout.setOnClickListener(toFollowers);
        followingLayout.setOnClickListener(toFollowing);
        albums.setOnClickListener(showAlbums);
        if(id==null) {
            Drawable drawable = Utils.createSearchListBackground(getContext(), profile);
            background.setBackground(drawable);
        }
        if(!Constants.DEBUG_STATUS) {
            RestApi restApi = new RestApi();
            restApi.getCurrentUserPlaylists(getContext(), this,id);
            restApi.getNumbersoUserFollowers(getContext(),this,id);
            restApi.getNumbersoUserFollowing(getContext(),this,id);
            if (id != null) {
                restApi.getCurrentUserProfile(getContext(),this,id);
            }
        }
        else {
            seeAll.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(true);
            //       ProfilePlaylistsAdapter adapter = new ProfilePlaylistsAdapter(controller.getFourPlaylists(getContext(),"", this);
            // recyclerView.setAdapter(adapter);
        }
        //handle animation
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            /**
             * give views transparency depnding on i
             * @param appBarLayout  the AppBarLayout which offset has changed
             * @param i the vertical offset for the parent AppBarLayout, in px
             */
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                float density = getResources().getDisplayMetrics().density;
                float dp=Math.abs(i)/density;
                float alphaRatio=1;
                if(dp<=300&&dp>=0) {
                    if (dp == 300) alphaRatio = 1;
                    else if (dp == 0) alphaRatio = 0;
                    else {
                        alphaRatio = (float) Math.ceil(dp / 30);
                        alphaRatio /= 10;
                    }
                    appBarLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), alphaRatio));
                    animatedLayout.setBackgroundColor(getColorWithAlpha(getResources().getColor(R.color.colorDark), alphaRatio));
                    profileName.setTextColor(getColorWithAlpha(getResources().getColor(R.color.colorWhite), alphaRatio));
                }
                if(dp<=100&&dp>=0){
                    profileImage.setScaleX((float) (1));
                    profileImage.setScaleY((float) (1));
                }
                else if(dp<=200&&dp>100){
                    profileImage.setScaleX((float) (1-alphaRatio+.3));
                    profileImage.setScaleY((float) (1-alphaRatio+.3));
                }

            }
        });
        return root;
    }


    private int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    /**
     * handle long click on item playlist item
     * @param c the playlist that user long clicked on
     */
    @Override
    public void onProfileItemlongClickListener(Container c) {
        BottomSheetDialogProfile bottomSheet = new BottomSheetDialogProfile(c,3);
        assert getParentFragmentManager() != null;
        bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());
    }

    /**
     * update the playlists after successful response
     * @param p the list of playlists
     */
    public void updatePlaylists(ArrayList<Container> p){
        int n=3;
        if(p.size()>3) {n=3; seeAll.setVisibility(View.VISIBLE);}
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        List<Container>l=p.subList(0,n);
        ArrayList<Container>c=new ArrayList<>();
        c.addAll(l);
        ProfilePlaylistsAdapter adapter=new ProfilePlaylistsAdapter(c,this);
        recyclerView.setAdapter(adapter);
        playlistsNum.setText(String.valueOf(p.size()));
    }

    /**
     * update number of following
     * @param num number of following
     */
    public void updateUiFollowing(String num){
        followingNum.setText(num);
    }

    /**
     * update number of followers
     * @param s number of followers
     */
    public void updateUiFollowers(String s){
        followersNum.setText(s);
    }

    public void updateUiProfile(Profile p){
        final Bitmap[] bitmap = new Bitmap[1];
        if(p.getImgUrl().contains("facebook")) {
            Glide.with(getContext()).load(p.getImgUrl()).into(profileImage);
            BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
            bitmap[0] =drawable.getBitmap();
            profile.setImgBitmap(bitmap[0]);
        }
        else {
        Picasso.get()
                .load(p.getImgUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.img_init_profile)
                .into(profileImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        profile=new Container(p.getCat_Name(),p.getImgUrl(),null,id);
                        BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
                        bitmap[0] =drawable.getBitmap();
                        profile.setImgBitmap(bitmap[0]);
                        profileName.setText(p.getCat_Name());
                        backgroundProfileName.setText(p.getCat_Name());
                        Drawable drawable2=Utils.createSearchListBackground(getContext(),profile);
                        background.setBackground(drawable2);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        }
        profile=new Container(p.getCat_Name(),p.getImgUrl(),null,id);
        profile.setImgBitmap(bitmap[0]);
        profileName.setText(p.getCat_Name());
        backgroundProfileName.setText(p.getCat_Name());
        Drawable drawable=Utils.createSearchListBackground(getContext(),profile);
        background.setBackground(drawable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            String name=data.getStringExtra("name");
            String image=data.getStringExtra("image");
            byte[] decodedBytes = Base64.decode(image, 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            RestApi restApi=new RestApi();
            restApi.editProfile(getContext(),this,name,image);

            profileImage.setImageBitmap(bitmap);
            profileName.setText(name);
            backgroundProfileName.setText(name);
            profile=new Container(profile.getCat_Name(),profile.getImgUrl(),null,null);
            profile.setImgBitmap(bitmap);
            Drawable drawable=Utils.createSearchListBackground(getContext(),profile);
            background.setBackground(drawable);
        }
    }
}
