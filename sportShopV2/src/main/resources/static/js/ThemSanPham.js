function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'block';
    }
}

// Hàm để đóng modal
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
    }
}

// Đóng modal khi nhấn ngoài modal
window.onclick = function (event) {
    const modals = document.getElementsByClassName('modal');
    for (let modal of modals) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    }
}

function addBrand(event) {
    event.preventDefault(); // Ngăn chặn hành động gửi mặc định

    const newBrandName = document.getElementById('newBrand').value;

    // Gửi yêu cầu thêm thương hiệu mới
    fetch('/thuong-hieu/them-nhanh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tenThuongHieu: newBrandName}) // Gửi tên thương hiệu mới
    })
        .then(response => {
            if (response.ok) {
                updateBrandList(); // Cập nhật danh sách thương hiệu trong combobox
                closeModal('brandModal'); // Đóng modal
                document.getElementById('brandForm').reset(); // Reset form
            } else {
                console.error('Error adding brand:', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
}

function updateBrandList() {
    fetch('thuong-hieu/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
        .then(response => response.json())
        .then(data => {
            const brandSelect = document.getElementById('brand');
            brandSelect.innerHTML = ''; // Xóa các tùy chọn hiện tại
            brandSelect.innerHTML += '<option value="" disabled selected hidden>Chọn thương hiệu</option>'; // Thêm tùy chọn mặc định

            data.forEach(brand => {
                brandSelect.innerHTML += `<option value="${brand.id}">${brand.tenThuongHieu}</option>`;
            });
        })
        .catch(error => console.error('Error fetching brand list:', error));
}

//The loai
//
// --------------------------------------
function addTheLoai(event) {
    event.preventDefault(); // Ngăn chặn hành động gửi mặc định

    const tenTheLoai = document.getElementById('tenTheLoai').value;

    // Gửi yêu cầu thêm thương hiệu mới
    fetch('/the-loai/them-nhanh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tenTheLoai: tenTheLoai}) // Gửi tên thương hiệu mới
    })
        .then(response => {
            if (response.ok) {
                updateCboTheLoai(); // Cập nhật danh sách thương hiệu trong combobox
                closeModal('categoryModal'); // Đóng modal
                document.getElementById('theLoaiForm').reset(); // Reset form
            } else {
                console.error('Error adding :', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
}

function updateCboTheLoai() {
    fetch('the-loai/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
        .then(response => response.json())
        .then(data => {
            const brandSelect = document.getElementById('category');
            brandSelect.innerHTML = ''; // Xóa các tùy chọn hiện tại
            brandSelect.innerHTML += '<option value="" disabled selected hidden>Chọn thể loại</option>'; // Thêm tùy chọn mặc định

            data.forEach(theLoai => {
                brandSelect.innerHTML += `<option value="${theLoai.id}">${theLoai.tenTheLoai}</option>`;
            });
        })
        .catch(error => console.error('Error fetching brand list:', error));
}
//Chất liệu
//
// --------------------------------------
function addChatLieu(event) {
    event.preventDefault(); // Ngăn chặn hành động gửi mặc định

    const tenChatLieu = document.getElementById('tenChatLieu').value;

    // Gửi yêu cầu thêm thương hiệu mới
    fetch('/chat-lieu/them-nhanh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tenChatLieu: tenChatLieu}) // Gửi tên thương hiệu mới
    })
        .then(response => {
            if (response.ok) {
                updateCboChatLieu(); // Cập nhật danh sách thương hiệu trong combobox
                closeModal('materialModal'); // Đóng modal
                document.getElementById('chatLieuForm').reset(); // Reset form
            } else {
                console.error('Error adding :', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
}

function updateCboChatLieu() {
    fetch('chat-lieu/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
        .then(response => response.json())
        .then(data => {
            const brandSelect = document.getElementById('material');
            brandSelect.innerHTML = ''; // Xóa các tùy chọn hiện tại
            brandSelect.innerHTML += '<option value="" disabled selected hidden>Chọn Chất Liệu</option>'; // Thêm tùy chọn mặc định

            data.forEach(chatLieu => {
                brandSelect.innerHTML += `<option value="${chatLieu.id}">${chatLieu.tenChatLieu}</option>`;
            });
        })
        .catch(error => console.error('Error fetching brand list:', error));
}