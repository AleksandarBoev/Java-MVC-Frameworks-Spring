package com.example.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getSession().setAttribute("username", authentication.getName());
        request.getSession().setAttribute("yesss", "it works!");
        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(role -> ((GrantedAuthority) role).getAuthority())
                .collect(Collectors.toSet());
        request.getSession().setAttribute("roles", roles);


    }
}
