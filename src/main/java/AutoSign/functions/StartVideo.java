package AutoSign.functions;

import AutoSign.HomeView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;


// Class to pass the video url to the final result page where the video is shown
// It is needed to go from the Loading page to the Result Page
public class StartVideo {
    public static void startVideo(@RequestParam("url") String url, @ModelAttribute HomeView home, Model model) {
        HomeView homeee = new HomeView(); //create a new object of type HomeView()
        homeee.setURL(url); // pass the URL to it
        model.addAttribute("homeee", homeee); // inject model into the method
        System.out.print(homeee.getURL());
    }

}
