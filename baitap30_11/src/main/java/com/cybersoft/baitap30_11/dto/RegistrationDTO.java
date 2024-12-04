package com.cybersoft.baitap30_11.dto;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Data
public class RegistrationDTO {

    private int registrationId;
    private int studentId;
    private int courseId;
    private Date registrationDate;

}