package nz.theappstore.com.imagegallerymodule.uploadHandler;

import android.content.Context;
import android.util.Log;

import net.gotev.uploadservice.Logger;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;

public class ImageUploader {

    final String uploadUrl;
    final String filePath;
    public ImageUploader(String uploadUrl, String filePath) {
        this.filePath = filePath;
        this.uploadUrl = uploadUrl;
    }

    public String uploadMultipart(final Context context) {
        try {
            File file = new File(filePath);
            Log.i("FILE_UPLOAD", "exists = "+file.exists()+ " size= "+file.length());
            Logger.setLogLevel(Logger.LogLevel.DEBUG);

            return new MultipartUploadRequest(context, uploadUrl) {}
                            // starting from 3.1+, you can also use content:// URI string instead of absolute file
                            .addFileToUpload(filePath, "file")
                            .setNotificationConfig(new UploadNotificationConfig() )
                            .setMaxRetries(2)
                            .setBasicAuth("Ram","Crimson")
                            .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
        return null;
    }
}
