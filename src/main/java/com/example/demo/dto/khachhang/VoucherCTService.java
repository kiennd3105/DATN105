package com.example.demo.dto.khachhang;


import com.example.demo.entity.VoucherCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoucherCTService {
    Page<VoucherCT> getAll(String keyword, Pageable pageable);

    VoucherCT getById(String id);

    void save(VoucherCT v);

    void delete(String id);
}
