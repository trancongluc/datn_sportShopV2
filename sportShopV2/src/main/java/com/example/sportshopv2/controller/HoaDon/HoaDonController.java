package com.example.sportshopv2.controller.HoaDon;

import com.example.sportshopv2.model.AnhSanPham;
import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.repository.AnhSanPhamRepository;
import com.itextpdf.text.pdf.BaseFont;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.sportshopv2.model.HoaDonChiTiet;
import com.example.sportshopv2.repository.HoaDonChiTietRepo;
import com.example.sportshopv2.repository.HoaDonRepo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.xhtmlrenderer.pdf.ITextRenderer;

@Controller
@RequestMapping("/bill")
public class HoaDonController {
    @Autowired
    private HoaDonChiTietRepo hdctrepo;
    @Autowired
    private HoaDonRepo hdrepo;
    private AnhSanPhamRepository anhSanPhamRepository;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ServletContext servletContext;

    private Map<Integer, String> tongTienHD;


    public HoaDonController(AnhSanPhamRepository anhSanPhamRepository,
                            HoaDonChiTietRepo hdctrepo,
                            HoaDonRepo hdrepo) {
        this.anhSanPhamRepository = anhSanPhamRepository;
        this.hdctrepo = hdctrepo;
        this.hdrepo = hdrepo;
    }

    @RequestMapping("/view")
    public String view(Model model) {
        List<HoaDon> hdList = hdrepo.findAllByOrderByCreateAtDesc();
        Map<Integer, String> tongTienHoaDon = hdList.stream().collect(Collectors.toMap(
                HoaDon::getId, // Key là ID của hóa đơn
                hd -> {
                    // Tính tổng tiền của hóa đơn
                    BigDecimal total = hdctrepo.findAllByHoaDon_Id(hd.getId()).stream()
                            .map(hdct -> new BigDecimal(hdct.getPrice()).multiply(BigDecimal.valueOf(hdct.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // Làm tròn tổng tiền đến 2 chữ số thập phân
                    total = total.setScale(2, RoundingMode.HALF_UP); // Sử dụng RoundingMode.HALF_UP để làm tròn

                    // Định dạng tổng tiền
                    return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(total);
                }
        ));
        tongTienHD = tongTienHoaDon;
        model.addAttribute("hdList", hdList);
        model.addAttribute("tongTienHoaDon", tongTienHoaDon);
        model.addAttribute("tab", "all");
        return "HoaDon/HoaDon";
    }

    @GetMapping("/tab")
    public String tab(Model model, @RequestParam(value = "status", defaultValue = "defaultStatus") String status) {
        List<HoaDon> hdctList = hdrepo.findAllByStatusLike(status);
        model.addAttribute("hdList", hdctList);
        model.addAttribute("tab", status);
        model.addAttribute("tongTienHoaDon", tongTienHD);
        return "HoaDon/HoaDon";
    }


    @GetMapping("/detail")
    public String detail(Model model, @RequestParam("id") Integer id) {
        List<HoaDonChiTiet> hoaDonDetail = hdctrepo.findAllByHoaDon_Id(id);
        HoaDon hoaDon = hdrepo.findAllById(id);
        List<AnhSanPham> anhSanPhams = hoaDonDetail.stream()
                .map(hdct -> anhSanPhamRepository.findByIdSPCT(hdct.getSanPhamChiTiet().getId())) // Lấy id từ SanPhamChiTiet
                .flatMap(List::stream) // Gộp danh sách ảnh từ từng sản phẩm thành một danh sách duy nhất
                .collect(Collectors.toList());

        if (anhSanPhams.isEmpty()) {
            System.out.println("Danh sách hình ảnh rỗng.");
        }

// Kiểm tra và truyền dữ liệu
        model.addAttribute("list", hoaDonDetail);
        model.addAttribute("listImage", anhSanPhams);

        model.addAttribute("hoaDon", hoaDon);
        return "HoaDon/ThongTinHoaDon";
    }


    @GetMapping("/pdf")
    public String pdf(Model model, @RequestParam("id") Integer id) {
//        model.addAttribute("list", hdctrepo.findAllById(id));
        return "HoaDon/HinhAnhHoaDon";
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        List<HoaDonChiTiet> billList = hdctrepo.findAll(); // Lấy toàn bộ dữ liệu từ database
        List<HoaDon> billList2 =  hdrepo.findAll();
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
            row.createCell(0).setCellValue(bill.getHoaDon().getBillCode().toString());
            row.createCell(1).setCellValue(bill.getHoaDon().getCreateAt());
            row.createCell(2).setCellValue(bill.getHoaDon().getCreate_by());
            row.createCell(3).setCellValue(bill.getHoaDon().getUpdateAt());
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
    public void viewInvoice(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer id) {
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
            context.setVariable("hoaDon", bill);
            context.setVariable("items", billDetail);
            context.setVariable("productCount", billDetail.size());
            context.setVariable("discount", bill.getMoney_reduced());
            context.setVariable("total", billDetail.stream().mapToDouble(HoaDonChiTiet::getPrice).sum());
            context.setVariable("totalQuantity", billDetail.stream().mapToInt(HoaDonChiTiet::getQuantity).sum());
            context.setVariable("totalPayment", bill.getTotal_money());

            String htmlContent = templateEngine.process("HoaDon/HinhAnhHoaDon", context);

            // Convert HTML to PDF
            ITextRenderer renderer = new ITextRenderer();

            String fontPath = "fonts/Arial.ttf";  // Đường dẫn đến font
            renderer.getFontResolver().addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();

            // Write PDF to response output stream
            try (OutputStream outputStream = response.getOutputStream()) {
                renderer.createPDF(outputStream);
                outputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{id}")
    public String getStatus(@PathVariable Integer id, Model model) {
        Optional<HoaDon> hoaDon = hdrepo.findById(id);
        hoaDon.ifPresent(hd -> model.addAttribute("hoaDon", hd));
        return "HoaDon/ThongTinHoaDon"; // Assuming this is the template name for the bill details
    }

    @PostMapping("/status/update")
    public String updateStatus(@RequestParam Integer id, @RequestParam String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<HoaDon> hoaDonOptional = hdrepo.findById(id);
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();
            hoaDon.setStatus(status);
            hoaDon.setUpdateAt(LocalDateTime.now());
            hoaDon.setUpdate_by(username);
            hdrepo.save(hoaDon);
        }
        return "redirect:/bill/detail?id=" + id;
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(value = "maHoaDon", required = false, defaultValue = "") String maHoaDon,
                         @RequestParam(value = "Type", required = false, defaultValue = "") String Type,
                         @RequestParam(value = "createAt", required = false, defaultValue = "01/01/1900") String createAt,
                         @RequestParam(value = "updateAt", required = false, defaultValue = "31/12/9999") String updateAt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime startDate = LocalDate.parse(createAt, formatter).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(updateAt, formatter).atTime(23, 59, 59);

        List<HoaDon> hdList = hdrepo.searchHoaDon(
                maHoaDon.isBlank() ? null : maHoaDon,
                Type.isBlank() ? null : Type,
                startDate,
                endDate
        );
        model.addAttribute("tongTienHoaDon", tongTienHD);

        model.addAttribute("hdList", hdList);
        return "HoaDon/HoaDon";
    }


}
