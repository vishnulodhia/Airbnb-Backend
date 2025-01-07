package com.spring_boot.Airbnb.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Room{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false,precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(columnDefinition = "text[]")
    private String[] photos;

    @Column(columnDefinition = "text[]")
    private String[] amenities;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(nullable = false)
    private Boolean active;


}
