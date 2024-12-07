package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ThongKeService {
    @Autowired
    private ThongKeRepository billRepository;

    public Map<String, Object> getCompletedOrderStats() {
        return billRepository.getCompletedOrdersAndTotalMoney();
    }

    public Map<String, Object> getCompletedOrderStatsToday() {
        return billRepository.getCompletedOrdersAndTotalMoneyToday();
    }
}
