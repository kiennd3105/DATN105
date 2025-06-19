package com.example.demo.contrroller;

import com.example.demo.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class SizeController {

    @Autowired
    SizeRepository sizeRepository;
}
