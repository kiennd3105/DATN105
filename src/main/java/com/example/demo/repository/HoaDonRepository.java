package com.example.demo.repository;

import com.example.demo.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String> {
    List<HoaDon> findByMaHoaDonContaining(String keyword);
    List<HoaDon> findByTrangThai(Integer trangThai);
    List<HoaDon> findByMaHoaDonContainingAndTrangThai(String keyword, Integer trangThai);

}
