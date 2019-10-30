package com.shantanu.society.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.shantanu.society.R;
import com.shantanu.society.presentation.LocalFunctions;
import com.shantanu.society.presentation.SQLiteHelper;
import java.io.IOException;
import java.util.Objects;

public class MyData extends AppCompatActivity implements View.OnClickListener {
    Bitmap bitmap = null;
    Uri filePath;
    private static final int CAMERA_REQUEST = 1888;
    EditText e2,e1;
    SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);
        sqLiteHelper = new SQLiteHelper(this,"Data.sqlite",null,1);
        sqLiteHelper.queryData(getResources().getString(R.string.queryTable));
        findViewById(R.id.SelectImage).setOnClickListener(this);
        findViewById(R.id.CCreate).setOnClickListener(this);
        findViewById(R.id.ClickPhoto).setOnClickListener(this);
        e1 = findViewById(R.id.CTitle);
        e2 = findViewById(R.id.CDesc);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SelectImage: {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 111);
                break;
            }
            case R.id.ClickPhoto: {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            }
            case R.id.CCreate: {
                String title = e1.getText().toString().trim();
                String Desc = e2.getText().toString().trim();
                LocalFunctions.uploadImage(title, Desc,filePath,this,sqLiteHelper);
                break;
            }
        }
    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                filePath = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }
    }

}
