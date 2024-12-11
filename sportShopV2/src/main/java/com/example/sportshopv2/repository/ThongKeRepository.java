package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ThongKeRepository extends JpaRepository<HoaDon, Integer> {

    @Query(value = "SELECT COALESCE(COUNT(*), 0) AS totalCompletedOrders, COALESCE(SUM(total_money), 0) AS totalCompletedMoney " +
            "FROM Bill " +
            "WHERE status = 'Hoàn thành'", nativeQuery = true)
    Map<String, Object> getCompletedOrdersAndTotalMoney();

    @Query(value = "SELECT " +
            "    COALESCE(COUNT(*), 0) AS total_completed_orders_today, " +
            "   COALESCE(SUM(total_money), 0) AS total_completed_money_today " +
            "FROM " +
            "    Bill " +
            "WHERE " +
            "    status = 'Hoàn thành' " +
            "    AND CONVERT(DATE, create_at) = CONVERT(DATE, GETDATE())", nativeQuery = true)
    Map<String, Object> getCompletedOrdersAndTotalMoneyToday();
}
