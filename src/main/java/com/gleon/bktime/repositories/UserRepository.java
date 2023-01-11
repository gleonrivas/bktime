package com.gleon.bktime.repositories;

import com.gleon.bktime.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user where id = :id", nativeQuery = true)
    User findUserById(Integer id);

    @Query(value = "select * from user where user_name like :param or email like :param limit 1", nativeQuery = true)
    User verifyUsernameOrEmail(String param);

    @Query(value = "select user_name from user where user_name like :userName", nativeQuery = true)
    String findUserName(String userName);

    @Query(value = "select email from user where email like :email", nativeQuery = true)
    String findEmail(String email);

    @Query(value = "select * from user where user_name like :userName", nativeQuery = true)
    User findByUserName(String userName);

    @Query(value = "select * from user", nativeQuery = true)
    List<User> showUsers();

    @Query(value = "select * from user where email like :email", nativeQuery = true)
    User findUserByEmail(String email);

}
