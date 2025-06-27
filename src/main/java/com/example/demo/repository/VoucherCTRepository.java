package com.example.totnghiep.Repository;

import com.example.totnghiep.Entity.VoucherCT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoucherCTRepository extends JpaRepository<VoucherCT, String> {
    @Query("SELECT v FROM VoucherCT v WHERE " +
            "LOWER(v.khachHang.tenKH) LIKE %:keyword% OR " +
            "LOWER(v.voucher.mavc) LIKE %:keyword% OR " +
            "LOWER(v.hoaDon.maHD) LIKE %:keyword%")
    Page<VoucherCT> search(@Param("keyword") String keyword, Pageable pageable);

}

