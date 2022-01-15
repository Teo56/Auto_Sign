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

import AutoSign.functions.StartVideo;
import AutoSign.functions.UrlSubmit;

// This controller class contains all the mapping of the java app
// Different get and post mapping requests are sent in order to retrieve and later display the dffferent html pages
// Some of its methods have been implemented as static methods in separate classes in order to make the AutoSignController class cleaner
// These classes have been packaged into the folder "functions"
@Controller
public class AutoSignController {
    
    // Get mapping to homepage
    @GetMapping("/" )
    public String Home(Model model){
        // New HomeView object created to store the information regarding url submission by the user
        model.addAttribute("home", new HomeView());
        return "home";
    }

    // Get mapping to homepage (dark mode)
    @GetMapping("/homedark" )
    public String HomeDark(Model model){
        model.addAttribute("home", new HomeView());
        return "homedark";
    }
    
    // Post mapping to "check video exists"
    // When this post requests is sent, the checkVideoRounter method is called
    @PostMapping("/checkvideoexists")
    @ResponseBody
    public Boolean checkVideoRouter(@RequestParam("videoid") String videoid, @ModelAttribute HomeView home, Model model) throws IOException {
        
        // This method takes the created HomeView object as input and, by calling its checkVideoExist method, repetitively checks for the translation video on google cloud storage
        // This is done by using the videoID of the video that the user wants to translate (videoID is used in the name of the saved video file)
        
        model.addAttribute("home", home);
        Boolean exist;
        String[] videoID = videoid.split("v=");
        String videoId = videoID[1];

        // When the video gets saved on google cloud storage by python, the method checkVideoExists will return TRUE
        exist = home.checkVideoExists(videoId);
        if (exist) {
            // When checkVideoExists returns true, the downloadVideo method of HomeView is called in order to stream it from google cloud storage in the draggable window
            home.downloadVideo(videoId);
            return true;
        } else if (exist == false) {
            // If the video is not found in google cloud storage the method returns FALSE
            return false;
        }
        return false;
    }


    // The following method is used to exit the loading page (during the translation time) and be redirected to the result page, where the youtube and translation videos are displayed
    @RequestMapping(value = "/", method = RequestMethod.POST, params = "videourl")
    public String startVideo(@RequestParam("url") String url, @ModelAttribute HomeView home, Model model) {
        StartVideo.startVideo(url, home, model);
        /*HomeView homeee = new HomeView();
        homeee.setURL(url);
        model.addAttribute("homeee", homeee);
        System.out.print(homeee.getURL());*/

        return "result";
    }

    // Result page (dark)
    @RequestMapping(value = "/homedark", method = RequestMethod.POST, params = "videourl")
    public String startVideoDark(@RequestParam("url") String url, @ModelAttribute HomeView home, Model model) {
        StartVideo.startVideo(url, home, model);
        /*HomeView homeee = new HomeView();
        homeee.setURL(url);
        model.addAttribute("homeee", homeee);
        System.out.print(homeee.getURL());*/
        return "resultdark";
    }
    

    // When a post request is made to "/", the loading page is returned
    // The loading oage is held as long as the python algorithm processes the youtube video and creates its translation
    @PostMapping("/")
    public String URLsubmit(@ModelAttribute HomeView home, Model model) throws IOException, JSONException {
        
        // The urlsubmit method is used to insert the youtube-video url, duration and title into the database
        // The database entries can be seen in the history page
        UrlSubmit.urlsubmit(home, model);
        return "loading";
    }


    @PostMapping("/homedark")
    public String URLsubmitDark(@ModelAttribute HomeView home, Model model) throws IOException, JSONException {
        UrlSubmit.urlsubmit(home, model);
        return "loading";
    }

    // Mapping to the history page, where the database entries are displayed (youtube-video url, title and duration)
    // The database contains all past searches, and therefore all the previously translated videos
    // This measn that, being the the translations already on google cloud storage, no processing of those videos will be required at their next submission
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

    // Mapping to contact us page, where where a contact form is available for the user to submit feedback and help us improve the app
    @GetMapping("/contactus")
    public String ContactUs(){
        return "contactus.html";
    }
    
    

    // Mapping to contact us page where team members are listed, as well as general information about the project
    @GetMapping("/aboutus")
    public String AboutUs(){
        return "aboutus";
    }

    // mapping to donate page for generous donations
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





