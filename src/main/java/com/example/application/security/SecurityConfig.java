package com.example.application.security;

import com.example.application.ui.view.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(
                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")).permitAll());
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("John")
                .password("{bcrypt}$2a$10$u6raNOYPsRWTIHU0IEe40OgcOo3geBfvziuaSXCN/3C/Os3xnv8O2")
                .roles("USER")
                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles("USER", "ADMIN")
//                .build();
        UserDetails luca = User.builder()
                .username("Luca")
                .password("{bcrypt}$2a$10$G9z2cGjH4Fl6IyTuUK4lROLh2dE.m9e6LIgipv4UDVc778w2VUYFS")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, luca);
    }
}