package com.cybersoft.demojwt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="users")
@Data
public class UserEntity {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
