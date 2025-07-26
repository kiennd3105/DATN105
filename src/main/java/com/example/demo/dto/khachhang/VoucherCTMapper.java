package com.example.demo.dto.khachhang;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Voucher;
import com.example.demo.entity.VoucherCT;

public class VoucherCTMapper {

    public static VoucherCTDTO toDTO(VoucherCT entity) {
        if (entity == null) return null;
        VoucherCTDTO dto = new VoucherCTDTO();
        dto.setId(entity.getId());

        if (entity.getVoucher() != null) {
            dto.setIdvc(entity.getVoucher().getId());
        }

        if (entity.getKhachHang() != null) {
            dto.setIdkh(entity.getKhachHang().getId());
            dto.setTenKhachHang(entity.getKhachHang().getTen());
        }

        if (entity.getHoaDon() != null) {
            dto.setIdhd(entity.getHoaDon().getId());
            dto.setMaHoaDon(entity.getHoaDon().getMaHoaDon());
        }

        dto.setTrangthai(entity.getTrangthai());
        dto.setNgaytao(entity.getNgaytao() != null ? entity.getNgaytao().toString() : null);
        dto.setNgaysua(entity.getNgaysua() != null ? entity.getNgaysua().toString() : null);
        return dto;
    }

    //  Map ngược từ DTO sang Entity
    public static VoucherCT toEntity(VoucherCTDTO dto) {
        if (dto == null) return null;

        VoucherCT entity = new VoucherCT();
        entity.setId(dto.getId());
        entity.setTrangthai(dto.getTrangthai());

        if (dto.getIdvc() != null) {
            var v = new Voucher();
            v.setId(dto.getIdvc());
            entity.setVoucher(v);
        }

        if (dto.getIdkh() != null) {
            var kh = new KhachHang();
            kh.setId(dto.getIdkh());
            entity.setKhachHang(kh);
        }

        if (dto.getIdhd() != null) {
            var hd = new HoaDon();
            hd.setId(dto.getIdhd());
            entity.setHoaDon(hd);
        }

        return entity;
    }
}
