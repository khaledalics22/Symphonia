package com.example.symphonia.Fragments_and_models.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symphonia.Adapters.FollowersAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;

import java.util.ArrayList;

/**
 * FragmentProfile to show the differant data for user
 *  * @author Mahmoud Amr Nabil
 *  * @version 1.0
 */
public class ProfileFollowersFragment extends Fragment implements FollowersAdapter.ProfileFollowersItemClickListner{
    private ArrayList<Container>data;
    private ServiceController controller;
    private RecyclerView recyclerView;
    private TextView textView;
    private ImageView backImg;
    private String id;
    private View.OnClickListener back=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager().popBackStack();
        }
    };

    private String getRightData;//to choose if we get the data of followers or following by artist

    public ProfileFollowersFragment(String b, String i){
        getRightData=b;
        id=i;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_folllowers, container, false);

        controller= ServiceController.getInstance();
        //attach views
        recyclerView=root.findViewById(R.id.rv_followers_profile);
        textView=root.findViewById(R.id.tv_followers_following);
        backImg=root.findViewById(R.id.img_back_profile_main);
        //handle click
        backImg.setOnClickListener(back);
        //handle recycler view
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        data=new ArrayList<>();
        RestApi restApi=new RestApi();
        if(getRightData=="followers") {
            textView.setText(getResources().getString(R.string.followers));
            if(!Constants.DEBUG_STATUS)
                restApi.getCurrentUserFollowers(getContext(),this,id);
            else
                data=controller.getCurrentUserFollowers(getContext(),this,id);
            //data=controller.getProfileFollowers(getContext());
        }
        else if(getRightData=="Following"){
            textView.setText(getResources().getString(R.string.Following));
            if(!Constants.DEBUG_STATUS) restApi.getCurrentUserFollowing(getContext(),this,id);
            else data=controller.getProfileFollowing(getContext());
        }

        if(Constants.DEBUG_STATUS) {
            FollowersAdapter adapter = new FollowersAdapter(data, this);
            recyclerView.setAdapter(adapter);
        }
        return root;
    }

    /**
     * handle long click on item follower item
     * @param c the follower that user long clicked on
     */
    @Override
    public void onProfileFollowerItemlongClickListener(Container c, int p) {
        BottomSheetDialogProfile bottomSheet = new BottomSheetDialogProfile(c,4);
        assert getParentFragmentManager() != null;
        bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());
    }

    @Override
    public void onProfileFollowerItemClickListener(Container c) {

    }

    /**
     * update the list of followers after successful response
     * @param c followers list
     */
    public void updateUiFollowers(ArrayList<Container> c){
        FollowersAdapter adapter = new FollowersAdapter(c, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * update the list of following after successful response
     * @param c following list
     */
    public void updateUiFollowing(ArrayList<Container> c){
        FollowersAdapter adapter = new FollowersAdapter(c, this);
        recyclerView.setAdapter(adapter);
    }

}
