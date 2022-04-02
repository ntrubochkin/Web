package com.weblaba.mt.repository;

import com.weblaba.mt.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFollowRepo extends JpaRepository<Follow, Long> {
    List<Follow> findAllByUserId(Long id);
}