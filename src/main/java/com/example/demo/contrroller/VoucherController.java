package com.example.demo.contrroller;

import com.example.demo.dto.khachhang.KhachHangService;
import com.example.demo.dto.khachhang.VoucherRespond;
import com.example.demo.dto.khachhang.VoucherService;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vouchers")
@CrossOrigin(origins = "*")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private KhachHangService khachHangService;

    // --- 1. Danh sách có phân trang và tìm kiếm ---
    @GetMapping
    public ResponseEntity<VoucherRespond> listVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String status
    ) {
        Page<Voucher> result = voucherService.searchByKeywordAndStatus(keyword.trim(), status.trim(), PageRequest.of(page, size));
        return ResponseEntity.ok(new VoucherRespond(result));
    }

    // --- 2. Chi tiết một voucher ---
    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucher(@PathVariable String id) {
        return voucherService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- 3. Tạo hoặc cập nhật voucher ---
    @PostMapping("/save")
    public ResponseEntity<?> saveVoucher(@RequestBody Voucher voucher) {
        boolean isNew = (voucher.getId() == null || voucher.getId().trim().isEmpty());

        // Validate khách hàng
        Set<KhachHang> khachHangs = new HashSet<>();
        if (voucher.getKhachHangs() != null) {
            List<String> ids = voucher.getKhachHangs().stream()
                    .map(KhachHang::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            List<KhachHang> found = khachHangService.findAllByIds(ids);
            if (found.size() != ids.size()) {
                return ResponseEntity.badRequest().body("Một hoặc nhiều khách hàng không tồn tại.");
            }
            khachHangs.addAll(found);
        }
        voucher.setKhachHangs(khachHangs);

        // Ngày tạo và sửa
        if (isNew) {
            voucher.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
            voucher.setNgaytao(LocalDateTime.now());
        } else {
            voucherService.findById(voucher.getId()).ifPresent(old -> voucher.setNgaytao(old.getNgaytao()));
        }
        voucher.setNgaysua(LocalDateTime.now());

        try {
            Voucher saved = voucherService.save(voucher);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi khi lưu voucher: " + e.getMessage());
        }
    }

    // --- 4. Đổi trạng thái voucher ---
    @PatchMapping("/toggle-status/{id}")
    public ResponseEntity<?> toggleStatus(@PathVariable String id) {
        Optional<Voucher> opt = voucherService.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Voucher voucher = opt.get();
        voucher.setTrangthai(voucher.getTrangthai() == 1 ? 0 : 1);
        voucher.setNgaysua(LocalDateTime.now());
        voucherService.save(voucher);

        return ResponseEntity.ok().build();
    }

    // --- 5. Lấy tất cả khách hàng (cho modal chọn) ---
    @GetMapping("/khachhang/all")
    public ResponseEntity<List<KhachHang>> getAllKhachHang() {
        return ResponseEntity.ok(khachHangService.findAll());
    }


}
