package AutoSign.functions;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;


public class DownloadObject {
    public static void downloadObject(
            String projectId, String bucketName, String objectName, String destFilePath) throws IOException {
        
        System.out.print("dest file path is:" + destFilePath);
        System.out.print("object name is:" + objectName);
        System.out.print(bucketName);

        // Change the directory of the credentials to get access to the Database online
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("google-credentials.json"));

        // This is to set the name of the project online
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId("autosign-336618").build().getService();

        // THIS IS TO DOWNLOAD THE FILE FROM THE CLOUD
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(Paths.get(destFilePath));

        System.out.println(
                "Downloaded object "
                        + objectName
                        + " from bucket name "
                        + bucketName
                        + " to "
                        + destFilePath);
    }
}
