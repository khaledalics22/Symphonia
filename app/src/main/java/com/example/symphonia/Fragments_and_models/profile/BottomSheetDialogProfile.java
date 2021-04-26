package com.example.symphonia.Fragments_and_models.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.symphonia.Activities.User_Interface.RenameAlbum;
import com.example.symphonia.Constants;
import com.example.symphonia.Entities.Container;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;
import com.example.symphonia.Service.ServiceController;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

/**
 * BottomSheetDialogProfile to show the BottomSheetDialogProfile layout
 *  * @author Mahmoud Amr Nabil
 *  * @version 1.0
 */
public class BottomSheetDialogProfile extends BottomSheetDialogFragment {

    private Container profile;
    private int chooseLayout;
    private ServiceController controller;

    private LinearLayout options1;
    private View divider;
    private LinearLayout following;
    private LinearLayout follow;
    private LinearLayout hide;
    private LinearLayout share;
    private LinearLayout homeScreen;
    private LinearLayout options2;
    private LinearLayout whatsAppShare;
    private LinearLayout facebookShare;
    private LinearLayout messengerShare;
    private LinearLayout smsShare;
    private LinearLayout linkShare;
    private LinearLayout moreShare;
    private LinearLayout artistOptions;
    private LinearLayout delete;
    private LinearLayout rename;
    private int position;

    private ArtistAlbums artistAlbums;
    private ArtistAlbumTracks artistAlbumTracks;

    public BottomSheetDialogProfile(Container container, int i) {
        profile=container;
        chooseLayout=i;
    }

    public BottomSheetDialogProfile(Container container, int i, ArtistAlbums artAlbums, int p){
        profile=container;
        chooseLayout=i;
        position=p;
        artistAlbums=artAlbums;
    }

    public BottomSheetDialogProfile(Container container, int i, ArtistAlbumTracks artistAlbumTrks, int p){
        profile=container;
        chooseLayout=i;
        position=p;
        artistAlbumTracks=artistAlbumTrks;
    }

