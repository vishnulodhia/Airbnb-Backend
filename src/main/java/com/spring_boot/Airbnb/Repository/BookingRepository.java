package com.spring_boot.Airbnb.Repository;

import com.spring_boot.Airbnb.Model.Booking;
import com.spring_boot.Airbnb.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
