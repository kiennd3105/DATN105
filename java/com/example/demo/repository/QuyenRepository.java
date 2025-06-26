package com.example.demo.repository;

import com.example.demo.entity.Quyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuyenRepository extends JpaRepository<Quyen, String> {
    Quyen findByMa(String ma);

    Optional<Quyen> findById(String id);

}
