package com.weblaba.mt.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "image_url")
    private String imageUrl;
    @Basic
    @Column(name = "uname")
    private String uname;
    @Basic
    @Column(name = "created")
    private Timestamp created;
    @Basic
    @Column(name = "pf_info")
    private String pfInfo;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "pf_img_name")
    private Long pfImgName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getPfInfo() {
        return pfInfo;
    }

    public void setPfInfo(String pfInfo) {
        this.pfInfo = pfInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPfImgName() {
        return pfImgName;
    }

    public void setPfImgName(Long pfImgName) {
        this.pfImgName = pfImgName;
    }

    public User() { }

    public User(String email, String uname, String password) {
        this.email = email;
        this.uname = uname;
        this.password = password;
        this.created = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (imageUrl != null ? !imageUrl.equals(user.imageUrl) : user.imageUrl != null) return false;
        if (uname != null ? !uname.equals(user.uname) : user.uname != null) return false;
        if (created != null ? !created.equals(user.created) : user.created != null) return false;
        if (pfInfo != null ? !pfInfo.equals(user.pfInfo) : user.pfInfo != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (pfImgName != null ? !pfImgName.equals(user.pfImgName) : user.pfImgName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (uname != null ? uname.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (pfInfo != null ? pfInfo.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (pfImgName != null ? pfImgName.hashCode() : 0);
        return result;
    }
}
