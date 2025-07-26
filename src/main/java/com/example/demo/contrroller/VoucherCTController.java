package com.example.demo.contrroller;

import com.example.demo.dto.khachhang.VoucherCTDTO;
import com.example.demo.dto.khachhang.VoucherCTMapper;
import com.example.demo.dto.khachhang.VoucherCTService;
import com.example.demo.entity.VoucherCT;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/voucherct")
@CrossOrigin(origins = "*")
public class VoucherCTController {

    @Autowired
    private VoucherCTService service;

    //  Lấy tất cả (không phân trang)
    @GetMapping
    public ResponseEntity<List<VoucherCTDTO>> getAllVoucherCT() {
        List<VoucherCTDTO> dtos = service.findAll().stream()
                .map(VoucherCTMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    //  Lấy theo ID
    @GetMapping("/{id}")
    public ResponseEntity<VoucherCTDTO> getById(@PathVariable String id) {
        return service.findById(id)
                .map(VoucherCTMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Tạo mới
    @PostMapping
    public ResponseEntity<VoucherCTDTO> create(@RequestBody VoucherCTDTO dto) {
        VoucherCT entity = VoucherCTMapper.toEntity(dto);
        entity.setNgaytao(LocalDateTime.now());
        entity.setNgaysua(LocalDateTime.now());

        VoucherCT saved = service.save(entity);

        //  Load lại từ DB để đảm bảo có đủ dữ liệu liên kết (voucher, khách hàng, hóa đơn)
        return service.findById(saved.getId())
                .map(VoucherCTMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(500).build());
    }

    //  Sửa
    @PutMapping("/{id}")
    public ResponseEntity<VoucherCTDTO> update(@PathVariable String id, @RequestBody VoucherCTDTO dto) {
        Optional<VoucherCT> existingOpt = service.findById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.notFound().build();

        VoucherCT existing = existingOpt.get();
        VoucherCT updated = VoucherCTMapper.toEntity(dto);
        updated.setId(id);
        updated.setNgaytao(existing.getNgaytao());
        updated.setNgaysua(LocalDateTime.now());

        VoucherCT saved = service.save(updated);

        //  Load lại để lấy đủ thông tin liên kết
        return service.findById(saved.getId())
                .map(VoucherCTMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(500).build());
    }

    //  Xoá
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Xoá thành công");
        } catch (Exception e) {
            return ResponseEntity.status(409).body("Xoá thất bại do liên kết dữ liệu");
        }
    }

    //  Lọc + phân trang
    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> filter(
            @RequestParam(required = false) String idvc,
            @RequestParam(required = false) String idkh,
            @RequestParam(required = false) String idhd,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VoucherCTDTO> dtoPage = service.filterBy(idvc, idkh, idhd, pageable)
                .map(VoucherCTMapper::toDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("content", dtoPage.getContent());
        response.put("currentPage", dtoPage.getNumber());
        response.put("totalItems", dtoPage.getTotalElements());
        response.put("totalPages", dtoPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    //  Export CSV
    @GetMapping("/export/csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=voucherct.csv");

        List<VoucherCT> list = service.findAll();
        PrintWriter writer = response.getWriter();
        writer.println("ID,IDVC,IDKH,IDHD,TrangThai,NgayTao,NgaySua");

        for (VoucherCT v : list) {
            writer.printf("%s,%s,%s,%s,%d,%s,%s\n",
                    v.getId(),
                    v.getVoucher() != null ? v.getVoucher().getId() : "",
                    v.getKhachHang() != null ? v.getKhachHang().getId() : "",
                    v.getHoaDon() != null ? v.getHoaDon().getId() : "",
                    v.getTrangthai() != null ? v.getTrangthai() : 0,
                    v.getNgaytao() != null ? v.getNgaytao() : "",
                    v.getNgaysua() != null ? v.getNgaysua() : "");
        }
        writer.flush();
    }

    //  Đánh dấu lại là chưa dùng
    @PutMapping("/reuse/{id}")
    public ResponseEntity<?> reuse(@PathVariable String id) {
        Optional<VoucherCT> optional = service.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        VoucherCT vc = optional.get();
        vc.setTrangthai(0);
        vc.setNgaysua(LocalDateTime.now());
        service.save(vc);

        return ResponseEntity.ok("Đã cập nhật trạng thái về chưa dùng");
    }

    //  Danh sách các ID voucher đã dùng
    @GetMapping("/used-voucher-ids")
    public ResponseEntity<List<String>> usedVoucherIds() {
        List<String> usedIds = service.findUsedVoucherIds();
        return ResponseEntity.ok(usedIds);
    }

    //  Kiểm tra API
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("VoucherCT API online");
    }

    //  All (dự phòng)
    @GetMapping("/all")
    public ResponseEntity<List<VoucherCTDTO>> getAll() {
        List<VoucherCTDTO> dtos = service.findAll().stream()
                .map(VoucherCTMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
