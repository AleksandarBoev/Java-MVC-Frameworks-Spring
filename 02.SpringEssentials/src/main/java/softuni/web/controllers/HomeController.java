package softuni.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
    public HomeController(HttpSession httpSession) {
        super(httpSession);
    }

    @GetMapping
    public String getIndexPage() {
        if (super.isLoggedIn())
            return "redirect:/home";

        return "index";
    }

    @GetMapping
    public String getHomePage() {
        if (!super.isLoggedIn())
            return "redirect:/";

        return "home";
    }
}
