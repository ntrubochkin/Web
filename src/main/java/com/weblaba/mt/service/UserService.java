package com.weblaba.mt.service;

import com.weblaba.mt.exception.UserNotFoundException;
import com.weblaba.mt.model.User;
import com.weblaba.mt.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IUserRepo repo;

    @Autowired
    public UserService(IUserRepo repo) {
        this.repo = repo;
    }

    public User findByEmail(String email) {
        return repo.findUserByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User with email address " + email + " not found."));
    }

    public User findByName(String name) {
        return repo.findUserByUname(name)
                .orElseThrow(()-> new UserNotFoundException(("User with nickname " + name + " not found.")));
    }

    public String findPasswordByEmail(String email) {
        return repo.findPasswordByEmail(email);
    }

    public Long findIdByNickname(String name) {
        return repo.findIdByUname(name);
    }

    public User addUser(User user) {
        return repo.save(user);
    }
}