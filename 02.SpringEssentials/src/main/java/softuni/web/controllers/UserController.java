package softuni.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    //TODO make the password hached
    @GetMapping("/login")
    public String getLoginPage(HttpSession httpSession) {
        if (httpSession.getAttribute("user") != null) {
            return "redirect:/";
        }

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.setAttribute("user", null);
        return "redirect:/";
    }
}
