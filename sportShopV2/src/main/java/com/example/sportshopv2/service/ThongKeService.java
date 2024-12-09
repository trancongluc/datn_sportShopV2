package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.HoaDonRepo;
import com.example.sportshopv2.repository.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    public List<Object[]> getBillStatisticsByDay(int month, int year) {
        return hoaDonRepo.countBillsByDayInMonth(month, year);
    }
    public List<Object[]> getProductStatisticsByDay(int month, int year) {
        return hoaDonRepo.countProductsByDayInMonth(month, year);
    }
    public Map<String, List<Integer>> thongKeTheoNgay(LocalDate date) {
        List<Object[]> billData = hoaDonRepo.countBillsByHour(date);
        List<Object[]> productData = hoaDonRepo.countProductsByHour(date);

        // Tạo danh sách mặc định với 24 giá trị (tương ứng 24 giờ)
        List<Integer> totalBills = new ArrayList<>(Collections.nCopies(24, 0));
        List<Integer> totalProducts = new ArrayList<>(Collections.nCopies(24, 0));

        // Cập nhật dữ liệu vào danh sách
        for (Object[] bill : billData) {
            int hour = (int) bill[0];
            totalBills.set(hour, ((Long) bill[1]).intValue());
        }

        for (Object[] product : productData) {
            int hour = (int) product[0];
            totalProducts.set(hour, ((Long) product[1]).intValue());
        }

        // Trả về kết quả
        Map<String, List<Integer>> result = new HashMap<>();
        result.put("totalBills", totalBills);
        result.put("totalProducts", totalProducts);
        return result;
    }
    public List<Object[]> getBillStatisticsByDayInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return hoaDonRepo.countBillsByDayInDateRange(startDate, endDate);
    }

    // Thống kê sản phẩm theo ngày trong khoảng thời gian
    public List<Object[]> getProductStatisticsByDayInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return hoaDonRepo.countProductsByDayInDateRange(startDate, endDate);
    }
}
