package com.shantanu.society.presentation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shantanu.society.R;
import com.shantanu.society.model.MyDataDAO;
import com.shantanu.society.view.Notes;

import java.util.UUID;

public class LocalFunctions {

    public static void uploadImage(final String title, final String desc, Uri filePath, final Context context, final SQLiteHelper sqLiteHelper) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        final MyDataDAO myDataDAO = new MyDataDAO();
        final String[] url = {""};
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final StorageReference storageReferencefilepath = storageReference.child(UUID.randomUUID().toString());
            storageReferencefilepath.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            storageReferencefilepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    System.out.println(task.getResult().toString());
                                    url[0] =  task.getResult().toString();
                                    uploadToDatabase();
                                }
                                private void uploadToDatabase() {
                                    myDataDAO.setName(title);
                                    myDataDAO.setDesc(desc);
                                    myDataDAO.setImage(url[0]);
                                    sqLiteHelper.insertData(myDataDAO);
                                }
                            });
                            Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, Notes.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

        }
    }
    public static void showNotices(final Context context, final TableLayout tableLayout, final Drawable rowlayout, final float textlayout) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    tableLayout.removeAllViews();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TableRow tr = new TableRow(context);
                    tr.setBackground(rowlayout);
                    tr.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    TextView title = new TextView(context);
                    title.setText((String)dataSnapshot1.getKey()+"\n"+(String)dataSnapshot1.getValue());
                    title.setTextSize(textlayout);
                    title.setTextColor(Color.BLACK);
                    title.setPadding(5, 5, 5, 5);
                    tr.addView(title);
                    tableLayout.addView(tr, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
