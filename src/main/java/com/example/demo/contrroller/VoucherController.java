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
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    // Hiển thị danh sách voucher có tìm kiếm và phân trang
    @GetMapping
    public String viewVouchers(
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
        model.addAttribute("voucher", new Voucher());
        return "vouchers";
    }

    // Lưu hoặc cập nhật voucher
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("voucher", new Voucher());
        return "TaoMoi";
    }



    //  Lưu dữ liệu tạo mới hoặc cập nhật
    @PostMapping("/save")
    public String saveVoucher(
            @Valid @ModelAttribute("voucher") Voucher voucher,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("voucher", voucher);
            return "vouchers";
        }

        // Nếu ID null thì là tạo mới
        if (voucher.getId() == null || voucher.getId().isEmpty()) {
            // Sinh ID 8 ký tự, viết hoa
            String id = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
            voucher.setId(id);
            voucher.setNgaytao(LocalDateTime.now());
        }

        voucher.setNgaysua(LocalDateTime.now());
        voucherService.save(voucher);

        return "redirect:/vouchers";
    }

    @GetMapping("/view/{id}")
    public String viewVoucher(@PathVariable("id") String id, Model model) {
        Optional<Voucher> voucher = voucherService.findById(id);
        if (voucher.isEmpty()) {
            return "redirect:/vouchers";
        }
        model.addAttribute("voucher", voucher.get());
        return "add"; // ← CHỖ NÀY
    }





    // Form sửa voucher
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Optional<Voucher> optionalVoucher = voucherService.findById(id);
        if (optionalVoucher.isPresent()) {
            model.addAttribute("voucher", optionalVoucher.get());
            return "edit"; // → tên file .html
        } else {
            return "redirect:/vouchers";
        }
    }

    // Xoá voucher
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        voucherService.delete(id);
        return "redirect:/vouchers";
    }
}
