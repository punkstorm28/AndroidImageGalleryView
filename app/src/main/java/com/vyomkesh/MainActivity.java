package com.vyomkesh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.vyomkeshjha.imagegallery.R;

import java.util.ArrayList;

import nz.theappstore.com.imagegallerymodule.imageGallery.GalleryActivity;
import nz.theappstore.com.imagegallerymodule.procureUpload.CameraActivity;
import nz.theappstore.com.imagegallerymodule.rxBus.PhotoUrlListBus;
import nz.theappstore.com.imagegallerymodule.rxBus.utils.PhotoEntity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<PhotoEntity> photoEntities = new ArrayList<>();
        photoEntities.add(new PhotoEntity("http://i.imgur.com/3wQcZeY.jpg", "Satellite"));

        PhotoUrlListBus.setPhotoList(photoEntities);
        Button showGalleryBtn = (Button) findViewById(R.id.btn_show_gallery);
        showGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(galleryIntent);
            }
        });

        Button showGif = (Button) findViewById(R.id.camera_button);
        showGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gifIntent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(gifIntent);
            }
        });

    }
}
