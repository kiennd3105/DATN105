package com.example.totnghiep.Service;

import com.example.totnghiep.Entity.KhachHang;
import com.example.totnghiep.Entity.Voucher;
import com.example.totnghiep.Repository.KhachHangRepository;
import com.example.totnghiep.Repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public Voucher view(Voucher voucher) {
        // Set các thông tin mặc định nếu cần
        voucher.setNgaytao(LocalDateTime.now());

        // Tạo ID tự động nếu chưa có
        if (voucher.getId() == null || voucher.getId().isEmpty()) {
            String newId = "VC" + String.format("%06d", voucherRepository.count() + 1);
            voucher.setId(newId);
        }

        return voucherRepository.save(voucher);
    }

//    @Override
//    public Voucher save(Voucher voucher) {
//        if (voucher.getId() == null || voucher.getId().isEmpty()) {
//            voucher.setId(UUID.randomUUID().toString());
//            voucher.setNgaytao(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
//        }
//        voucher.setNgaysua(LocalDateTime.now());
//        return voucherRepository.save(voucher);
//    }
@Override
public Voucher save(Voucher voucher) {
    if (voucher.getId() == null || voucher.getId().isEmpty()) {
        int nextId = getNextSequenceNumber(); // tự viết hàm này
        String newId = "VC" + String.format("%06d", nextId);
        voucher.setId(newId);
        voucher.setNgaytao(LocalDateTime.now());
    } else {
        voucher.setNgaysua(LocalDateTime.now());
    }

    voucherRepository.save(voucher);
    return voucher;
}

    // 👇 Tùy chọn: lấy số ID lớn nhất từ DB và +1
    private int getNextSequenceNumber() {
        String maxId = voucherRepository.findMaxId(); // VD: VC000128
        if (maxId == null) return 1;
        return Integer.parseInt(maxId.substring(2)) + 1;
    }

    @Override
    public Voucher update(String id, Voucher newVoucher) {
        return voucherRepository.findById(id).map(v -> {
            v.setCode(newVoucher.getCode());
            v.setDieukien(newVoucher.getDieukien());
            v.setGiatrigiam(newVoucher.getGiatrigiam());
            v.setSoluong(newVoucher.getSoluong());
            v.setTrangthai(newVoucher.getTrangthai());
            v.setNgaysua(LocalDateTime.now());
//            v.setNgayBatDau(LocalDate.from(LocalDateTime.now()));
            return voucherRepository.save(v);
        }).orElse(null);
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

    @Override
    public Page<Voucher> searchByKeywordAndStatus(String keyword, String status, Pageable pageable) {
        if (status == null || status.isEmpty()) {
            return voucherRepository.findByKeyword(keyword, pageable);
        } else {
            try {
                int statusInt = Integer.parseInt(status);
                return voucherRepository.findByKeywordAndTrangthai(keyword, statusInt, pageable);
            } catch (NumberFormatException e) {
                return voucherRepository.findByKeyword(keyword, pageable); // fallback nếu status không phải số
            }
        }
    }
}
