let invoiceCount = 0;
let maxInvoices = 5;
let invoiceData = {};

function addTab() {
    if (invoiceCount >= maxInvoices) {
        showToast("Số lượng hóa đơn chờ đã đạt tối đa.");
        return;
    }

    invoiceCount++;
    const tabContainer = document.getElementById('tabContainer');

    // Tạo dữ liệu cho hóa đơn mới
    invoiceData[invoiceCount] = {
        products: `Sản phẩm của Hóa Đơn ${invoiceCount}`,
        customer: `Khách hàng của Hóa Đơn ${invoiceCount}`,
        deliveryOption: 'Tại Quầy', // Mặc định là Tại Quầy
        customerInfo: {
            name: '',
            phone: '',
            email: '',
            code: ''
        }
    };

    // Tạo thẻ mới cho hóa đơn
    const newTab = document.createElement('div');
    newTab.classList.add('tab-item', 'active');
    newTab.innerHTML = `Hóa Đơn ${invoiceCount} <span class="close-btn" onclick="removeTab(this, event)">&times;</span>`;
    newTab.setAttribute("onclick", `selectTab(${invoiceCount})`);

    // Xóa active của các tab khác
    const tabs = document.querySelectorAll('.tab-item');
    tabs.forEach(tab => tab.classList.remove('active'));

    // Thêm thẻ mới vào DOM
    tabContainer.insertBefore(newTab, tabContainer.lastElementChild);

    // Hiển thị chi tiết hóa đơn
    selectTab(invoiceCount);
}

function selectTab(invoiceNumber) {
    const tabs = document.querySelectorAll('.tab-item');
    tabs.forEach(tab => tab.classList.remove('active'));

    const selectedTab = document.querySelector(`.tab-item:nth-child(${invoiceNumber})`);
    if (selectedTab) selectedTab.classList.add('active');

    // Cập nhật danh sách sản phẩm
    document.getElementById('productList').textContent = invoiceData[invoiceNumber].products;

    // Cập nhật thông tin khách hàng
    const customer = invoiceData[invoiceNumber].customerInfo;
    document.getElementById('customerInfo').textContent = `Khách hàng: ${customer.name || 'Chưa có dữ liệu'}`;

    // Cập nhật phương thức giao hàng
    document.querySelector('input[name="deliveryOption"][id="atStore"]').checked = (invoiceData[invoiceNumber].deliveryOption === 'Tại Quầy');
    document.querySelector('input[name="deliveryOption"][id="delivery"]').checked = (invoiceData[invoiceNumber].deliveryOption === 'Chuyển Phát');

    // Cập nhật thông tin khách hàng trong các trường nhập liệu
    document.getElementById('customerName').value = customer.name || '';
    document.getElementById('phone').value = customer.phone || '';
    document.getElementById('email').value = customer.email || '';
    document.getElementById('customerCode').value = customer.code || '';
}

function updateCustomerInfo(invoiceNumber) {
    const customerName = document.getElementById('customerName').value;
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const customerCode = document.getElementById('customerCode').value;

    invoiceData[invoiceNumber].customerInfo = {
        name: customerName,
        phone: phone,
        email: email,
        code: customerCode
    };
}

function updateDeliveryOption(invoiceNumber) {
    const deliveryOptions = document.querySelectorAll('input[name="deliveryOption"]:checked');
    const selectedOption = deliveryOptions[0].id;

    // Cập nhật phương thức giao hàng
    if (invoiceData[invoiceNumber]) {
        invoiceData[invoiceNumber].deliveryOption = selectedOption === 'atStore' ? 'Tại Quầy' : 'Chuyển Phát';
    } else {
        console.error(`invoiceData[${invoiceNumber}] is undefined`);
    }

    // Hiển thị hoặc ẩn phần địa chỉ
    const deliveryAddressDiv = document.getElementById('deliveryAddress');
    if (selectedOption === 'delivery') {
        deliveryAddressDiv.style.display = 'block'; // Hiển thị phần địa chỉ
    } else {
        deliveryAddressDiv.style.display = 'none'; // Ẩn phần địa chỉ
    }
    $(document).ready(function () {
        //Lấy tỉnh thành
        $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
            if (data_tinh.error == 0) {
                $.each(data_tinh.data, function (key_tinh, val_tinh) {
                    $("#tinh").append('<option value="' + val_tinh.id + '">' + val_tinh.full_name + '</option>');
                });
                $("#tinh").change(function (e) {
                    var idtinh = $(this).val();
                    //Lấy quận huyện
                    $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                        if (data_quan.error == 0) {
                            $("#quan").html('<option value="0">Quận Huyện</option>');
                            $("#phuong").html('<option value="0">Phường Xã</option>');
                            $.each(data_quan.data, function (key_quan, val_quan) {
                                $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                            });
                            //Lấy phường xã
                            $("#quan").change(function (e) {
                                var idquan = $(this).val();
                                $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                                    if (data_phuong.error == 0) {
                                        $("#phuong").html('<option value="0">Phường Xã</option>');
                                        $.each(data_phuong.data, function (key_phuong, val_phuong) {
                                            $("#phuong").append('<option value="' + val_phuong.id + '">' + val_phuong.full_name + '</option>');

                                        });
                                    }
                                });
                            });

                        }
                    });
                });

            }
        });

    });
}

