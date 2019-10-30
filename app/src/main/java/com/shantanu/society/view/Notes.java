package com.shantanu.society.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shantanu.society.R;
import com.shantanu.society.presentation.LocalFunctions;

import org.w3c.dom.Text;

import java.util.Map;

public class Notes extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        tableLayout = findViewById(R.id.tableLayout);
        LocalFunctions.showNotices(this,tableLayout,getResources().getDrawable(R.drawable.row_border),getResources().getDimension(R.dimen.textsize));
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()) {
            case R.id.CreateData: {
                startActivity(new Intent(this, MyData.class));
                break;
            }
            case R.id.ViewData: {
                startActivity(new Intent(this, ViewData.class));
                break;
            }
            case R.id.Logout: {
                startActivity(new Intent(this, SignIn.class));
                finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
