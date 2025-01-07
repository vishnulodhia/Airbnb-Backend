package com.spring_boot.Airbnb.Repository;

import com.spring_boot.Airbnb.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
