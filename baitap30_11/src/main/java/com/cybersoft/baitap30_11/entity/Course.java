package com.cybersoft.baitap30_11.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "duration")
    private int duration;

    @OneToMany(mappedBy = "course")
    private List<Registration> registrations;

}
