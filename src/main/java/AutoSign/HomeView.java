package AutoSign;

import AutoSign.functions.CheckVideoExist;
import AutoSign.functions.DownloadObject;
import AutoSign.functions.PrintDbUrl;
import AutoSign.functions.SendUrl;
import org.json.JSONException;

import java.io.IOException;

// The HomeView class is used as a holder for the url submitted by the user, as well as important functions to retrieve it and perform operations of the youtube and translation videos
public class HomeView {

    private String url;
    private String embed;

    // returns the saved url
    public String getURL() {
        return url;
    }

    // Returns a string used for dysplaying the youtube video in the result page
    public String getEmbed(){
        String link = getURL();
        String[] videoID = link.split("v="); // splits the string at "v=" to obtain the videoID
        embed = "https://www.youtube.com/embed/" + videoID[1];
        return embed;
    }
    
    // Returns a string containing the name of the saved translation video
    public String VideoFileName() {
        String link = getURL();
        String[] videoID = link.split("v=");
        String filename = videoID[1] + ".mp4";
        return filename;
    }

    // Allows to set the url string
    public void setURL(String url) {
        this.url = url;
    }


    // Method used to send the url of the submitted video to the python server through a post request
    public void sendURL(String url) throws JSONException {
        SendUrl.sendurl(url);
    }

    // Method used to stream the translation video saved in google cloud storage into the draggable window
    public void downloadVideo(String videoid) throws IOException {
        videoid = videoid + ".mp4";
        DownloadObject.downloadObject("autosign-334513", "auto-sign-main", "/tmp/" + videoid, "/tmp/" + videoid);
    }

    // Method to check when the translation video is uploaded on google cloud storage
    public boolean checkVideoExists(String url) throws IOException {
        boolean exists = CheckVideoExist.CheckVideoExists(url);

        return exists;
    }

    // Method used to print out the database entries in the html history page
    public String printDBURL() {
        return PrintDbUrl.printdburl();
    }
}
