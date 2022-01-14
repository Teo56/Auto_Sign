package AutoSign;

import AutoSign.functions.DownloadObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;




@Controller
public class AutoSignController {
    /**GetMapping associates the URL / to the method home()**/
    @GetMapping("/" )
    public String Home(Model model){
        model.addAttribute("home", new HomeView());
        /** returning the logical name of the html to be viewed, here home.html**/
        return "home";
    }

    @GetMapping("/homedark" )
    public String HomeDark(Model model){
        model.addAttribute("home", new HomeView());
        return "homedark";
    }
    
    @PostMapping("/checkvideoexists")
    @ResponseBody
    public Boolean checkVideoRouter(@RequestParam("videoid") String videoid, @ModelAttribute HomeView home, Model model) throws IOException {
        model.addAttribute("home", home);
        Boolean exist;
        String[] videoID = videoid.split("v=");
        String videoId = videoID[1];

        exist = home.checkVideoExists(videoId);
        if (exist) {
            home.downloadVideo(videoId);
            return true;
        } else if (exist == false) {
            return false;
        }
        return false;
    }


    @RequestMapping(value = "/", method = RequestMethod.POST, params = "videourl")
    public String startVideo(@RequestParam("url") String url, @ModelAttribute HomeView home, Model model) {

        HomeView homeee = new HomeView();
        homeee.setURL(url);
        model.addAttribute("homeee", homeee);


        System.out.print(homeee.getURL());
        return "result";
    }


    @PostMapping("/")
    public String URLsubmit(@ModelAttribute HomeView home, Model model) throws IOException, JSONException {
        model.addAttribute("home", home);
        System.out.println(home.getURL());
        home.sendURL(home.getURL());
        String url ="postgresql://nmcnudamsuzpvx:1a486086f1f7ece7c2c568408c62d36e23b78bbe38118c288e088f9be6042433@ec2-54-170-212-187.eu-west-1.compute.amazonaws.com:5432/d9p4pv538aoe9l";
        //String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "nmcnudamsuzpvx";
        //String user = "postgres";
        String password = "1a486086f1f7ece7c2c568408c62d36e23b78bbe38118c288e088f9be6042433";
        //String password = "Arius135";

        // TEST CONNECTION WITH DATABASE
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT VERSION()")) {

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AutoSignController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        // ---------------------------
        // RETRIEVE INFORMATION FROM YOUTUBE VIDEO
        String link = home.getURL();
        String[] videoID = link.split("v=");

        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("video-test").build();

        final String videoId = videoID[1];
        YouTube.Videos.List videoRequest = youtube.videos().list("snippet,statistics,contentDetails");
        videoRequest.setId(videoId);
        videoRequest.setKey("AIzaSyClhWbvW7KER61FWEw5hz4AdDg8opLQQ1M");
        VideoListResponse listResponse = videoRequest.execute();
        List<Video> videoList = listResponse.getItems();

        Video targetVideo = videoList.iterator().next();

        String title = targetVideo.getSnippet().getTitle();
        String duration = targetVideo.getContentDetails().getDuration();

        String[] duration_1 = duration.split("PT");
        String duration_check = duration_1[1];
        String hours = "00";
        String minutes = "00";
        String seconds = "00";

        if (duration_1[1].indexOf("H") != -1)
        {
            hours = (duration_1[1].split("H"))[0];
            duration_check = (duration_1[1].split("H"))[1];
        }
        if (duration_1[1].indexOf("M") != -1)
        {
            minutes = duration_check.split("M")[0];
            duration_check = duration_check.split("M")[1];
        }
        if (duration_1[1].indexOf("S") != -1)
        {
            seconds = duration_check.split("S")[0];
        }

        System.out.println(hours + ":" + minutes + ":" + seconds);



        // ----------------------
        // FILL IN THE TABLE
        String query = "INSERT INTO d9p4pv538aoe9l(url, title, duration) VALUES(?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, link);
            pst.setString(2, title);
            pst.setString(3, hours + ":" + minutes + ":" + seconds);
            pst.executeUpdate();

            System.out.println(hours);
            //System.out.println(String(hours));

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AutoSignController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }



