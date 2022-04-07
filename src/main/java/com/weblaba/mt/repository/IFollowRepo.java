package com.weblaba.mt.repository;

import com.weblaba.mt.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFollowRepo extends JpaRepository<Follow, Long> {
    List<Follow> findAllByUserId(Long id);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.userId = ?1")
    int findFollowsCountById(Long id);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.followingId = ?1")
    int findFollowedCountById(Long id);

    @Query("SELECT f.idRow FROM Follow f WHERE f.userId = ?1 AND f.followingId = ?2")
    Long findRowIdByIdPair(Long userId, Long followedId);

    Follow findFollowByUserIdAndFollowingId(Long userId, Long followingId);
}