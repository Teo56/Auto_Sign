package AutoSign;

import AutoSign.functions.UrlSubmit;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;


@Controller
public class AutoSignController {
    @GetMapping("/" )
    private String Home(Model model){
        model.addAttribute("home", new HomeView());
        return "home";
    }

    @PostMapping("/")
    private String URLsubmit(@ModelAttribute HomeView home, Model model) throws IOException, JSONException {
        UrlSubmit.urlsubmit(home, model);
        return "result";
    }

    @GetMapping("/history")
    private String History(Model model){
        model.addAttribute("home", new HomeView());

        return "history";
    }

    @GetMapping("/contactus")
    private String ContactUs(){
        return "contactus.html";
    }

    @GetMapping("/aboutus")
    private String AboutUs(){
        return "aboutus";
    }

    @GetMapping("/donate")
    private String Donate(){
        return "donate";
    }
}





