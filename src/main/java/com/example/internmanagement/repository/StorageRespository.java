package com.example.internmanagement.repository;

import com.example.internmanagement.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRespository extends JpaRepository<ImageData,Long> {

    Optional<ImageData> findByName(String fileName);
}
