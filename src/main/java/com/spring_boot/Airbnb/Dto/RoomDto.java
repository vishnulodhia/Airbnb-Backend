package com.spring_boot.Airbnb.Dto;

import com.spring_boot.Airbnb.Model.Hotel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RoomDto {
    private Long id;
    private String type;
    private Integer totalCount;
    private Integer capacity;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private Boolean active;
}
