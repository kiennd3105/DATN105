package com.example.demo.contrroller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.repository.HoaDonChiTietRepository;
import com.example.demo.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/hien-thi")
    public String hienThi(@RequestParam(name = "keyword", required = false) String keyword,
                          @RequestParam(name = "id", required = false) String id,
                          Model model) {

        boolean khongTimThay = false;
        List<HoaDon> daThanhToan;
        List<HoaDon> chuaThanhToan;

        // Tìm kiếm theo mã hóa đơn nếu có keyword
        if (keyword != null && !keyword.isEmpty()) {
            daThanhToan = hoaDonRepository.findByMaHoaDonContainingAndTrangThai(keyword, 1);
            chuaThanhToan = hoaDonRepository.findByMaHoaDonContainingAndTrangThai(keyword, 0);

            if (daThanhToan.isEmpty() && chuaThanhToan.isEmpty()) {
                khongTimThay = true;
            }
        } else {
            // Nếu không có keyword thì lấy toàn bộ danh sách
            daThanhToan = hoaDonRepository.findByTrangThai(1);
            chuaThanhToan = hoaDonRepository.findByTrangThai(0);
        }

        // Truyền dữ liệu sang view
        model.addAttribute("daThanhToan", daThanhToan);
        model.addAttribute("chuaThanhToan", chuaThanhToan);
        model.addAttribute("keyword", keyword);
        model.addAttribute("khongTimThay", khongTimThay);

        // Hiển thị chi tiết nếu có ID
        if (id != null && !id.isEmpty()) {
            HoaDon hoaDon = hoaDonRepository.findById(id).orElse(null);
            if (hoaDon != null) {
                List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDon_Id(id);
                model.addAttribute("hoaDon", hoaDon);
                model.addAttribute("chiTietList", chiTietList);
            }
        }

        return "hoadon";
    }
}
