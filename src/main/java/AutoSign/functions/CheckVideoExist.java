package AutoSign.functions;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.IOException;


public class CheckVideoExist {

    public static boolean CheckVideoExists(String videoid) throws IOException {
        boolean exists;
        String videoID = videoid;
        String vidpath = "/tmp/" + videoID + ".mp4";
        String bucketName = "auto-sign-main";

        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("google-credentials.json"));

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId("autosign-336618").build().getService();

        if (storage.get(BlobId.of(bucketName, vidpath)) != null) {
            exists = true;
        } else {
            exists = false;
        }

        return exists;
    }


}
