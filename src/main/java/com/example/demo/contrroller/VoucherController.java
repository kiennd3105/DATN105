package com.example.demo.contrroller;

import com.example.demo.dto.khachhang.VoucherService;
import com.example.demo.entity.Voucher;
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

    // Trang danh sách voucher + tìm kiếm + phân trang
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

        // Không gán voucher hoặc mode nếu chỉ muốn hiển thị danh sách
        return "vouchers";
    }
    // Xem chi tiết voucher
    @GetMapping("/view/{id}")
    public String viewVoucher(@PathVariable("id") String id,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              @RequestParam(defaultValue = "") String keyword,
                              @RequestParam(defaultValue = "") String status,
                              Model model) {

        Optional<Voucher> optionalVoucher = voucherService.findById(id);
        if (optionalVoucher.isPresent()) {
            Page<Voucher> pageResult = voucherService.searchByKeywordAndStatus(keyword, status, PageRequest.of(page, size));

            model.addAttribute("vouchers", pageResult);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pageResult.getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("status", status);

            model.addAttribute("voucher", optionalVoucher.get());
            model.addAttribute("mode", "view");
            return "vouchers";
        }

        return "redirect:/vouchers";
    }

    // Hiển thị form tạo mới
    @GetMapping("/create")
    public String createVoucherForm(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size,
                                    @RequestParam(defaultValue = "") String keyword,
                                    @RequestParam(defaultValue = "") String status) {

        Page<Voucher> pageResult = voucherService.searchByKeywordAndStatus(keyword, status, PageRequest.of(page, size));

        model.addAttribute("vouchers", pageResult);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);

        model.addAttribute("voucher", new Voucher());
        model.addAttribute("mode", "create");
        return "vouchers";
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String editVoucherForm(@PathVariable("id") String id,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  @RequestParam(defaultValue = "") String keyword,
                                  @RequestParam(defaultValue = "") String status,
                                  Model model) {

        Optional<Voucher> optionalVoucher = voucherService.findById(id);
        if (optionalVoucher.isPresent()) {

            Page<Voucher> pageResult = voucherService.searchByKeywordAndStatus(keyword, status, PageRequest.of(page, size));

            model.addAttribute("vouchers", pageResult);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pageResult.getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("status", status);

            model.addAttribute("voucher", optionalVoucher.get());
            model.addAttribute("mode", "edit");
            return "vouchers";
        }
        return "redirect:/vouchers";
    }

    // Lưu voucher (tạo mới hoặc cập nhật)
    @PostMapping("/save")
    public String saveVoucher(
            @Valid @ModelAttribute("voucher") Voucher voucher,
            BindingResult result,
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String status) {

        if (result.hasErrors()) {
            Page<Voucher> pageResult = voucherService.searchByKeywordAndStatus(keyword, status, PageRequest.of(page, size));

            model.addAttribute("vouchers", pageResult);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", pageResult.getTotalPages());
            model.addAttribute("keyword", keyword);
            model.addAttribute("status", status);

            model.addAttribute("mode", (voucher.getId() == null || voucher.getId().isEmpty()) ? "create" : "edit");
            return "vouchers";
        }

        if (voucher.getId() == null || voucher.getId().isEmpty()) {
            String id = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
            voucher.setId(id);
            voucher.setNgaytao(LocalDateTime.now());
        }

        voucher.setNgaysua(LocalDateTime.now());
        voucherService.save(voucher);
        return "redirect:/vouchers";
    }

    // Xoá voucher
    @GetMapping("/delete/{id}")
    public String deleteVoucher(@PathVariable String id) {
        voucherService.delete(id);
        return "redirect:/vouchers";
    }
}
