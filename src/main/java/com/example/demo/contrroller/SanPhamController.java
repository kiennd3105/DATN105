package com.example.demo.contrroller; // Sửa lại chính tả

import com.example.demo.entity.SanPham;
import com.example.demo.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @GetMapping("/hien-thi")
    public String hienThi(Model model) {
        List<SanPham> sanPhams = sanPhamRepository.findAll();
        model.addAttribute("sanPhams", sanPhams);
        model.addAttribute("sanPham", new SanPham()); // Thêm đối tượng mới để sử dụng trong form
        return "sanpham"; // Đảm bảo tên file HTML là đúng
    }

    @PostMapping("/luu")
    public String luuSanPham(@ModelAttribute SanPham sanPham) {
        sanPhamRepository.save(sanPham);
        return "redirect:/san-pham/hien-thi"; // Chuyển hướng về danh sách sản phẩm
    }
}
