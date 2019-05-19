package com.heroesapi;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadImageStrict_Activity extends AppCompatActivity {
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image_strict_);
        imgPhoto = findViewById(R.id.imgPhoto);

        loadFromUrl();
    }

    private void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy =
                new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);

    }

    private void loadFromUrl() {
        StrictMode();
        try {
            String imgUrl = "https://static.interestingengineering.com/images/APRIL/sizes/black_hole_resize_md.jpg";
            URL url = new URL(imgUrl);
            imgPhoto.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }
}
