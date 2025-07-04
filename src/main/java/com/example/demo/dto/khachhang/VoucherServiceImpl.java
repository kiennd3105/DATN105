package com.example.demo.dto.khachhang;

import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public List<Voucher> getAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Optional<Voucher> findById(String id) {
        return voucherRepository.findById(id);
    }

    @Override
    public Voucher save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher update(String id, Voucher voucher) {
        if (!voucherRepository.existsById(id)) return null;
        voucher.setId(id);
        return voucherRepository.save(voucher);
    }

    @Override
    public void delete(String id) {
        voucherRepository.deleteById(id);
    }

    @Override
    public Optional<Voucher> getById(String id) {
        return voucherRepository.findById(id);
    }

    @Override
    public List<Voucher> search(String keyword) {
        return voucherRepository.findByCodeContainingIgnoreCase(keyword);
    }

    // ✅ Quan trọng: hiện thực đúng search theo keyword + status
    @Override
    public Page<Voucher> searchByKeywordAndStatus(String keyword, String status, Pageable pageable) {
        if (status == null || status.isEmpty()) {
            return voucherRepository.findByKeyword(keyword, pageable);
        } else {
            try {
                int statusInt = Integer.parseInt(status);
                return voucherRepository.findByKeywordAndTrangthai(keyword, statusInt, pageable);
            } catch (NumberFormatException e) {
                return voucherRepository.findByKeyword(keyword, pageable);
            }
        }
    }
}
