package com.example.demo.contrroller;

import com.example.demo.dto.khachhang.HoaDonService;
import com.example.demo.dto.khachhang.KhachHangService;
import com.example.demo.dto.khachhang.VoucherCTService;
import com.example.demo.dto.khachhang.VoucherService;
import com.example.demo.entity.VoucherCT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/voucher-ct")
public class VoucherCTController {

    @Autowired private VoucherCTService voucherCTService;
    @Autowired private KhachHangService khachHangService;
    @Autowired private HoaDonService hoaDonService;
    @Autowired private VoucherService voucherService;

    // Hiển thị danh sách + tìm kiếm + phân trang
    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<VoucherCT> pageResult = voucherCTService.getAll(keyword, pageable);

        model.addAttribute("list", pageResult);
        model.addAttribute("keyword", keyword);
        model.addAttribute("mode", "list");
        return "voucherCT";
    }

    // Hiển thị form tạo mới
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("voucherCT", new VoucherCT());
        addDropdownData(model);
        model.addAttribute("mode", "form");
        return "voucherCT";
    }

    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") String id, Model model) {
        VoucherCT voucherCT = voucherCTService.getById(id);
        if (voucherCT == null) {
            return "redirect:/voucher-ct";
        }
        model.addAttribute("voucherCT", voucherCT);
        addDropdownData(model);
        model.addAttribute("mode", "form");
        return "voucherCT";
    }

    // Lưu dữ liệu thêm hoặc cập nhật
    @PostMapping("/save")
    public String save(@ModelAttribute("voucherCT") VoucherCT input) {
        if (input.getId() != null && !input.getId().isEmpty()) {
            VoucherCT existing = voucherCTService.getById(input.getId());
            if (existing != null) {
                existing.setVoucher(input.getVoucher());
                existing.setKhachHang(input.getKhachHang());
                existing.setHoaDon(input.getHoaDon());
                existing.setTrangThai(input.getTrangThai());
                existing.setNgaySua(LocalDateTime.now());
                voucherCTService.save(existing);
            }
        } else {
            input.setNgayTao(LocalDateTime.now());
            voucherCTService.save(input);
        }
        return "redirect:/voucher-ct";
    }

    // Xem chi tiết
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") String id, Model model) {
        VoucherCT voucherCT = voucherCTService.getById(id);
        if (voucherCT == null) {
            return "redirect:/voucher-ct";
        }
        model.addAttribute("voucherCT", voucherCT);
        model.addAttribute("mode", "view");
        return "voucherCT";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        voucherCTService.delete(id);
        return "redirect:/voucher-ct";
    }

    // Thêm dữ liệu dropdown
    private void addDropdownData(Model model) {
        model.addAttribute("vouchers", voucherService.getAll());
        model.addAttribute("khachHangs", khachHangService.getAll());
        model.addAttribute("hoaDons", hoaDonService.getAll());
    }
}
