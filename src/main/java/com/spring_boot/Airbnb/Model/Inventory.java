package com.spring_boot.Airbnb.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(name = "unique_hotel_room_date", columnNames = {"hotel_id","room_id","date"}))
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id",nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false,columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookedCount;

    @Column(nullable = false,columnDefinition = "INTEGER DEFAULT 0")
    private Integer reserveCount;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false,precision = 5, scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false,precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private String city;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    private Boolean closed;
}
