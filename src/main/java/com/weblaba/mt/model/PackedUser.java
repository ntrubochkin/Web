package com.weblaba.mt.model;

public class PackedUser {
    private long id;
    private String uname;
    private String created;
    private Long pfImgName;

    public long getId() {
        return id;
    }

    public String getUname() {
        return uname;
    }

    public String getCreated() {
        return created;
    }

    public Long getPfImgName() {
        return pfImgName;
    }

    public PackedUser(long id, String uname, String created, Long pfImgName) {
        this.id = id;
        this.uname = uname;
        this.created = created;
        this.pfImgName = pfImgName;
    }

    @Override
    public String toString() {
        return "PackedUser{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", created='" + created + '\'' +
                ", pfImgName=" + pfImgName +
                '}';
    }
}