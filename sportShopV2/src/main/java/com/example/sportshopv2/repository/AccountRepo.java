package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username); // Tìm Account theo username
}
