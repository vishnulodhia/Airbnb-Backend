package com.spring_boot.Airbnb.Repository;

import com.spring_boot.Airbnb.Model.Room;
import com.spring_boot.Airbnb.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
}
