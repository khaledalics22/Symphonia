package com.example.symphonia.Activities.User_Interface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
/**
 * AddAlbum to add new album
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class AddAlbum extends AppCompatActivity {
    private TextView save;
    private ImageView albumImg;
    private EditText name;
    private EditText copyRights;
    private String copyRightsType;
    private String image;
    private ImageView back;
    private TextView textView;
    private String albumId;
    private RadioGroup radioGroup;
    private boolean track_album=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            albumId=b.getString("albumId");
            if(albumId!=null){
                track_album=true;
            }
        }

        //attach views
        back=findViewById(R.id.img_back_artist_albums);
        save=findViewById(R.id.save);
        name=findViewById(R.id.edit_text_album);
        copyRights=findViewById(R.id.copyrights);
        albumImg=findViewById(R.id.album_img);
        textView=findViewById(R.id.tv_copyrights);
        radioGroup=findViewById(R.id.choose_copyRight);
        //handle clicks
        if(track_album) {
            radioGroup.setVisibility(View.GONE);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("audio/mpeg");
                    startActivityForResult(intent, 3333);
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        albumImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    RestApi restApi=new RestApi();
                    BitmapDrawable drawable=(BitmapDrawable)albumImg.getDrawable();
                    Bitmap bitmap=drawable.getBitmap();
                    try {
                        image= Utils.getStringImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("image", image);
                    intent.putExtra("albumType", "album");
                    intent.putExtra("copyRights",copyRights.getText().toString());
                    intent.putExtra("copyRightsType",copyRightsType);
                    setResult(RESULT_OK, intent);
                    finish();
            }
        });
    }
    //////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                albumImg.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (requestCode == 3333 && resultCode == Activity.RESULT_OK) {
            if ((data != null) && (data.getData() != null)) {
                Uri audioFileUri = data.getData();
                //File file=new File(String.valueOf(new File(getRealPathFromURI(audioFileUri))));
                byte[] bytearray = new byte[0];
                try {
                    InputStream inputStream =
                            getContentResolver().openInputStream(audioFileUri);

                    bytearray = new byte[inputStream.available()];
                    //bytearray = toByteArray(inputStream);

                    Toast.makeText(this, "" + bytearray, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String reducedArray = Base64.encodeToString(bytearray, Base64.NO_WRAP);
                RestApi restApi=new RestApi();
                restApi.createTrack(this,reducedArray,albumId);
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_p:
                if (checked)
                    copyRightsType="p";
                    break;
            case R.id.radio_c:
                if (checked)
                    copyRightsType="c";
                    break;
        }
    }
}
