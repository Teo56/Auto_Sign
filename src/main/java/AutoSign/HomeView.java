package AutoSign;

import AutoSign.functions.CheckVideoExist;
import AutoSign.functions.DownloadObject;
import AutoSign.functions.PrintDbUrl;
import AutoSign.functions.SendUrl;
import org.json.JSONException;

import java.io.IOException;


public class HomeView {

    private String url;
    private String embed;

    public String getURL() {
        return url;
    }

    public String getEmbed(){
        String link = getURL();
        String[] videoID = link.split("v=");
        embed = "https://www.youtube.com/embed/" + videoID[1];
        return embed;
    }
    
    public String VideoFileName() {
        String link = getURL();
        String[] videoID = link.split("v=");
        String filename = videoID[1] + ".mp4";
        return filename;
    }

    public void setURL(String url) {
        this.url = url;
    }


    public void sendURL(String url) throws JSONException {
        SendUrl.sendurl(url);
    }

    public void downloadVideo(String videoid) throws IOException {
        videoid = videoid + ".mp4";
        DownloadObject.downloadObject("autosign-334513", "auto-sign-main", "/tmp/" + videoid, "/tmp/" + videoid);
    }

    public boolean checkVideoExists(String url) throws IOException {
        boolean exists = CheckVideoExist.CheckVideoExists(url);
        return exists;
    }

    public String printDBURL() {
        return PrintDbUrl.printdburl();
    }
}
