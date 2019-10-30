package com.shantanu.society.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shantanu.society.R;
import com.shantanu.society.model.GlideApp;
import com.shantanu.society.model.MyAppGlideModule;
import com.shantanu.society.presentation.SQLiteHelper;

public class ViewData extends AppCompatActivity implements View.OnClickListener {
    SQLiteHelper sqLiteHelper;
    TableLayout tableLayout;
    TextView t1,t2;
    ImageView iv;
    int count,maxcount;
    private Exception IOException;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        sqLiteHelper = new SQLiteHelper(this,"Data.sqlite",null,1);
        sqLiteHelper.queryData(getResources().getString(R.string.queryTable));
        t1 = findViewById(R.id.VTitle);
        t2 = findViewById(R.id.VDesc);
        iv = findViewById(R.id.Viv);
        count = 1;
        maxcount = sqLiteHelper.getMaxCount();
        if(maxcount==0){
            Toast.makeText(this,"No Data stored, Please add data",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MyData.class));
            finish();
        }
        CreateLayout(count);
        findViewById(R.id.Vprev).setOnClickListener(this);
        findViewById(R.id.VNext).setOnClickListener(this);
    }
    public void CreateLayout(int id){
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM Data where id="+id);
        while (cursor.moveToNext()) {
            t1.setText(cursor.getString(1));
            t2.setText(cursor.getString(2));
            GlideApp.with(this).load(cursor.getString(3)).into(iv);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Vprev: {
                if(maxcount>1){
                    count--;
                    CreateLayout(count);}
                else
                    Toast.makeText(this,"First data in the set", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.VNext:{
                if(count<maxcount){
                    count++;
                    CreateLayout(count);}
                else{
                    Toast.makeText(this,"Last data in the set", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
