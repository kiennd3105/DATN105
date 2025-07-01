package com.example.demo.repository;

import com.example.demo.entity.VoucherCT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoucherCTRepository extends JpaRepository<VoucherCT, String> {
    @Query("SELECT v FROM VoucherCT v WHERE " +
            "LOWER(v.khachHang.ten) LIKE %:keyword% OR " +
            "LOWER(v.voucher.mavc) LIKE %:keyword% OR " +
            "LOWER(v.hoaDon.maHoaDon) LIKE %:keyword%")
    Page<VoucherCT> search(@Param("keyword") String keyword, Pageable pageable);

}

