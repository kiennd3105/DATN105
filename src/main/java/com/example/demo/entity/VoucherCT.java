package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "VoucherCT")
@Data
public class VoucherCT {

    @Id
    @Column(name = "ID", length = 8)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDVC")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Voucher voucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDKH")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDHD")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private HoaDon hoaDon;

    @Column(name = "TRANGTHAI")
    private Integer trangthai;

    @Column(name = "NGAYTAO", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngaytao;

    @Column(name = "NGAYSUA")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ngaysua;

    @PrePersist
    public void onCreate() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        }
        this.ngaytao = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.ngaysua = LocalDateTime.now();
    }
}
