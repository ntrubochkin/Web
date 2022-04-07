package com.weblaba.mt.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Note {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_note")
    private long idNote;

    @Basic
    @Column(name = "user_id")
    private long userId;

    @Basic
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Basic
    @Column(name = "text")
    private String text;

    @Basic
    @Column(name = "img_name")
    private Long imgName;

    public long getIdNote() {
        return idNote;
    }

    public void setIdNote(long idNote) {
        this.idNote = idNote;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getImgName() {
        return imgName;
    }

    public void setImgName(Long imgName) {
        this.imgName = imgName;
    }

    public Note() { }

    public Note(long userId, String text, Long imgName) {
        this.userId = userId;
        this.text = text;
        this.imgName = imgName;
        creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (idNote != note.idNote) return false;
        if (userId != note.userId) return false;
        if (creationDate != null ? !creationDate.equals(note.creationDate) : note.creationDate != null) return false;
        if (text != null ? !text.equals(note.text) : note.text != null) return false;
        if (imgName != null ? !imgName.equals(note.imgName) : note.imgName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idNote ^ (idNote >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (imgName != null ? imgName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "idNote=" + idNote +
                ", userId=" + userId +
                ", creationDate=" + creationDate +
                ", text='" + text + '\'' +
                ", imgName=" + imgName +
                '}';
    }
}
