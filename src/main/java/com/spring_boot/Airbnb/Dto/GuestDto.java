package com.spring_boot.Airbnb.Dto;

import com.spring_boot.Airbnb.Model.Gender;
import com.spring_boot.Airbnb.Model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
