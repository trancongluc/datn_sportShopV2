    $(document).ready(function () {
        // Lấy danh sách tỉnh
        $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
            if (data_tinh.error === 0) {

                $.each(data_tinh.data, function (key_tinh, val_tinh) {
                    const selected = val_tinh.id == "${customer.addresses[0].province_id}" ? "selected" : "";

                    $("#tinh").append('<option value="' + val_tinh.id + '" ' + selected + '>' + val_tinh.full_name + '</option>');
                });

                // Kích hoạt sự kiện thay đổi tỉnh để tải quận ban đầu
                $("#tinh").change(function () {
                    var idtinh = $(this).val();

                    // Lấy danh sách quận dựa trên tỉnh đã chọn
                    $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                        // $("#quan").html('<option value="0">Quận Huyện</option>');
                        // $("#phuong").html('<option value="0">Phường Xã</option>'); // Xóa danh sách phường

                        $.each(data_quan.data, function (key_quan, val_quan) {
                            $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                        });

                        // Kích hoạt sự kiện thay đổi quận để tải phường ban đầu
                        $("#quan").change();
                    });
                }).change(); // Kích hoạt tải quận ban đầu

                // Lấy danh sách phường dựa trên quận đã chọn
                $("#quan").change(function () {
                    var idquan = $(this).val();

                    $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                        // $("#phuong").html('<option value="0">Phường Xã</option>');

                        $.each(data_phuong.data, function (key_phuong, val_phuong) {
                            const selected = val_phuong.id == "${customer.addresses[0].ward_id}" ? "selected" : "";
                            $("#phuong").append('<option value="' + val_phuong.id + '" ' + selected + '>' + val_phuong.full_name + '</option>');
                        });
                    });
                }).change(); // Kích hoạt tải phường ban đầu
            }
        });
    });




