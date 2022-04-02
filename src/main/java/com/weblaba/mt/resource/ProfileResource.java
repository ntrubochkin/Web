package com.weblaba.mt.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/shrimp")
public class ProfileResource {

    @GetMapping("/{id}")
    @ResponseBody
    public List<String> sendToProfile(@PathVariable("id") Long id) {
        return List.of(new String(id.toString()));
    }
}