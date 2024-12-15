package com.cybersoft.demojwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    public ResponseEntity<?> getProduct() {
        return ResponseEntity.ok("Get All Product");
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertProduct() {
        return ResponseEntity.ok("Insert Product");
    }
}

