package com.example.demo.contrroller;

import com.example.demo.entity.Quyen;
import com.example.demo.repository.QuyenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuyenController {
    @Autowired
    private QuyenRepository quyenRepo;

    public Quyen findById(String id) {
        for (var temp : quyenRepo.findAll()) {
            if (temp.getId() == id) {
                return temp;
            }
        }
        return null;
    }

}
