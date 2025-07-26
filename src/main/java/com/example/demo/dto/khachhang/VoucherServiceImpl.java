package com.example.demo.dto.khachhang;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private KhachHangService khachHangService;

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
    public Optional<Voucher> findByIdWithKhachHangs(String id) {
        return voucherRepository.findByIdWithKhachHangs(id);
    }

    @Override
    public Voucher save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher update(String id, Voucher voucher) {
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

    @Override
    public Voucher toggleStatus(String id) {
        return findById(id).map(v -> {
            v.setTrangthai(v.getTrangthai() == 1 ? 0 : 1);
            return voucherRepository.save(v);
        }).orElseThrow(() -> new RuntimeException("Voucher không tồn tại"));
    }

    @Override
    public Page<Voucher> searchByKeywordAndStatus(String keyword, String status, Pageable pageable) {
        keyword = keyword == null ? "" : keyword.trim();
        if (status == null || status.trim().isEmpty()) {
            return voucherRepository.findByKeyword(keyword, pageable);
        }
        int st = Integer.parseInt(status.trim());
        return voucherRepository.findByKeywordAndTrangthai(keyword, st, pageable);
    }

//    @Override
//    public VoucherDTO toDTO(Voucher entity) {
//        VoucherDTO dto = modelMapper.map(entity, VoucherDTO.class);
//        if (entity.getKhachHangs() != null) {
//            Set<KhachHangDTO> khachHangs = entity.getKhachHangs().stream()
//                    .map(khachHangService::toDTO)
//                    .collect(Collectors.toSet());
//            dto.setKhachHangs(khachHangs);
//        }
//        return dto;
//    }
//
//    @Override
//    public Voucher toEntity(VoucherDTO dto) {
//        Voucher entity = modelMapper.map(dto, Voucher.class);
//        if (dto.getKhachHangs() != null) {
//            Set<KhachHang> khachHangs = dto.getKhachHangs().stream()
//                    .map(khachHangService::toEntity)
//                    .collect(Collectors.toSet());
//            entity.setKhachHangs(khachHangs);
//        }
//        return entity;
//    }

}

