package com.example.demo.repository;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {

    // Tìm kiếm theo mã CODE (không phân trang)
    List<Voucher> findByCodeContainingIgnoreCase(String keyword);

    // Lấy mã ID lớn nhất dạng 'VC...'
    @Query("SELECT MAX(v.id) FROM Voucher v WHERE v.id LIKE 'VC%'")
    String findMaxId();

    // Tìm kiếm theo keyword (mavc hoặc code) - có phân trang
    @Query("""
        SELECT v FROM Voucher v
        WHERE LOWER(v.mavc) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(v.code) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Voucher> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Tìm kiếm theo keyword + trạng thái
    @Query("""
        SELECT v FROM Voucher v
        WHERE (LOWER(v.mavc) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(v.code) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND v.trangthai = :status
    """)
    Page<Voucher> findByKeywordAndTrangthai(@Param("keyword") String keyword,
                                            @Param("status") Integer status,
                                            Pageable pageable);
}
