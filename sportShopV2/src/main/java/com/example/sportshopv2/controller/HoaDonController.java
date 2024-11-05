package com.example.sportshopv2.controller;

import com.example.sportshopv2.Entity.HoaDon;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.sportshopv2.Entity.HoaDonChiTiet;
import com.example.sportshopv2.Repository.HoaDonChiTietRepo;
import com.example.sportshopv2.Repository.HoaDonRepo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.xhtmlrenderer.pdf.ITextRenderer;

@Controller
//@RequestMapping("/Sport-Shop")
public class HoaDonController {
    @Autowired
    private HoaDonChiTietRepo hdctrepo;
    @Autowired
    private HoaDonRepo hdrepo;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ServletContext servletContext;


    @RequestMapping("/bill/view")
    public String view(Model model) {
        List<HoaDonChiTiet> hdctList = hdctrepo.findAll();
        model.addAttribute("hdctList", hdctList);
        model.addAttribute("tab", "all");
        return "HoaDon/HoaDon";
    }

    @GetMapping("/bill/tab")
    public String tab(Model model, @RequestParam("status") String status) {
        List<HoaDonChiTiet> hdctList = hdctrepo.findAllByHoaDon_Status(status);
        model.addAttribute("hdctList", hdctList);
        model.addAttribute("tab", status);
        return "HoaDon/HoaDon";
    }

    @GetMapping("/bill/detail")
    public String detail(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("list", hdctrepo.findAllById(id));
        return "HoaDon/ThongTinHoaDon";
    }

    @GetMapping("/bill/export/excel")
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

    @GetMapping("/export/pdf")
    public void viewInvoice(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam Integer id) {

        List<HoaDonChiTiet> billDetail = hdctrepo.findAllByHoaDon_Id(id);
        HoaDon bill = hdrepo.findAllById(id);
        if (bill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Invoice_" + id + ".pdf");

            // Prepare Thymeleaf context and template
            Context context = new Context();
            context.setVariable("hoaDon", bill); // Use HoaDon entity for invoice details
            context.setVariable("items", billDetail); // Assuming HoaDonChiTiet has a method to get items
            context.setVariable("productCount", bill); // Total number of products
            context.setVariable("discount", bill);
            context.setVariable("shippingFee", bill);
            context.setVariable("total", billDetail);
            context.setVariable("totalQuantity", billDetail);
//            context.setVariable("payment", bill.getQuantity() * bill.getPrice());
            context.setVariable("totalPayment", bill);
//            context.setVariable("change", bill.getHoaDon().getChange());

            String htmlContent = templateEngine.process("HoaDon/HinhAnhHoaDon", context);

            // Convert HTML to PDF
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();

            // Write PDF to response output stream
            OutputStream outputStream = response.getOutputStream();
            renderer.createPDF(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/bill/search")
    public String search(Model model, @RequestParam("Type") String Type) {
        List<HoaDonChiTiet> list = hdctrepo.findAllByHoaDon_Type(Type);
        model.addAttribute("hdctList", list);
        return "HoaDon/HoaDon";
    }

    @GetMapping("/doitra/detail")
    public String viewDTCT(Model model) {
        return "DoiTra/DoiTraChiTiet";
    }
}
