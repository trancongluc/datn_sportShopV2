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
                showNotification("Thành Công!");
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

    const newTheLoai = document.getElementById('newTheLoai').value;

    // Gửi yêu cầu thêm thương hiệu mới
    fetch('/the-loai/them-nhanh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tenTheLoai: newTheLoai}) // Gửi tên thương hiệu mới
    })
        .then(response => {
            if (response.ok) {
                updateCboTheLoai(); // Cập nhật danh sách thương hiệu trong combobox
                closeModal('categoryModal'); // Đóng modal
                document.getElementById('theLoaiForm').reset(); // Reset form
                showNotification("Thành Công!");
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

    const newChatLieu = document.getElementById('newChatLieu').value;

    // Gửi yêu cầu thêm thương hiệu mới
    fetch('/chat-lieu/them-nhanh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tenChatLieu: newChatLieu}) // Gửi tên thương hiệu mới
    })
        .then(response => {
            if (response.ok) {
                updateCboChatLieu(); // Cập nhật danh sách thương hiệu trong combobox
                closeModal('materialModal'); // Đóng modal
                document.getElementById('chatLieuForm').reset(); // Reset form
                showNotification("Thành Công!");
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

//Cổ Giày --------------
function addCoGiay(event) {
    event.preventDefault(); // Ngăn chặn hành động gửi mặc định

    const newCoGiay = document.getElementById('newCoGiay').value;

    // Gửi yêu cầu thêm thương hiệu mới
    fetch('/co-giay/them-nhanh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tenCoGiay: newCoGiay}) // Gửi tên thương hiệu mới
    })
        .then(response => {
            if (response.ok) {
                updateCboCoGiay(); // Cập nhật danh sách thương hiệu trong combobox
                closeModal('collarModal'); // Đóng modal
                document.getElementById('coGiayForm').reset(); // Reset form
                showNotification("Thành Công!");
            } else {
                console.error('Error adding :', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
}

function updateCboCoGiay() {
    fetch('co-giay/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
        .then(response => response.json())
        .then(data => {
            const brandSelect = document.getElementById('collar');
            brandSelect.innerHTML = ''; // Xóa các tùy chọn hiện tại
            brandSelect.innerHTML += '<option value="" disabled selected hidden>Chọn Cổ Giày</option>'; // Thêm tùy chọn mặc định

            data.forEach(coGiay => {
                brandSelect.innerHTML += `<option value="${coGiay.id}">${coGiay.tenCoGiay}</option>`;
            });
        })
        .catch(error => console.error('Error fetching brand list:', error));
}

//Đế giày ==================
function addDeGiay(event) {
    event.preventDefault(); // Ngăn chặn hành động gửi mặc định

    const newDeGiay = document.getElementById('newDeGiay').value;

    // Gửi yêu cầu thêm thương hiệu mới
    fetch('/de-giay/them-nhanh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tenDeGiay: newDeGiay}) // Gửi tên thương hiệu mới
    })
        .then(response => {
            if (response.ok) {
                updateCboDeGiay(); // Cập nhật danh sách thương hiệu trong combobox
                closeModal('soleModal'); // Đóng modal
                document.getElementById('deGiayForm').reset(); // Reset form
                showNotification("Thành Công!");
            } else {
                console.error('Error adding :', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
}

function updateCboDeGiay() {
    fetch('de-giay/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
        .then(response => response.json())
        .then(data => {
            const brandSelect = document.getElementById('sole');
            brandSelect.innerHTML = ''; // Xóa các tùy chọn hiện tại
            brandSelect.innerHTML += '<option value="" disabled selected hidden>Chọn Đế Giày</option>'; // Thêm tùy chọn mặc định

            data.forEach(deGiay => {
                brandSelect.innerHTML += `<option value="${deGiay.id}">${deGiay.tenDeGiay}</option>`;
            });
        })
        .catch(error => console.error('Error fetching brand list:', error));
}

//Thông báo khi thành công

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

//Chọn size

let selectedSizes = []; // Mảng lưu trữ kích cỡ đã chọn

function selectSize(button) {
    const size = button.textContent; // Lấy tên kích cỡ
    const sizeId = button.getAttribute('data-size'); // Lấy ID kích cỡ

    const sizeObj = {id: sizeId, name: size}; // Tạo đối tượng với tên và ID

    if (selectedSizes.some(s => s.id === sizeId)) {
        // Nếu kích cỡ đã được chọn, xóa nó khỏi mảng
        selectedSizes = selectedSizes.filter(s => s.id !== sizeId);
        button.classList.remove('active');
    } else {
        // Nếu chưa chọn, thêm vào mảng
        selectedSizes.push(sizeObj);
        button.classList.add('active');
    }
    updateProductTable();
    console.log('Kích cỡ đã chọn:', selectedSizes); // In ra mảng chứa kích cỡ đã chọn
}

function updateSelectedSizes() {
    const selectedSizesElement = document.getElementById('selected-sizes');
    selectedSizesElement.innerHTML = ''; // Xóa nội dung cũ

    selectedSizes.forEach(size => {
        const span = document.createElement('span');
        span.className = 'selected-size';
        span.textContent = size.name; // Hiển thị tên kích cỡ
        span.onclick = () => removeSize(size.id); // Thêm sự kiện để xóa kích cỡ khi nhấp
        selectedSizesElement.appendChild(span);
    });
}

function removeSize(sizeId) {
    // Xóa kích cỡ khỏi mảng và cập nhật lại
    selectedSizes = selectedSizes.filter(s => s.id !== sizeId);
    updateSelectedSizes(); // Cập nhật hiển thị bên ngoài modal

    // Cập nhật trạng thái của các nút trong modal
    const sizeButtons = document.querySelectorAll('.size-button');
    sizeButtons.forEach(button => {
        if (button.getAttribute('data-size') === sizeId) {
            button.classList.remove('active'); // Bỏ chọn nút tương ứng trong modal
        }
    });
}

function confirmSelectedSizes() {
    updateSelectedSizes();
    closeModal('sizeModal');

}

// function updateModalState() {
//     const sizeButtons = document.querySelectorAll('.size-button');
//     sizeButtons.forEach(button => {
//         const sizeId = button.getAttribute('data-size');
//         // Đánh dấu các nút trong modal là active nếu chúng có trong selectedSizes
//         if (selectedSizes.some(size => size.id === sizeId)) {
//             button.classList.add('active');
//         } else {
//             button.classList.remove('active');
//         }
//     });
// }
let selectedColors = [];

function selectedColor(colorButton) {
    // Lấy mã màu và ID từ nút
    const colorCode = colorButton.style.backgroundColor;
    const colorId = colorButton.getAttribute('data-size');

    // Kiểm tra xem mã màu đã được chọn chưa
    if (selectedColors.some(color => color.id === colorId)) {
        // Nếu đã chọn, xóa nó khỏi danh sách
        selectedColors = selectedColors.filter(color => color.id !== colorId);
        colorButton.classList.remove('active');
    } else {
        // Nếu chưa chọn, thêm vào danh sách
        selectedColors.push({id: colorId, code: colorCode});
        colorButton.classList.add('active');
    }

    console.log('Các màu sắc đã chọn:', selectedColors);
    updateProductTable();
}

function confirmSelectedColors() {
    updateSelectedColors(); // Cập nhật hiển thị các màu sắc đã chọn
    closeModal('mauSacModal'); // Đóng modal màu sắc
}

function updateSelectedColors() {
    const selectedColorsElement = document.getElementById('selected-colors'); // Phần tử hiển thị màu sắc đã chọn
    selectedColorsElement.innerHTML = ''; // Xóa nội dung cũ

    selectedColors.forEach(color => {
        const span = document.createElement('span');
        span.className = 'selected-color';
        span.style.backgroundColor = color.code; // Đặt màu nền cho span
        span.title = color.code; // Thêm title để hiển thị mã màu
        selectedColorsElement.appendChild(span);
    });
}

function updateColorPreview(color) {
    document.getElementById('colorPreview').style.backgroundColor = color;
}

// function saveCustomColor() {
//     const selectedColor = document.getElementById('tenMauSac').value;
//     console.log('Màu đã chọn:', selectedColor);
//     // Lưu màu sắc vào database hoặc sử dụng cho mục đích khác
//     closeModal('colorPickerModal');
// }

//Kích THước
function addKichThuoc(event) {
    event.preventDefault(); // Ngăn chặn hành động gửi mặc định

    const newSize = document.getElementById('newSize').value;

    // Gửi yêu cầu thêm thương hiệu mới
    fetch('/kich-thuoc/them-nhanh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tenKichThuoc: newSize}) // Gửi tên thương hiệu mới
    })
        .then(response => {
            if (response.ok) {
                updateSizeList(); // Cập nhật danh sách thương hiệu trong combobox
                closeModal('sizeAddModal'); // Đóng modal
                document.getElementById('sizeForm').reset(); // Reset form
                showNotification("Thành Công!");
            } else {
                console.error('Error adding :', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
}

function updateSizeList() {
    fetch('/kich-thuoc') // Đường dẫn đến API để lấy danh sách kích thước
        .then(response => response.json())
        .then(data => {
            const sizeListContainer = document.querySelector('.size-list');
            sizeListContainer.innerHTML = ''; // Xóa nội dung cũ

            data.forEach(size => {
                const button = document.createElement('button');
                button.textContent = size.tenKichThuoc;
                button.setAttribute('data-size', size.id);
                button.onclick = () => selectSize(button);
                sizeListContainer.appendChild(button);
            });
        })
        .catch(error => console.error('Error fetching sizes:', error));
}

//Màu sắc
function addMauSac(event) {
    event.preventDefault(); // Ngăn chặn hành động gửi mặc định

    const mauSacNew = document.getElementById('mauSacNew').value;

    // Gửi yêu cầu thêm thương hiệu mới
    fetch('/mau-sac/them-nhanh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({tenMauSac: mauSacNew}) // Gửi tên thương hiệu mới
    })
        .then(response => {
            if (response.ok) {
                updateMauSac(); // Cập nhật danh sách thương hiệu trong combobox
                closeModal('colorPickerModal'); // Đóng modal
                document.getElementById('mauSacForm').reset();
                openModal('mauSacModal');
                showNotification("Thành Công!");
            } else {
                console.error('Error adding :', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
}

function updateMauSac() {
    fetch('/mau-sac') // Đường dẫn đến API để lấy danh sách kích thước
        .then(response => response.json())
        .then(data => {
            const listContainer = document.querySelector('.ms-list');
            listContainer.innerHTML = ''; // Xóa nội dung cũ

            data.forEach(mauSac => {
                const button = document.createElement('button');
                button.textContent = mauSac.tenMauSac;
                button.setAttribute('data-size', mauSac.id);
                button.style.backgroundColor = mauSac.tenMauSac;
                button.onclick = () => selectedColor(button);
                listContainer.appendChild(button);

            });
        })
        .catch(error => console.error('Error fetching sizes:', error));
}

//
function formatPrice(input) {
    // Xóa " VND" và dấu chấm để xử lý giá
    let value = input.value.replace(/ VND/g, '').replace(/\./g, '');
    const numericValue = parseInt(value, 10); // Chuyển đổi thành số nguyên

    if (!isNaN(numericValue)) {
        // Định dạng giá và thêm " VND" vào cuối
        input.value = numericValue.toLocaleString('vi-VN', {style: 'decimal', minimumFractionDigits: 0}) + ' VND';
    } else {
        input.value = '0 VND'; // Nếu không phải số, đặt về mặc định
    }
}

//Tự động tạo ra row trong table
function updateProductTable() {
    const tableBody = document.querySelector('table tbody');
    tableBody.innerHTML = ''; // Xóa các hàng hiện có

    const productName = document.getElementById('tenSanPham').value || 'Tên sản phẩm'; // Tên mặc định nếu không được nhập

    const productRows = {};

    selectedColors.forEach(color => {
        selectedSizes.forEach(size => {
            const rowKey = `${productName} [${size.name} - ${color.code}]`;
            if (!productRows[rowKey]) {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td><input type="checkbox" class="selectItem"></td>
                    <td>${tableBody.rows.length + 1}</td>
                    <td>
                        ${productName} [${size.name} - <span style="display:inline-block; width: 20px; height: 20px; background-color: ${color.code}; border: 1px solid #000;"></span>]
                    </td>
                    <td><input type="number" value="1" style="width: 50px;"></td>
                    <td><input type="text" class="price" value="0 VNĐ" style="width: 180px;" onblur="formatPrice(this)"></td>
                   <td class="action-buttons">
                        <div class="action-buttons-container">
                        <i class="fas fa-trash-alt" onclick="removeRow(this)"></i>
                    </div>
                        </td>
                    <td>
                        <div class="upload-button" onclick="triggerFileInput(this, '${rowKey}')"><i class="fas fa-plus"></i> Tải lên</div>
                        <input type="file" class="fileInput" style="display:none;" multiple onchange="handleFileSelect(this, '${rowKey}')">
                        <div class="uploaded-images"></div>
                    </td>
                `;
                productRows[rowKey] = row;
                tableBody.appendChild(row);
            }
        });
    });
}

function triggerFileInput(button, rowKey) {
    const fileInput = button.nextElementSibling;
    fileInput.click();
    fileInput.setAttribute('data-row-key', rowKey);
}
//Tải aảnh len
function handleFileSelect(inputElement, rowKey) {
    const files = inputElement.files;
    const uploadedImagesDiv = inputElement.closest('tr').querySelector('.uploaded-images');
    const existingImages = uploadedImagesDiv.querySelectorAll('div.image-container');

    // Kiểm tra nếu đã có 6 ảnh, không cho chọn thêm
    if (existingImages.length + files.length > 6) {
        alert('Bạn chỉ có thể chọn tối đa 6 ảnh.');
        inputElement.value = ''; // Xóa lựa chọn ảnh
        return;
    }

    const imageContainers = [];

    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const imageContainer = document.createElement('div');
        imageContainer.classList.add('image-container');
        imageContainer.style.width = '100px';
        imageContainer.style.height = '100px';
        imageContainer.style.position = 'relative';
        imageContainer.style.marginRight = '10px';
        imageContainer.style.marginBottom = '10px';
        imageContainer.style.display = 'inline-block';

        const img = document.createElement('img');
        img.src = URL.createObjectURL(file);
        img.style.maxWidth = '100%';
        img.style.maxHeight = '100%';
        imageContainer.appendChild(img);

        const overlay = document.createElement('div');
        overlay.classList.add('overlay');
        overlay.style.position = 'absolute';
        overlay.style.top = '0';
        overlay.style.left = '0';
        overlay.style.width = '100%';
        overlay.style.height = '100%';
        overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
        overlay.style.display = 'flex';
        overlay.style.justifyContent = 'center';
        overlay.style.alignItems = 'center';
        overlay.style.opacity = '0';
        overlay.style.transition = 'opacity 0.3s';

        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete-button');
        deleteButton.style.backgroundColor = 'red';
        deleteButton.style.color = 'white';
        deleteButton.style.border = 'none';
        deleteButton.style.padding = '5px 10px';
        deleteButton.style.borderRadius = '5px';
        deleteButton.style.cursor = 'pointer';
        deleteButton.innerHTML = '<i class="fas fa-trash"></i>';
        deleteButton.onclick = () => {
            imageContainer.remove();
            const uploadButton = inputElement.closest('td').querySelector('.upload-button');
            uploadButton.style.display = uploadedImagesDiv.querySelectorAll('div.image-container').length < 6 ? 'inline-block' : 'none';
        };

        const eyeIcon = document.createElement('i');
        eyeIcon.classList.add('fas', 'fa-eye');
        eyeIcon.style.color = 'white';  // Đặt màu icon mắt thành trắng
        eyeIcon.style.marginRight = '10px';

        // Thêm sự kiện click vào icon mắt để xem ảnh
        eyeIcon.onclick = () => {
            const modal = document.createElement('div');
            modal.style.position = 'fixed';
            modal.style.top = '0';
            modal.style.left = '0';
            modal.style.width = '100%';
            modal.style.height = '100%';
            modal.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
            modal.style.display = 'flex';
            modal.style.justifyContent = 'center';
            modal.style.alignItems = 'center';
            modal.style.zIndex = '1000';

            const fullSizeImg = document.createElement('img');
            fullSizeImg.src = img.src;
            fullSizeImg.style.maxWidth = '90%';
            fullSizeImg.style.maxHeight = '90%';
            modal.appendChild(fullSizeImg);

            // Thêm sự kiện để đóng modal khi nhấn vào
            modal.onclick = () => {
                modal.remove();
            };

            document.body.appendChild(modal);
        };

        overlay.appendChild(eyeIcon);
        overlay.appendChild(deleteButton);

        // Thêm sự kiện hover vào image container
        imageContainer.addEventListener('mouseover', () => {
            overlay.style.opacity = '1';
        });
        imageContainer.addEventListener('mouseout', () => {
            overlay.style.opacity = '0';
        });

        imageContainer.appendChild(overlay);
        imageContainers.push(imageContainer);
    }

    // Thêm các ảnh mới vào uploadedImagesDiv mà không xóa ảnh cũ
    imageContainers.forEach(container => {
        uploadedImagesDiv.appendChild(container);
    });

    // Ẩn nút tải lên khi đã có 6 ảnh
    const uploadButton = inputElement.closest('td').querySelector('.upload-button');
    uploadButton.style.display = existingImages.length + files.length >= 6 ? 'none' : 'inline-block';
}

function removeRow(button) {
    button.closest('tr').remove();
}