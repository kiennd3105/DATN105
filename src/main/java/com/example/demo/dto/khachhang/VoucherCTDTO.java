package com.example.demo.dto.khachhang;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherCTDTO {
    private String id;
    private String idvc;
    private String idkh;
    private String idhd;

    private String tenKhachHang;
    private String maHoaDon;

    private Integer trangthai;
    private String ngaytao;
    private String ngaysua;
}
