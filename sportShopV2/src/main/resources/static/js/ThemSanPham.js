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
    fetch('/thuong-hieu/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
        .then(response => response.json())
        .then(data => {
            const brandSelect = document.getElementById('idThuongHieu');
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
    fetch('/the-loai/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
        .then(response => response.json())
        .then(data => {
            const brandSelect = document.getElementById('category');
            brandSelect.innerHTML = ''; // Xóa các tùy chọn hiện tại
            brandSelect.innerHTML += '<option value="" disabled selected hidden>Chọn thể loại</option>'; // Thêm tùy chọn mặc định
            console.log(data);
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
    fetch('/chat-lieu/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
        .then(response => response.json())
        .then(data => {
            const brandSelect = document.getElementById('idChatLieu');
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
    fetch('/co-giay/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
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
    fetch('/de-giay/combobox')  // Đường dẫn API để lấy danh sách thương hiệu
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

function showToast(message) {
    const toast = document.getElementById('toast');
    const toastMessage = document.getElementById('toast-message');
    const progressBar = document.getElementById('progress-bar');

    // Kiểm tra xem các phần tử có tồn tại không
    if (!toast || !toastMessage || !progressBar) {
        console.error("Một trong các phần tử không tồn tại.");
        return; // Dừng hàm nếu không tìm thấy phần tử
    }

    // Cập nhật nội dung thông báo
    toastMessage.textContent = message;

    // Hiển thị thông báo
    toast.classList.remove('hide');
    toast.classList.add('show');

    // Đặt chiều rộng thanh tiến trình về 100%
    progressBar.style.width = '100%';

    // Sau 3 giây, thay đổi chiều rộng thanh tiến trình về 0
    setTimeout(() => {
        progressBar.style.width = '0'; // Đặt lại chiều rộng thanh tiến trình về 0
    }, 2000); // Sau 3 giây

    // Ẩn toast sau 3.3 giây
    setTimeout(() => {
        toast.classList.remove('show');
        toast.classList.add('hide');
    }, 2000); // Thêm chút thời gian cho hiệu ứng ẩn
}

let selectedColors = [];
let selectedSizes = [];
let selectedColorsUpdate = [];
let selectedSizesUpdate = [];// Mảng lưu trữ kích cỡ đã chọn
let selectedSPCTsUpdate = [];

function loadSelectedValues() {
    // Lấy các kích cỡ đã chọn từ HTML và lưu vào mảng
    document.querySelectorAll('.selected-size').forEach(sizeElement => {
        const sizeId = sizeElement.getAttribute('data-id'); // Lấy id của kích cỡ từ thuộc tính data-id
        const sizeName = sizeElement.innerText; // Lấy tên kích cỡ từ nội dung
        selectedSizesUpdate.push({id: sizeId, name: sizeName}); // Lưu cả id và tên vào mảng
    });

    // Lấy các màu sắc đã chọn từ HTML và lưu vào mảng
    document.querySelectorAll('.selected-color').forEach(colorElement => {
        const colorId = colorElement.getAttribute('data-id'); // Lấy id của màu sắc từ thuộc tính data-id
        const colorCode = colorElement.style.backgroundColor || colorElement.getAttribute('data-color'); // Lấy mã màu từ backgroundColor hoặc data-color
        selectedColorsUpdate.push({id: colorId, code: colorCode}); // Lưu cả id và mã màu vào mảng
    });
    // Lấy các idSPCT từ mỗi dòng trong bảng và lưu vào mảng
    document.querySelectorAll('tr[data-id]').forEach(row => {
        const spctId = row.getAttribute('data-id'); // Lấy giá trị id từ thuộc tính data-id của dòng
        if (spctId) {
            selectedSPCTsUpdate.push({id: spctId}); // Lưu idSPCT vào mảng
        }
    });
    console.log('Sizes in database:', selectedSizesUpdate);
    console.log('Colors in database:', selectedColorsUpdate);
    console.log('idSPCT in database:', selectedSPCTsUpdate);
}


// Gọi hàm loadSelectedValues khi trang được tải
document.addEventListener('DOMContentLoaded', loadSelectedValues);

//Chọn size

function selectSize(button) {
    const size = button.textContent; // Lấy tên kích cỡ
    const sizeId = button.getAttribute('data-size'); // Lấy ID kích cỡ

    const sizeObj = {id: sizeId, name: size}; // Tạo đối tượng kích cỡ

    if (selectedSizes.some(s => s.id === sizeId)) {
        // Nếu kích cỡ đã được chọn, xóa nó khỏi mảng
        selectedSizes = selectedSizes.filter(s => s.id !== sizeId);
        button.classList.remove('active');
    } else {
        // Nếu chưa chọn, thêm vào mảng
        selectedSizes.push(sizeObj);
        button.classList.add('active');
    }

    updateProductTable(); // Cập nhật bảng sản phẩm
    updateSelectedSizes(); // Cập nhật giao diện hiển thị các kích cỡ đã chọn

    console.log('Kích cỡ đã chọn:', selectedSizes); // In ra mảng chứa kích cỡ đã chọn
}

function updateSelectedSizes() {
    const selectedSizesElement = document.getElementById('selected-sizes');
    selectedSizesElement.innerHTML = ''; // Xóa tất cả phần tử hiện tại trong selected-sizes

    // Duyệt qua các kích cỡ đã chọn và hiển thị chúng
    selectedSizes.forEach(size => {
        // Kiểm tra nếu phần tử đã được tạo cho size này chưa
        let span = document.getElementById(`size-${size.id}`);
        if (!span) {
            // Nếu chưa, tạo mới
            span = document.createElement('span');
            span.className = 'selected-size';
            span.id = `size-${size.id}`; // Gán id duy nhất cho từng kích cỡ
            span.textContent = size.name; // Hiển thị tên kích cỡ
            span.onclick = () => removeSize(size.id); // Xử lý khi bỏ chọn kích cỡ
            selectedSizesElement.appendChild(span);
        }
    });
}

function removeSize(sizeId) {
    // Xóa kích cỡ khỏi mảng selectedSizes
    selectedSizes = selectedSizes.filter(s => s.id !== sizeId);

    // Cập nhật lại giao diện
    updateSelectedSizes(); // Cập nhật hiển thị sau khi bỏ chọn
    updateProductTable();
}

function confirmSelectedSizes() {
    updateSelectedSizes();
    closeModal('sizeModal');

}

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

function rgbToHex(rgb) {
    // Loại bỏ "rgb(" và ")" rồi tách các giá trị r, g, b
    const rgbValues = rgb.match(/\d+/g);
    if (!rgbValues || rgbValues.length !== 3) return null;

    // Chuyển đổi từng giá trị thành mã Hex
    const r = parseInt(rgbValues[0]).toString(16).padStart(2, '0');
    const g = parseInt(rgbValues[1]).toString(16).padStart(2, '0');
    const b = parseInt(rgbValues[2]).toString(16).padStart(2, '0');

    // Trả về mã Hex đầy đủ
    return `#${r}${g}${b}`;
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
                updateSizeList();
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
        input.value = numericValue.toLocaleString('vi-VN', {style: 'decimal', minimumFractionDigits: 0}) + ' đ';
    } else {
        input.value = '0 VND'; // Nếu không phải số, đặt về mặc định
    }
}

function getNumericPrice(input) {
    // Xóa " VND" và dấu chấm để lấy giá trị số
    let value = input.replace(/ VND/g, '').replace(/\./g, '');
    // Chuyển đổi sang số nguyên
    let numericValue = parseInt(value, 10);
    return isNaN(numericValue) ? 0 : numericValue; // Trả về giá trị số hoặc 0 nếu không hợp lệ
}

//Tự động tạo ra row trong table
function updateProductTable() {
    const tableBody = document.querySelector('table tbody');
    tableBody.innerHTML = ''; // Xóa các hàng hiện có
    const productName = document.getElementById('tenSanPham').value || 'Tên sản phẩm'; // Tên mặc định nếu không được nhập
    const productRows = {}; // Lưu trữ các hàng theo key (tên sản phẩm và màu sắc)

    // Lấy tất cả các size và màu (gồm cả danh sách hiện tại và trước đó)
    const allSizes = [...new Set([...selectedSizes, ...selectedSizesUpdate])];
    const allColors = [...new Set([...selectedColors, ...selectedColorsUpdate])];

    // 1. Trường hợp chỉ chọn size
    if (selectedSizes.length > 0 && selectedColors.length === 0) {
        selectedSizes.forEach(size => {
            selectedColorsUpdate.forEach(color => {
                createRow(size, color, productName, productRows, tableBody);
            });
        });
    }

    // 2. Trường hợp chỉ chọn màu
    if (selectedColors.length > 0 && selectedSizes.length === 0) {
        selectedColors.forEach(color => {
            selectedSizesUpdate.forEach(size => {
                createRow(size, color, productName, productRows, tableBody);
            });
        });
    }

    // 3. Trường hợp chọn cả size và màu
    if (selectedSizes.length > 0 && selectedColors.length > 0) {
        // Kết hợp size với màu hiện tại
        selectedSizes.forEach(size => {
            selectedColors.forEach(color => {
                createRow(size, color, productName, productRows, tableBody);
            });
        });

        // Kết hợp size với màu đã chọn trước đó
        selectedSizes.forEach(size => {
            selectedColorsUpdate.forEach(color => {
                createRow(size, color, productName, productRows, tableBody);
            });
        });

        // Kết hợp màu với size đã chọn trước đó
        selectedColors.forEach(color => {
            selectedSizesUpdate.forEach(size => {
                createRow(size, color, productName, productRows, tableBody);
            });
        });
    }

}

function createRow(size, color, productName, productRows, tableBody) {
    const rowKey = `${productName} [${size.name} - ${color.code}]`; // Khóa duy nhất cho sản phẩm

    // Kiểm tra xem dòng này đã được tạo chưa
    if (productRows[rowKey]) {
        return; // Nếu đã có, bỏ qua việc tạo thêm dòng mới
    }

    const row = document.createElement('tr');
    row.innerHTML = `
        <td><input type="checkbox" class="selectItem"></td>
        <td>${tableBody.rows.length + 1}</td>
        <td data-size-id="${size.id}" data-color-id="${color.id}">
            ${productName} [${size.name} - 
            <span style="display:inline-block; width: 20px; height: 20px; background-color: ${color.code}; border: 1px solid #000;"></span> 
            <span class="color-name" id="color-name-${color.id}">${color.code}</span>]
        </td>
        <td><input type="number" value="1" style="width: 50px;" class="quantity-input" min="0"></td>
        <td><input type="text" class="price" value="0 đ" style="width: 180px;" ></td>
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

    // Lấy phần tử cột màu để gọi hàm lấy tên màu
    const colorNameElement = row.querySelector(`#color-name-${color.id}`);
    getColorName(color.code, colorNameElement);

    // Ràng buộc sự kiện để ngăn giá trị âm
    const quantityInput = row.querySelector('.quantity-input');
    quantityInput.addEventListener('input', () => {
        if (quantityInput.value < 0) {
            quantityInput.value = 0; // Không cho phép số âm
        }
    });

    const priceInput = row.querySelector('.price');
    priceInput.addEventListener('input', () => {
        let value = parseInt(priceInput.value.replace(/\D/g, ''), 10) || 0; // Lấy số từ chuỗi
        if (value < 0) {
            value = 0; // Nếu giá trị âm, đặt lại về 0
        }
        priceInput.value = formatCurrency(value); // Định dạng giá trị
    });


}

document.getElementById('selectAll').addEventListener('change', function () {
    const checkboxes = document.querySelectorAll('.selectItem');
    checkboxes.forEach(checkbox => {
        checkbox.checked = this.checked; // Chọn hoặc bỏ chọn tất cả các checkbox
    });
});

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
        imageContainer.dataset.fileName = file.name;
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

async function themSPCT() {
    const sanPhamNew = document.getElementById('tenSanPham').value;
    const moTa = document.getElementById('description').value;
    const gioiTinh = document.getElementById('gender').value;
    const idThuongHieu = document.getElementById('idThuongHieu').value;
    const idTheLoai = document.getElementById('category').value;
    const idChatLieu = document.getElementById('idChatLieu').value;
    const idDeGiay = document.getElementById('sole').value;
    const idCoGiay = document.getElementById('collar').value;

    resetBorders();

    // Kiểm tra validate cho các trường
    if (!sanPhamNew.trim()) {
        showToast('Tên sản phẩm không được để trống!');
        document.getElementById('tenSanPham').style.border = '1px solid red'; // Bo viền đỏ
        return;
    }
    const sanPhamAll = await fetch(`san-pham/list`).then(handleResponse);
    console.log(sanPhamAll);
    const isDuplicate = sanPhamAll.some(product => product.tenSanPham.trim().toLowerCase() === sanPhamNew.toLowerCase());
    if (isDuplicate) {
        showToast('Tên sản phẩm đã tồn tại!');
        document.getElementById('tenSanPham').style.border = '1px solid red'; // Bỏ viền đỏ
        return;
    }
    if (!moTa.trim()) {
        showToast('Mô tả không được để trống!');
        document.getElementById('description').style.border = '1px solid red'; // Bo viền đỏ
        return;
    }
    if (!gioiTinh) {
        showToast('Vui lòng chọn giới tính!');
        document.getElementById('gender').style.border = '1px solid red'; // Bo viền đỏ
        return;
    }
    if (!idThuongHieu) {
        showToast('Vui lòng chọn thương hiệu!');
        document.getElementById('idThuongHieu').style.border = '1px solid red'; // Bo viền đỏ
        return;
    }
    if (!idTheLoai) {
        showToast('Vui lòng chọn thể loại!');
        document.getElementById('category').style.border = '1px solid red'; // Bo viền đỏ
        return;
    }
    if (!idChatLieu) {
        showToast('Vui lòng chọn chất liệu!');
        document.getElementById('idChatLieu').style.border = '1px solid red'; // Bo viền đỏ
        return;
    }
    if (!idDeGiay) {
        showToast('Vui lòng chọn đế giày!');
        document.getElementById('sole').style.border = '1px solid red'; // Bo viền đỏ
        return;
    }
    if (!idCoGiay) {
        showToast('Vui lòng chọn cổ giày!');
        document.getElementById('collar').style.border = '1px solid red'; // Bo viền đỏ
        return;
    }

    // Kiểm tra thông tin chi tiết sản phẩm
    const productDetails = getInfoTable();
    if (productDetails.length === 0) {
        showToast('Vui lòng chọn màu sắc và kích thước!');
        return;
    }
    const uploadedImageElements = document.querySelectorAll('.uploaded-images');
    let hasImages = false;

    uploadedImageElements.forEach(uploadedImagesDiv => {
        const imageContainers = uploadedImagesDiv.querySelectorAll('.image-container');
        if (imageContainers.length > 0) {
            hasImages = true; // Nếu có ảnh, gán true
        }
    });

    if (!hasImages) {
        showToast('Mỗi sản phẩm phải có ít nhất một ảnh!');
        return; // Dừng thực hiện nếu không có ảnh
    }
    try {
        // Thêm sản phẩm chính
        const sanPhamResponse = await fetch('/san-pham/them-san-pham', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                tenSanPham: sanPhamNew
            })
        });

        if (!sanPhamResponse.ok) {
            const errorText = await sanPhamResponse.text();
            throw new Error(`Lỗi khi thêm sản phẩm: ${errorText}`);
        }

        const sanPhamData = await sanPhamResponse.json();
        const sanPhamId = sanPhamData.id; // Lấy ID sản phẩm mới tạo

        // Lấy thông tin chi tiết từ bảng
        const productDetails = getInfoTable();
        console.log('Thông tin chi tiết sản phẩm:', productDetails);
        // Tạo mảng các yêu cầu thêm chi tiết sản phẩm
        const chiTietPromises = productDetails.map(detail => {
            const trangThai = detail.quantity === 0 ? "Không hoạt động" : "Đang hoạt động"; // Kiểm tra số lượng để đặt trạng thái
            return fetch('san-pham-chi-tiet/them-san-pham-chi-tiet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    idSanPham: sanPhamId, // ID sản phẩm đã thêm
                    idKichThuoc: detail.sizeId, // ID kích cỡ
                    idMauSac: detail.colorId, // ID màu sắc
                    idThuongHieu: idThuongHieu,
                    idDeGiay: idDeGiay,
                    idTheLoai: idTheLoai,
                    idCoGiay: idCoGiay,
                    idChatLieu: idChatLieu,
                    moTa: moTa,
                    gioiTinh: gioiTinh,
                    soLuong: detail.quantity, // Số lượng
                    gia: detail.price, // Giá tiền
                    trangThai: trangThai
                })
            });
        });

        // Chờ tất cả các yêu cầu chi tiết sản phẩm hoàn thành
        const chiTietResponses = await Promise.all(chiTietPromises);

        // Kiểm tra phản hồi
        const idSPCTs = [];
        for (let response of chiTietResponses) {
            if (!response.ok) {
                throw new Error('Lỗi khi thêm chi tiết sản phẩm');
            }
            const detailData = await response.json();
            idSPCTs.push(detailData.id); // Giả sử response trả về id của sản phẩm chi tiết
        }

        // Lưu ảnh cho sản phẩm chi tiết
        await saveProductImages(idSPCTs);
        console.log('Thêm sản phẩm và chi tiết thành công!');
        window.location.href = '/san-pham';
        localStorage.setItem('notification', showNotification("Thêm Thành Công"));
    } catch (error) {
        console.log('Có lỗi xảy ra: ' + error.message);
    }

}

async function capNhatSPCT() {
    const sanPhamNew = document.getElementById('tenSanPham').value;
    const moTa = document.getElementById('description').value;
    const gioiTinh = document.getElementById('gender').value;
    const idThuongHieu = document.getElementById('idThuongHieu').value;
    const idTheLoai = document.getElementById('category').value;
    const idChatLieu = document.getElementById('idChatLieu').value;
    const idDeGiay = document.getElementById('sole').value;
    const idCoGiay = document.getElementById('collar').value;
    resetBorders();

    // Kiểm tra validate cho các trường
    if (!sanPhamNew.trim()) {
        showToast('Tên sản phẩm không được để trống!');
        document.getElementById('tenSanPham').style.border = '1px solid red';
        return;
    }
    if (!moTa.trim()) {
        showToast('Mô tả không được để trống!');
        document.getElementById('description').style.border = '1px solid red';
        return;
    }

    const path = window.location.pathname;
    const segments = path.split("/");
    const sanPhamId = segments[segments.length - 1]; // Lấy idSP từ URL

    try {
        // Lấy thông tin sản phẩm chi tiết hiện tại từ server
        const spctInfoPromises = selectedSPCTsUpdate.map(detail => {
            return fetch(`/san-pham-chi-tiet/thong-tin-spct/${detail.id}`)
                .then(response => response.json())
                .then(data => {
                    return {
                        id: detail.id,
                        idSanPham: sanPhamId,
                        idKichThuoc: data.idKichThuoc,
                        idMauSac: data.idMauSac,
                        idThuongHieu: data.idThuongHieu,
                        idDeGiay: data.idDeGiay,
                        idTheLoai: data.idTheLoai,
                        idCoGiay: data.idCoGiay,
                        idChatLieu: data.idChatLieu,
                        moTa: data.moTa,
                        gioiTinh: data.gioiTinh,
                        soLuong: data.soLuong,
                        gia: data.gia,
                        trangThai: data.trangThai
                    };
                });
        });

        const spctInfoList = await Promise.all(spctInfoPromises);
        // Cập nhật `soLuong` và `gia` từ bảng HTML
        spctInfoList.forEach(detail => {
            const row = document.querySelector(`tr[data-id="${detail.id}"]`); // Lấy hàng tương ứng theo ID
            const giaInput = row.querySelector('#gia'); // Lấy ô input giá
            const soLuongInput = row.querySelector('#soLuong'); // Lấy ô input số lượng
            const giaThuc = parseCurrency(giaInput.value);
            detail.gia = giaThuc; // Cập nhật giá từ ô input
            detail.soLuong = parseInt(soLuongInput.value, 10); // Cập nhật số lượng từ ô input

        });

        console.log(spctInfoList);
        // Cập nhật sản phẩm chính
        const sanPhamResponse = await fetch(`/san-pham/update/${sanPhamId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                tenSanPham: sanPhamNew
            })
        });

        if (!sanPhamResponse.ok) {
            const errorText = await sanPhamResponse.text();
            throw new Error(`Lỗi khi cập nhật sản phẩm: ${errorText}`);
        }

        // Cập nhật thông tin chi tiết của từng SPCT từ danh sách spctInfoList
        const updateSPCTPromises  = spctInfoList.map(detail => {
            const trangThai = detail.soLuong === 0 ? "Không hoạt động" : "Đang hoạt động";
            return fetch(`/san-pham-chi-tiet/update/${detail.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },

                body: JSON.stringify({
                    idSanPham: sanPhamId,
                    idKichThuoc: detail.idKichThuoc,
                    idMauSac: detail.idMauSac,
                    idThuongHieu: idThuongHieu,
                    idDeGiay: idDeGiay,
                    idTheLoai: idTheLoai,
                    idCoGiay: idCoGiay,
                    idChatLieu: idChatLieu,
                    moTa: moTa,
                    gioiTinh: gioiTinh,
                    soLuong: detail.soLuong,
                    gia: detail.gia,
                    trangThai: trangThai
                })
            });
        });

        const updateResponses = await Promise.all(updateSPCTPromises);
        updateResponses.forEach((res, index) => {
            if (!res.ok) {
                console.error(`Lỗi cập nhật SPCT ID ${spctInfoList[index].id}`);
            }
        });
        const productDetails = getInfoTable(); // Giả sử hàm này trả về mảng thông tin

        // Tạo các sản phẩm chi tiết mới
        const addSPCTPromises = productDetails.map(detail => {
            const trangThai = detail.quantity === 0 ? "Không hoạt động" : "Đang hoạt động"; // Kiểm tra số lượng để đặt trạng thái

            return fetch(`/san-pham-chi-tiet/them-san-pham-chi-tiet`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    idSanPham: sanPhamId,
                    idKichThuoc: detail.sizeId,
                    idMauSac: detail.colorId,
                    idThuongHieu,
                    idDeGiay,
                    idTheLoai,
                    idCoGiay,
                    idChatLieu,
                    moTa,
                    gioiTinh,
                    soLuong: detail.quantity,
                    gia: detail.price,
                    trangThai: trangThai // Thêm trạng thái vào body
                }),
            });
        });


        const addResponses = await Promise.all(addSPCTPromises);
        const newIds = [];
        for (let res of addResponses) {
            if (!res.ok) {
                throw new Error('Lỗi khi thêm sản phẩm chi tiết!');
            }
            const data = await res.json();
            newIds.push(data.id); // Lưu ID của sản phẩm mới
        }
        if (newIds.length > 0) {
            await saveProductImages(newIds);
        }
        window.location.href = '/san-pham';
        // Hiển thị thông báo thành công
        localStorage.setItem('notification', showNotification("Cập nhật Thành Công"));

    } catch (error) {
        console.log('Có lỗi xảy ra: ' + error.message);
    }
}

