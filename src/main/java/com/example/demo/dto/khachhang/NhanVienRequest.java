package com.example.demo.dto.khachhang;


import com.example.demo.entity.Quyen;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NhanVienRequest {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MANV")
    private String ma;

    @Column(name = "TENNV")
    private String ten;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASS")
    private String passw;

    @Column(name = "GIOITINH")
    private String gioiTinh;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "DIACHI")
    private String diaChi;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @Column(name = "NGAYTAO")
    private Date ngayTao;

    @Column(name = "NGAYSUA")
    private Date ngaySua;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDQUYEN")
    private Quyen idquyen;

}
