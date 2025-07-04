package com.example.demo.contrroller;

import com.example.demo.dto.khachhang.VoucherRespond;
import com.example.demo.dto.khachhang.VoucherService;
import com.example.demo.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/vouchers")
@CrossOrigin(origins = "*")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    // ✅ Lấy danh sách voucher có tìm kiếm và phân trang
    @GetMapping
    public ResponseEntity<VoucherRespond> listVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String status) {

        Page<Voucher> pageResult = voucherService.searchByKeywordAndStatus(keyword, status, PageRequest.of(page, size));

        if (pageResult == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new VoucherRespond(pageResult));
    }

    // ✅ Lấy chi tiết một voucher theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucher(@PathVariable String id) {
        Optional<Voucher> voucher = voucherService.findById(id);
        return voucher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Tạo mới hoặc cập nhật voucher
    @PostMapping("/save")
    public ResponseEntity<Voucher> saveVoucher(@RequestBody Voucher voucher) {
        boolean isNew = (voucher.getId() == null || voucher.getId().isEmpty());

        if (isNew) {
            // Tạo mới
            String id = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
            voucher.setId(id);
            voucher.setNgaytao(LocalDateTime.now());
        } else {
            // Cập nhật: nếu voucher cũ có ngày tạo thì giữ lại
            Optional<Voucher> oldVoucher = voucherService.findById(voucher.getId());
            if (oldVoucher.isPresent() && oldVoucher.get().getNgaytao() != null) {
                voucher.setNgaytao(oldVoucher.get().getNgaytao());
            } else {
                voucher.setNgaytao(LocalDateTime.now()); // fallback
            }
        }

        voucher.setNgaysua(LocalDateTime.now());

        Voucher saved = voucherService.save(voucher);
        return ResponseEntity.ok(saved);
    }

    // ✅ Xoá voucher
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable String id) {
        try {
            voucherService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Ghi log nếu cần
            return ResponseEntity.status(500).body("Không thể xoá voucher. Có thể đang được sử dụng.");
        }
    }

}
