package com.example.demo.repository;

import com.example.demo.entity.VoucherCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoucherCTRepository extends JpaRepository<VoucherCT, String> {

    // ✅ Filter có phân trang - THÊM JOIN FETCH để tránh null
    @Query("""
        SELECT v FROM VoucherCT v
        LEFT JOIN FETCH v.voucher
        LEFT JOIN FETCH v.khachHang
        LEFT JOIN FETCH v.hoaDon
        WHERE 
            (:idvc IS NULL OR :idvc = '' OR v.voucher.id = :idvc) AND 
            (:idkh IS NULL OR :idkh = '' OR v.khachHang.id = :idkh) AND 
            (:idhd IS NULL OR :idhd = '' OR v.hoaDon.id = :idhd)
    """)
    Page<VoucherCT> filter(
            @Param("idvc") String idvc,
            @Param("idkh") String idkh,
            @Param("idhd") String idhd,
            Pageable pageable
    );

    // ✅ Filter không phân trang - cũng cần JOIN FETCH
    @Query("""
        SELECT v FROM VoucherCT v
        LEFT JOIN FETCH v.voucher
        LEFT JOIN FETCH v.khachHang
        LEFT JOIN FETCH v.hoaDon
        WHERE 
            (:idvc IS NULL OR :idvc = '' OR v.voucher.id = :idvc) AND 
            (:idkh IS NULL OR :idkh = '' OR v.khachHang.id = :idkh) AND 
            (:idhd IS NULL OR :idhd = '' OR v.hoaDon.id = :idhd)
    """)
    List<VoucherCT> filterNoPage(
            @Param("idvc") String idvc,
            @Param("idkh") String idkh,
            @Param("idhd") String idhd
    );

    // ✅ Chi tiết theo ID (dùng khi sửa)
    @Query("""
        SELECT v FROM VoucherCT v
        LEFT JOIN FETCH v.voucher
        LEFT JOIN FETCH v.khachHang
        LEFT JOIN FETCH v.hoaDon
        WHERE v.id = :id
    """)
    Optional<VoucherCT> findDetailById(@Param("id") String id);

    // ✅ Lấy danh sách ID voucher đã dùng (trangthai = 1)
    @Query("""
        SELECT DISTINCT v.voucher.id FROM VoucherCT v
        WHERE v.trangthai = 1
    """)
    List<String> findUsedVoucherIds();
}
