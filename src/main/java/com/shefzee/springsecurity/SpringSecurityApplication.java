package com.shefzee.springsecurity;

import com.shefzee.springsecurity.domain.*;
import com.shefzee.springsecurity.repository.RolePermissionsRepositiory;
import com.shefzee.springsecurity.repository.RoleRepository;
import com.shefzee.springsecurity.repository.UserRepository;
import com.shefzee.springsecurity.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
	UserRepository userRepository;

    @Autowired
	RoleRepository roleRepository;

    @Autowired
	UserRoleRepository userRoleRepository;

    @Autowired
	RolePermissionsRepositiory rolePermissionsRepositiory;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Role role1 = createRole("ADMIN_L1",Arrays.asList("ADMIN_WRITE","ADMIN_DELETE"));
		Role role2 = createRole("ADMIN_L2",Arrays.asList("ADMIN_WRITE"));

		createUserRole("shef1",Arrays.asList(role1));
		createUserRole("shef2",Arrays.asList(role2));

	}

	private Role createRole(String roleName, List<String> permissions){

		Role role1 = Role.builder().name("ROLE_"+roleName).build();
		role1 = roleRepository.save(role1);
		for(String p : permissions){
			RolePermissions permission = RolePermissions.builder().role(role1).permission(p).build();
			rolePermissionsRepositiory.save(permission);
		}

		return role1;
	}

	private User createUserRole(String userName, List<Role> roles){

		User user = User.builder().name(userName).password(passwordEncoder.encode("1234")).build();
		user = userRepository.save(user);
		for(Role r : roles){
			UserRole userRole = UserRole.builder().role(r).user(user).build();
			userRoleRepository.save(userRole);
		}

		return user;

	}
}
