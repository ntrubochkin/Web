package com.weblaba.mt.stuff;

import com.weblaba.mt.model.*;
import com.weblaba.mt.service.FollowService;
import com.weblaba.mt.service.NoteService;
import com.weblaba.mt.service.UserService;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientState {
    private static ClientState instance;

    private boolean loggedOn;
    private User client;

    private Long observableId;
    private boolean followingState;

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

    public User getClient() {
        return client;
    }

    public boolean loggedIn() {
        return loggedOn;
    }

    public Long getId() {
        return client.getId();
    }

    public Long getObservableId() {
        return observableId;
    }

    public boolean getFollowingState() {
        return followingState;
    }

    public Timestamp getRegistrationDate() {
        return client.getCreated();
    }

    public PackedNote[] getContent() {
        return content;
    }

    public void setObservableId(Long id) {
        this.observableId = id;
    }

    private boolean isNotMyProfile() {
        return client.getId() != observableId;
    }

    public void setFollowButtonState(Model md, FollowService service) {
        if(!loggedOn) {
            return;
        }

        boolean isNotMyProfile = isNotMyProfile();
        String name = "";

        md.addAttribute(PagesAttributes.FOLLOW_BTN_STATE_ATTR, isNotMyProfile);

        if(isNotMyProfile) {
            followingState = service.isFollowing(client.getId(), observableId);

            name = followingState ? "Unfollow" : "Follow";
        }

        md.addAttribute(PagesAttributes.FOLLOW_BTN_NAME_ATTR, name);
    }

    public void setChangeInfoButtonState(Model md) {
        boolean show = loggedOn && !isNotMyProfile();
        md.addAttribute(PagesAttributes.CHANGE_INFO_BTN_STATE_ATTR, show);
    }

    public void fillStaticClientInfo(Model md) {
        fillStaticClientInfo(md, client.getUname(), client.getPfInfo(), UserService.getProfileImage(client.getPfImgName()));
    }

    public void fillStaticInfo(Model md, UserService service, Long id) {
        fillStaticClientInfo(md, service.findNameById(id), service.findInfoById(id), service.findProfileImageById(id));
    }

    public void fillStaticClientInfo(Model md, String name, String info, Long imgName) {
        if(loggedOn) {
            md.addAttribute(PagesAttributes.ID_ATTR, client.getId());
        }

        md.addAttribute(PagesAttributes.NAME_ATTR, name);
        md.addAttribute(PagesAttributes.INFO_ATTR, info);
        md.addAttribute(PagesAttributes.AVATAR_ATTR, imgName);
    }

    public void updateContentState(UserService userService, FollowService followService, NoteService noteService) {
        List<Note> notes = new ArrayList<Note>();
        Map<Long, String> map = new HashMap<Long, String>();

        if(loggedOn) {
            Long clientId = client.getId();
            List<Follow> follows = followService.findAllFollowsById(clientId);
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

        content = packNotes(notes, map, userService);
    }

    public PackedNote[] packNotes(List<Note> notes, Map<Long, String> names, UserService service) {
        List<PackedNote> pack = new ArrayList<PackedNote>(notes.size());

        for(int i = 0; i < notes.size(); i++) {
            Note current = notes.get(i);
            long id = current.getUserId();

            PackedNote pn = new PackedNote(
                    current.getUserId(),
                    UploadTimeConverter.convert(current.getCreationDate()),
                    names.get(id),
                    current.getText(),
                    service.findProfileImageById(id),
                    current.getImgName());

            pack.add(pn);
        }

        return pack.toArray(new PackedNote[pack.size()]);
    }

    public PackedFollow[] packFollows(List<Follow> follows, UserService service) {
        List<PackedFollow> pack = new ArrayList<>(follows.size());

        for(int i = 0; i < follows.size(); i++) {
            long id = follows.get(i).getFollowingId();

            PackedFollow pf = new PackedFollow(
                    id,
                    service.findNameById(id),
                    service.findProfileImageById(id));

            pack.add(pf);
        }

        return pack.toArray(new PackedFollow[pack.size()]);
    }

    public void sortNotes(List<Note> notes) {
        notes.sort((a, b) -> b.getCreationDate().compareTo(a.getCreationDate()));
    }
}