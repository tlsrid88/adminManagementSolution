package com.example.study.repository;

import com.example.study.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // select * from user where account = ? <- 전달인자로 들어오는 것이 들어오는 것이다.
    Optional<User> findByAccount(String accout);

    Optional<User> findByEmail(String email);

    // select * from user where account = ? and email = ?
    Optional<User> findByEmailAndAccount(String email, String account);

    Optional<User> findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);



}
