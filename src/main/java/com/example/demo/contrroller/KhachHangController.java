package com.example.demo.contrroller;

import com.example.demo.dto.khachhang.KhachHangResponse;
import com.example.demo.dto.khachhang.KhachHangService;
import com.example.demo.entity.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("khachhang")
public class KhachHangController {

    @Autowired
    KhachHangRepository khachHangRepository;
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/table")
    public ResponseEntity<?> findAll() {
        List<KhachHangResponse> list = new ArrayList<>();

        khachHangRepository.findAll().forEach(khachHang -> list.add(khachHang.toResponse()));

        khachHangRepository.findAll().forEach(kh -> list.add(kh.toResponse()));

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        Optional<KhachHang> opt = khachHangRepository.findById(id);
        return opt.map(kh -> ResponseEntity.ok(kh.toResponse()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid KhachHang khachHang) {
        khachHang.setId(null); // để JPA tự sinh ID nếu cần
        khachHang.setNgayTao(LocalDateTime.now());
        return ResponseEntity.ok(khachHangRepository.save(khachHang).toResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody KhachHang updateData) {
        Optional<KhachHang> opt = khachHangRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        KhachHang kh = opt.get();
        kh.setTen(updateData.getTen());
        kh.setEmail(updateData.getEmail());
        kh.setGioiTinh(updateData.getGioiTinh());
        kh.setSdt(updateData.getSdt());
        kh.setDiaChi(updateData.getDiaChi());
        kh.setNgaySua(LocalDateTime.now());

        return ResponseEntity.ok(khachHangRepository.save(kh).toResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!khachHangRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        khachHangRepository.deleteById(id);
        return ResponseEntity.ok("Đã xóa khách hàng có ID: " + id);
    }
    @GetMapping("/all")
    public List<KhachHang> getAllKhachHang() {
        return khachHangService.getAll();
    }
}
