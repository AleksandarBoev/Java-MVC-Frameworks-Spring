package softuni.web.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.domain.models.binding.UserLoginBindingModel;
import softuni.domain.models.binding.UserRegisterBindingModel;
import softuni.domain.models.service.UserServiceModel;
import softuni.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, HttpSession session) {
        super(session);
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        if (super.isLoggedIn())
            return "redirect:/";

        return "login";
    }

    @PostMapping("/login")
    public String getLoginInfo(@ModelAttribute UserLoginBindingModel userLoginBindingModel) {
        UserServiceModel userServiceModel = this.modelMapper.map(userLoginBindingModel, UserServiceModel.class);
        try {
            userServiceModel = this.userService.loginUser(userServiceModel);
            super.getHttpSession().setAttribute("user", userServiceModel);
            return "redirect:/home";
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            return "redirect:/user/login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        super.getHttpSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        if (super.isLoggedIn())
            return "redirect:/";

        return "register";
    }

    @PostMapping("/register")
    public String registerPerson(@ModelAttribute UserRegisterBindingModel userRegisterBindingModel) {
        if (super.isLoggedIn())
            return "redirect:/";

        UserServiceModel userToBeRegistered = this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        this.userService.register(userToBeRegistered);

        return "redirect:/user/login";
    }

}
