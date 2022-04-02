package com.weblaba.mt.model;

public class PackedNote {
    public String uploadTime;
    public String name;
    public String text;
    public Long avatarImg;
    public Long memeImg;

    public String getUploadTime() {
        return uploadTime;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Long getMemeImg() {
        return memeImg;
    }

    public Long getAvatarImg() {
        return avatarImg;
    }

    public PackedNote(String uploadTime, String name, String text, Long avatarImg, Long memeImg) {
        this.uploadTime = uploadTime;
        this.name = name;
        this.text = text;
        this.avatarImg = avatarImg;
        this.memeImg = memeImg;
    }
}