package com.weblaba.mt.stuff;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageServer {
    private static ImageServer instance;

    private static final String ROOT_PATH = Paths.get("C:", "LABS", "mt-images").toString();
    private static final String AVATARS_PATH = Paths.get(ROOT_PATH, "avatars").toString();
    private static final String MEMES_PATH = Paths.get(ROOT_PATH, "memes").toString();

    private static final String DEFAULT_AVATAR_NAME = "0";

    private File defaultAvatar;

    private Long lastAvatar;
    private Long lastMeme;

    private ImageServer() {
        initialize();
    }

    public static ImageServer getInstance() {
        if(instance == null) {
            instance = new ImageServer();
        }
        return instance;
    }

    private void initialize() {
        defaultAvatar = findFileByName(AVATARS_PATH, DEFAULT_AVATAR_NAME);
        lastAvatar = getLastFileNameAsLong(AVATARS_PATH);
        lastMeme = getLastFileNameAsLong(MEMES_PATH);
    }

    private Long getLastFileNameAsLong(String folder) {
        File dir = new File(folder);
        File[] files = dir.listFiles();
        long max = Long.MIN_VALUE;

        for (File f : files) {
            String name = getNumberFromName(f.getName());
            long value = Long.parseLong(name);
            if(value > max) {
                max = value;
            }
        }

        return max;
    }

    private String getNumberFromName(String name) {
        return name.split("\\.")[0];
    }

    private File findFileByName(String folder, String filter) {
        File dir = new File(folder);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(filter);
            }
        });
        return files[0];
    }

    public byte[] getAvatar(String name) {
        try {
            if(name.equals(DEFAULT_AVATAR_NAME)) {
                return Files.readAllBytes(defaultAvatar.toPath());
            }

            File f = findFileByName(AVATARS_PATH, name);
            return Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public byte[] getMeme(String name) {
        try {
            File f = findFileByName(MEMES_PATH, name);
            return Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public Long saveMeme(MultipartFile file) {
        lastMeme++;
        String imgFormat = file.getOriginalFilename().split("\\.")[1];
        String fileName = String.join(".", lastMeme.toString(), imgFormat);
        Path outPath = Paths.get(MEMES_PATH, fileName);

        try {
            Files.copy(file.getInputStream(), outPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return lastMeme;
    }
}