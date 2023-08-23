package com.example.application.security;

import com.example.application.backend.model.Role;
import com.example.application.backend.model.entity.User;
import com.example.application.backend.repository.UserRepository;
import com.example.application.backend.service.UserService;
import com.example.application.ui.view.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {

    }

    public class AuthException extends Exception {

    }

    @Autowired
    private UserService userService;

//    private final MailSender mailSender;

//    public AuthService(UserService userService, MailSender mailSender) {
//        this.userService = userService;
//        this.mailSender = mailSender;
//    }

    public void authenticate(String username, String password) throws AuthException {
        User user = userService.findByUsername(username);
        if (user != null && user.checkPassword(password) && user.isActive()) {
            VaadinSession.getCurrent().setAttribute(User.class, user);

        } else {
            throw new AuthException();
        }
    }


//    public void register(String email, String password) {
//        User user = userService.save(new User(email, password, Role.USER));
//        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@example.com");
//        message.setSubject("Confirmation email");
//        message.setText(text);
//        message.setTo(email);
//        mailSender.send(message);
//    }
//
//    public void activate(String activationCode) throws AuthException {
//        User user = userService.findByActivationCode(activationCode);
//        if (user != null) {
//            user.setActive(true);
//            userService.save(user);
//        } else {
//            throw new AuthException();
//        }
//    }
}
