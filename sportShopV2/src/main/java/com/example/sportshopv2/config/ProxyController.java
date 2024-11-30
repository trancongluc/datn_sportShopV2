package com.example.sportshopv2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/proxy")
public class ProxyController {
    @Value("${api.base.url}")
    private String apiUrl; // Địa chỉ API bạn muốn truy cập

    private final RestTemplate restTemplate;

    public ProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/calculate-fee")
    public ResponseEntity<?> calculateShippingFee(
            @RequestParam String pick_province,
            @RequestParam String pick_district,
            @RequestParam String pick_ward,
            @RequestParam String province,
            @RequestParam String district,
            @RequestParam String ward,
            @RequestParam int weight,
            @RequestParam float value,
            @RequestParam String deliver_option) {

        // Xây dựng URL yêu cầu GHTK API
        String apiUrl = "https://services.giaohangtietkiem.vn/services/shipment/fee";
        String apiKey = "89qVTzij6wuEF7W2FA9nByv9uG2WHH0WXfcKww"; // Thay bằng API key hợp lệ

        // Xây dựng các tham số cho GHTK API
        String params = String.format("pick_province=%s&pick_district=%s&pick_ward=%s&province=%s&district=%s&ward=%s&weight=%d&value=%.2f&deliver_option=%s",
                pick_province, pick_district, pick_ward, province, district, ward, weight, value, deliver_option);

        String fullUrl = apiUrl + "?" + params;

        // Gửi yêu cầu đến GHTK API
        try {
            ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, new HttpEntity<>(createHeaders(apiKey)), String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi gọi API GHTK");
        }
    }

    private HttpHeaders createHeaders(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Token", apiKey);
        headers.set("X-Client-Source", "S22778079");
        return headers;
    }


}
