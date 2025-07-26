package com.example.demo.dto.khachhang;

import com.example.demo.entity.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public List<KhachHang> findAll() {
        return khachHangRepository.findAll();
    }
    @Override
    public List<KhachHang> getAll() {
        return khachHangRepository.findAll();
    }
    @Override
    public Optional<KhachHang> findById(String id) {
        return khachHangRepository.findById(id);
    }
    @Override
    public List<KhachHang> findAllByIds(List<String> ids) {
        return khachHangRepository.findAllByIdIn((ids));
    }
}

