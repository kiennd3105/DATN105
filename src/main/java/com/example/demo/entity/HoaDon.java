package com.example.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "HoaDon")
public class HoaDon {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MAHD")
    private String maHoaDon;

    @Column(name = "NGAYTAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAYSUA")
    private LocalDateTime ngaySua;

    @Column(name = "LOAIHOADON")
    private Integer loaiHoaDon;

    @Column(name = "TONGTIEN")
    private Float tongTien;

    @Column(name = "HINHTHUCTHANHTOAN")
    private Integer hinhThucThanhToan;

    @Column(name = "PHIVANCHUYEN")
    private Float phiVanChuyen;

    @Column(name = "TENNGUOINHAN")
    private String tenNguoiNhan;

    @Column(name = "DIACHINGUOINHAN")
    private String diaChiNguoiNhan;

    @Column(name = "SDTNGUOINHAN")
    private String sdtNguoiNhan;

    @Column(name = "TRANGTHAI")
    private Integer trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDKH")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDNV")
    private NhanVien nhanVien;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HoaDonChiTiet> chiTietHoaDons;

}
