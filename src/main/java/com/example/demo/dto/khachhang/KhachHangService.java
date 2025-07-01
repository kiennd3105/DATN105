package com.example.demo.dto.khachhang;


import com.example.demo.entity.KhachHang;

import java.util.List;

public interface KhachHangService {
    List<KhachHang> findAll();
    List<KhachHang> getAll();
}
