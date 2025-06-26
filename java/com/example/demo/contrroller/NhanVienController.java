package com.example.demo.contrroller;

import com.example.demo.dto.khachhang.NhanVienRequest;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.Quyen;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.NhanVienRepositoty;

import com.example.demo.repository.QuyenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NhanVienController {

    @Autowired
    private NhanVienRepositoty nhanVienRepositoty;

    @Autowired
    private QuyenRepository quyenRepo;

    public Page<NhanVien> getEmployees(int pageNo, int pageSize, String key, String trangThai) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return nhanVienRepositoty.findEmployees(pageable,
                "%" + trangThai + "%",
                "%" + key + "%");
    }


    public ArrayList<NhanVien> getAll() {
        return (ArrayList<NhanVien>) nhanVienRepositoty.findAll();
    }


    public NhanVien getEmployeeByID(String id) {
        return nhanVienRepositoty.findEmployeeByID(id);
    }


    //    public NhanVien addEmployee(NhanVienRequest req) {
//
//        if (nhanVienRepositoty.findNhanVienByEmail(req.getEmail()) != null) {
//            throw new RuntimeException("Email đã tồn tại");
//        } else {
//            NhanVien employee = new NhanVien();
//
//            employee.setMa(generateCode());
//            employee.setTen(req.getTen());
//            employee.setEmail(req.getEmail());
//            employee.setPassw(req.getPassw());
//            employee.setGioiTinh(req.getGioiTinh());
//            employee.setDiaChi(req.getDiaChi());
//            employee.setTrangThai(true);
//            employee.setNgayTao(req.getNgayTao());
//            employee.setNgaySua(req.getNgaySua());
////            employee.setIdquyen(req.getPhuong());
//
////            employee.setChucVu(chucVuService.findById(req.getChucVu()));
//            if (req.getIdquyen() == 0) {
//                if (quyenRepo.findByMa("0") != null) {
//                    employee.setIdquyen(quyenRepo.findByMa("0"));
//                } else {
//                    Quyen quyen = new Quyen();
//                    quyen.setMa("0");
//                    chucVu.setTen("Nhân viên");
//                    chucVuRepo.save(chucVu);
//                    employee.setChucVu(chucVu);
//                }
//            } else if (req.getChucVu() == 1) {
//                if (chucVuRepo.findByMa("1") != null) {
//                    employee.setChucVu(chucVuRepo.findByMa("1"));
//                } else {
//                    ChucVu chucVu = new ChucVu();
//                    chucVu.setMa("1");
//                    chucVu.setTen("Quản trị viên");
//                    chucVuRepo.save(chucVu);
//                    employee.setChucVu(chucVu);
//                }
//            }
//
//            return nhanVienRepo.save(employee);
//        }
//    }
    public String generateCode() {
        // generate code
        String newestCode = nhanVienRepositoty.generateNewestCode();
        if (newestCode == null || newestCode.equals("")) {
            return "EMPLOYEE_" + 0;
        }
        return "EMPLOYEE_" + (Integer.parseInt(newestCode.substring(9)) + 1);
    }
}
