function showModal(type, id) {
    $('#myModal').css('display', 'block'); // Hiện modal
    $('#chatLieuId').val(id); // Cập nhật ID chất liệu vào input ẩn
    $('#chatLieuForm')[0].reset(); // Reset form khi mở modal

    if (type === 'view' || type === 'edit') {
        // Gọi API để lấy chi tiết chất liệu
        $.get('/chat-lieu/' + id)
            .done(function (chatLieu) {
                $('#myModal h2').text(type === 'view' ? 'Chi tiết chất liệu' : 'Cập nhật chất liệu');
                $('#tenChatLieu').val(chatLieu.tenChatLieu);
                $('#trangThai').val(chatLieu.trangThai); // Đặt giá trị trạng thái vào combobox
                $('#actionType').val(type); // Gán hành động
                $('#myModal button[type=submit]').toggle(type === 'edit'); // Hiện nút lưu nếu là edit
            })
            .fail(function (jqXHR, textStatus) {
                console.error("Error:", textStatus);
                alert('Có lỗi xảy ra khi lấy thông tin chất liệu.');
            });

    } else if (type === 'add') {
        $('#myModal h2').text('Thêm chất liệu mới');
        $('#actionType').val('add'); // Gán hành động
        $('#myModal button[type=submit]').show(); // Hiện nút lưu
    }
}

// Xử lý sự kiện submit của form
$('#chatLieuForm').submit(function (event) {
    event.preventDefault(); // Ngăn chặn hành động submit mặc định
    const actionType = $('#actionType').val();
    const chatLieuId = $('#chatLieuId').val();
    const formData = $(this).serialize(); // Lấy toàn bộ dữ liệu từ form
    let url = '';
    let method = '';
    if (actionType === 'add') {
        url = '/chat-lieu/them-chat-lieu';
        method = 'POST';
    } else if (actionType === 'edit') {
        url = '/chat-lieu/' + chatLieuId;
        method = 'PUT';
    }
    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json', // Đặt content type để gửi JSON
        data: JSON.stringify({
            tenChatLieu: $('#tenChatLieu').val(),
            trangThai: $('#trangThai').val()
        }),
        success: function (response) {
            alert(actionType === 'add' ? 'Thêm chất liệu thành công!' : 'Cập nhật chất liệu thành công!');
            closeModal();
            window.location.reload();
        },
        error: function (jqXHR, textStatus) {
            console.error("Error:", textStatus);
            alert('Có lỗi xảy ra trong quá trình xử lý.');
        }
    });
});

// Đóng modal khi nhấn vào biểu tượng đóng hoặc bên ngoài modal
function closeModal() {
    $('#myModal').css('display', 'none'); // Ẩn modal
}

// Xử lý sự kiện khi nhấn ra ngoài modal
$(window).on('click', function (event) {
    if (event.target.id === 'myModal') closeModal();
});
// co giay
