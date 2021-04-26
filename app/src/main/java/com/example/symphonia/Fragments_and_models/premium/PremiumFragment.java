package com.example.symphonia.Fragments_and_models.premium;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.symphonia.Activities.User_Interface.MainActivity;
import com.example.symphonia.Activities.User_Management.Notifications.MyFirebaseInstanceIdService;
import com.example.symphonia.Activities.User_Management.Redirect.PaymentActivity;
import com.example.symphonia.Adapters.PremiumAdapter;
import com.example.symphonia.Constants;
import com.example.symphonia.R;
import com.example.symphonia.Service.ServiceController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Fragment that handles user interaction with premium page
 * handles all requests to be premium account
 *
 * @author Hossam Alaa
 * @since 22-3-2020
 * @version 1.0
 */
public class PremiumFragment extends Fragment {

    /**
     * array of free features to be displayed
     */
    private ArrayList<String> mFeaturesFree;
    /**
     * array of premium features to be displayed
     */
    private ArrayList<String> mFeaturesPrem;

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater object that can be used to inflate any views in the fragment
     * @param container parent view that the fragment's UI should be attached to.
     * @param savedInstanceState this fragment is being re-constructed from a previous saved state as given here.
     * @return returns the View for the fragment's UI
     */

    /**
     * holds text view that advertises premium
     */
    private TextView text_view_try_premium;
    /**
     * holds text view that shows user's current plan
     */
    private TextView text_view_current_plan;
    /**
     * button that holds promote premium button
     */
    private Button btn_promote;

    /**
     * inflate view of fragment
     *
     * @param inflater           inflate the fragment
     * @param container          viewGroup of the fragment
     * @param savedInstanceState saved data
     * @return fragment view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //sets layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_premium, container, false);

        //checks user type
        checkPremium(root);
        //makes text view with anchor to be clickable
        TextView text_view_anchor = (TextView) root.findViewById(R.id.t1);
        text_view_anchor.setMovementMethod(LinkMovementMethod.getInstance());

        //fills arrays of features from stored strings for free
        mFeaturesFree = new ArrayList<>();
        mFeaturesFree.add(getResources().getString(R.string.ad_break));
        mFeaturesFree.add(getResources().getString(R.string.with_locked_songs));

        //fills arrays of features from stored strings for premium
        mFeaturesPrem = new ArrayList<>();
        mFeaturesPrem.add(getResources().getString(R.string.ad_free_music));
        mFeaturesPrem.add(getResources().getString(R.string.play_any_song));

        //object of layoutManager that controls recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()
                    , LinearLayoutManager.HORIZONTAL, false);

        //gets layout of recycler view by id
        RecyclerView recyclerView = root.findViewById(R.id.rv_premium);

        //snapHelper that makes features for recycler view
        SnapHelper snapHelper = new PagerSnapHelper();

        //indicates the position of current item for dotIndicator
        layoutManager.findFirstVisibleItemPosition();
        recyclerView.setLayoutManager(layoutManager);

        //set Dot indicator for recycler view
        recyclerView.addItemDecoration(new com.example.symphonia.Adapters.CirclePagerIndicatorDecoration());

        //makes one item shown in scrolling
        snapHelper.attachToRecyclerView(recyclerView);

        //set adapter of recycler view with arrays of features
        PremiumAdapter adapter = new PremiumAdapter(mFeaturesFree, mFeaturesPrem, getContext());
        recyclerView.setAdapter(adapter);

        //holds listener for promote premium button
        btn_promote = (Button) root.findViewById(R.id.promote_premium);
        btn_promote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //if mode is mock, then change user's type immediately
                if(Constants.DEBUG_STATUS){
                    ServiceController serviceController = ServiceController.getInstance();
                    serviceController.promotePremium(getContext(), root, Constants.currentToken);
                    checkPremium(root);
                } else{
                    //if mode is server, then go to apply page to send email
                    Intent intent = new Intent(getActivity(), PaymentActivity.class);
                    intent.putExtra("request","true");
                    startActivity(intent);
                }
            }
        });

        return root;
    }

    /**
     * checks type of user to change page contain
     * @param root root view of fragment
     */
    public void checkPremium(View root){

        //get button,plans ids
        btn_promote = (Button) root.findViewById(R.id.promote_premium);
        text_view_current_plan = (TextView) root.findViewById(R.id.try_premium);
        text_view_try_premium = (TextView) root.findViewById(R.id.symphonia_free);

        //checks if user is premium
        if(Constants.currentUser.isPremuim()){
            //lock promote button, set new plan of premium
            btn_promote.setEnabled(false);
            btn_promote.setBackgroundResource(R.drawable.btn_curved_gray);
            text_view_current_plan.setText(getResources().getString(R.string.symphonia_premium));
            text_view_try_premium.setText(getResources().getString(R.string.promoted_premium));
        } else{
            //if not premium then set current plan
            btn_promote.setEnabled(true);
            btn_promote.setBackgroundResource(R.drawable.btn_curved_white);
            text_view_current_plan.setText(getResources().getString(R.string.try_premium));
            text_view_try_premium.setText(getResources().getString(R.string.spotify_free));
        }
    }

    /**
     * on resume fragment, make flag for main activity that it's opened
     */
    @Override
    public void onResume() {
        super.onResume();
        if(getTag() != null && getTag().equals("premium"))
            ((MainActivity)getActivity()).setRoot(false);
    }

    /**
     * on pause fragment, reset flag of main activity
     */
    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).setRoot(true);
    }

}