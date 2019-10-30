package com.shantanu.society.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shantanu.society.R;

public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText e1,e2,e3;
    private FirebaseAuth mAuth;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        e1 = findViewById(R.id.RegisterEmail);
        e2 = findViewById(R.id.RegisterPassword);
        e3 = findViewById(R.id.RegisterPassword1);
        findViewById(R.id.RegisterRegister).setOnClickListener(this);
        progress= new ProgressDialog(this);
        progress.setTitle("Logging in");
        progress.setMessage("Please wait...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RegisterRegister:{
                String s1,s2,s3;
                s1 = e1.getText().toString().trim();
                s2 = e2.getText().toString().trim();
                s3 = e3.getText().toString().trim();
                if(s2.equals(s3)){
                    mAuth.createUserWithEmailAndPassword(s1, s2)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(Register.this, "User Created",Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Register.this, "User creation failed.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                progress.show();
                startActivity(new Intent(this, SignIn.class));
                progress.dismiss();
                finish();
                break;
            }
            case R.id.RegisterLogin:{
                startActivity(new Intent(this,SignIn.class));
                finish();
            }
        }
    }
}
