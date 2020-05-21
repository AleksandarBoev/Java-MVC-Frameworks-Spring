package com.example.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticatedModelAndView extends ModelAndView {
    public AuthenticatedModelAndView() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        this.addObject("userInfo", authentication);
        //authentication.getName() //raises NullPointerException for some reason.
        //but when this code is written in the controller, it doesn't raise the exception. Weird
    }
}