function handleResponse(response) {
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status} - ${response.statusText}`);
    }
    return response.json();
}

function resetBorders() {
    const inputFields = [
        'tenSanPham', 'description', 'gender', 'idThuongHieu', 'category',
        'idChatLieu', 'sole', 'collar'
    ];
    inputFields.forEach(id => {
        document.getElementById(id).style.border = ''; // Reset viền về mặc định
    });
}

async function saveProductImages(idSPCTs) {
    const uploadedImageElements = document.querySelectorAll('.uploaded-images');

    const images = [];
    let allImagesUploaded = true; // Biến kiểm tra nếu tất cả sản phẩm đều có ảnh

    uploadedImageElements.forEach((uploadedImagesDiv, index) => {
        const imageContainers = uploadedImagesDiv.querySelectorAll('.image-container');
        if (imageContainers.length === 0) {
            // Nếu không có ảnh cho sản phẩm này, đánh dấu là chưa có ảnh
            allImagesUploaded = false;
        }

        imageContainers.forEach(container => {
            const imgTag = container.querySelector('img');
            const fileName = container.dataset.fileName; // Lấy tên file từ data attribute

            images.push({
                tenAnh: fileName, // Lưu tên file
                trangThai: 'Đang hoạt động', // Hoặc trạng thái khác tùy ý
                idSPCT: idSPCTs[index], // Liên kết đến ID sản phẩm chi tiết
            });
        });
    });

    // Kiểm tra nếu không có ảnh nào được tải lên
    if (!allImagesUploaded) {
        showToast('Mỗi sản phẩm phải có ít nhất một ảnh!');
        return; // Dừng hàm nếu không có ảnh
    }

    // Gửi ảnh đến server
    const response = await fetch('/anh-san-pham/upload', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(images)
    });

    if (!response.ok) {
        throw new Error('Lỗi khi lưu ảnh');
    }
}

function getInfoTable() {
    const details = [];
    const rows = document.querySelectorAll('table tbody tr');

    console.log('Số hàng trong bảng:', rows.length);

    rows.forEach(row => {
        // Lấy số lượng và giá tiền
        const quantity = row.querySelector('input[type="number"]').value;
        const priceText = row.querySelector('.price').value;
        const priceNumber = getNumericPrice(priceText);
        // Chuyển đổi giá tiền sang số

        // Lấy ID từ thuộc tính dữ liệu
        const sizeId = row.cells[2].getAttribute('data-size-id');
        const colorId = row.cells[2].getAttribute('data-color-id');

        // Kiểm tra xem ID có hợp lệ không
        if (sizeId && colorId) {
            details.push({
                sizeId: sizeId, // ID kích cỡ
                colorId: colorId, // ID màu sắc
                quantity: quantity,
                price: priceNumber
            });
        } else {
            showToast('Không tìm thấy ID kích cỡ hoặc màu sắc.');
        }
    });

    console.log('Chi tiết sản phẩm:', details);
    return details;
}

function applyQuantityAndPrice() {
    const soLuongChung = document.getElementById('soLuongChung').value.trim();
    const giaChung = parseCurrency(document.getElementById('giaChung').value.trim());

    if (!soLuongChung || !giaChung) {
        alert('Vui lòng nhập đầy đủ số lượng và giá!');
        return;
    }

    const selectedRows = document.querySelectorAll('table tr .selectItem:checked');

    if (selectedRows.length === 0) {
        showToast('Vui lòng chọn ít nhất một sản phẩm!');
        return;
    }

    selectedRows.forEach(checkbox => {
        const row = checkbox.closest('tr');
        const quantityInput = row.querySelector('input[type="number"]');
        const priceInput = row.querySelector('.price');

        // Cập nhật số lượng và giá cho hàng được chọn
        quantityInput.value = soLuongChung;
        priceInput.value = formatCurrency(giaChung);
    });

    // Đóng modal sau khi áp dụng
    closeModal('slAndGia');
}

/*function formatCurrency(value) {
    const number = parseInt(value.replace(/\D/g, ''), 10);
    return number.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}*/


function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(amount);
}

function parseCurrency(currencyStr) {
    // Kiểm tra đầu vào
    if (typeof currencyStr !== 'string') {
        console.error("Invalid input: currencyStr must be a string");
        return NaN;
    }

    // Loại bỏ các ký tự không phải số, dấu chấm, dấu phẩy hoặc dấu âm
    const sanitizedStr = currencyStr.replace(/[^\d.,-]/g, '');

    // Thay đổi để xử lý định dạng `vi-VN`
    let numberStr;
    if (sanitizedStr.includes(',') && sanitizedStr.includes('.')) {
        // Nếu chuỗi chứa cả ',' và '.', kiểm tra thứ tự
        if (sanitizedStr.lastIndexOf('.') > sanitizedStr.lastIndexOf(',')) {
            // Chuẩn Mỹ: '.' là thập phân
            numberStr = sanitizedStr.replace(/,/g, '');
        } else {
            // Chuẩn Châu Âu/Việt Nam: ',' là thập phân
            numberStr = sanitizedStr.replace(/\./g, '').replace(',', '.');
        }
    } else if (sanitizedStr.includes('.')) {
        // Nếu chỉ có '.', xem như chuẩn Việt Nam
        numberStr = sanitizedStr.replace(/\./g, '');
    } else if (sanitizedStr.includes(',')) {
        // Nếu chỉ có ',', xem như chuẩn Châu Âu
        numberStr = sanitizedStr.replace(',', '.');
    } else {
        // Không có dấu phân cách nào
        numberStr = sanitizedStr;
    }

    // Xử lý dấu âm
    if (numberStr.includes('-') && numberStr.indexOf('-') !== 0) {
        console.error("Invalid input: '-' must be at the start of the number");
        return NaN;
    }

    // Chuyển đổi sang số thực
    return parseFloat(numberStr);
}



document.querySelectorAll('.price').forEach(function (el) {
    el.textContent = formatCurrency(el.textContent);
});
document.addEventListener('DOMContentLoaded', function () {
    // Lấy tất cả các phần tử chứa mã màu
    document.querySelectorAll('.color-name').forEach(function (el) {
        const colorHex = el.innerText.trim(); // Lấy mã hex từ nội dung của phần tử span
        if (colorHex.startsWith('#')) { // Kiểm tra mã có hợp lệ không
            getColorName(colorHex, el); // Gọi hàm để lấy tên màu từ API
        } else {
            el.textContent = "Tên màu không hợp lệ"; // Nếu mã không hợp lệ, hiển thị thông báo lỗi
        }
    });
    const priceElements = document.querySelectorAll('.price');

    priceElements.forEach(function (priceElement) {
        let price = parseFloat(priceElement.value);
        if (!isNaN(price)) {
            priceElement.value = formatCurrency(price);
        }
    });
});

// Hàm gọi API để lấy tên màu
function getColorName(colorCode, el) {
    // Nếu colorCode là RGB, chuyển sang Hex
    const hexCode = colorCode.startsWith('#') ? colorCode : rgbToHex(colorCode);

    if (!hexCode) {
        el.textContent = "Không xác định được mã màu";
        return;
    }

    fetch(`https://www.thecolorapi.com/id?hex=${hexCode.substring(1)}`)
        .then(response => response.json())
        .then(data => {
            el.textContent = data.name.value; // Cập nhật tên màu vào phần tử span
        })
        .catch(error => {
            console.error("Lỗi khi gọi API lấy tên màu:", error);
            el.textContent = "Không tìm thấy tên màu"; // Hiển thị lỗi nếu không lấy được tên màu
        });
}

//cập nhật màu sắc và size để updateSPct
