// Xử lý toggle mở rộng/thu gọn sidebar
<<<<<<< HEAD
function debounce(func, wait) {
    let timeout;
    return function (...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

document.getElementById('toggle-btn').addEventListener('click', debounce(function () {
=======
document.getElementById('toggle-btn').addEventListener('click', function () {
>>>>>>> tan
    var sidebar = document.getElementById('sidebar');
    var main = document.querySelector('.main');

    sidebar.classList.toggle('expand'); // Thêm/xóa lớp 'expand' cho sidebar

    if (sidebar.classList.contains('expand')) {
        main.classList.add('sidebar-expanded'); // Thêm class để thay đổi layout của main khi sidebar mở rộng
    } else {
        main.classList.remove('sidebar-expanded'); // Xóa class khi sidebar thu gọn
    }
}, 300)); // Khoảng cách debounce là 300ms

<<<<<<< HEAD
=======
// Hiển thị nội dung tương ứng với mục được chọn trong sidebar
// Khi trang được tải
window.onload = function () {
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
>>>>>>> tan

// Lưu trạng thái lựa chọn cho các mục trong sidebar
document.querySelectorAll('.sidebar-link').forEach(link => {
    link.addEventListener('click', function (e) {
<<<<<<< HEAD
        e.preventDefault(); // Ngăn chặn tải lại trang

        // Lấy đường dẫn của liên kết
        const href = this.getAttribute('href');

        if (href) {
            // Gọi hàm để tải nội dung bằng AJAX
            fetchContent(href);
        }
=======
        // Lưu ID của mục đang được chọn vào Local Storage
        const activeItemId = this.getAttribute('data-id');
        localStorage.setItem("activeSidebarItem", activeItemId);
>>>>>>> tan

        // Xóa lớp active từ tất cả các mục trong sidebar
        document.querySelectorAll('.sidebar-link').forEach(item => item.classList.remove('active'));

        // Thêm lớp active cho mục đang được chọn
        this.classList.add('active');
<<<<<<< HEAD
=======

        // Gọi hàm hiển thị nội dung tương ứng
        showContent(activeItemId);

        // Điều hướng đến đường dẫn của liên kết
>>>>>>> luctcph35904
    });
});


<<<<<<< HEAD
// Hàm tải nội dung từ URL mà không tải lại trang
function fetchContent(url) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            let parser = new DOMParser();
            let doc = parser.parseFromString(data, 'text/html');
            let mainContent = doc.querySelector('.main'); // Chỉ lấy nội dung main

            if (mainContent) {
                document.querySelector('.main').innerHTML = mainContent.innerHTML; // Cập nhật nội dung main
            }

            // Sau khi cập nhật nội dung, gắn lại sự kiện cho các phần tử
            reattachToggleSidebarEvent();
        })
        .catch(error => console.error('Lỗi khi tải nội dung:', error));
}

// Hàm để gắn lại sự kiện toggle cho sidebar
function reattachToggleSidebarEvent() {
    document.getElementById('toggle-btn').addEventListener('click', function () {
        var sidebar = document.getElementById('sidebar');
        var main = document.querySelector('.main');

        sidebar.classList.toggle('expand'); // Thêm/xóa lớp 'expand' cho sidebar

        if (sidebar.classList.contains('expand')) {
            main.classList.add('sidebar-expanded'); // Thêm class để thay đổi layout của main khi sidebar mở rộng
        } else {
            main.classList.remove('sidebar-expanded'); // Xóa class khi sidebar thu gọn
        }
    });
}


=======
    // Hiển thị phần nội dung tương ứng với ID được chọn
    const contentSection = document.querySelector(`.content-section[data-id="${itemId}"]`);
    if (contentSection) {
        contentSection.style.display = 'block';
    }
}
<<<<<<< HEAD
>>>>>>> luctcph35904
=======




>>>>>>> long
