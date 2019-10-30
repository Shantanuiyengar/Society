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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shantanu.society.R;

public class SignIn extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    EditText e1,e2;
    String email,password;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        e1 = findViewById(R.id.LoginEmail);
        e2 = findViewById(R.id.LoginPassword);
        findViewById(R.id.LoginLogin).setOnClickListener(this);
        findViewById(R.id.LoginRegister).setOnClickListener(this);
        progress= new ProgressDialog(this);
        progress.setTitle("Logging in");
        progress.setMessage("Please wait...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.LoginLogin:{
                email = e1.getText().toString().trim();
                password = e2.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if(email.equals(getResources().getString(R.string.admin))){
                                        System.out.println(email);
                                        startActivity(new Intent(SignIn.this,AddNotice.class));
                                        progress.dismiss();
                                        finish();
                                    }
                                    else {
                                        startActivity(new Intent(SignIn.this, Notes.class));
                                        progress.dismiss();
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(SignIn.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(this, new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress.dismiss();
                        e.printStackTrace();
                    }
                });
                progress.show();
                break;
            }
            case R.id.LoginRegister:{
                startActivity(new Intent(this, Register.class));
                break;
            }
        }

    }
}
