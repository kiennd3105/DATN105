package com.example.demo.dto.khachhang;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.dto.khachhang.VoucherDTO;
import com.example.demo.dto.khachhang.KhachHangDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

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
    @Transactional
    public Voucher save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    @Transactional
    public Voucher update(String id, Voucher voucher) {
        if (!voucherRepository.existsById(id)) {
            throw new NoSuchElementException("Voucher not found with id: " + id);
        }
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
        Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        if (optionalVoucher.isEmpty()) {
            throw new NoSuchElementException("Voucher not found with id: " + id);
        }
        Voucher voucher = optionalVoucher.get();
        int currentStatus = voucher.getTrangthai();
        voucher.setTrangthai(currentStatus == 1 ? 0 : 1);
        return voucherRepository.save(voucher);
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

    @Override
    public KhachHangDTO toDTO(KhachHang entity) {
        return KhachHangDTO.builder()
                .id(entity.getId())
                .ten(entity.getTen())
                .email(entity.getEmail())
                .sdt(entity.getSdt())
                .build();
    }

    @Override
    public KhachHang toEntity(KhachHangDTO dto) {
        return KhachHang.builder()
                .id(dto.getId())
                .ten(dto.getTen())
                .email(dto.getEmail())
                .sdt(dto.getSdt())
                .build();
    }

    @Override
    public VoucherDTO toDTO(Voucher entity) {
        return VoucherDTO.builder()
                .id(entity.getId())
                .mavc(entity.getMavc())
                .code(entity.getCode())
                .dieukien(entity.getDieukien())
                .giatrigiam(entity.getGiatrigiam())
                .loaivc(entity.getLoaivc())
                .soluong(entity.getSoluong())
                .loaigiamgia(entity.getLoaigiamgia())
                .trangthai(entity.getTrangthai())
                .ngaytao(entity.getNgaytao())
                .ngaysua(entity.getNgaysua())
                .ngayBatDau(entity.getNgayBatDau())
                .ngayHetHan(entity.getNgayHetHan())
                .khachHangs(entity.getKhachHangs()
                        .stream()
                        .map(this::toDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Voucher toEntity(VoucherDTO dto) {
        Set<KhachHang> khachHangs = dto.getKhachHangs().stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());

        return Voucher.builder()
                .id(dto.getId())
                .mavc(dto.getMavc())
                .code(dto.getCode())
                .dieukien(dto.getDieukien())
                .giatrigiam(dto.getGiatrigiam())
                .loaivc(dto.getLoaivc())
                .soluong(dto.getSoluong())
                .loaigiamgia(dto.getLoaigiamgia())
                .trangthai(dto.getTrangthai())
                .ngaytao(dto.getNgaytao())
                .ngaysua(dto.getNgaysua())
                .ngayBatDau(dto.getNgayBatDau())
                .ngayHetHan(dto.getNgayHetHan())
                .khachHangs(khachHangs)
                .build();
    }
}