        return "loading";
    }


    @PostMapping("/homedark")
    public String URLsubmitdark(@ModelAttribute HomeView home, Model model) throws IOException, JSONException {

        model.addAttribute("home", home);
        System.out.println(home.getURL());
        home.sendURL(home.getURL());
        String url ="postgres://nmcnudamsuzpvx:1a486086f1f7ece7c2c568408c62d36e23b78bbe38118c288e088f9be6042433@ec2-54-170-212-187.eu-west-1.compute.amazonaws.com:5432/d9p4pv538aoe9l";
        //String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "nmcnudamsuzpvx";
        //String user = "postgres";
        String password = "1a486086f1f7ece7c2c568408c62d36e23b78bbe38118c288e088f9be6042433";
        //String password = "Arius135";

        // TEST CONNECTION WITH DATABASE
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT VERSION()")) {

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AutoSignController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        // ---------------------------
        // RETRIEVE INFORMATION FROM YOUTUBE VIDEO
        String link = home.getURL();
        String[] videoID = link.split("v=");

        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("video-test").build();

        final String videoId = videoID[1];
        YouTube.Videos.List videoRequest = youtube.videos().list("snippet,statistics,contentDetails");
        videoRequest.setId(videoId);
        videoRequest.setKey("AIzaSyClhWbvW7KER61FWEw5hz4AdDg8opLQQ1M");
        VideoListResponse listResponse = videoRequest.execute();
        List<Video> videoList = listResponse.getItems();

        Video targetVideo = videoList.iterator().next();

        String title = targetVideo.getSnippet().getTitle();
        String duration = targetVideo.getContentDetails().getDuration();

        String[] duration_1 = duration.split("PT");
        String duration_check = duration_1[1];
        String hours = "00";
        String minutes = "00";
        String seconds = "00";

        if (duration_1[1].indexOf("H") != -1)
        {
            hours = (duration_1[1].split("H"))[0];
            duration_check = (duration_1[1].split("H"))[1];
        }
        if (duration_1[1].indexOf("M") != -1)
        {
            minutes = duration_check.split("M")[0];
            duration_check = duration_check.split("M")[1];
        }
        if (duration_1[1].indexOf("S") != -1)
        {
            seconds = duration_check.split("S")[0];
        }

        System.out.println(hours + ":" + minutes + ":" + seconds);



        // ----------------------
        // FILL IN THE TABLE
        String query = "INSERT INTO d9p4pv538aoe9l(url, title, duration) VALUES(?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, link);
            pst.setString(2, title);
            pst.setString(3, hours + ":" + minutes + ":" + seconds);
            pst.executeUpdate();

            System.out.println(hours);
            //System.out.println(String(hours));

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AutoSignController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }



        return "resultdark";
    }

    @GetMapping("/history")
    public String History(Model model){
        model.addAttribute("home", new HomeView());

        return "history";
    }

    @GetMapping("/historydark")
    public String HistoryDark(Model model){
        model.addAttribute("home", new HomeView());

        return "historydark";
    }

    @GetMapping("/contactus")
    public String ContactUs(){
        return "contactus.html";
    }

    @GetMapping("/aboutus")
    public String AboutUs(){
        return "aboutus";
    }

    @GetMapping("/donate")
    public String Donate(){
        return "donate";
    }

    @GetMapping("/contactusdark")
    public String ContactUsDark(){
        return "contactusdark.html";
    }

    @GetMapping("/aboutusdark")
    public String AboutUsDark(){
        return "aboutusdark";
    }

    @GetMapping("/donatedark")
    public String DonateDark(){
        return "donatedark";
    }

    @GetMapping("/loading")
    public String loading(){
        return "loading";
    }


}





