package com.example.security.web;

import com.example.security.domain.entities.User;
import com.example.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {
    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getHome(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
//        Authentication userInfo = (Authentication)modelAndView.getModel().get("userInfo");
//        List<String> authorities = userInfo.getAuthorities()
//                .stream()
//                .map(a -> ((GrantedAuthority)a).getAuthority())
//                .collect(Collectors.toList());
//
//        System.out.println("Authorities: " + authorities);
//        System.out.println("Credentials: " + userInfo.getCredentials());
//        System.out.println("Is authenticated: " + userInfo.isAuthenticated());
//        System.out.println("Name: " + userInfo.getName());
//        System.out.println(userInfo.getAuthorities().contains("ROLE_ANONYMOUS"));
//
//        List<String> no = new ArrayList<>();
//        no.add("aaaa");
//        no.add("bbbb");
//        modelAndView.addObject("wat", no);
        return modelAndView;
    }

    @GetMapping("/userpage")
    public String getUserPage(HttpSession session) {
//        SecurityContext securityContext = ((SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
//        Authentication authentication = securityContext.getAuthentication();
//
//        System.out.println("MY INFO I WORKED SO HARD FOR HERE:");
//        System.out.println(authentication.getName());
//        System.out.println(authentication.getAuthorities());
        return "user-page.html";
    }


    @GetMapping("/guestpage")
    public String getGuestPage() {
        return "guest-page.html";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register-page.html";
    }

    @PostMapping("/register")
    public String postRegisterPage(@RequestParam(name = "username") String username,
                                   @RequestParam(name = "password") String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        System.out.println("Trying to register user");
        userService.register(user);
        System.out.println("User with name " + user.getUsername() + " registered!");
        return "redirect:/";
    }
}