function removeTab(element, event) {
    event.stopPropagation();
    const tab = element.parentElement;
    const tabContainer = document.getElementById('tabContainer');
    const invoiceNumber = Array.from(tabContainer.children).indexOf(tab);

    delete invoiceData[invoiceNumber + 1];
    tabContainer.removeChild(tab);
    invoiceCount--;

    const tabs = document.querySelectorAll('.tab-item');
    if (tabs.length > 0) {
        const lastTab = tabs[tabs.length - 1];
        lastTab.classList.add('active');
        const lastInvoiceNumber = Array.from(tabContainer.children).indexOf(lastTab);
        selectTab(lastInvoiceNumber + 1);
    } else {
        document.getElementById('productList').textContent = "Chưa có dữ liệu.";
    }
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
    }, 3000); // Sau 3 giây

    // Ẩn toast sau 3.3 giây
    setTimeout(() => {
        toast.classList.remove('show');
        toast.classList.add('hide');
    }, 3300); // Thêm chút thời gian cho hiệu ứng ẩn
}


const inputField = document.getElementById('sanPhamChiTiet');
const productList = document.getElementById('product-list');
const productRows = productList.getElementsByTagName('tr');
const selectedProductsTable = document.getElementById('selected-products');
const searchBar = document.querySelector('.search-bar');

inputField.addEventListener('focus', function () {
    productList.style.display = 'block'; // Hiển thị danh sách sản phẩm
    if (productRows.length === 0) {
        document.getElementById('productList').style.display = 'block'; // Hiện thông báo "Chưa có dữ liệu" nếu không có sản phẩm
    } else {
        document.getElementById('productList').style.display = 'none'; // Ẩn thông báo nếu có sản phẩm
    }
});

// Giấu danh sách sản phẩm nếu người dùng nhấp ra ngoài ô input
document.addEventListener('click', function (event) {
    if (!inputField.contains(event.target) && !productList.contains(event.target)) {
        productList.style.display = 'none'; // Ẩn danh sách sản phẩm
    }
});

// Hàm tìm kiếm sản phẩm
inputField.addEventListener('input', function () {
    const searchTerm = inputField.value.toLowerCase(); // Lấy giá trị nhập vào và chuyển thành chữ thường
    for (let i = 0; i < productRows.length; i++) {
        const row = productRows[i];
        const productName = row.textContent.toLowerCase(); // Lấy tên sản phẩm từ hàng
        if (productName.includes(searchTerm)) {
            row.style.display = 'block'; // Hiện hàng nếu tìm thấy
        } else {
            row.style.display = 'none'; // Ẩn hàng nếu không tìm thấy
        }
    }
});

function formatCurrency(value) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
    }).format(value);
}

