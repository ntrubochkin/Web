package com.weblaba.mt.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Follow {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_row")
    private long idRow;
    @Basic
    @Column(name = "user_id")
    private long userId;
    @Basic
    @Column(name = "following_id")
    private Long followingId;

    public long getIdRow() {
        return idRow;
    }

    public void setIdRow(long idRow) {
        this.idRow = idRow;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Follow follow = (Follow) o;

        if (idRow != follow.idRow) return false;
        if (userId != follow.userId) return false;
        if (followingId != null ? !followingId.equals(follow.followingId) : follow.followingId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idRow ^ (idRow >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (followingId != null ? followingId.hashCode() : 0);
        return result;
    }
}
