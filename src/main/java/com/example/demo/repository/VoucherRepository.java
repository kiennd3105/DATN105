package com.example.demo.repository;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, String> {

    // Tìm theo code (keyword)
    @Query("SELECT v FROM Voucher v WHERE LOWER(v.code) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Voucher> findByKeyword(String keyword, Pageable pageable);

    // Tìm theo code + trạng thái
    @Query("SELECT v FROM Voucher v WHERE LOWER(v.code) LIKE LOWER(CONCAT('%', :keyword, '%')) AND v.trangthai = :status")
    Page<Voucher> findByKeywordAndTrangthai(String keyword, int status, Pageable pageable);

    @Query("select v from Voucher v left join fetch v.khachHangs where v.id = :id")
    Optional<Voucher> findByIdWithKhachHangs(@Param("id") String id);

    List<Voucher> findByCodeContainingIgnoreCase(String code);
}