// Thêm sự kiện click cho từng hàng sản phẩm
for (let i = 0; i < productRows.length; i++) {
    productRows[i].addEventListener('click', function () {
        // Lấy ID sản phẩm từ thuộc tính data-id
        const productId = productRows[i].getAttribute('data-id');
        console.log("Product ID:", productId); // Kiểm tra xem ID có được lấy không

        // Gọi API để lấy thông tin sản phẩm chi tiết
        fetch(`ban-hang-tai-quay/spct/${productId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(spctDTO => {
                // Tạo một hàng mới để thêm vào bảng đã chọn
                const selectedProduct = document.createElement('tr');

                // Thêm các ô vào hàng mới
                const imageCell = document.createElement('td');
                const imagesDiv = document.createElement('div'); // Div để chứa hình ảnh

                // Kiểm tra nếu listAnh tồn tại và không rỗng
                if (Array.isArray(spctDTO.anhSanPham) && spctDTO.anhSanPham.length > 0) {
                    const imgElement = document.createElement('img');
                    imgElement.src = `/images/${spctDTO.anhSanPham[0].tenAnh}`; // Lấy hình ảnh đầu tiên
                    imgElement.alt = `Hình sản phẩm ${spctDTO.id}`;
                    imgElement.style.width = '50px';
                    imgElement.style.height = 'auto';
                    imagesDiv.appendChild(imgElement);
                } else {
                    imagesDiv.textContent = 'Không có hình ảnh';
                }

                imageCell.appendChild(imagesDiv); // Thêm div chứa hình ảnh vào ô
                selectedProduct.appendChild(imageCell);

                const nameCell = document.createElement('td');

// Tạo phần tử span cho tên sản phẩm
                const nameSpan = document.createElement('span');
                nameSpan.textContent = spctDTO.sanPham.tenSanPham; // Thêm tên sản phẩm
                nameCell.appendChild(nameSpan);

// Tạo phần tử span cho kích thước và thêm vào ngoặc vuông
                const sizeSpan = document.createElement('span');
                sizeSpan.textContent = `[ ${spctDTO.kichThuoc.tenKichThuoc} - `; // Thêm kích thước với ngoặc mở
                nameCell.appendChild(sizeSpan);

// Tạo phần tử span cho màu sắc
                const colorSpan = document.createElement('span');
                colorSpan.style.display = 'inline-block'; // Đặt thuộc tính hiển thị
                colorSpan.style.backgroundColor = spctDTO.mauSac.tenMauSac; // Thêm màu sắc
                colorSpan.style.width = '20px';
                colorSpan.style.height = '20px';
                colorSpan.style.border = '1px solid #000'; // Đặt viền

// Thêm span màu sắc vào nameCell sau sizeSpan
                nameCell.appendChild(colorSpan); // Thêm span màu vào nameCell

// Thêm phần tử đóng ngoặc vào nameCell
                const closingBracketSpan = document.createElement('span');
                closingBracketSpan.textContent = ' ]'; // Thêm ngoặc đóng
                nameCell.appendChild(closingBracketSpan);

// Thêm ô tên sản phẩm vào hàng
                selectedProduct.appendChild(nameCell);

                const priceCell = document.createElement('td');
                priceCell.textContent = formatCurrency(spctDTO.gia); // Định dạng giá
                selectedProduct.appendChild(priceCell);
//Số lượng
                const quantityCell = document.createElement('td');
                const quantityInput = document.createElement('input');
                let availableQuantity = spctDTO.soLuong;
                quantityInput.type = 'number'; // Đặt loại là number để chỉ cho phép nhập số
                quantityInput.value = 1; // Khởi tạo với giá trị 1
                quantityInput.min = 1; // Đặt giá trị tối thiểu là 1
                quantityInput.style.width = '60px'; // Đặt chiều rộng cho input
                quantityCell.appendChild(quantityInput); // Thêm input vào ô
                selectedProduct.appendChild(quantityCell);
                quantityInput.addEventListener('input', function () {
                    const inputQuantity = parseInt(quantityInput.value, 10);
                    if (quantityInput.value < 1) {
                        quantityInput.value = 1; // Đặt lại giá trị về 1 nếu người dùng nhập số âm hoặc 0
                    } else if (inputQuantity > availableQuantity) {
                        // Hiển thị thông báo nếu số lượng nhập vào lớn hơn số lượng có sẵn
                        showToast("Số lượng nhập vào vượt quá số lượng có sẵn!");
                    }
                });

                const deleteCell = document.createElement('td');
                const deleteIcon = document.createElement('i');
                deleteIcon.className = 'fas fa-trash-alt';
                deleteIcon.style.cursor = 'pointer'; // Thay đổi con trỏ chuột khi hover vào icon
                deleteCell.appendChild(deleteIcon);
                selectedProduct.appendChild(deleteCell);

                // Thêm sự kiện click cho icon xóa
                deleteIcon.addEventListener('click', function () {
                    selectedProduct.remove(); // Xóa hàng sản phẩm khỏi bảng
                    if (selectedProductsTable.querySelector('tbody').children.length === 0) {
                        searchBar.classList.remove('expanded-search-bar'); // Xóa class mở rộng search-bar
                    }
                });
                // Thêm hàng sản phẩm vào bảng đã chọn
                selectedProductsTable.querySelector('tbody').appendChild(selectedProduct);
                productList.style.display = 'none';
                inputField.value = ''; // Xóa giá trị ô tìm kiếm

                // Thêm class "expanded-search-bar" để mở rộng search-bar
                searchBar.classList.add('expanded-search-bar');
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    });
}

// Xóa class "expanded-search-bar" khi không còn sản phẩm nào được chọn
selectedProductsTable.querySelector('tbody').addEventListener('DOMSubtreeModified', function () {
    if (selectedProductsTable.querySelector('tbody').children.length === 0) {
        searchBar.classList.remove('expanded-search-bar');
    }
});





