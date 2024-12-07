package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.HoaDonRepo;
import com.example.sportshopv2.repository.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<Integer> getLast7DaysProductCounts() {
        LocalDate now = LocalDate.now();
        List<Integer> dailyCounts = new ArrayList<>();

        // Lặp qua 7 ngày (tính từ hôm nay trở về trước)
        for (int i = 0; i < 7; i++) {
            LocalDate date = now.minusDays(i);
            Integer countSp = hoaDonRepo.countProductsByDay(date); // Sử dụng query theo từng ngày
            dailyCounts.add(countSp != null ? countSp : 0); // Nếu kết quả null, set về 0
            System.out.println("Ngày " + date + " có " + countSp + " sản phẩm.");
        }

        return dailyCounts;
    }
    public List<Integer> getLast7DaysBillCounts() {
        LocalDate now = LocalDate.now();
        List<Integer> dailyCounts = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate startDate = now.minusDays(i);       // Ngày cần thống kê
            LocalDate endDate = startDate;               // Chỉ thống kê trong 1 ngày
            Integer countBills = hoaDonRepo.countBillsLast7Days(startDate, endDate);
            dailyCounts.add(countBills != null ? countBills : 0); // Nếu null, set về 0
            System.out.println("Ngày " + startDate + " có " + countBills + " hóa đơn.");
        }

        return dailyCounts;
    }

}
