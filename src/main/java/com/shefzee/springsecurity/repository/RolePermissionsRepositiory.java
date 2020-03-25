package com.shefzee.springsecurity.repository;

import com.shefzee.springsecurity.domain.RolePermissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionsRepositiory  extends JpaRepository<RolePermissions,Integer>{

    List<RolePermissions> findByRole_Name(String name);
}
