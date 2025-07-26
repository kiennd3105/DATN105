package com.example.demo.dto.khachhang;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherDTO {
    private String id;

    private String mavc;

    private String code;

    private Double dieukien;

    private Double giatrigiam;

    private Integer loaivc;

    private Integer soluong;

    private Integer loaigiamgia;

    private Integer trangthai;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime ngaytao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime ngaysua;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime ngayBatDau;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime ngayHetHan;

    private Set<KhachHangDTO> khachHangs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMavc() {
        return mavc;
    }

    public void setMavc(String mavc) {
        this.mavc = mavc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDieukien() {
        return dieukien;
    }

    public void setDieukien(Double dieukien) {
        this.dieukien = dieukien;
    }

    public Double getGiatrigiam() {
        return giatrigiam;
    }

    public void setGiatrigiam(Double giatrigiam) {
        this.giatrigiam = giatrigiam;
    }

    public Integer getLoaivc() {
        return loaivc;
    }

    public void setLoaivc(Integer loaivc) {
        this.loaivc = loaivc;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public Integer getLoaigiamgia() {
        return loaigiamgia;
    }

    public void setLoaigiamgia(Integer loaigiamgia) {
        this.loaigiamgia = loaigiamgia;
    }

    public Integer getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(Integer trangthai) {
        this.trangthai = trangthai;
    }

    public LocalDateTime getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(LocalDateTime ngaytao) {
        this.ngaytao = ngaytao;
    }

    public LocalDateTime getNgaysua() {
        return ngaysua;
    }

    public void setNgaysua(LocalDateTime ngaysua) {
        this.ngaysua = ngaysua;
    }

    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDateTime getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDateTime ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public Set<KhachHangDTO> getKhachHangs() {
        return khachHangs;
    }

    public void setKhachHangs(Set<KhachHangDTO> khachHangs) {
        this.khachHangs = khachHangs;
    }


}

