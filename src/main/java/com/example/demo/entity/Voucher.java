package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@Table(name = "Voucher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @Column(name = "ID", length = 8, nullable = false, updatable = false)
    private String id;

    @NotBlank(message = "Mã voucher không được để trống")
    @Column(name = "MAVC", nullable = false)
    private String mavc;

    @NotBlank(message = "Code không được để trống")
    @Column(name = "CODE", nullable = false)
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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "NGAYTAO")
    private LocalDateTime ngaytao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "NGAYSUA")
    private LocalDateTime ngaysua;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "NGAYBATDAU")
    private LocalDateTime ngayBatDau;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "NGAYHETHAN")
    private LocalDateTime ngayHetHan;



    // Cho phép chọn nhiều khách hàng
    @ManyToMany
    @JoinTable(
            name = "Voucher_KhachHang",
            joinColumns = @JoinColumn(name = "voucher_id"),
            inverseJoinColumns = @JoinColumn(name = "khachhang_id")
    )
    @JsonIgnore
    private Set<KhachHang> khachHangs = new HashSet<>();


    @PrePersist
    public void generateId() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        }
    }
}
