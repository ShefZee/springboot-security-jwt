package com.shefzee.springsecurity.repository;

import com.shefzee.springsecurity.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer>{

    List<UserRole> findAllByUser_Name(String name);
}
