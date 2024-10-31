package com.example.sportshopv2.Repository;

import com.example.sportshopv2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u JOIN u.account a LEFT JOIN FETCH u.addresses  WHERE a.role = 'Customer'")
    Page<User> findAllCustomers(Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.account a LEFT JOIN FETCH u.addresses WHERE a.role = 'Customer' AND " +
            "(u.fullName LIKE %:keyword% OR u.phoneNumber LIKE %:keyword% OR u.email LIKE %:keyword%)")
    Page<User> searchCustomers(@Param("keyword") String keyword, Pageable pageable);
}
