package com.example.totnghiep.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Voucher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

        @Id
        @Column(name = "ID", length = 8, updatable = false, nullable = false)
        private String id;


        @NotBlank(message = "Mã voucher không được để trống")
        @Column(name = "MAVC")
        private String mavc;

        @NotBlank(message = "Code không được để trống")
        @Column(name = "CODE")
        private String code;

        @Min(value = 0, message = "Điều kiện tối thiểu là 0")
        @Column(name = "DIEUKIEN")
        private Double dieukien;

        @Min(value = 1, message = "Giá trị giảm phải lớn hơn 0")
        @Column(name = "GIATRIGIAM")
        private Double giatrigiam;

        @Column(name = "LOAIVC")
        private Integer loaivc;

        @Column(name = "SOLUONG")
        private Integer soluong;

        @Column(name = "LOAIGIAMGIA")
        private Integer loaigiamgia;

        @Column(name = "TRANGTHAI")
        private Integer trangthai;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "NGAYTAO")
    private LocalDateTime ngaytao;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @Column(name = "NGAYSUA")
        private LocalDateTime ngaysua;

        @Column(name = "NGAYKETTHUC")
        private LocalDate ngayHetHan;

    @Column(name = "NGAYBATDAU")
    private LocalDate ngayBatDau;


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

    public LocalDate getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(LocalDate ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}


