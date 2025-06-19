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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {
    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @GetMapping("/hien-thi")
    public String hienThi(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<HoaDon> list;
        boolean khongTimThay = false;

        if (keyword != null && !keyword.isEmpty()) {
            list = hoaDonRepository.findByMaHoaDonContaining(keyword);
            if (list.isEmpty()) {
                khongTimThay = true;
            }
        } else {
            list = hoaDonRepository.findAll();
        }

        model.addAttribute("dsHoaDon", list);
        model.addAttribute("keyword", keyword);
        model.addAttribute("khongTimThay", khongTimThay);
        return "hoadon";
    }

    @GetMapping("/detail/{id}")
    public String chiTietHoaDon(@PathVariable("id") String id, Model model) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElse(null);

        if (hoaDon == null) {
            return "redirect:/hoa-don/hien-thi"; // không tìm thấy thì quay về danh sách
        }

        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDon_Id(id);

        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("chiTietList", chiTietList);

        return "detail";
    }
}
