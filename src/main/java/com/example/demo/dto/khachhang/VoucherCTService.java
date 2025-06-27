package com.example.totnghiep.Service;

import com.example.totnghiep.Entity.VoucherCT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoucherCTService {
    Page<VoucherCT> getAll(String keyword, Pageable pageable);

    VoucherCT getById(String id);

    void save(VoucherCT v);

    void delete(String id);
}
