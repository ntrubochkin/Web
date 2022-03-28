package com.weblaba.mt.resource;

import com.weblaba.mt.exception.UserNotFoundException;
import com.weblaba.mt.model.User;
import com.weblaba.mt.service.UserService;
import com.weblaba.mt.stuff.EmailValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeResource {
    private final UserService service;

    public WelcomeResource(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String sendToLogin() {
        return "login";
    }

    @GetMapping("/registration")
    public String sendToRegistration() {
        return "registration";
    }

    @PostMapping("/login")
    public String tryLogin(Model md, @RequestParam(value = "login") String login, @RequestParam(value = "pwd") String pwd) {
        try {
            User user = EmailValidation.validate(login) ? service.findByEmail(login) : service.findByName(login);
            return "login"; /* go to index */
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            md.addAttribute("msg","Incorrect login or password");
            md.addAttribute("show", true);
            return sendToLogin();
        }
    }

    @PostMapping("/register")
    public String register(
            Model md,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "uname") String name,
            @RequestParam(value = "pwd") String pwd) {
        md.addAttribute("show", true);

        if(!EmailValidation.validate(email)) {
            md.addAttribute("msg","Wrong email Address");
            return sendToRegistration();
        }

        if(pwd.length() < 8) {
            md.addAttribute("msg","Too short password");
            return sendToRegistration();
        }

        System.out.println(service.addUser(new User(email, name, pwd)));

        md.addAttribute("msg","Registration successful");
        return sendToLogin();
    }
}