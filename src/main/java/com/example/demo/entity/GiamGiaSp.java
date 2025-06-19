package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Table(name = "GiamGiaSanPham")
public class GiamGiaSp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "MAGG")
    private String maGiamGia;

    @Column(name = "THOIGIANBATDAU")
    private LocalDateTime thoiGianBatDau;

    @Column(name = "THOIGIANKETTHUC")
    private LocalDateTime thoiGianKetThuc;

    @Column(name = "MUCGIAM")
    private Float mucGiam;

    @Column(name = "MOTA")
    private String moTa;

    @Column(name = "NGAYTAO")
    private LocalDateTime ngayTao;

    @Column(name = "NGAYSUA")
    private LocalDateTime ngaySua;
}
