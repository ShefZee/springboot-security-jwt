package com.shefzee.springsecurity.controller;

import com.shefzee.springsecurity.repository.RoleRepository;
import com.shefzee.springsecurity.repository.UserRepository;
import com.shefzee.springsecurity.request.AuthRequest;
import com.shefzee.springsecurity.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class HomeController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    JWTUtil jwtUtil;

    @GetMapping("/api/users")
    public ResponseEntity getAllUsers(){

        return ResponseEntity.ok(userRepository.findAll());

    }

    @GetMapping("/api/roles")
    public ResponseEntity getAllRoles(){

        return ResponseEntity.ok(roleRepository.findAll());

    }

    @GetMapping("/api/test1")
    @PreAuthorize("hasAnyAuthority('ADMIN_WRITE', 'ADMIN_DELETE')")
    //shef1 & shef2 has access
    public ResponseEntity getTest1(){
        return ResponseEntity.ok("Test 1, Admin Write, Delete access");
    }

    @GetMapping("/api/test2")
    @PreAuthorize("hasAnyAuthority('ADMIN_WRITE')")
    //shef1 & shef2 has access
    public ResponseEntity getTest2(){
        return ResponseEntity.ok("Test 2, Admin Write access");
    }

    @GetMapping("/api/test3")
    @PreAuthorize("hasAnyAuthority('ADMIN_DELETE')")
    //shef1 has access
    public ResponseEntity getTest3(){
        return ResponseEntity.ok("Test 3, Admin Delete access");
    }

    @GetMapping("/api/test4")
    @PreAuthorize("hasRole('ROLE_ADMIN_L1')")
    //shef1 has access
    public ResponseEntity getTest4(){
        return ResponseEntity.ok("Test 4, Admin_L1 access");
    }

    @GetMapping("/api/test5")
    @PreAuthorize("hasRole('ROLE_ADMIN_L2')")
    //shef2 has access
    public ResponseEntity getTest5(){
        return ResponseEntity.ok("Test 5, Admin_L2 access");
    }

    @PostMapping("/api/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }

        return jwtUtil.createJWT(UUID.randomUUID().toString(), authRequest.getUserName());
    }
}
