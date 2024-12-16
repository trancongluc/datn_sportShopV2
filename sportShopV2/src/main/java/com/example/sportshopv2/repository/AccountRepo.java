package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface AccountRepo extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    // Tìm Account theo username
    @Query(value = "SELECT a.* FROM Account a WHERE id_user = :id", nativeQuery = true)
    Account getAccByIdUser(@Param("id") Integer id);
}
