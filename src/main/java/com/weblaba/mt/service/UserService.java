package com.weblaba.mt.service;

import com.weblaba.mt.exception.UserNotFoundException;
import com.weblaba.mt.model.User;
import com.weblaba.mt.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {
    public static final String NO_INFO = "No information.";

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
        return getProfileImage(name);
    }

    public static Long getProfileImage(Long name) {
        return (name == null) ? 0L : name;
    }

    public void addNewUser(String email, String uname, String password) {
        User u = new User(email, uname, pwdEncoder.encode(password));
        repo.save(u);
    }

    public void updateUser(User user) {
        repo.save(user);
    }

    public boolean passwordMatches(String rawPwd, String userPwd) {
        return pwdEncoder.matches(rawPwd, userPwd);
    }

    public List<User> searchByPart(String part) {
        return repo.findByNameContaining(part);
    }

    public String findInfoById(Long id) {
        String info = repo.findProfileInfoById(id);
        return (info == null) ? NO_INFO : info;
    }

    public Timestamp findRegistrationDate(Long id) {
        return repo.findCreationDateById(id);
    }
}