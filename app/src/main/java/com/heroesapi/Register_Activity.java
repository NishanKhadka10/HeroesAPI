package com.heroesapi;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import heroesapi.HeroesAPI;
import model.Heroes;
import model.ImageResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import url.Url;

public class Register_Activity extends AppCompatActivity {
    private EditText etName, etDesc;
    private Button btnSave, btnFieldMap, btnField;
    private ImageView imgPhoto;
    String imagePath, imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        etName = findViewById(R.id.etname);
        etDesc = findViewById(R.id.etDesc);

        btnSave = findViewById(R.id.btnSave);
        btnField = findViewById(R.id.btnField);
        btnFieldMap = findViewById(R.id.btnFieldMap);
        imgPhoto = findViewById(R.id.imgPhoto);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
        btnField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveField();
            }
        });

        btnFieldMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveFieldMap();
            }
        });

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });
    }

    private void Save() {
        SaveImageOnly();
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("desc", desc);
        map.put("image", imageName);

        HeroesAPI heroesAPI = Url.getInstance().create(HeroesAPI.class);

//        Heroes heroes = new Heroes(name, desc);
        Call<Void> heroesCall = heroesAPI.addHero(map);
        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Register_Activity.this, "Code" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(Register_Activity.this, "Successfully Saved", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Register_Activity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

        //openDashboard
        Intent intent = new Intent(Register_Activity.this,LoadImageStrict_Activity.class);
        startActivity(intent);
        finish();
    }


    //Using @Field
    private void SaveField() {
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        Heroes heroes = new Heroes(name, desc);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroesAPI heroesAPI = retrofit.create(HeroesAPI.class);
        Call<Void> heroesCall = heroesAPI.addHero(name, desc);
        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Register_Activity.this, "Code" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(Register_Activity.this, "Successfully Saved", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Register_Activity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    //Using @FieldMap(Key and Value)
    private void SaveFieldMap() {
        String name = etName.getText().toString();
        String desc = etDesc.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("desc", desc);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroesAPI heroesAPI = retrofit.create(HeroesAPI.class);
        Call<Void> heroesCall = heroesAPI.addHero(map);
        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Register_Activity.this, "Code" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(Register_Activity.this, "Successfully Saved", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Register_Activity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show();
            }
        }
        Uri uri = data.getData();
        imagePath = getRealPathFromUri(uri);
        priviewImage(imagePath);

    }

    private void priviewImage(String imagePath) {

        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            Bitmap myBitMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgPhoto.setImageBitmap(myBitMap);
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;

    }

    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void SaveImageOnly() {
        File file = new File(imagePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);
        HeroesAPI heroesAPI = Url.getInstance().create(HeroesAPI.class);
        Call<ImageResponse> responseBodyCall = heroesAPI.uploadImage(body);
        StrictMode();

        Response<ImageResponse> imageResponseResponse = null;
        try {
            imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}

