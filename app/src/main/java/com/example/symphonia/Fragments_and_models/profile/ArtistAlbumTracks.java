package com.example.symphonia.Fragments_and_models.profile;

import android.content.Intent;
import android.os.Bundle;
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
public class ArtistAlbumTracks extends Fragment implements FollowersAdapter.ProfileFollowersItemClickListner {
    private ServiceController controller;
    private String id;
    private ArrayList<Container> data;
    private RecyclerView recyclerView;
    private TextView textView;
    private ImageView backImg;
    private LinearLayout linearLayout;
    private TextView add;
    private FollowersAdapter albumsAdapter;


    public ArtistAlbumTracks(String i){
        id=i;
    }

    private View.OnClickListener back = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getFragmentManager().popBackStack();
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_folllowers, container, false);
        controller = ServiceController.getInstance();

        recyclerView = root.findViewById(R.id.rv_followers_profile);
        textView = root.findViewById(R.id.tv_followers_following);
        backImg = root.findViewById(R.id.img_back_profile_main);
        linearLayout=root.findViewById(R.id.add_album);
        linearLayout.setVisibility(View.VISIBLE);
        add=root.findViewById(R.id.tv_add);
        add.setText("Add Track");
        //handle click
        backImg.setOnClickListener(back);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent addAlbums = new Intent(getContext(), AddAlbum.class);
                Bundle b=new Bundle();
                b.putString("albumId",id);
                addAlbums.putExtras(b);
                startActivityForResult(addAlbums,3456);*/
            }
        });
        //handle recycler view
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        textView.setText("Albums");
        if (Constants.DEBUG_STATUS == true) {
            data = controller.getAlbumTracks(getContext(),this,id);
            albumsAdapter = new FollowersAdapter(data, this);
            recyclerView.setAdapter(albumsAdapter);
        }
        else {
            RestApi restApi=new RestApi();
            restApi.getAlbumTracks(getContext(),this,id);
        }

        return root;
    }
    public void updateUiGetTracks(ArrayList<Container> t){
        data=t;
        albumsAdapter = new FollowersAdapter(data, this);
        recyclerView.setAdapter(albumsAdapter);
    }

    @Override
    public void onProfileFollowerItemlongClickListener(Container c, int p) {
        BottomSheetDialogProfile bottomSheet = new BottomSheetDialogProfile(c,6,this,p);
        assert getParentFragmentManager() != null;
        bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());
    }

    @Override
    public void onProfileFollowerItemClickListener(Container c) {

    }

    public void OnDelTrackSuccess(int pos){
        data.remove(pos);
        albumsAdapter.notifyItemRemoved(pos);
        albumsAdapter.notifyItemRangeChanged(pos, data.size());
    }

    public void OnDelTrackFailure(){
        Toast toast=Toast.makeText(getContext(),"Sorry your track couldn't be deleted",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void OnRenameTrackSuccess(int pos,String name){
        data.get(pos).setCatName(name);
        albumsAdapter.notifyDataSetChanged();

    }

    public void OnRenameTrackFailure(){
        Toast toast=Toast.makeText(getContext(),"Sorry your track couldn't be renamed",Toast.LENGTH_SHORT);
        toast.show();
    }
}
