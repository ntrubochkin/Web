package com.weblaba.mt.service;

import com.weblaba.mt.model.Note;
import com.weblaba.mt.repository.INoteRepo;
import com.weblaba.mt.stuff.UploadTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NoteService {
    private INoteRepo repo;

    @Autowired
    public NoteService(INoteRepo repo) {
        this.repo = repo;
    }

    public List<Note> getAllNotesById(Long id) {
        List<Note> list = repo.findAllByUserId(id);
        return list;
    }

    public List<Note> getAllNotesWithinAWeek() {
        long end = System.currentTimeMillis();
        long start = end - UploadTimeConverter.ONE_WEEK_MS;
        return repo.findAllBetweenDates(new Timestamp(start), new Timestamp(end));
    }

    public void addNote(Note n) {
        repo.save(n);
    }
}