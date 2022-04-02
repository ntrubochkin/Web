package com.weblaba.mt.service;

import com.weblaba.mt.exception.UserNotFoundException;
import com.weblaba.mt.model.User;
import com.weblaba.mt.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final PasswordEncoder pwdEncoder;
    private final IUserRepo repo;

    @Autowired
    public UserService(IUserRepo repo, PasswordEncoder pwdEncoder) {
        this.repo = repo;
        this.pwdEncoder = pwdEncoder;
    }

    public User findUserByEmail(String email) {
        return repo.findUserByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User with email address " + email + " not found."));
    }

    public User findUserByName(String name) {
        return repo.findUserByUname(name)
                .orElseThrow(()-> new UserNotFoundException(("User with nickname \"" + name + "\" not found.")));
    }

    public Long findIdByEmail(String email) {
        return repo.findIdByEmail(email);
    }

    public Long findIdByNickname(String uName) {
        return repo.findIdByUname(uName);
    }

    public String findNameById(Long id) {
        return repo.findUnameById(id);
    }

    public Long findProfileImageById(Long id) {
        Long name = repo.findPfImgNameById(id);
        return (name == null) ? 0L : name;
    }

    public void addNewUser(String email, String uname, String password) {
        User u = new User(email, uname, pwdEncoder.encode(password));
        repo.save(u);
    }

    public boolean passwordMatches(String rawPwd, String userPwd) {
        return pwdEncoder.matches(rawPwd, userPwd);
    }

    public List<User> searchByPart(String part) {
        return repo.findByNameContaining(part);
    }
}