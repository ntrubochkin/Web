package com.weblaba.mt.resource;

import com.weblaba.mt.model.PackedUser;
import com.weblaba.mt.model.User;
import com.weblaba.mt.service.UserService;
import com.weblaba.mt.stuff.UploadTimeConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchResource {
    private final UserService service;

    public SearchResource(UserService service) {
        this.service = service;
    }

    @GetMapping("/search/{text}")
    @ResponseBody
    public List<PackedUser> searchUsers(@PathVariable("text") String text) {
        List<User> list = service.searchByPart(text);
        List<PackedUser> response = new ArrayList<>(list.size());

        for(int i = 0; i < list.size(); i++) {
            User u = list.get(i);

            PackedUser pu = new PackedUser(
                    u.getId(),
                    u.getUname(),
                    UploadTimeConverter.formatTimestamp(u.getCreated()),
                    UserService.getProfileImage(u.getPfImgName()));

            response.add(pu);
        }

        return response;
    }
}