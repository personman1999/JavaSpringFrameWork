package com.cybersoft.demospringsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    public ResponseEntity<?> getProduct() {
        return ResponseEntity.ok("Get request");
    }


    @PostMapping
    public ResponseEntity<?> postProduct() {
        return ResponseEntity.ok("Post request");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct() {
        return ResponseEntity.ok("Delete request");
    }
}
