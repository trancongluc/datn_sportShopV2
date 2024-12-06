let tongTien = 1000;

function getCurrentInvoiceId() {
    const activeTab = document.querySelector('.tab-item.active');
    return activeTab ? activeTab.dataset.invoiceId : null;
}

// Hàm cập nhật phương thức nhận hàng


$(document).ready(function () {
    // Lấy danh sách tỉnh
    $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
        if (data_tinh.error === 0) {
            // Thêm tỉnh vào dropdown
            $.each(data_tinh.data, function (key_tinh, val_tinh) {
                const selected = val_tinh.id == "${customer.addresses[0].province_id}" ? "selected" : "";
                $("#tinh").append('<option value="' + val_tinh.id + '" ' + selected + '>' + val_tinh.full_name + '</option>');
            });

            // Kích hoạt sự kiện thay đổi tỉnh để tải quận
            $("#tinh").change(function () {
                var idtinh = $(this).val();
                // Lấy danh sách quận
                $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                    $("#quan").empty(); // Xóa danh sách quận trước
                    $.each(data_quan.data, function (key_quan, val_quan) {
                        $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                    });
                    $("#quan").change(); // Kích hoạt tải phường
                });
            }).change(); // Kích hoạt tải quận ban đầu

            // Kích hoạt sự kiện thay đổi quận để tải phường
            $("#quan").change(function () {
                var idquan = $(this).val();
                // Lấy danh sách phường
                $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                    $("#phuong").empty(); // Xóa danh sách phường trước
                    $.each(data_phuong.data, function (key_phuong, val_phuong) {
                        const selected = val_phuong.id == "${customer.addresses[0].ward_id}" ? "selected" : "";
                        $("#phuong").append('<option value="' + val_phuong.id + '" ' + selected + '>' + val_phuong.full_name + '</option>');
                    });
                });
            }).change(); // Kích hoạt tải phường ban đầu
        }
    });

    // Tạo địa chỉ từ các lựa chọn và gọi hàm tính phí ship
    // Sự kiện thay đổi địa chỉ và cập nhật phí ship
    $(document).ready(function () {
        $("#tinh, #quan, #phuong").change(function () {
            const soNha = $("#soNha").val() || ""; // Số nhà
            const xa = $("#phuong").find("option:selected").text(); // Phường/Xã
            const quan = $("#quan").find("option:selected").text(); // Quận/Huyện
            const tinh = $("#tinh").find("option:selected").text(); // Tỉnh/Thành phố
            const tongTien = 1000; // Tổng tiền đơn hàng (có thể thay đổi)

            // Hiển thị địa chỉ trong console
            const address = `${soNha}, ${xa}, ${quan}, ${tinh}`;
            console.log("Địa chỉ giao hàng:", address);

            // Gọi hàm tính phí ship
            tinhPhiShipGHTK(tongTien).then(fee => {
                if (fee !== null) {
                    console.log("Phí ship:", fee);
                    // Cập nhật phí ship vào ô input
                    if (fee === 0) {
                        $("#ship").val("Miễn Phí");
                    } else {
                        $("#ship").val(new Intl.NumberFormat('vi-VN', {
                            style: 'decimal',
                            minimumFractionDigits: 2,
                            maximumFractionDigits: 2
                        }).format(fee) + "VND");
                        calculateTotal();
                    }
                } else {
                    console.error("Không thể tính phí ship.");
                    $("#ship").val("0VND");
                }
            });
        });
    });
});
$("#ship").on("change", function () {
    const shipFee = $(this).val();
    console.log("Phí ship thay đổi:", shipFee);
    // Xử lý logic nếu cần...
});

async function tinhPhiShipGHTK(tongTienTinhShip) {
    const pickProvince = "Hà Nội";
    const pickDistrict = "Thanh Oai";
    const pickWard = "Xã Dân Hòa";

    const province = $("#tinh").find("option:selected").text();
    const district = $("#quan").find("option:selected").text();
    const ward = $("#phuong").find("option:selected").text();
    const weight = 500;

    if (!province || !district || !ward) {
        console.error("Thiếu thông tin địa chỉ nhận hàng!");
        return null;
    }

    const params = new URLSearchParams({
        pick_province: pickProvince,
        pick_district: pickDistrict,
        pick_ward: pickWard,
        province: province,
        district: district,
        ward: ward,
        weight: weight,
        value: tongTienTinhShip || 0,
        deliver_option: "none"
    });

    console.log("Yêu cầu gửi tới backend:", `/api/proxy/calculate-fee?${params.toString()}`);

    try {
        const response = await fetch(`/api/proxy/calculate-fee?${params.toString()}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            console.error(`HTTP error! Status: ${response.status}`);
            return null;
        }

        const result = await response.json();

        if (result.success) {
            console.log("Phí ship tính toán thành công:", result.fee.options.shipMoney);
            return result.fee.options.shipMoney;
        } else {
            console.error("API trả về lỗi:", result.message);
            return null;
        }
    } catch (error) {
        console.error("Lỗi kết nối hoặc xử lý API:", error);
        return null;
    }
}
