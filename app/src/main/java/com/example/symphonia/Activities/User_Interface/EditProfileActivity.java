package com.example.symphonia.Activities.User_Interface;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.Entities.Container;
import com.example.symphonia.Helpers.Utils;
import com.example.symphonia.R;
import com.example.symphonia.Service.RestApi;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * EditProfileActivity to show the editpofile layout
 *  @author Mahmoud Amr Nabil
 *  @version 1.0
 */
public class EditProfileActivity extends AppCompatActivity {
    private Container profile;
    private EditText editText;
    private ImageView imageView;
    private ImageView backToProfile;
    private TextView changeImg;
    private TextView save;
    private Bitmap bitmap;

    private View.OnClickListener selectImg=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 3);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Toast toast=Toast.makeText(this,"can't use this image",Toast.LENGTH_SHORT);
                toast.show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast toast=Toast.makeText(this,"can't use this image",Toast.LENGTH_SHORT);
                toast.show();
                e.printStackTrace();
            }
        }
        /*if (requestCode == 2 && resultCode == Activity.RESULT_OK){
            if ((data != null) && (data.getData() != null)){
                Uri audioFileUri = data.getData();
                //File file=new File(String.valueOf(new File(getRealPathFromURI(audioFileUri))));
                byte[] bytearray = new byte[0];
                try {
                    InputStream inputStream =
                            getContentResolver().openInputStream(audioFileUri);

                    bytearray = new byte[inputStream.available()];
                    bytearray = toByteArray(inputStream);

                    Toast.makeText(this, "" + bytearray, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String reducedArray = Base64.encodeToString(bytearray,Base64.NO_WRAP);
                reducedArray=reducedArray;
            }
        }*/
    }

    //public EditProfileActivity(Container p){profile=p;}
    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profiel);

        Bundle b = getIntent().getExtras();

        byte[] bytes = b.getByteArray("bitmap");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        String name = b.getString("name");


        editText = findViewById(R.id.edit_text_profile);
        imageView = findViewById(R.id.profile_img);
        backToProfile = findViewById(R.id.img_back_Edit_profile);
        changeImg = findViewById(R.id.tv_change_img);
        save=findViewById(R.id.save_profile);

        editText.setText(name);
        imageView.setImageBitmap(bmp);

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image=null;
                String newName=null;
                try {
                    newName=editText.getText().toString();
                    BitmapDrawable drawable=(BitmapDrawable)imageView.getDrawable();
                    Bitmap imgBitmap=drawable.getBitmap();
                    image= Utils.getStringImage(imgBitmap);
                    RestApi restApi=new RestApi();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.putExtra("name", newName);
                intent.putExtra("image", image);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        imageView.setOnClickListener(selectImg);
        changeImg.setOnClickListener(selectImg);

    }
        /*changeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/mpeg");
                startActivityForResult(intent, 2);

            }
        });
    }



    /*private String getStringFile(File file) throws IOException {
        byte[] bytes = new byte[(int)file.length()];
        String encoded = Base64.encodeToString(bytes, 0);
        return encoded;
    }

    /*private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }*/
}

