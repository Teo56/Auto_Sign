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
    public void checkVideoRouter(@RequestParam("videoid") String videoid, @ModelAttribute HomeView home, Model model) throws IOException {
        CheckVideoRouter.checkVideoRouter(videoid, home, model);
        /*model.addAttribute("home", home);
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
        return false;*/
    }


    @RequestMapping(value = "/", method = RequestMethod.POST, params = "videourl")
    public String startVideo(@RequestParam("url") String url, @ModelAttribute HomeView home, Model model) {
        StartVideo.startVideo(url, home, model);
        /*HomeView homeee = new HomeView();
        homeee.setURL(url);
        model.addAttribute("homeee", homeee);
        System.out.print(homeee.getURL());*/

        return "result";
    }

    @RequestMapping(value = "/homedark", method = RequestMethod.POST, params = "videourl")
    public String startVideoDark(@RequestParam("url") String url, @ModelAttribute HomeView home, Model model) {
        StartVideo.startVideo(url, home, model);
        /*HomeView homeee = new HomeView();
        homeee.setURL(url);
        model.addAttribute("homeee", homeee);
        System.out.print(homeee.getURL());*/
        return "resultdark";
    }
    

    @PostMapping("/")
    public String URLsubmit(@ModelAttribute HomeView home, Model model) throws IOException, JSONException {
        UrlSubmit.urlsubmit(home, model);
        return "loading";
    }


    @PostMapping("/homedark")
    public String URLsubmitDark(@ModelAttribute HomeView home, Model model) throws IOException, JSONException {
        UrlSubmit.urlsubmit(home, model);
        return "loading";
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





