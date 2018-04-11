package nz.theappstore.com.imagegallerymodule.procureUpload;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import nz.theappstore.com.imagegallerymodule.R;
import nz.theappstore.com.imagegallerymodule.procureUpload.util.ButtonFragmentEventsLive;
import nz.theappstore.com.imagegallerymodule.uploadHandler.ImageUploader;
import rx.Observer;

public class CameraActivity extends AppCompatActivity {

    public static String FRAGMENT_TAG = "camera_fragment";
    private CameraFragment cameraFragment;
    private byte[] currentImage;
    Uri imageUri;
    private ImageView imagePreview;
    private Bitmap bitmap;
    private File imageFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity_layout);
        imagePreview = findViewById(R.id.image_preview);
        setupEverything();
        takePhoto();
        attachButtonsFragment();
    }

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, 100);
    }

    void attachButtonsFragment() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
        ButtonsFragment buttonsFragment = new ButtonsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.buttons_container, buttonsFragment, "buttons_fragment")
                .commit();
    }

    void setupEverything() {
        ButtonFragmentEventsLive.getDiscardPhotoButtonLive().subscribe(new Observer<View>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(View view) {
                takePhoto();
            }
        });

        ButtonFragmentEventsLive.getUploadPhotoButtonLive().subscribe(new Observer<View>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(View view) {
                ImageUploader uploader = new ImageUploader("http://192.168.0.4:8080/lead/3/image", imageFile.getPath());
                uploader.uploadMultipart(CameraActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getContentResolver();
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        imageFile = compressAndSaveImage(bitmap);
                        imagePreview.setImageBitmap(bitmap);

                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    private File compressAndSaveImage(Bitmap mBitmap) {
        File f3 = new File(Environment.getExternalStorageDirectory() + "/AsBuiltManager/");
        if (!f3.exists())
            f3.mkdirs();
        OutputStream outStream = null;
        File file = new File(Environment.getExternalStorageDirectory() + "/AsBuiltManager/" + "image" + ".jpg");
        try {
            outStream = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outStream);
            outStream.close();
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
