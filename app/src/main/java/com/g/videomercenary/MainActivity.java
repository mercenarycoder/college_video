package com.g.videomercenary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
Button uploader,reader,saver;
public  static  int GetRe=2;
private Uri video_uri=null;
StorageReference stRef;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    uploader=(Button)findViewById(R.id.uploader);
    reader=(Button)findViewById(R.id.reader);
    saver=(Button)findViewById(R.id.saver);
    uploader.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Intent video=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(isCameraPermissionGranted()) {
            if (video.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(video, GetRe);
            }
        }
        else
        {
            Toast.makeText(MainActivity.this,"First Camera permission",Toast.LENGTH_SHORT).show();
        }
        }
    });
    reader.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(video_uri!=null) {
                Intent player = new Intent(MainActivity.this, videoplayer.class);
                player.putExtra("uri", video_uri.toString());
                startActivity(player);
            }
            else
            {
                Toast.makeText(MainActivity.this,"Record a Video First",Toast.LENGTH_SHORT).show();
            }
            }
    });
    saver.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           if(video_uri!=null) {
               Random generator = new Random();
               int n = 10000;
               n = generator.nextInt(n);
               String fname = "Video-" + n + ".mp4";
               stRef = FirebaseStorage.getInstance().getReference().child("Videos");

               StorageReference stst = stRef.child(fname);
               UploadTask upup = stst.putFile(video_uri);
               upup.addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {

                       Toast.makeText(MainActivity.this,
                               "Failed ......................", Toast.LENGTH_SHORT).show();

                   }
               }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       Toast.makeText(MainActivity.this,
                               "Shop Video uploaded Successfully ...", Toast.LENGTH_SHORT).show();

                   }
               }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       if (task.isSuccessful()) {
                           Toast.makeText(MainActivity.this,
                                   "Shop Video uploaded Successfully 2...", Toast.LENGTH_SHORT).show();
                            video_uri=null;
                       }
                   }
               });

           }
           else
           {
               Toast.makeText(MainActivity.this,"Video Uploaded re record it",Toast.LENGTH_SHORT).show();
           }
        }
    });

   }
    public  boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                //save();
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                        .CAMERA}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //save();
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode== GetRe && resultCode==RESULT_OK && data!=null)
    {
    video_uri=data.getData();
    }
    }
}
