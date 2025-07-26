package com.example.demo.dto.khachhang;


import com.example.demo.entity.KhachHang;

import java.util.List;
import java.util.Optional;

public interface KhachHangService {
    List<KhachHang> findAll();
    List<KhachHang> getAll();
    Optional<KhachHang> findById(String id);

    List<KhachHang> findAllByIds(List<String> ids);
}
