package com.example.symphonia.Fragments_and_models.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.ProfilePlaylistsAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * FragmentProfile to show the all_profile_playlists layout
 *  * @author Mahmoud Amr Nabil
 *  * @version 1.0
 */
public class ProfilePlaylistsFragment extends Fragment implements ProfilePlaylistsAdapter.ProfileplaylistItemClickListner {
    private ServiceController controller;
    private RecyclerView recyclerView;
    private ImageView backImg;
    private String id;

    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager().popBackStack();
        }
    };

    public ProfilePlaylistsFragment(String i){
        id=i;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.all_profile_playlists, container, false);
        controller= ServiceController.getInstance();

        recyclerView=root.findViewById(R.id.rv_all_profile_playlists);
        backImg=root.findViewById(R.id.img_back_profile_main);

        backImg.setOnClickListener(listener);
        if(!Constants.DEBUG_STATUS) {
            RestApi restApi=new RestApi();
            restApi.getAllCurrentUserPlaylists(getContext(),this,id);
        }
        else {
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            ProfilePlaylistsAdapter adapter = new ProfilePlaylistsAdapter(controller.getAllPopularPlaylists(getContext()), this);
            recyclerView.setAdapter(adapter);
        }
        return root;
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
     * @param c the list of playlists
     */
    public void updatePlaylists(ArrayList<Container>c){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ProfilePlaylistsAdapter adapter = new ProfilePlaylistsAdapter(c, this);
        recyclerView.setAdapter(adapter);
    }

}
