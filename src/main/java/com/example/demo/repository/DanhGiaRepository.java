package com.example.demo.repository;

import com.example.demo.entity.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DanhGiaRepository extends JpaRepository<DanhGia, String> {
}
