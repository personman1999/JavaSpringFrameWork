package com.cybersoft.demospringsecurity.controller;

import com.cybersoft.demospringsecurity.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    @Qualifier("b")
    private User user;

    @GetMapping
    public ResponseEntity<?> demo() {

        return ResponseEntity.ok("Hello World " + user.getUsername());
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello World 2");
    }
}
