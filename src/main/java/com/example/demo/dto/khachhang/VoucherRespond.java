package com.example.demo.dto.khachhang;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;

import java.util.List;

public class VoucherRespond {
    private List<Voucher> content;
    private int totalPages;
    private int number;

    public VoucherRespond(Page<Voucher> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.number = page.getNumber();
    }

    public List<Voucher> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getNumber() {
        return number;
    }
}
