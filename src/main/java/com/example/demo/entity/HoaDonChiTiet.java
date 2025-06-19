package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "ChiTietHoaDon")
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 36)
    private String id;

    @Column(name = "MACTHD", length = 20, unique = true, nullable = false)
    private String maCTHD;

    @ManyToOne
    @JoinColumn(name = "IDHD")
    private HoaDon hoaDon;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "IDCTSP")
//    private ChiTietSanPham chiTietSanPham;

    @Column(name = "SOLUONG")
    private Integer soLuong;

    @Column(name = "DONGIA", precision = 18, scale = 2)
    private BigDecimal donGia;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @Column(name = "NGAYTAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAYSUA")
    private LocalDateTime ngaySua;
}
