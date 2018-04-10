package nz.theappstore.com.imagegallerymodule.rxBus.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Chike on 2/11/2017.
 */

public class PhotoEntity implements Parcelable {

    private String mUrl;
    private String mTitle;

    public PhotoEntity(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected PhotoEntity(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<PhotoEntity> CREATOR = new Creator<PhotoEntity>() {
        @Override
        public PhotoEntity createFromParcel(Parcel in) {
            return new PhotoEntity(in);
        }

        @Override
        public PhotoEntity[] newArray(int size) {
            return new PhotoEntity[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static  ArrayList<PhotoEntity> getPhotos() {
        ArrayList<PhotoEntity> photos = new ArrayList<>();
        photos.add(new PhotoEntity("http://i.imgur.com/zuG2bGQ.jpg", "Galaxy"));
        photos.add(new PhotoEntity("http://i.imgur.com/ovr0NAF.jpg", "Space Shuttle"));
        photos.add(new PhotoEntity("http://i.imgur.com/qpr5LR2.jpg", "Earth"));
        return photos;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }
}
