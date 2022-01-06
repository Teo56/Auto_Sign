package AutoSign;

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

    public void setURL(String url) {
        this.url = url;
    }

    public String getEmbed(){
        String link = getURL();
        String[] videoID = link.split("v=");
        embed = "https://www.youtube.com/embed/" + videoID[1] + "?enablejsapi=1&html5=1";
        return embed;
    }

    public void sendURL(String url) throws JSONException {
        SendUrl.sendurl(url);
    }

    public void downloadVideo(String url) throws IOException {
        DownloadObject.downloadObject("autosign-334513", "data_bucket_video_swag", "finals.mp4", "/static/images/file.mp4");
    }

    public String printDBURL() {
        return PrintDbUrl.printdburl();
    }
}
