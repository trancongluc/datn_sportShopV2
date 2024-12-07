package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.HoaDonRepo;
import com.example.sportshopv2.repository.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ThongKeService {
    @Autowired
    private ThongKeRepository billRepository;
    @Autowired
    private HoaDonRepo hoaDonRepo;
    public Map<String, Object> getCompletedOrderStats() {
        return billRepository.getCompletedOrdersAndTotalMoney();
    }

    public Map<String, Object> getCompletedOrderStatsToday() {
        return billRepository.getCompletedOrdersAndTotalMoneyToday();
    }
    public List<Integer> getMonthlyBillCounts() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        List<Integer> monthlyCounts = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Integer count = hoaDonRepo.countBillsByMonth(i,currentYear);
            monthlyCounts.add(count);
            System.out.println("Tháng " + i + " có " + count + " hóa đơn.");
        }
        return monthlyCounts;
    }

    public List<Integer> getMonthlyProductCounts() {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        List<Integer> monthlyCounts = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Integer countSp = hoaDonRepo.countProductsByMonth(i,currentYear);
            monthlyCounts.add(countSp);
            System.out.println("Tháng " + i + " có " + countSp + " hóa đơn.");
        }
        return monthlyCounts;
    }
    public List<Integer> getLast7DaysBillCounts() {
        List<Integer> bills = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime date = LocalDateTime.now().minusDays(i);
            bills.add(hoaDonRepo.countBillsByDate(date));
        }
        return bills;
    }

    public List<Integer> getLast7DaysProductCounts() {
        List<Integer> products = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime date = LocalDateTime.now().minusDays(i);
            products.add(hoaDonRepo.countProductsByDate(date));
        }
        return products;
    }


}
