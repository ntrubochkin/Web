package com.weblaba.mt.repository;

import com.weblaba.mt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUname(String name);

    @Query("SELECT u.password FROM User u WHERE u.email = ?1")
    String findPasswordByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.uname = ?1")
    long findIdByUname(String name);
}