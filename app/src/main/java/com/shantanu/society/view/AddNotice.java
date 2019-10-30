package com.shantanu.society.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shantanu.society.R;

public class AddNotice extends AppCompatActivity {
    EditText e1,e2;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        e1 = findViewById(R.id.NTitle);
        e2 = findViewById(R.id.NDesc);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        findViewById(R.id.postNotice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = e1.getText().toString().trim();
                String desc = e2.getText().toString().trim();
                mDatabase.child(title).setValue(desc);
                e1.setText("");
                e2.setText("");
                Toast.makeText(AddNotice.this,"Notice Sent",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
