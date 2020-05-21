package com.example.security.web;

import com.example.security.domain.models.UserInfoModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/process")
public class LoginLogoutProcessController {
    @GetMapping("/login")
    public String processLogin(Authentication authentication, HttpSession httpSession) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUsername(authentication.getName());
        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(a -> ((GrantedAuthority) a).getAuthority())
                .collect(Collectors.toSet());
        userInfoModel.setRoles(roles);

        httpSession.setAttribute("userInfo", userInfoModel);
        System.out.println("Added needed attributes to session!");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String processLogout(HttpSession session) {
        System.out.println("Logging out...");
        session.removeAttribute("userInfo");
        return "redirect:/";
    }
}
