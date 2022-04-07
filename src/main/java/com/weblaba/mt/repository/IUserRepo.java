package com.weblaba.mt.repository;

import com.weblaba.mt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface IUserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUname(String name);

    @Query("SELECT u.id FROM User u WHERE u.email = ?1")
    Long findIdByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.uname = ?1")
    Long findIdByUname(String nickname);

    @Query("SELECT u.uname FROM User u WHERE u.id = ?1")
    String findUnameById(Long id);

    @Query("SELECT u.pfImgName FROM User u WHERE u.id = ?1")
    Long findPfImgNameById(Long id);

    @Query("SELECT u FROM User u WHERE u.uname LIKE %:part%")
    List<User> findByNameContaining(String part);

    @Query("SELECT u.pfInfo FROM User u WHERE u.id = ?1")
    String findProfileInfoById(Long id);

    @Query("SELECT u.created FROM User u WHERE u.id = ?1")
    Timestamp findCreationDateById(Long id);
}