package com.spring_boot.Airbnb.Repository;

import com.spring_boot.Airbnb.Dto.HotelPriceDto;
import com.spring_boot.Airbnb.Model.Hotel;
import com.spring_boot.Airbnb.Model.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface HotelMinRepository extends JpaRepository<HotelMinPrice,Long> {

    @Query(
            """
            SELECT new com.spring_boot.Airbnb.Dto.HotelPriceDto(i.hotel,AVG(i.price)) FROM HotelMinPrice i
            WHERE i.hotel.city = :city
                AND i.date BETWEEN :startDate AND :endDate
                AND i.hotel.active = true
               Group By i.hotel
            """
    )
    Page<HotelPriceDto> findHotelWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable
    );

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}
