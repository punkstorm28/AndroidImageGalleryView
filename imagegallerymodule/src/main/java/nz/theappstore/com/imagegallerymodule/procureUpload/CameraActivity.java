package nz.theappstore.com.imagegallerymodule.procureUpload;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultAdapter;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultListener;

import nz.theappstore.com.imagegallerymodule.R;
import nz.theappstore.com.imagegallerymodule.procureUpload.util.ButtonFragmentEventsLive;
import rx.Observer;

public class CameraActivity extends AppCompatActivity {

    public static String FRAGMENT_TAG = "camera_fragment";
    private CameraFragment cameraFragment;
    private byte[] currentImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity_layout);
        attachCameraFragment();
        attachButtonsFragment();
        setupEverything();
    }

    void attachCameraFragment() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
        cameraFragment = CameraFragment.newInstance(new Configuration.Builder().build());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, cameraFragment, FRAGMENT_TAG)
                .commit();

        cameraFragment.setResultListener(new CameraFragmentResultAdapter() {
            @Override
            public void onPhotoTaken(byte[] bytes, String filePath) {
                //called when the photo is taken and saved

            }
        });
        }

    void attachButtonsFragment() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
        ButtonsFragment buttonsFragment = new ButtonsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.buttons_container, buttonsFragment,"buttons_fragment")
                .commit();
    }

    void setupEverything() {
        ButtonFragmentEventsLive.getPhotoClickButtonLive().subscribe(new Observer<View>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(View view) {

                cameraFragment.takePhotoOrCaptureVideo(new CameraFragmentResultAdapter() {
                    @Override
                    public void onPhotoTaken(byte[] bytes, String filePath) {
                        super.onPhotoTaken(bytes, filePath);
                        CameraActivity.this.currentImage = bytes;

                    }

                }, "/images", "photo");
            }
        });
        ButtonFragmentEventsLive.getDiscardPhotoButtonLive().subscribe(new Observer<View>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(View view) {
                attachCameraFragment();
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

            }
        });
    }

}
