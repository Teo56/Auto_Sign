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

    // This method checks whether the translation video (recognised by its filename) is present in google cloud storage
    // When that happens, it returns true
    public static boolean CheckVideoExists(String videoid) throws IOException {
        boolean exists;
        String videoID = videoid;
        String vidpath = "/tmp/" + videoID + ".mp4"; // What to look for in the cloud storage
        String bucketName = "auto-sign-main"; // bucket of the cloud where to look into

        // Credentials to access the cloud
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("google-credentials.json"));

        // Access the cloud
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId("autosign-336618").build().getService();

        // Check if the video is there or not
        if (storage.get(BlobId.of(bucketName, vidpath)) != null) {
            exists = true; // If video found exits = true
        } else {
            exists = false; // If video not found exit = false
        }

        return exists; // returns the state of the search (found or not found)
    }


}