    private View.OnClickListener delTrack=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!Constants.DEBUG_STATUS){
                RestApi restApi=new RestApi();
                restApi.deleteTrack(getContext(),artistAlbumTracks,profile.getId(),position);
            }
            else {
                controller.deleteTrack(getContext(),artistAlbumTracks,profile.getId(),position);
            }
            dismiss();
        }
    };

    private View.OnClickListener renTrack=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent renameAlbums = new Intent(getContext(), RenameAlbum.class);
            Bundle b = new Bundle();
            b.putString("name", profile.getCat_Name());
            renameAlbums.putExtras(b);
            startActivityForResult(renameAlbums,88);
        }
    };

    private View.OnClickListener del=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!Constants.DEBUG_STATUS){
                RestApi restApi=new RestApi();
                restApi.deleteAlbum(getContext(),artistAlbums,profile.getId(),position);
            }
            else {
                controller.deleteAlbum(getContext(),artistAlbums,profile.getId(),position);
            }
            dismiss();
        }
    };

    private View.OnClickListener ren=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent renameAlbums = new Intent(getContext(), RenameAlbum.class);
            Bundle b = new Bundle();
            b.putString("name", profile.getCat_Name());
            renameAlbums.putExtras(b);
            startActivityForResult(renameAlbums,66);
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.bottom_sheet_dialog_profile, null);
        bottomSheet.setContentView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        bottomSheet.getWindow()
                .findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(R.drawable.background_bottom_sheet);


        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        controller= ServiceController.getInstance();
        //attach views
        TextView profileName = view.findViewById(R.id.tv_profile_playlist_name);
        profileName.setText(profile.getCat_Name());
        ImageView profileImage = view.findViewById(R.id.image_profile_or_playlist);
        final TextView followText=view.findViewById(R.id.tv_follow);
        final ImageView followImg=view.findViewById(R.id.imgv_follow);
        LinearLayout imageFrame = view.findViewById(R.id.image_frame);
        ImageView symphoniaImage = view.findViewById(R.id.image_symphonia);
        ImageView soundWave = view.findViewById(R.id.image_sound_wave);
        if((chooseLayout==3||chooseLayout==5)&& Constants.DEBUG_STATUS==false) {
            Picasso.get()
                    .load(profile.getImgUrl())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_album)
                    .into(profileImage);
        }
        else if(chooseLayout==4&& Constants.DEBUG_STATUS==false) {
            Picasso.get()
                    .load(profile.getImgUrl())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.img_init_profile)
                    .into(profileImage);
        }
        else
            profileImage.setImageBitmap(profile.getImg_Res());
        /*BitmapDrawable drawable=(BitmapDrawable)profileImage.getDrawable();
        Bitmap bitmap=drawable.getBitmap();

        int dominantColor = Utils.getDominantColor(bitmap);
        imageFrame.setBackgroundColor(dominantColor);
        if(!Utils.isColorDark(dominantColor)){
            symphoniaImage.setColorFilter(Color.rgb(0, 0, 0));
            soundWave.setColorFilter(Color.rgb(0, 0, 0));
        }
        else{
            symphoniaImage.setColorFilter(Color.rgb(255, 255, 255));
            soundWave.setColorFilter(Color.rgb(255, 255, 255));
        }*/
        //attach views
        options1=view.findViewById(R.id.list_of_options1);
        divider=view.findViewById(R.id.divider);
        following = view.findViewById(R.id.layout_following);
        follow=view.findViewById(R.id.layout_follow);
        hide = view.findViewById(R.id.layout_hide_playlist);
        share = view.findViewById(R.id.layout_share);
        homeScreen = view.findViewById(R.id.layout_home_screen);

        options2=view.findViewById(R.id.list_of_options2);
        whatsAppShare=view.findViewById(R.id.whatsapp_share);
        facebookShare=view.findViewById(R.id.facebook_share);
        messengerShare=view.findViewById(R.id.messenger_share);
        smsShare=view.findViewById(R.id.sms_share);
        linkShare=view.findViewById(R.id.link_share);
        moreShare=view.findViewById(R.id.more_share);

        artistOptions=view.findViewById(R.id.list_Artist_options);
        delete=view.findViewById(R.id.delete_album);
        rename=view.findViewById(R.id.rename_album);
        //handle which view will be shown
        if(chooseLayout==1){
            options1.setVisibility(View.VISIBLE);
            divider.setVisibility(View.GONE);
            following.setVisibility(View.GONE);
            follow.setVisibility(View.GONE);
            hide.setVisibility(View.GONE);
            homeScreen.setVisibility(View.GONE);
            options2.setVisibility(View.GONE);
        }
        else if(chooseLayout==2) {
            options1.setVisibility(View.GONE);
            options2.setVisibility(View.VISIBLE);
        }
        else if(chooseLayout==3){
            options2.setVisibility(View.GONE);
            options1.setVisibility(View.VISIBLE);
            divider.setVisibility(View.GONE);
            following.setVisibility(View.GONE);
            hide.setVisibility(View.GONE);
            homeScreen.setVisibility(View.VISIBLE);
            follow.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
        }
        else if(chooseLayout==4){
            options2.setVisibility(View.GONE);
            follow.setVisibility(View.GONE);
            divider.setVisibility(View.VISIBLE);
            following.setVisibility(View.VISIBLE);
            hide.setVisibility(View.VISIBLE);
            homeScreen.setVisibility(View.VISIBLE);
            share.setVisibility(View.VISIBLE);
        }
        else if(chooseLayout==5){
            options1.setVisibility(View.GONE);
            options2.setVisibility(View.GONE);
            artistOptions.setVisibility(View.VISIBLE);
            //handle clicks
            delete.setOnClickListener(del);
            rename.setOnClickListener(ren);
        }
        else if(chooseLayout==6){
            options1.setVisibility(View.GONE);
            options2.setVisibility(View.GONE);
            artistOptions.setVisibility(View.VISIBLE);
            //handle clicks
            delete.setOnClickListener(delTrack);
            rename.setOnClickListener(renTrack);
        }

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followText.getText()=="Follow") {
                    followText.setText("Stop Following");
                    followImg.setImageResource(R.mipmap.close_white_foreground);

                }
                else if(followText.getText()=="Stop Following"){
                    followText.setText("Follow");
                    followImg.setImageResource(R.drawable.ic_add);
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.dismiss();
                BottomSheetDialogProfile bottomSheet = new BottomSheetDialogProfile(profile,2);
                assert getParentFragmentManager() != null;
                bottomSheet.show(getParentFragmentManager(),bottomSheet.getTag());

            }
        });

        whatsAppShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast toast=Toast.makeText(getContext(),"no whatsapp",Toast.LENGTH_SHORT);
                }
            }
        });
        messengerShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent massengerIntent = new Intent(Intent.ACTION_SEND);
                massengerIntent.setType("text/plain");
                massengerIntent.setPackage("com.facebook.orca");
                massengerIntent.putExtra(Intent.EXTRA_TEXT, "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                try {
                    startActivity(massengerIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast toast=Toast.makeText(getContext(),"no massenger",Toast.LENGTH_SHORT);
                }
            }
        });
        facebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent1 = new Intent();
                    intent1.setPackage("com.facebook.katana");
                    intent1.setAction("android.intent.action.SEND");
                    intent1.setType("text/plain");
                    intent1.putExtra(Intent.EXTRA_TEXT, "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                    startActivity(intent1);
                } catch (Exception e) {
                    Toast toast=Toast.makeText(getContext(),"no facebook app",Toast.LENGTH_SHORT);
                }
            }
        });
        smsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("sms_body","https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(smsIntent);
                }catch (Exception e){
                    Toast toast=Toast.makeText(getContext(),"no sms app",Toast.LENGTH_SHORT);
                }
            }
        });
        linkShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                clipboard.setPrimaryClip(clip);
            }
        });
        moreShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.google.com/search?q=spotify+api+documentation&oq=spotif&aqs=chrome.0.69i59j69i57j35i39j69i60l5.3902j0j7&sourceid=chrome&ie=UTF-8");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                try {
                    startActivity(shareIntent);
                }catch (Exception e){
                    Toast toast=Toast.makeText(getContext(),"no sharing app",Toast.LENGTH_SHORT);
                }
            }
        });

        return bottomSheet;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 66 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("name");
            RestApi restApi=new RestApi();
            restApi.renameAlbum(getContext(),artistAlbums,profile.getId(),position,name);
            dismiss();
        }
        else if (requestCode == 88 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("name");
            RestApi restApi=new RestApi();
            restApi.renameTrack(getContext(),artistAlbumTracks,profile.getId(),position,name);
            dismiss();
        }
    }
}

