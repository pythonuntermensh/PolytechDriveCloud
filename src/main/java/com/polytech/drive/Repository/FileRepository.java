package com.polytech.drive.Repository;

import com.polytech.drive.Model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Long, File> {
}
