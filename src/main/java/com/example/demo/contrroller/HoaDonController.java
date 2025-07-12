package com.example.demo.contrroller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.repository.HoaDonChiTietRepository;
import com.example.demo.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    // Hiển thị danh sách hóa đơn
    @GetMapping("/hien-thi")
    public String hienThi(@RequestParam(name = "keyword", required = false) String keyword,
                          Model model) {

        boolean khongTimThay = false;
        List<HoaDon> daThanhToan;
        List<HoaDon> chuaThanhToan;

        if (keyword != null && !keyword.isEmpty()) {
            daThanhToan = hoaDonRepository.findByMaHoaDonContainingAndTrangThai(keyword, 1);
            chuaThanhToan = hoaDonRepository.findByMaHoaDonContainingAndTrangThai(keyword, 0);

            if (daThanhToan.isEmpty() && chuaThanhToan.isEmpty()) {
                khongTimThay = true;
            }
        } else {
            daThanhToan = hoaDonRepository.findByTrangThai(1);
            chuaThanhToan = hoaDonRepository.findByTrangThai(0);
        }

        model.addAttribute("daThanhToan", daThanhToan);
        model.addAttribute("chuaThanhToan", chuaThanhToan);
        model.addAttribute("keyword", keyword);
        model.addAttribute("khongTimThay", khongTimThay);

        return "hoadon"; // hoadon.html trong /WEB-INF/views/
    }

    // Trả về nội dung chi tiết hóa đơn để hiển thị trong modal (dùng PathVariable)
    @GetMapping("/chi-tiet/{id}")
    public String chiTietModal(@PathVariable("id") String id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElse(null);

        if (hoaDon != null) {
            List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDon_Id(id);
            model.addAttribute("hoaDon", hoaDon);
            model.addAttribute("chiTietList", chiTietList);
        }

        return "modal_chi_tiet"; // modal_chi_tiet.html trong /WEB-INF/views/
    }
    @PostMapping("/huy/{id}")
    public String huyHoaDon(@PathVariable("id") String id) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElse(null);
        if (hoaDon != null) {
            hoaDon.setTrangThai(-1); // -1 là trạng thái bị hủy
            hoaDonRepository.save(hoaDon);
        }
        return "redirect:/hoa-don/hien-thi";
    }

}
