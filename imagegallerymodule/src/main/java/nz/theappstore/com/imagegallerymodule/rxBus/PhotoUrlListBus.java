package nz.theappstore.com.imagegallerymodule.rxBus;


import java.util.List;

import nz.theappstore.com.imagegallerymodule.rxBus.utils.PhotoEntity;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class PhotoUrlListBus {

    private static ReplaySubject<List<PhotoEntity>> subject = ReplaySubject.create();

    public static void setPhotoList(List<PhotoEntity> photos) {
        subject.onNext(photos);
    }

    public static Observable<List<PhotoEntity>> getSpacePhotoObservable() {
        return subject;
    }

}
