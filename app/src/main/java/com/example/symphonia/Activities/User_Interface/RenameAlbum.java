package com.example.symphonia.Activities.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.symphonia.R;

/**
 * RenameAlbum use to show view to rename album or track
 * @author Mahmoud Amr Nabil
 * @version 1.0
 */
public class RenameAlbum extends AppCompatActivity {
    private TextView save;
    private TextView cancel;
    private EditText editText;
    private String preName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_album);

        Bundle b = getIntent().getExtras();
        preName= b.getString("name");

        save=findViewById(R.id.save_rename_album);
        cancel=findViewById(R.id.cancel_rename_album);
        editText=findViewById(R.id.edit_rename_album);

        editText.setText(preName);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", editText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", preName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
