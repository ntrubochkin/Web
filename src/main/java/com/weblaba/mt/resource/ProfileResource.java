package com.weblaba.mt.resource;

import com.weblaba.mt.model.Follow;
import com.weblaba.mt.model.Note;
import com.weblaba.mt.service.FollowService;
import com.weblaba.mt.service.NoteService;
import com.weblaba.mt.service.UserService;
import com.weblaba.mt.stuff.ClientState;
import com.weblaba.mt.stuff.PagesAttributes;
import com.weblaba.mt.stuff.UploadTimeConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shrimp")
public class ProfileResource {
    private final UserService userService;
    private final NoteService noteService;
    private final FollowService followService;

    public ProfileResource(UserService userService, NoteService noteService, FollowService followService) {
        this.userService = userService;
        this.noteService = noteService;
        this.followService = followService;
    }

    @GetMapping("/{id}")
    public String redirectToPosts(@PathVariable("id") Long id) {
        return redirectToPostsWithId(id);
    }

    public static String redirectToProfilePage() {
        return redirectToPostsWithId(ClientState.getInstance().getId());
    }

    public static String redirectToFollowsPage() {
        return redirectToFollowsWithId(ClientState.getInstance().getId());
    }

    public static String redirectToPostsWithId(Long id) {
        return String.format("redirect:/shrimp/%s/posts", id.toString());
    }

    public static String redirectToFollowsWithId(Long id) {
        return String.format("redirect:/shrimp/%s/follows", id.toString());
    }

    @PostMapping("/follow/{page}")
    public String followObservedPerson(@PathVariable("page") int page) {
        ClientState state = ClientState.getInstance();
        Long clientId = state.getId();
        Long observedId = state.getObservableId();

        if(state.getFollowingState()) {
            followService.deleteByIdPair(clientId, observedId);
        } else {
            followService.addFollow(clientId, observedId);
        }

        if(page == 1) {
            return redirectToPostsWithId(observedId);
        } else {
            return redirectToFollowsWithId(observedId);
        }
    }

    @GetMapping("/{id}/follows")
    public String redirectToFollows(Model md, @PathVariable("id") Long id) {
        ClientState cs = ClientState.getInstance();

        fillStaticProfileInfo(md, cs, id);

        List<Follow> list = followService.findAllFollowsById(id);
        md.addAttribute(PagesAttributes.FOLLOW_CARDS_ATTR, cs.packFollows(list, userService));
        md.addAttribute(PagesAttributes.POSTS_COUNT_ATTR, noteService.findPostsCountById(id));

        return "follows";
    }

    @GetMapping("/{id}/posts")
    public String sendToPosts(Model md, @PathVariable("id") Long id) {
        ClientState cs = ClientState.getInstance();

        fillStaticProfileInfo(md, cs, id);

        List<Note> notes = noteService.getAllNotesById(id);
        cs.sortNotes(notes);
        md.addAttribute(PagesAttributes.CONTENT_ATTR, cs.packNotes(
                notes,
                Map.of(id, userService.findNameById(id)),
                userService
        ));
        md.addAttribute(PagesAttributes.POSTS_COUNT_ATTR, NoteService.getPostsString(notes.size()));

        return "posts";
    }

    public void fillStaticProfileInfo(Model md, ClientState cs, Long id) {
        boolean loggedIn = cs.loggedIn();
        String date = "";

        md.addAttribute(PagesAttributes.LOGGED_IN_ATTR, loggedIn);

        if(loggedIn && (id == cs.getId())) {
            cs.fillStaticClientInfo(md);
            date = UploadTimeConverter.formatTimestamp(cs.getRegistrationDate());
        } else {
            cs.fillStaticInfo(md, userService, id);
            date = UploadTimeConverter.formatTimestamp(userService.findRegistrationDate(id));
        }

        cs.setObservableId(id);
        cs.setFollowButtonState(md, followService);
        cs.setChangeInfoButtonState(md);

        md.addAttribute(PagesAttributes.OBSERVED_ID_ATTR, id);
        md.addAttribute(PagesAttributes.REGISTRATION_DATE_ATTR, date);
        md.addAttribute(PagesAttributes.FOLLOWS_ATTR, followService.findFollowsCountById(id));
        md.addAttribute(PagesAttributes.FOLLOWED_ATTR, followService.findFollowedCountById(id));
    }
}