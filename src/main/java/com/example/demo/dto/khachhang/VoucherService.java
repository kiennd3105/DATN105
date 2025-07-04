package com.example.demo.dto.khachhang;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VoucherService {

    // Lấy tất cả (không phân trang)
    List<Voucher> findAll();

    // (nếu cần) khác gì findAll? Nếu không khác thì bỏ getAll()
    List<Voucher> getAll();

    // Lấy theo ID
    Optional<Voucher> findById(String id);

    // Tạo mới hoặc cập nhật
    Voucher save(Voucher voucher);

    // Cập nhật riêng biệt (nếu thực sự có logic riêng)
    Voucher update(String id, Voucher voucher);

    // Xoá theo ID
    void delete(String id);

    // Trùng với findById → có thể xoá 1 trong 2
    Optional<Voucher> getById(String id);

    // Tìm theo keyword không phân trang
    List<Voucher> search(String keyword);

    // Tìm có keyword và trạng thái + phân trang
    Page<Voucher> searchByKeywordAndStatus(String keyword, String status, Pageable pageable);
}
