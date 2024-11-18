package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhanVienRepo extends JpaRepository<User, Integer> {

    @Query(value = """
        SELECT u.*
        FROM [User] u
        JOIN Account a ON u.id = a.id_user
        LEFT JOIN Address addr ON u.id = addr.id_User
        WHERE a.role = 'Staff'
        """, nativeQuery = true)
    Page<User> findAllEmp(Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.account a LEFT JOIN FETCH u.addresses WHERE a.role = 'Staff' AND " +
            "(u.fullName LIKE %:keyword% OR u.phoneNumber LIKE %:keyword% OR u.email LIKE %:keyword%)")
    Page<User> searchEmp(@Param("keyword") String keyword, Pageable pageable);

}
