package com.weblaba.mt.repository;

import com.weblaba.mt.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface INoteRepo extends JpaRepository<Note, Long> {
    List<Note> findAllByUserId(Long id);

    @Query("SELECT n FROM Note n WHERE n.creationDate BETWEEN ?1 AND ?2")
    List<Note> findAllBetweenDates(Timestamp start, Timestamp end);
}