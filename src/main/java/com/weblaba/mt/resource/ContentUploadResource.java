package com.weblaba.mt.resource;

import com.weblaba.mt.model.Note;
import com.weblaba.mt.model.User;
import com.weblaba.mt.service.NoteService;
import com.weblaba.mt.service.UserService;
import com.weblaba.mt.stuff.ClientState;
import com.weblaba.mt.stuff.ImageServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ContentUploadResource {
    private ClientState state;

    private NoteService noteService;
    private UserService userService;

    public ContentUploadResource(NoteService noteService, UserService userService) {
        this.userService = userService;
        this.noteService = noteService;
        state = ClientState.getInstance();
    }

    //opt = 0 - Post content upload
    //opt = 1 - Profile info upload
    @PostMapping(value = "/upload/{opt}/{page}", consumes = "multipart/form-data")
    public String uploadNote(
            @RequestParam(value = "uImgInput", required = false) MultipartFile file,
            @RequestParam(value = "uTextInput", required = false) String message,
            @PathVariable("opt") int opt,
            @PathVariable("page") int page) {
        boolean fileIsEmpty = file.isEmpty();
        boolean msgIsEmpty = "".equals(message);

        if(fileIsEmpty && msgIsEmpty) {
            return HomeResource.redirectToHome();
        }

        ImageServer is = ImageServer.getInstance();
        Long fileName = fileIsEmpty ? null :
                (opt == 0) ? is.saveMeme(file) : is.saveAvatar(file);
        String outMsg = msgIsEmpty ? null : message;

        if(opt == 0) {
            Note note = new Note(state.getId(), outMsg, fileName);
            noteService.addNote(note);
        } else {
            User client = state.getClient();
            boolean hasChanges = false;

            if(fileName != null && fileName != -1) {
                client.setPfImgName(fileName);
                hasChanges = true;
            }

            if(outMsg != null) {
                client.setPfInfo(outMsg);
                hasChanges = true;
            }

            if(hasChanges) {
                userService.updateUser(client);
            }
        }

        return getRedirectionPage(page);
    }

    public static String getRedirectionPage(int opt) {
        switch (opt) {
            default:
            case 0:
                return HomeResource.redirectToHome();
            case 1:
                return ProfileResource.redirectToProfilePage();
            case 2:
                return ProfileResource.redirectToFollowsPage();
        }
    }
}