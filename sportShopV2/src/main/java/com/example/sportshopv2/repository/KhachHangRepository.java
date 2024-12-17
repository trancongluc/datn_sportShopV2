package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<User, Integer> {


    @Query("SELECT u FROM User u JOIN u.account a LEFT JOIN FETCH u.addresses  WHERE  a.role = 'Employee' AND u.deleted = false")
    Page<User> findAllCustomers(Pageable pageable);

    @Query("SELECT u FROM User u " +
            "JOIN u.account a " +
            "LEFT JOIN FETCH u.addresses " +
            "WHERE a.role = 'Employee' AND u.deleted = false " +
            "ORDER BY u.createdAt DESC")
    List<User> findAllKhachHang();
    @Query("SELECT kh FROM User kh WHERE kh.fullName LIKE %:keyword% OR kh.phoneNumber LIKE %:keyword% ORDER BY kh.createdAt DESC")
    List<User> findByTenHoacSoDienThoai(@Param("keyword") String keyword);

    @Query("SELECT u FROM User u JOIN u.account a LEFT JOIN FETCH u.addresses WHERE a.role = 'Employee' AND " +
            "(u.fullName LIKE %:keyword% OR u.phoneNumber LIKE %:keyword% OR u.email LIKE %:keyword%)")
    Page<User> searchCustomers(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT u "
            + "FROM User u "
            + "JOIN u.account a "
            + "WHERE u.id = :userId AND a.role = 'Employee'")
    User getKhachHangById(@Param("userId") Integer userId);

    // Tìm kiếm người dùng theo số điện thoại
    Optional<User> findByPhoneNumber(String phoneNumber);

    // Tìm kiếm người dùng theo email
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);



}
