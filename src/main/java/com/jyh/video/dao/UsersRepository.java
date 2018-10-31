package com.jyh.video.dao;


import com.jyh.video.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {

    Users findByUsername(String username);



}