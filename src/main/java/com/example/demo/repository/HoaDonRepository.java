package com.example.totnghiep.Repository;

import com.example.totnghiep.Entity.HoaDon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonRepository extends JpaRepository<HoaDon, String> {

}
