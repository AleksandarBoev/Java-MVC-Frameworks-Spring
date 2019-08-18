package demo.rolemanagement.web.controllers;

import demo.rolemanagement.domain.entities.User;
import demo.rolemanagement.domain.models.binding.UserBindingModel;
import demo.rolemanagement.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {
    private UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/important-stuff")
    public String getImportantStuffPage() {
        return "important-stuff";
    }

    @GetMapping("/guest-stuff")
    public String getGuestStuffPage() {
        return "guest-stuff";
    }

    @GetMapping(value = "/add-user-priviledges", produces = "application/json")
    @ResponseBody
    public void addUserPriviledges(HttpSession session) {
        session.setAttribute("ROLE", "USER");
    }

    @GetMapping(value = "/remove-user-priviledges", produces = "application/json")
    @ResponseBody
    public void removeUserPriviledges(HttpSession session) {
        session.removeAttribute("ROLE");
    }

    @PostMapping("/create-user")
    public String createUser(@ModelAttribute("userBindingModel") UserBindingModel userBindingModel,
                             ModelMapper modelMapper) {
        User user = modelMapper.map(userBindingModel, User.class);
        userRepository.save(user);
        return "redirect:/home";
    }

    @GetMapping(value = "/get-users", produces = "application/json")
    @ResponseBody
    public Object getUsers() {
        return userRepository.findAll();
    }
}
