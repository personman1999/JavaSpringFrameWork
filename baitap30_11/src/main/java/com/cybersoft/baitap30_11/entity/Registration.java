package com.cybersoft.baitap30_11.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registrationId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "registration_date")
    private Date registrationDate;
}
