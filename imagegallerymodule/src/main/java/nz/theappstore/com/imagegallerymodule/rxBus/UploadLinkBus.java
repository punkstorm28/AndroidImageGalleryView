package nz.theappstore.com.imagegallerymodule.rxBus;

import rx.subjects.PublishSubject;

public class UploadLinkBus {

    private static PublishSubject<String> subject = PublishSubject.create();

    public static void setPhotoList(String uploadLink) {
        subject.onNext(uploadLink);
    }

    public static PublishSubject<String> getSpacePhotoObservable() {
        return subject;
    }
}
