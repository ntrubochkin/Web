package com.weblaba.mt.service;

import com.weblaba.mt.model.Follow;
import com.weblaba.mt.repository.IFollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    private IFollowRepo repo;

    @Autowired
    public FollowService(IFollowRepo repo) {
        this.repo = repo;
    }

    public List<Follow> findAllFollowsById(Long id) {
        List<Follow> list = repo.findAllByUserId(id);
        return list;
    }

    public int findFollowsCountById(Long id) {
        return repo.findFollowsCountById(id);
    }

    public int findFollowedCountById(Long id) {
        return repo.findFollowedCountById(id);
    }

    public void addFollow(Long userId, Long toFollowId) {
        Follow f = new Follow(userId, toFollowId);
        repo.save(f);
    }

    public boolean isFollowing(Long userId, Long checkId) {
        Long rowId = repo.findRowIdByIdPair(userId, checkId);
        return rowId != null;
    }

    public void deleteByIdPair(Long clientId, Long observedId) {
        Follow f = repo.findFollowByUserIdAndFollowingId(clientId, observedId);
        repo.delete(f);
    }
}