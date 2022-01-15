package AutoSign.functions;

import AutoSign.HomeView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public class StartVideo {
    public static void startVideo(@RequestParam("url") String url, @ModelAttribute HomeView home, Model model) {
        HomeView homeee = new HomeView();
        homeee.setURL(url);
        model.addAttribute("homeee", homeee);
        System.out.print(homeee.getURL());
    }

}
