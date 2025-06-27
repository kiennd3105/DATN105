package com.example.totnghiep.Repository;

import com.example.totnghiep.Entity.KhachHang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangRepository extends JpaRepository<KhachHang, String> {

}
