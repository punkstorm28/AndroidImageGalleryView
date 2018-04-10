package nz.theappstore.com.imagegallerymodule.procureUpload.util;

import android.view.View;

import rx.subjects.PublishSubject;

public class ButtonFragmentEventsLive {
    private static PublishSubject<View> photoClickButton = PublishSubject.create();
    private static PublishSubject<View> discardPhotoButton = PublishSubject.create();
    private static PublishSubject<View> uploadPhotoButton = PublishSubject.create();

    public static PublishSubject<View> getPhotoClickButtonLive() {
        return photoClickButton;
    }

    public static void setPhotoClickButtonEvent(View  v) {
       photoClickButton.onNext(v);
    }

    public static PublishSubject<View> getDiscardPhotoButtonLive() {
        return discardPhotoButton;
    }

    public static void setDiscardPhotoButtonEvent(View v) {
        discardPhotoButton.onNext(v);
    }

    public static PublishSubject<View> getUploadPhotoButtonLive() {
        return uploadPhotoButton;
    }

    public static void setUploadButtonEvent(View  v) {
        uploadPhotoButton.onNext(v);
    }

}
