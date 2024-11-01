package com.example.sportshopv2.controller;

import com.example.sportshopv2.Entity.HoaDonChiTiet;
import com.example.sportshopv2.Repository.HoaDonChiTietRepo;
import com.example.sportshopv2.Repository.HoaDonRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Controller
public class HoaDonController {
    @Autowired
    private HoaDonRepo hdrepo;

    @Autowired
    private HoaDonChiTietRepo hdctrepo;

    @RequestMapping("/bill/view")
    public String view(Model model) {
        List<HoaDonChiTiet> hdctList = hdctrepo.findAll();
        model.addAttribute("hdctList", hdctList); // Đồng nhất tên biến là "hdctList"
        model.addAttribute("tab", "all"); // Add this line to set the tab
        return "HoaDon/HoaDon";
    }

    @GetMapping("/bill/tab")
    public String tab(Model model, @RequestParam("status") String status) {
        System.out.println("Status received: " + status); // In ra log để kiểm tra giá trị
        List<HoaDonChiTiet> hdctList = hdctrepo.findAllByHoaDon_Status(status);
        model.addAttribute("hdctList", hdctList);
        model.addAttribute("tab", status); // Set the tab to the current status
        return "HoaDon/HoaDon";
    }

    @GetMapping("/bill/detail")
    public String detail(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("list", hdctrepo.findAllById(id));
        return "HoaDon/ThongTinHoaDon";
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        List<HoaDonChiTiet> billList = hdctrepo.findAll(); // Lấy toàn bộ dữ liệu từ database

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách hóa đơn");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã hóa đơn");
        headerRow.createCell(1).setCellValue("Ngày tạo hóa đơn");
        headerRow.createCell(2).setCellValue("Người tạo");
        headerRow.createCell(3).setCellValue("Ngày cập nhật cuối");
        headerRow.createCell(4).setCellValue("Người cập nhật");
        headerRow.createCell(5).setCellValue("Trạng thái hóa đơn");
        headerRow.createCell(6).setCellValue("Tên sản phẩm");
        headerRow.createCell(7).setCellValue("Số lượng");
        headerRow.createCell(8).setCellValue("Giá Bán");
        headerRow.createCell(9).setCellValue("Tổng tiền");
        int rowIndex = 1;
        for (HoaDonChiTiet bill : billList) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(bill.getHoaDon().getBill_code().toString());
            row.createCell(1).setCellValue(bill.getHoaDon().getCreate_at());
            row.createCell(2).setCellValue(bill.getHoaDon().getCreate_by());
            row.createCell(3).setCellValue(bill.getHoaDon().getUpdate_at());
            row.createCell(4).setCellValue(bill.getHoaDon().getUpdate_by());
            row.createCell(5).setCellValue(bill.getHoaDon().getStatus());
            row.createCell(6).setCellValue(bill.getSanPhamChiTiet().getId());
            row.createCell(7).setCellValue(bill.getQuantity());
            row.createCell(8).setCellValue(bill.getSanPhamChiTiet().getId());
            row.createCell(9).setCellValue(bill.getPrice());
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=staff_list.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(outputStream.toByteArray());
    }

    @GetMapping("/doitra/view")
    public String viewDT(Model model) {
//        model.addAttribute("messager", "Hello");
        return "DoiTra/DoiTra";
    }

    @GetMapping("/doitra/detail")
    public String viewDTCT(Model model) {
//        model.addAttribute("messager", "Hello");
        return "DoiTra/DoiTraChiTiet";
    }
//    @RequestMapping
}
