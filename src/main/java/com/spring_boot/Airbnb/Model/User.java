package com.spring_boot.Airbnb.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionIdMutability;

import java.util.Set;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
