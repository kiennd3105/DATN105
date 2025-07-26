package com.example.demo.dto.khachhang;

import com.example.demo.entity.VoucherCT;
import com.example.demo.repository.VoucherCTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherCTServiceImpl implements VoucherCTService {

    @Autowired
    private VoucherCTRepository repository;

    @Override
    public List<VoucherCT> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<VoucherCT> findById(String id) {
        return repository.findDetailById(id);
    }

    @Override
    public VoucherCT save(VoucherCT voucherCT) {
        VoucherCT saved = repository.save(voucherCT);
        return repository.findDetailById(saved.getId()).orElse(saved);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<VoucherCT> filterBy(String idvc, String idkh, String idhd) {
        return repository.filterNoPage(idvc, idkh, idhd);
    }

    @Override
    public Page<VoucherCT> filterBy(String idvc, String idkh, String idhd, Pageable pageable) {
        return repository.filter(idvc, idkh, idhd, pageable);
    }

    @Override
    public List<String> findUsedVoucherIds() {
        return repository.findUsedVoucherIds();
    }
}
