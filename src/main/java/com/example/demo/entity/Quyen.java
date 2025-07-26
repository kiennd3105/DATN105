package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Quyen")
public class Quyen {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "MAQUYEN")
    private String ma;

    @Column(name = "TENQUYEN")
    private String ten;

    @Column(name = "TRANGTHAI")
    private Boolean trangThai;

    @Column(name = "NGAYTAO")
    private Date ngayTao;

    @Column(name = "NGAYSUA")
    private Date ngaySua;

}
