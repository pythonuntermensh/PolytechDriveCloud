package com.polytech.drive.Repository;


import com.polytech.drive.Model.Userr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Userr, Long> {
    Optional<Userr> findByEmail(String email);
}
