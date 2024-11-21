package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhachHangRepository extends JpaRepository<User, Integer> {


    @Query("SELECT u FROM User u JOIN u.account a LEFT JOIN FETCH u.addresses  WHERE  a.role = 'Customer' AND u.deleted = false")
    Page<User> findAllCustomers(Pageable pageable);

    @Query("SELECT u FROM User u " +
            "JOIN u.account a " +
            "LEFT JOIN FETCH u.addresses " +
            "WHERE a.role = 'Customer' AND u.deleted = false " +
            "ORDER BY u.createdAt DESC")
    List<User> findAllKhachHang();


    @Query("SELECT u FROM User u JOIN u.account a LEFT JOIN FETCH u.addresses WHERE a.role = 'Customer' AND " +
            "(u.fullName LIKE %:keyword% OR u.phoneNumber LIKE %:keyword% OR u.email LIKE %:keyword%)")
    Page<User> searchCustomers(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT u "
            + "FROM User u "
            + "JOIN u.account a "
            + "WHERE u.id = :userId AND a.role = 'Customer'")
    User getKhachHangById(@Param("userId") Integer userId);


}
