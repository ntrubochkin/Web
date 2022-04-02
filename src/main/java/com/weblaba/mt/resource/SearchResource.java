package com.weblaba.mt.resource;

import com.weblaba.mt.model.User;
import com.weblaba.mt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchResource {
    private final UserService service;

    public SearchResource(UserService service) {
        this.service = service;
    }

    @GetMapping("/search/{text}")
    @ResponseBody
    public List<User> searchUsers(@PathVariable("text") String text) {
        return service.searchByPart(text);
    }
}