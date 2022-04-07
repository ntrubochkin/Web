package com.weblaba.mt.model;

public class PackedFollow {
    public long userId;
    public String userName;
    public Long avatarImg;

    public PackedFollow(long userId, String userName, Long avatarImg) {
        this.userId = userId;
        this.userName = userName;
        this.avatarImg = avatarImg;
    }
}