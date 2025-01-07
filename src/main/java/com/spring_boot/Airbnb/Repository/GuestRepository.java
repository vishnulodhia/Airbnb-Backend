package com.spring_boot.Airbnb.Repository;

import com.spring_boot.Airbnb.Model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest,Long> {
}
