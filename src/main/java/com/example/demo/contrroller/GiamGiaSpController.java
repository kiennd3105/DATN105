package com.example.demo.contrroller;

import com.example.demo.repository.GiamGiaSpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class GiamGiaSpController {

    @Autowired
    GiamGiaSpRepository giamGiaSpRepository;
}
