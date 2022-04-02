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

    public List<Follow> getAllFollowsById(Long id) {
        List<Follow> list = repo.findAllByUserId(id);
        return list;
    }
}