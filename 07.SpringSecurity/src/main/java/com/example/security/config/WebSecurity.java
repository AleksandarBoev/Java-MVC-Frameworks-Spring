package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
//                .cors()
//                .disable()
                .csrf()
                .csrfTokenRepository(this.csrfTokenRepository())
//                .disable()
                .and()
                .authorizeRequests()
                    .antMatchers("/guestpage").anonymous()
                    .antMatchers("/userpage").hasAuthority("USER")
                    .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(myAuthenticationSuccessHandler())
                    .defaultSuccessUrl("/process/login")
                    .failureUrl("/login")
                .and()
                .logout()
                    .logoutUrl("/logout")
                //^ Ctrl + Q for more info. But the default behaviour is: when going to "/logout", the user is logged out
                //which means no controller is needed.
                    .logoutSuccessUrl("/process/logout") //redirect after logout
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/unauthorized");
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository =
                new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}
