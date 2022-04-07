package com.weblaba.mt.resource;

import com.weblaba.mt.service.FollowService;
import com.weblaba.mt.service.NoteService;
import com.weblaba.mt.service.UserService;
import com.weblaba.mt.stuff.ClientState;
import com.weblaba.mt.stuff.PagesAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeResource {
    private final UserService userService;
    private final FollowService followService;
    private final NoteService noteService;

    private ClientState state;

    public HomeResource(UserService userService, FollowService followService, NoteService noteService) {
        this.userService = userService;
        this.followService = followService;
        this.noteService = noteService;
        state = ClientState.getInstance();
    }

    public static String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String sendToHome(Model md) {
        boolean loggedIn = state.loggedIn();

        md.addAttribute(PagesAttributes.LOGGED_IN_ATTR, loggedIn);

        if(loggedIn) {
            state.fillStaticClientInfo(md);
        }

        state.updateContentState(userService, followService, noteService);
        md.addAttribute(PagesAttributes.CONTENT_ATTR, state.getContent());

        return "home";
    }
}