// Xử lý toggle mở rộng/thu gọn sidebar
document.getElementById('toggle-btn').addEventListener('click', function() {
    var sidebar = document.getElementById('sidebar');
    var main = document.querySelector('.main');

    sidebar.classList.toggle('expand'); // Thêm/xóa lớp 'expand' cho sidebar

    if (sidebar.classList.contains('expand')) {
        main.classList.add('sidebar-expanded'); // Thêm class để thay đổi layout của main khi sidebar mở rộng
    } else {
        main.classList.remove('sidebar-expanded'); // Xóa class khi sidebar thu gọn
    }
});

// Hiển thị nội dung tương ứng với mục được chọn trong sidebar
// Khi trang được tải
window.onload = function() {
    // Khôi phục trạng thái đã chọn cho các mục trong sidebar
    const activeItemId = localStorage.getItem("activeSidebarItem");
    if (activeItemId) {
        const activeLink = document.querySelector(`.sidebar-link[data-id="${activeItemId}"]`);
        if (activeLink) {
            activeLink.classList.add('active'); // Thêm lớp active cho mục đang được chọn
            showContent(activeItemId); // Gọi hàm hiển thị nội dung tương ứng
        }
    }
};

// Lưu trạng thái lựa chọn cho các mục trong sidebar
document.querySelectorAll('.sidebar-link').forEach(link => {
    link.addEventListener('click', function(e) {
        // Lưu ID của mục đang được chọn vào Local Storage
        const activeItemId = this.getAttribute('data-id');
        localStorage.setItem("activeSidebarItem", activeItemId);

        // Xóa lớp active từ tất cả các mục trong sidebar
        document.querySelectorAll('.sidebar-link').forEach(item => item.classList.remove('active'));

        // Thêm lớp active cho mục đang được chọn
        this.classList.add('active');

        // Gọi hàm hiển thị nội dung tương ứng
        showContent(activeItemId);

        // Điều hướng đến đường dẫn của liên kết
        const href = this.getAttribute('href');
        if (href) {
            window.location.href = href; // Chuyển hướng đến đường dẫn
        }
    });
});

// Hàm hiển thị nội dung dựa trên ID
function showContent(itemId) {
    // Ẩn tất cả các phần nội dung
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = 'none';
    });

    // Hiển thị phần nội dung tương ứng với ID được chọn
    const contentSection = document.querySelector(`.content-section[data-id="${itemId}"]`);
    if (contentSection) {
        contentSection.style.display = 'block';
    }
}
