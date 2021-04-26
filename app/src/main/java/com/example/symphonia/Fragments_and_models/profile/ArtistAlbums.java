package com.example.symphonia.Fragments_and_models.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Activities.User_Interface.AddAlbum;
import com.example.symphonia.Adapters.FollowersAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;
/**
 * ArtistAlbumTracks to show current aritst'tracks
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class ArtistAlbums extends Fragment implements FollowersAdapter.ProfileFollowersItemClickListner {
    private ArrayList<Container> data;
    private ServiceController controller;
    private RecyclerView recyclerView;
    private TextView textView;
    private ImageView backImg;
    private LinearLayout linearLayout;
    private TextView add;
    private FollowersAdapter albumsAdapter;

    private View.OnClickListener back = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().popBackStack();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("name");
            String image = data.getStringExtra("image");
            String albumType = data.getStringExtra("albumType");
            String copyRights = data.getStringExtra("copyRights");
            String copyRightsType = data.getStringExtra("copyRightsType");
            RestApi restApi = new RestApi();
            byte[] decodedBytes = Base64.decode(image, 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            if (Constants.DEBUG_STATUS) {
                controller.createAlbum(getContext(), this, name, image, albumType, copyRights, copyRightsType, bitmap);
            } else {
                restApi.createAlbum(getContext(), this, name, image, albumType, copyRights, copyRightsType, bitmap);
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_folllowers, container, false);
        controller = ServiceController.getInstance();

        recyclerView = root.findViewById(R.id.rv_followers_profile);
        textView = root.findViewById(R.id.tv_followers_following);
        backImg = root.findViewById(R.id.img_back_profile_main);
        linearLayout=root.findViewById(R.id.add_album);
        linearLayout.setVisibility(View.VISIBLE);
        add=root.findViewById(R.id.tv_add);
        add.setText("Add album");
        //handle click
        backImg.setOnClickListener(back);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addAlbums = new Intent(getContext(), AddAlbum.class);
                startActivityForResult(addAlbums,1);
            }
        });
        //handle recycler view
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        textView.setText("Albums");
        if (Constants.DEBUG_STATUS == true) {
            data = controller.getCurrentArtistAlbums(getContext(), this,"album");
            albumsAdapter = new FollowersAdapter(data, this);
            recyclerView.setAdapter(albumsAdapter);
        }
        else {
            RestApi restApi=new RestApi();
            restApi.getCurrentArtistAlbums(getContext(),this,"album");
        }
        return root;
    }

    public void updateUiArtistAlbums(ArrayList<Container> albums){
        data=albums;
        albumsAdapter = new FollowersAdapter(data, this);
        recyclerView.setAdapter(albumsAdapter);
    }

    @Override
    public void onProfileFollowerItemlongClickListener(Container c, int p) {
        BottomSheetDialogProfile bottomSheet = new BottomSheetDialogProfile(c,5,this,p);
        assert getParentFragmentManager() != null;
        bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());
    }

    @Override
    public void onProfileFollowerItemClickListener(Container c) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment,new ArtistAlbumTracks(c.getId()));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void OnAddAlbumSuccess(String id, String name, String imgUrl, Bitmap bitmap){
        if(Constants.DEBUG_STATUS==false) {
            data.add(new Container(name, imgUrl, "0 songs", id));
            albumsAdapter.notifyDataSetChanged();
        }
        else {
            data.add(new Container(name, "0 songs", bitmap));
            albumsAdapter.notifyDataSetChanged();
        }
    }

    public void OnAddAlbumFailure(){
        Toast toast=Toast.makeText(getContext(),"Sorry your album couldn't be created",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void OnDelAlbumSuccess(int pos){
        data.remove(pos);
        albumsAdapter.notifyItemRemoved(pos);
        albumsAdapter.notifyItemRangeChanged(pos, data.size());
    }

    public void OnDelAlbumFailure(){
        Toast toast=Toast.makeText(getContext(),"Sorry your album couldn't be deleted",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void OnRenameAlbumSuccess(int pos,String name){
        data.get(pos).setCatName(name);
        albumsAdapter.notifyDataSetChanged();

    }

    public void OnRenameAlbumFailure(){
        Toast toast=Toast.makeText(getContext(),"Sorry your album couldn't be renamed",Toast.LENGTH_SHORT);
        toast.show();
    }
}