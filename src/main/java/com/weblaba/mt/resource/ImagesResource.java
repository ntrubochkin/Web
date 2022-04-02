package com.weblaba.mt.resource;

import com.weblaba.mt.stuff.ImageServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImagesResource {
    private ImageServer imgServer;

    public ImagesResource() {
        imgServer = ImageServer.getInstance();
    }

    @RequestMapping("/image/avatar/{name}")
    @ResponseBody
    public byte[] getAvatar(@PathVariable("name") Long name) {
        return imgServer.getAvatar(name.toString());
    }

    @RequestMapping("/image/meme/{name}")
    @ResponseBody
    public byte[] getTheFunny(@PathVariable("name") Long name) {
        return imgServer.getMeme(name.toString());
    }
}