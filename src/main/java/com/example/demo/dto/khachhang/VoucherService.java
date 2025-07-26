package com.example.demo.dto.khachhang;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VoucherService {
    List<Voucher> findAll();
    List<Voucher> getAll();
    Optional<Voucher> findById(String id);
    Optional<Voucher> findByIdWithKhachHangs(String id);
    Voucher save(Voucher voucher);
    Voucher update(String id, Voucher voucher);
    void delete(String id);
    Optional<Voucher> getById(String id);
    List<Voucher> search(String keyword);
    Voucher toggleStatus(String id);
    Page<Voucher> searchByKeywordAndStatus(String keyword, String status, Pageable pageable);

    KhachHangDTO toDTO(KhachHang entity);
    KhachHang toEntity(KhachHangDTO dto);
    VoucherDTO toDTO(Voucher entity);
    Voucher toEntity(VoucherDTO dto);
}
