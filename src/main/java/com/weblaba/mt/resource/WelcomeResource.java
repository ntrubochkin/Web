package com.weblaba.mt.resource;

import com.weblaba.mt.model.User;
import com.weblaba.mt.service.UserService;
import com.weblaba.mt.stuff.ClientState;
import com.weblaba.mt.stuff.EmailValidation;
import com.weblaba.mt.stuff.WelcomeMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeResource {
    private WelcomeMessage msgToUser;
    private final UserService service;

    public WelcomeResource(UserService service) {
        msgToUser = WelcomeMessage.getInstance();
        this.service = service;
    }

    @GetMapping
    public String sendToLogin() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginWithError() {
        msgToUser.setMessage(WelcomeMessage.MessageToUser.IDK, sendToLogin());
        return sendToPageWithMessage();
    }

    @GetMapping("/registration")
    public String sendToRegistration() {
        return "registration";
    }

    public String sendToPageWithMessage() {
        return "redirect:/get-welcome-message";
    }

    @RequestMapping("/get-welcome-message")
    public String sendToPage(Model md) {
        md.addAttribute("show", true);
        md.addAttribute("msg", msgToUser.getMessage());
        return msgToUser.getPage();
    }

    @RequestMapping("/home")
    public String sendToHome() {
        return "home";
    }

    @PostMapping("/login")
    public String tryLogin(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "pwd") String pwd) {
        User user = EmailValidation.validate(login) ? service.findUserByEmail(login) : service.findUserByName(login);

        if(service.passwordMatches(pwd, user.getPassword())) {
            ClientState.getInstance().logIn(user);
            return "redirect:/home";
        }

        msgToUser.setMessage(WelcomeMessage.MessageToUser.INCORRECT_DATA, sendToLogin());
        return sendToPageWithMessage();
    }

    @PostMapping("/register")
    public String register(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "uname") String name,
            @RequestParam(value = "pwd") String pwd) {
        if(!EmailValidation.validate(email)) {
            msgToUser.setMessage(WelcomeMessage.MessageToUser.WRONG_EMAIL, sendToRegistration());
            return sendToPageWithMessage();
        }

        if(service.findIdByEmail(email) != null) {
            msgToUser.setMessage(WelcomeMessage.MessageToUser.EMAIL_EXISTS, sendToRegistration());
            return sendToPageWithMessage();
        }

        if(service.findIdByNickname(name) != null) {
            msgToUser.setMessage(WelcomeMessage.MessageToUser.NICKNAME_EXISTS, sendToRegistration());
            return sendToPageWithMessage();
        }

        if(pwd.length() < 8) {
            msgToUser.setMessage(WelcomeMessage.MessageToUser.BAD_PASSWORD_LENGTH, sendToRegistration());
            return sendToPageWithMessage();
        }

        service.addNewUser(email, name, pwd);

        msgToUser.setMessage(WelcomeMessage.MessageToUser.REGISTRATION_SUCCESSFUL, sendToLogin());
        return sendToPageWithMessage();
    }

    @GetMapping("/logout")
    public String logout() {
        ClientState.getInstance().logOut();
        return sendToLogin();
    }
}