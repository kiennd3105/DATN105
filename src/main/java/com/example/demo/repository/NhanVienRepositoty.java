package com.example.demo.repository;

import com.example.demo.entity.NhanVien;
//import org.hibernate.query.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//
//import java.awt.print.Pageable;
@Repository
public interface NhanVienRepositoty extends JpaRepository<NhanVien, String> {
    @Query(value = """
                SELECT * FROM NhanVien v
                WHERE TRANGTHAI LIKE :trangThai
                AND (MANV LIKE :key OR TEN LIKE :key)
                ORDER BY NGAYTAO DESC
            """, nativeQuery = true)
    Page<NhanVien> findEmployees(Pageable pageable, @Param("trangThai") String trangThai, @Param("key") String key);

    @Query(value = """
                SELECT * FROM NhanVien v
                WHERE v.ID = :id
            """, nativeQuery = true)
    NhanVien findEmployeeByID(@Param("id") String id); // Vì ID là VARCHAR(36), nên dùng String thay vì Long

    @Query(value = """
                SELECT TOP 1 MANV FROM NhanVien ORDER BY NGAYTAO DESC
            """, nativeQuery = true)
    String generateNewestCode();

    @Query(value = """
                SELECT * FROM NhanVien
                WHERE EMAIL = :email
            """, nativeQuery = true)
    NhanVien findNhanVienByEmail(@Param("email") String email);

    @Query(value = """
                SELECT * FROM NhanVien
                WHERE EMAIL = :email AND PASSW = :matKhau
            """, nativeQuery = true)
    NhanVien findNhanVienByEmailAndPass(@Param("email") String email, @Param("matKhau") String matKhau);

}
