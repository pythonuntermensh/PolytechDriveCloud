package com.polytech.drive.Repository;

import com.polytech.drive.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Long, User> {
    Optional<User> findByEmail(String email);
}
