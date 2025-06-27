package com.example.totnghiep.Controller;

import com.example.totnghiep.Entity.Voucher;
import com.example.totnghiep.Service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    // Danh sách có tìm kiếm & phân trang
    @GetMapping
    public String listVouchers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String status,
            Model model) {

        Page<Voucher> pageResult = voucherService.searchByKeywordAndStatus(keyword, status, PageRequest.of(page, size));

        model.addAttribute("vouchers", pageResult);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        return "voucher/vouchers"; // -> templates/voucher/vouchers.html
    }

    // Hiển thị form tạo mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("voucher", new Voucher());
        return "voucher/TaoMoi"; // -> templates/voucher/add.html
    }

    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Optional<Voucher> optionalVoucher = voucherService.findById(id);
        if (optionalVoucher.isPresent()) {
            model.addAttribute("voucher", optionalVoucher.get());
            return "voucher/edit"; // -> templates/voucher/edit.html
        }
        return "redirect:/vouchers";
    }

    // Xem chi tiết
    @GetMapping("/view/{id}")
    public String viewVoucher(@PathVariable("id") String id, Model model) {
        Optional<Voucher> voucher = voucherService.findById(id);
        if (voucher.isEmpty()) return "redirect:/vouchers";
        model.addAttribute("voucher", voucher.get());
        return "voucher/add"; // dùng lại nếu bạn thiết kế chi tiết ở đây
    }

    // Lưu (tạo mới hoặc cập nhật)
    @PostMapping("/save")
    public String saveVoucher(
            @Valid @ModelAttribute("voucher") Voucher voucher,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            // Nếu lỗi và có id → đang sửa
            if (voucher.getId() != null && !voucher.getId().isEmpty()) {
                return "voucher/edit";
            } else {
                return "voucher/TaoMoi";
            }
        }

        // Nếu tạo mới
        if (voucher.getId() == null || voucher.getId().isEmpty()) {
            String id = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
            voucher.setId(id);
            voucher.setNgaytao(LocalDateTime.now());
        }

        voucher.setNgaysua(LocalDateTime.now());
        voucherService.save(voucher);
        return "redirect:/vouchers";
    }

    // Xoá
    @GetMapping("/delete/{id}")
    public String deleteVoucher(@PathVariable String id) {
        voucherService.delete(id);
        return "redirect:/vouchers";
    }
}
