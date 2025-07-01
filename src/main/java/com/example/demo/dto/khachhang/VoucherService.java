package com.example.demo.dto.khachhang;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VoucherService {
    List<Voucher> getAll();
    List<Voucher> findAll();
    Optional<Voucher> findById(String id);


    Voucher save(Voucher voucher);
    Voucher update(String id, Voucher newVoucher);

    void delete(String id);

    Voucher view(Voucher voucher);

    Optional<Voucher> getById(String id);

    List<Voucher> search(String keyword);

    Page<Voucher> searchByKeywordAndStatus(String keyword, String status, Pageable pageable);
}

