package com.example.demo.dto.khachhang;


import com.example.demo.entity.HoaDon;

import java.util.List;

public interface HoaDonService {
    List<HoaDon> findAll();
    List<HoaDon> getAll();
}