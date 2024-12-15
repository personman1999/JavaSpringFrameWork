package com.cybersoft.demojwt.controller;

import com.cybersoft.demojwt.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
private LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password){
//        System.out.println("CustomsSecurityFilter " );
        return ResponseEntity.ok(loginService.login(username, password));
    }


}
