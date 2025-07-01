package com.example.demo.dto.khachhang;

import com.example.demo.entity.VoucherCT;
import com.example.demo.repository.VoucherCTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VoucherCTServiceImpl implements VoucherCTService {

    @Autowired
    private VoucherCTRepository repo;

    @Override
    public Page<VoucherCT> getAll(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return repo.search(keyword.toLowerCase(), pageable);
        }
        return repo.findAll(pageable);
    }

    @Override
    public VoucherCT getById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void save(VoucherCT v) {
        if (v.getId() == null || v.getId().isEmpty()) {
            v.setId(generateId());
            v.setNgayTao(LocalDateTime.now());
        } else {
            v.setNgaySua(LocalDateTime.now());
        }
        repo.save(v);
    }

    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
