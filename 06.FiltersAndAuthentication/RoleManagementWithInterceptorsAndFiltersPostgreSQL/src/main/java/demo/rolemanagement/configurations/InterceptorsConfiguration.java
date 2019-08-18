package demo.rolemanagement.configurations;

import demo.rolemanagement.web.interceptors.UserPagesInterceptor;
import demo.rolemanagement.web.interceptors.GuestPagesInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfiguration implements WebMvcConfigurer {
    private GuestPagesInterceptor guestPagesInterceptor;
    private UserPagesInterceptor userPagesInterceptor;

    @Autowired
    public InterceptorsConfiguration(GuestPagesInterceptor guestPagesInterceptor, UserPagesInterceptor userPagesInterceptor) {
        this.guestPagesInterceptor = guestPagesInterceptor;
        this.userPagesInterceptor = userPagesInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //idea is to check certain privileges for certain pages. For guest-stuff i want only guests
        //to access it. So I check that page for guest privileges.
        registry.addInterceptor(guestPagesInterceptor).addPathPatterns("/guest-stuff");
        registry.addInterceptor(userPagesInterceptor).addPathPatterns("/important-stuff");
    }
}
