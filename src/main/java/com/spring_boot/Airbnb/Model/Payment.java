package com.spring_boot.Airbnb.Model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = true)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false,precision =    10 ,scale = 2)
    private BigDecimal amount;

    @OneToOne(mappedBy = "payment",fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;


}
