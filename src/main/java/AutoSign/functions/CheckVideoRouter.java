package AutoSign.functions;

import AutoSign.HomeView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public class CheckVideoRouter {
    public static Boolean checkVideoRouter(@RequestParam("videoid") String videoid, @ModelAttribute HomeView home, Model model) throws IOException {
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
}
