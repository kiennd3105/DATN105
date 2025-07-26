package com.example.demo.dto.khachhang;

import com.example.demo.entity.VoucherCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VoucherCTService {

    List<VoucherCT> findAll();

    Optional<VoucherCT> findById(String id);

    VoucherCT save(VoucherCT voucherCT);

    void delete(String id);

    // Filter không phân trang
    List<VoucherCT> filterBy(String idvc, String idkh, String idhd);

    // Filter có phân trang
    Page<VoucherCT> filterBy(String idvc, String idkh, String idhd, Pageable pageable);

    // Lấy danh sách IDVC đã sử dụng
    List<String> findUsedVoucherIds();
}