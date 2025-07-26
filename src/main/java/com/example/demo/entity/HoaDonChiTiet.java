package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "HDCT")
public class HoaDonChiTiet {

    @Id
    @Column(name = "ID", length = 8)
    private String id;

    @ManyToOne
    @JoinColumn(name = "IDHD")
    private HoaDon hoaDon;

    // Nếu muốn dùng sau thì mở lại
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "IDCTSP")
    // private ChiTietSanPham chiTietSanPham;

    @Column(name = "SOLUONG")
    private Integer soLuong;

    @Column(name = "DONGIA")
    private Float donGia;

    @Column(name = "TONGTIEN")
    private Float tongTien;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @Column(name = "GHICHU")
    private String ghiChu;

    @Column(name = "NGAYTAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAYSUA")
    private LocalDateTime ngaySua;
}
