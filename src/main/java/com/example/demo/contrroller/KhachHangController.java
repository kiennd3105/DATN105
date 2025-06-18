package com.example.demo.contrroller;

import com.example.demo.dto.khachhang.KhachHangResponse;
import com.example.demo.entity.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("khachhang")
public class KhachHangController {

    @Autowired
    KhachHangRepository khachHangRepository;


    @GetMapping("/table")
    public ResponseEntity<?> findAll(){
        List<KhachHangResponse>list=new ArrayList<>();
        khachHangRepository.findAll().forEach(khachHang -> list.add(khachHang.toResponse()));
        return ResponseEntity.ok(list);
    }
}
