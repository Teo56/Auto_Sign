package AutoSign.functions;

import AutoSign.AutoSignController;
import AutoSign.HomeView;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.json.JSONException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrlSubmit {
    
    // This method allows to retrieve the necessary information from the youtube video knowing the url(title and duration), and insert them into the database
    public static void urlsubmit(@ModelAttribute HomeView home, Model model) throws IOException, JSONException {
        model.addAttribute("home", home);

        // SendURL method is called here
        // This allows to send the same url which will be added to the database, to the python server for the video translation
        home.sendURL(home.getURL());
        
        // Database credentials for its access
        String url ="postgresql://nmcnudamsuzpvx:1a486086f1f7ece7c2c568408c62d36e23b78bbe38118c288e088f9be6042433@ec2-54-170-212-187.eu-west-1.compute.amazonaws.com:5432/d9p4pv538aoe9l";
        String user = "nmcnudamsuzpvx";
        String password = "1a486086f1f7ece7c2c568408c62d36e23b78bbe38118c288e088f9be6042433";
        String db_name = "d9p4pv538aoe9l";

        // TEST CONNECTION WITH DATABASE
        // This makes sure the database can be accessed before inserting anything into it
        
        // Tries connection
        try (Connection con = DriverManager.getConnection(url, user, password);
             
             // Creates statement for the query
             Statement st = con.createStatement();
             // Executes the SELECT VERSION query
             ResultSet rs = st.executeQuery("SELECT VERSION()")) {

            // if query passed, print the outcome
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

            //catch exception if not passed
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AutoSignController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        
        // ---------------------------
        // RETRIEVE INFORMATION FROM YOUTUBE VIDEO (URL)
        String link = home.getURL(); // Get url from HomeView
        String[] videoID = link.split("v="); // Obtain videoID

        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("video-test").build();

        // Credentials to access youtibe video content details
        final String videoId = videoID[1];
        
        // Make request for the video information details (for the given videoID)
        YouTube.Videos.List videoRequest = youtube.videos().list("snippet,statistics,contentDetails");
        videoRequest.setId(videoId);
        
        // Credentials to access youtibe video content details
        videoRequest.setKey("AIzaSyClhWbvW7KER61FWEw5hz4AdDg8opLQQ1M"); // Google key previously obtained by google
        VideoListResponse listResponse = videoRequest.execute(); // Execute information request
        List<Video> videoList = listResponse.getItems(); // Get response for the request

        Video targetVideo = videoList.iterator().next(); // Response items into a list

        // Get title and duration from the item list
        String title = targetVideo.getSnippet().getTitle();
        String duration = targetVideo.getContentDetails().getDuration();

        
        // ---------------------------
        // THIS PART OF THE CODE CHANGES THE FORMAT OF THE DURATION VALUE TO MAKE IT MORE READABLE
        String[] duration_1 = duration.split("PT");
        String duration_check = duration_1[1];
        String hours = "00";
        String minutes = "00";
        String seconds = "00";
        
        
        // A series of string splitting is necessary to isolate the hour, minute and second values before displaying them
        // in the given format H -> hours, M -> minutes, S -> seconds
        // (e.g. 01H32M06S)
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

        // Print the obtained duration in the new format
        System.out.println(hours + ":" + minutes + ":" + seconds);



        // ----------------------
        // FILL IN THE TABLE (Database)
        
        // create query for the insertion
        String query = "INSERT INTO d9p4pv538aoe9l(url, title, duration) VALUES(?, ?, ?)";

        // Connect with database using credentials
        try (Connection con = DriverManager.getConnection(url, user, password);
             // make the query a statement
             PreparedStatement pst = con.prepareStatement(query)) {

            // Run the statement to insert the url, totle and duration in the three columns of each row
            // Recall thet the URL is the primary key, as it is different for each youtube video
            pst.setString(1, link);
            pst.setString(2, title);
            pst.setString(3, hours + ":" + minutes + ":" + seconds);
            pst.executeUpdate();

            System.out.println(hours);
            //System.out.println(String(hours));

            // Catch exception
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AutoSignController.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
}
