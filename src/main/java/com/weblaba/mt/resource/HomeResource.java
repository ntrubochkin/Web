package com.weblaba.mt.resource;

import com.weblaba.mt.model.Note;
import com.weblaba.mt.service.FollowService;
import com.weblaba.mt.service.NoteService;
import com.weblaba.mt.service.UserService;
import com.weblaba.mt.stuff.ClientState;
import com.weblaba.mt.stuff.ImageServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    public String redirectToHome() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String sendToHome(Model md) {
        boolean loggedIn = state.loggedIn();

        md.addAttribute("loggedIn", loggedIn);

        if(loggedIn) {
            md.addAttribute("clientName", state.getNickname());
            md.addAttribute("profileInfo", state.getProfileInfo());
            md.addAttribute("profileAvatar", state.getProfileImageName());
        }

        state.updateContentState(userService, followService, noteService);
        md.addAttribute("notes", state.getContent());

        return "home";
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String uploadNote(
            @RequestParam(value = "uImgInput", required = false) MultipartFile file,
            @RequestParam(value = "uTextInput", required = false) String message) {
        boolean fileIsEmpty = file.isEmpty();
        boolean msgIsEmpty = "".equals(message);

        if(fileIsEmpty && msgIsEmpty) {
            return redirectToHome();
        }

        Long fileName = fileIsEmpty ? null : ImageServer.getInstance().saveMeme(file);
        String outMsg = msgIsEmpty ? null : message;

        Note note = new Note(state.getId(), outMsg, fileName);
        noteService.addNote(note);

        return redirectToHome();
    }
}