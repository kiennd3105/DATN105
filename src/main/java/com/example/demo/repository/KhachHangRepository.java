package com.example.demo.repository;


import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, String> {

    List<KhachHang> findAllByIdIn(Collection<String> ids);
}
