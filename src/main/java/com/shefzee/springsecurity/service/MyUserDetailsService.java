package com.shefzee.springsecurity.service;

import com.shefzee.springsecurity.domain.MyUserDetails;
import com.shefzee.springsecurity.domain.User;
import com.shefzee.springsecurity.repository.RolePermissionsRepositiory;
import com.shefzee.springsecurity.repository.UserRepository;
import com.shefzee.springsecurity.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RolePermissionsRepositiory rolePermissionsRepositiory;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByName(s);
        return new MyUserDetails(user,userRoleRepository,rolePermissionsRepositiory);

    }
}
