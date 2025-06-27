package com.example.totnghiep.Controller;

import com.example.totnghiep.Entity.VoucherCT;
import com.example.totnghiep.Service.*;
import com.example.totnghiep.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/voucher-ct")
public class



VoucherCTController {

    @Autowired private VoucherCTService voucherCTService;
    @Autowired private KhachHangService khachHangService;
    @Autowired private HoaDonService hoaDonService;
    @Autowired private VoucherService voucherService;

    @GetMapping
    public String list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<VoucherCT> pageResult = voucherCTService.getAll(keyword, pageable);

        model.addAttribute("list", pageResult);
        model.addAttribute("keyword", keyword);
        return "voucherct/list";
    }

    @GetMapping("/create")
    public String showForm(Model model) {
        model.addAttribute("voucherCT", new VoucherCT());
        addDropdownData(model);
        return "voucherct/form";
    }





    @PostMapping("/save")
    public String save(@ModelAttribute("voucherCT") VoucherCT input) {
        if (input.getId() != null && !input.getId().isEmpty()) {
            // Cập nhật
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
            // Thêm mới
            input.setNgayTao(LocalDateTime.now());
            voucherCTService.save(input);
        }

        return "redirect:/voucher-ct";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        VoucherCT voucherCT = voucherCTService.getById(id);
        if (voucherCT == null) {
            return "redirect:/voucher-ct"; // không tìm thấy -> về lại danh sách
        }

        model.addAttribute("voucherCT", voucherCT);
        addDropdownData(model);
        return "voucherct/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        voucherCTService.delete(id);
        return "redirect:/voucher-ct";
    }
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") String id, Model model) {
        VoucherCT voucherCT = voucherCTService.getById(id);
        if (voucherCT == null) {
            return "redirect:/voucher-ct";
        }

        model.addAttribute("voucherCT", voucherCT);
        return "voucherct/view"; // file Thymeleaf để hiển thị chi tiết
    }

    private void addDropdownData(Model model) {
        model.addAttribute("vouchers", voucherService.getAll());
        model.addAttribute("khachHangs", khachHangService.getAll());
        model.addAttribute("hoaDons", hoaDonService.getAll());
    }
}
