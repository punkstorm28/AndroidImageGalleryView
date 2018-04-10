package nz.theappstore.com.imagegallerymodule.uploadHandler;

import android.content.Context;
import android.util.Log;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

public class ImageUploader {

    final String uploadUrl;
    final String filePath;
    ImageUploader(String uploadUrl, String filePath) {
        this.filePath = filePath;
        this.uploadUrl = uploadUrl;
    }

    public String uploadMultipart(final Context context) {
        try {
            return new MultipartUploadRequest(context, uploadUrl)
                            // starting from 3.1+, you can also use content:// URI string instead of absolute file
                            .addFileToUpload(filePath, "file")
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .startUpload();
        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
        return null;
    }
}
