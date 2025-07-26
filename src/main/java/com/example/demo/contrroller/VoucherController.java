package com.example.demo.contrroller;

import com.example.demo.dto.khachhang.*;
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

    // --- 1. Danh sách voucher có phân trang và lọc ---
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

    // --- 2. Lấy chi tiết một voucher theo ID ---
    @GetMapping("/{id}")
    public ResponseEntity<VoucherDTO> getVoucher(@PathVariable String id) {
        return voucherService.findByIdWithKhachHangs(id)
                .map(voucher -> ResponseEntity.ok(voucherService.toDTO(voucher)))
                .orElse(ResponseEntity.notFound().build());
    }

    // --- 3. Tạo hoặc cập nhật voucher ---
    @PostMapping("/save")
    public ResponseEntity<?> saveVoucher(@RequestBody VoucherDTO voucherDTO) {
        boolean isNew = (voucherDTO.getId() == null || voucherDTO.getId().trim().isEmpty());

        // Validate khách hàng
        Set<String> khachHangIds = voucherDTO.getKhachHangs().stream()
                .map(KhachHangDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<KhachHang> found = khachHangService.findAllByIds(new ArrayList<>(khachHangIds));
        if (found.size() != khachHangIds.size()) {
            return ResponseEntity.badRequest().body("Một hoặc nhiều khách hàng không tồn tại.");
        }

        Voucher voucher = voucherService.toEntity(voucherDTO);
        voucher.setKhachHangs(new HashSet<>(found));

        if (isNew) {
            voucher.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
            voucher.setNgaytao(LocalDateTime.now());
        } else {
            voucherService.findById(voucher.getId()).ifPresent(old -> voucher.setNgaytao(old.getNgaytao()));
        }

        voucher.setNgaysua(LocalDateTime.now());

        try {
            Voucher saved = voucherService.save(voucher);
            return ResponseEntity.ok(voucherService.toDTO(saved));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi khi lưu voucher: " + e.getMessage());
        }
    }

    // --- 4. Đổi trạng thái voucher ---
    @PatchMapping("/toggle-status/{id}")
    public ResponseEntity<?> toggleStatus(@PathVariable String id) {
        Optional<Voucher> optional = voucherService.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Voucher voucher = optional.get();
        voucher.setTrangthai(voucher.getTrangthai() == 1 ? 0 : 1);
        voucher.setNgaysua(LocalDateTime.now());

        Voucher saved = voucherService.save(voucher);
        return ResponseEntity.ok(voucherService.toDTO(saved));
    }

    // --- 5. Lấy danh sách khách hàng (cho modal chọn) ---
    @GetMapping("/khachhang/all")
    public ResponseEntity<List<KhachHang>> getAllKhachHang() {
        return ResponseEntity.ok(khachHangService.findAll());
    }
}
