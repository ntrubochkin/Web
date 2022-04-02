package com.weblaba.mt.stuff;

import com.weblaba.mt.model.Follow;
import com.weblaba.mt.model.Note;
import com.weblaba.mt.model.PackedNote;
import com.weblaba.mt.model.User;
import com.weblaba.mt.service.FollowService;
import com.weblaba.mt.service.NoteService;
import com.weblaba.mt.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientState {
    private static ClientState instance;

    private static final String NO_INFO = "No information.";

    private boolean loggedOn;
    private User client;

    private PackedNote[] content;

    private ClientState() {
        loggedOn = false;
    }

    public static ClientState getInstance() {
        if(instance == null) {
            instance = new ClientState();
        }
        return instance;
    }

    public void logIn(User user) {
        loggedOn = true;
        client = user;
    }

    public void logOut() {
        loggedOn = false;
        content = new PackedNote[0];
        client = new User();
    }

    public boolean loggedIn() {
        return loggedOn;
    }

    public String getProfileInfo() {
        String info = client.getPfInfo();
        return (info == null) ? NO_INFO : info;
    }

    public String getNickname() {
        return client.getUname();
    }

    public Long getId() {
        return client.getId();
    }

    public Long getProfileImageName() {
        Long name = client.getPfImgName();
        return (name == null) ? 0L : name;
    }

    public PackedNote[] getContent() {
        return content;
    }

    public void updateContentState(UserService userService, FollowService followService, NoteService noteService) {
        List<Note> notes = new ArrayList<Note>();
        List<PackedNote> pack = new ArrayList<PackedNote>();
        Map<Long, String> map = new HashMap<Long, String>();

        if(loggedOn) {
            Long clientId = client.getId();
            List<Follow> follows = followService.getAllFollowsById(clientId);
            map.put(clientId, client.getUname());
            notes.addAll(noteService.getAllNotesById(clientId));

            for (int i = 0; i < follows.size(); i++) {
                Long currentId = follows.get(i).getFollowingId();
                notes.addAll(noteService.getAllNotesById(currentId));
                map.put(currentId, userService.findNameById(currentId));
            }
        } else {
            notes.addAll(noteService.getAllNotesWithinAWeek());

            for(int i = 0; i < notes.size(); i++) {
                Long currentId = notes.get(i).getUserId();
                map.putIfAbsent(currentId, userService.findNameById(currentId));
            }
        }

        sortNotes(notes);

        for(int i = 0; i < notes.size(); i++) {
            Note current = notes.get(i);
            long id = current.getUserId();

            PackedNote pn = new PackedNote(
                    UploadTimeConverter.convert(current.getCreationDate()),
                    map.get(id),
                    current.getText(),
                    userService.findProfileImageById(id),
                    current.getImgName());

            pack.add(pn);
        }

        content = pack.toArray(new PackedNote[pack.size()]);
    }

    private void sortNotes(List<Note> notes) {
        notes.sort((a, b) -> b.getCreationDate().compareTo(a.getCreationDate()));
    }
}