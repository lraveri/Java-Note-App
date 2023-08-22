package com.example.application.ui.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/login")
public class LoginView extends VerticalLayout {

    LoginForm loginForm = new LoginForm();

    public LoginView() {

        this.setSizeFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);

        loginForm.setAction("login");

        add(new H1("Java Note App"), loginForm);

    }

}
