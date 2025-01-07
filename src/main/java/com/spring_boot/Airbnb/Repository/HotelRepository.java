package com.spring_boot.Airbnb.Repository;

import com.spring_boot.Airbnb.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
