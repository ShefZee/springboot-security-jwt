package com.shefzee.springsecurity.repository;

import com.shefzee.springsecurity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

    User findByName(String name);
}
