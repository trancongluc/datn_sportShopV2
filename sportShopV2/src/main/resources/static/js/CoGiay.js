function showModal(type, id) {
    $('#myModal').css('display', 'block'); // Hiện modal
    $('#coGiayId').val(id); // Cập nhật ID chất liệu vào input ẩn
    $('#coGiayForm')[0].reset(); // Reset form khi mở modal
    console.log("id :" + id);

    if (type === 'view' || type === 'edit') {
        // Gọi API để lấy chi tiết chất liệu
        $.get('/co-giay/' + id)
            .done(function (coGiay) {
                $('#myModal h2').text(type === 'view' ? 'Chi tiết Cổ Giày' : 'Cập nhật Cổ Giày');
                $('#tenCoGiay').val(coGiay.tenCoGiay);
                $('#trangThai').val(coGiay.trangThai); // Đặt giá trị trạng thái vào combobox
                $('#actionType').val(type); // Gán hành động
                $('#myModal button[type=submit]').toggle(type === 'edit'); // Hiện nút lưu nếu là edit
            })
            .fail(function (jqXHR, textStatus) {
                console.error("Error:", textStatus);
                alert('Có lỗi xảy ra khi lấy thông tin.');
            });
    } else if (type === 'add') {
        $('#myModal h2').text('Thêm cổ giày mới');
        $('#actionType').val('add'); // Gán hành động
        $('#myModal button[type=submit]').show(); // Hiện nút lưu
    }
}

// Xử lý sự kiện submit của form
$(document).ready(function () {
    $('#coGiayForm').submit(function (event) {
        event.preventDefault(); // Ngăn chặn hành động submit mặc định
        // Gọi modal xác nhận trước khi submit
        showConfirmation('lưu');
    });
});

function showConfirmation(action) {
    const modal = $('#confirmationModal');
    const modalMessage = $('#modalMessage');
    modalMessage.text(`Bạn có chắc muốn ${action} ?`); // Sử dụng đúng ký tự tiếng Việt
    modal.css('display', 'block');
    setTimeout(() => {
        modal.css('opacity', '1');
        $('.modal-content').css('transform', 'scale(1)');
    }, 10);
}

// Xác nhận hành động
function confirmAction() {
    const actionType = $('#actionType').val();
    const coGiayId = $('#coGiayId').val();
    let url = '';
    let method = '';

    if (actionType === 'add') {
        url = '/co-giay/them-co-giay';
        method = 'POST';
    } else if (actionType === 'edit') {
        url = '/co-giay/' + coGiayId;
        method = 'PUT';
    }

    const formData = JSON.stringify({
        tenCoGiay: $('#tenCoGiay').val(),
        trangThai: $('#trangThai').val()
    });

    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json',
        data: formData,
        success: function (response) {
            closeModal(); // Đóng modal xác nhận
            $('#myModal').css('display', 'none'); // Đóng modal thêm/cập nhật
            localStorage.setItem('notification', "Thành Công!");
            // Tải lại trang
            location.reload();


        },
        error: function (jqXHR, textStatus) {
            console.error("Error:", textStatus);
            alert('Có lỗi xảy ra trong quá trình xử lý.');
        }
    });
}

$(document).ready(function () {
    const notificationMessage = localStorage.getItem('notification');

    if (notificationMessage) {
        showNotification(notificationMessage);
        localStorage.removeItem('notification'); // Xóa thông báo sau khi hiển thị
    }
});

// Hiển thị thông báo
function showNotification(message) {
    const $notification = $('.notification');
    const $progressBar = $('.progress-bar');
    $notification.find('.message').text(message); // Cập nhật nội dung thông báo
    $notification.css('display', 'flex');
    $progressBar.css('animation', 'progress 3s linear forwards');
    setTimeout(() => {
        $notification.css('opacity', '0');
        setTimeout(() => {
            $notification.css('display', 'none');
            $notification.css('opacity', '1');
            $progressBar.css('animation', 'none');
        }, 500);
    }, 3000);
}

// Xử lý sự kiện khi nhấn ra ngoài modal
function closeModal() {
// Đóng tất cả các modal
    $('#myModal').css('display', 'none'); // Đóng modal chính
    $('#confirmationModal').css('display', 'none'); // Đóng modal xác nhận
    const modal = document.getElementById('confirmationModal');
    modal.style.opacity = '0';
    document.querySelector('.modal-content').style.transform = 'scale(0.7)';
// Đặt lại kích thước và kiểu dáng của modal
    $('#myModal .modal-content').css({
        width: 'auto',
        height: 'auto',
        transform: 'scale(1)', // Đặt lại tỷ lệ
        opacity: '1' // Đặt lại độ mờ
    });

    setTimeout(() => {
        modal.style.display = 'none';
    }, 500);
}

// Sự kiện khi bấm nút trong modal
$(window).on('click', function (event) {
    if (event.target.id === 'myModal') closeModal();
    if (event.target.id === 'confirmationModal') closeModal(); // Đóng modal xác nhận khi nhấn ra ngoài
});

