package com.spring_boot.Airbnb.Repository;

import com.spring_boot.Airbnb.Model.Room;
import com.spring_boot.Airbnb.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
