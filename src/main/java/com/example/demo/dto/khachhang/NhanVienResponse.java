package com.example.demo.dto.khachhang;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NhanVienResponse {

    private String id;
    private String ma;
    private String ten;
    private String email;
    private String passw;
    private String gioiTinh;
    private String sdt;
    private String diaChi;
    private boolean trangThai;
    private String ngayTao;
    private String ngaySua;
    private String idquyen;
}
