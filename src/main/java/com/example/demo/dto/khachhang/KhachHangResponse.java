package com.example.demo.dto.khachhang;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KhachHangResponse {

    private String id;
    private String ma;
    private String ten;
    private String email;
    private String passw;
    private String gioiTinh;
    private String sdt;
    private String diaChi;
    private Integer trangThai;


}
