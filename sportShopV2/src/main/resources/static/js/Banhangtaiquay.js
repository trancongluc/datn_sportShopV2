
let invoiceData = {};

// Hàm để thêm một tab mới

// Hàm thêm tab cho hóa đơn
function addTab(invoice) {
    const tabContainer = document.getElementById('tabContainer');

    // Tạo thẻ mới cho hóa đơn
    const newTab = document.createElement('div');
    newTab.classList.add('tab-item');
    newTab.innerHTML = `Hóa Đơn ${invoice.id}`;
    newTab.setAttribute("onclick", `selectTab(${invoice.id})`);

    // Thêm thẻ mới vào DOM
    tabContainer.insertBefore(newTab, tabContainer.lastElementChild);
}

function selectTab(invoiceId) {
    // Lấy tất cả các tab hóa đơn
    const tabs = document.querySelectorAll('.tab-item');

    // Duyệt qua từng tab và loại bỏ lớp "active"
    tabs.forEach(tab => {
        tab.classList.remove('active');
    });

    // Tìm tab tương ứng với invoiceId và thêm lớp "active"
    const selectedTab = Array.from(tabs).find(tab => tab.innerHTML.includes(`Hóa Đơn ${invoiceId}`));
    if (selectedTab) {
        selectedTab.classList.add('active');
    }

    // Thực hiện thêm các hành động cần thiết khi tab được chọn
    // Ví dụ: Hiển thị thông tin hóa đơn tương ứng
    displayInvoiceDetails(invoiceId);
}

// Hàm giả lập để hiển thị thông tin hóa đơn
function displayInvoiceDetails(invoiceId) {
    // Thay thế phần này bằng logic của bạn để hiển thị thông tin hóa đơn
    console.log(`Hiển thị thông tin cho hóa đơn ID: ${invoiceId}`);
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

let tongTien = 0;
let phiShip = 0; // Biến để lưu phí ship
let giamGia = 0;

// Hàm để cập nhật tổng tiền
function updateTotal() {
    const totalCell = document.getElementById('tongTien');
    totalCell.textContent = formatCurrency(tongTien); // Định dạng và cập nhật tổng tiền
    updateThucThu();
}

function updateThucThu() {
    const thucThuCell = document.getElementById('thucThu');

    thucThuCell.textContent = formatCurrency(tongTien);
}

let selectedProductIds = [];
// Thêm sự kiện click cho từng hàng sản phẩm
for (let i = 0; i < productRows.length; i++) {
    productRows[i].addEventListener('click', function () {
        const productId = productRows[i].getAttribute('data-id');
        console.log("Product ID:", productId);

        fetch(`ban-hang-tai-quay/spct/${productId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(spctDTO => {
                const selectedProduct = document.createElement('tr');
                selectedProduct.setAttribute('data-id', spctDTO.id);
                // Tạo ô hình ảnh
                const imageCell = document.createElement('td');
                const imagesDiv = document.createElement('div');
                if (Array.isArray(spctDTO.anhSanPham) && spctDTO.anhSanPham.length > 0) {
                    const imgElement = document.createElement('img');
                    imgElement.src = `/images/${spctDTO.anhSanPham[0].tenAnh}`;
                    imgElement.alt = `Hình sản phẩm ${spctDTO.id}`;
                    imgElement.style.width = '50px';
                    imageCell.appendChild(imgElement);
                } else {
                    imagesDiv.textContent = 'Không có hình ảnh';
                }
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

                // Tạo ô giá
                const priceCell = document.createElement('td');
                const giaSP = spctDTO.gia; // Lưu giá sản phẩm để tính tổng
                priceCell.textContent = formatCurrency(giaSP);
                selectedProduct.appendChild(priceCell);

                // Tạo ô số lượng
                const quantityCell = document.createElement('td');
                const quantityInput = document.createElement('input');
                quantityInput.type = 'number';
                quantityInput.value = 1;
                quantityInput.min = 1;
                quantityInput.style.width = '60px';
                quantityCell.appendChild(quantityInput);
                selectedProduct.appendChild(quantityCell);

                // Cập nhật tổng tiền khi số lượng thay đổi
                quantityInput.addEventListener('input', function () {
                    const inputQuantity = parseInt(quantityInput.value, 10);
                    if (inputQuantity < 1) {
                        quantityInput.value = 1;
                    }
                    const currentTotal = giaSP * inputQuantity; // Tính tổng cho sản phẩm hiện tại
                    tongTien = calculateTotal(); // Tính tổng tiền từ tất cả sản phẩm
                    updateTotal(); // Cập nhật hiển thị tổng tiền
                });

                // Tạo ô xóa sản phẩm
                const deleteCell = document.createElement('td');
                const deleteIcon = document.createElement('i');
                deleteIcon.className = 'fas fa-trash-alt';
                deleteIcon.style.cursor = 'pointer';
                deleteCell.appendChild(deleteIcon);
                selectedProduct.appendChild(deleteCell);

                // Xóa sản phẩm
                deleteIcon.addEventListener('click', function () {
                    selectedProduct.remove();
                    tongTien = calculateTotal(); // Tính lại tổng tiền sau khi xóa
                    updateTotal(); // Cập nhật hiển thị tổng tiền
                });

                // Thêm hàng sản phẩm vào bảng đã chọn
                selectedProductsTable.querySelector('tbody').appendChild(selectedProduct);
                selectedProductIds.push(spctDTO.id);
                tongTien += giaSP; // Cập nhật tổng tiền khi thêm sản phẩm
                updateTotal(); // Cập nhật hiển thị tổng tiền
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    });
}

// Hàm tính tổng tiền từ tất cả các sản phẩm trong bảng
const productQuantities = {};

function calculateTotal() {
    let total = 0;
    const rows = selectedProductsTable.querySelectorAll('tbody tr');
    rows.forEach(row => {
        const quantityInput = row.querySelector('input[type="number"]');
        const priceCell = row.querySelector('td:nth-child(3)'); // Giả sử giá là ô thứ 3
        const price = parseCurrency(priceCell.textContent); // Chuyển đổi giá trị về số
        const quantity = parseInt(quantityInput.value);
        const productId = row.dataset.productId; // Giả sử mỗi hàng có thuộc tính data-product-id
        productQuantities[productId] = quantity;
        total += price * quantity; // Tính tổng
    });
    return total;
}

// Hàm định dạng giá
function formatCurrency(value) {
    return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(value);
}

// Hàm phân tích giá trị tiền tệ về số
function parseCurrency(value) {
    // Bỏ dấu phẩy (,) và chuyển đổi sang số
    return parseFloat(value.replace(/\./g, '').replace(/[^0-9.-]+/g, ""));
}

// Xóa class "expanded-search-bar" khi không còn sản phẩm nào được chọn
selectedProductsTable.querySelector('tbody').addEventListener('DOMSubtreeModified', function () {
    if (selectedProductsTable.querySelector('tbody').children.length === 0) {
        searchBar.classList.remove('expanded-search-bar');
    }
});
let idKH;
let customerSelections = {};

document.getElementById("customerDropdown").addEventListener("change", function () {
    var selectedCustomerId = this.value;
    var currentInvoiceId = getCurrentInvoiceId(); // Lấy id của hóa đơn hiện tại

    // Lưu thông tin lựa chọn khách hàng tương ứng với hóa đơn
    customerSelections[currentInvoiceId] = selectedCustomerId;

    if (selectedCustomerId !== "all") {
        fetch("/ban-hang-tai-quay/thong-tin-kh?idKH=" + selectedCustomerId)
            .then(response => response.json())
            .then(data => {
                // Cập nhật các trường với thông tin khách hàng
                updateCustomerInfo(data);
            })
            .catch(error => console.error("Error fetching customer data:", error));
    } else {
        // Nếu chọn "Tất cả", xóa thông tin khách hàng
        clearCustomerInfo();
    }
});

function updateCustomerInfo(data) {
    document.getElementById("customerName").value = data.fullName || "";
    document.getElementById("phone").value = data.phoneNumber || "";
    document.getElementById("email").value = data.email || "";
    document.getElementById("customerCode").value = data.code || "";
}

function clearCustomerInfo() {
    document.getElementById("customerName").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("email").value = "";
    document.getElementById("customerCode").value = "";
}

function getCurrentInvoiceId() {
    // Lấy id của hóa đơn hiện tại, ví dụ:
    return "1027";
}

// Khi người dùng chọn một hóa đơn khác
document.getElementById("invoiceList").addEventListener("change", function () {
    var newInvoiceId = this.value;

    // Kiểm tra xem có thông tin lựa chọn khách hàng tương ứng với hóa đơn mới không
    if (customerSelections[newInvoiceId]) {
        // Cập nhật trường dropdown với lựa chọn của khách hàng
        document.getElementById("customerDropdown").value = customerSelections[newInvoiceId];

        // Cập nhật thông tin khách hàng
        fetch("/ban-hang-tai-quay/thong-tin-kh?idKH=" + customerSelections[newInvoiceId])
            .then(response => response.json())
            .then(data => {
                updateCustomerInfo(data);
            })
            .catch(error => console.error("Error fetching customer data:", error));
    } else {
        // Nếu không có thông tin lựa chọn, xóa thông tin khách hàng
        clearCustomerInfo();
    }
});

let soLuongHoaDonCho = 0; // Số lượng hóa đơn chờ
const maxInvoices = 5; // Giới hạn số lượng hóa đơn chờ

async function taoHoaDonCho() {
    // Kiểm tra số lượng hóa đơn chờ trước khi tạo hóa đơn mới
    if (soLuongHoaDonCho >= maxInvoices) {
        showToast("Số lượng hóa đơn chờ đã đạt tối đa.");
        return; // Ngừng thực hiện hàm nếu đã đủ
    }

    const idNV = 1; // ID nhân viên
    const fullName = "Đang cập nhật";
    const sdt = null;
    const email = null;
    const tienBatDau = 0;
    const phiShip = 0;
    const giamGia = 0;
    const status = "Hóa Đơn Chờ"; // Trạng thái mặc định
    const date = new Date().toISOString();

    try {
        const userKH = await fetch(`/khach-hang/thong-tin-kh/14`).then(res => res.json());
        const emp = await fetch(`/nhanvien/thong-tin-nv/${idNV}`).then(res => res.json());

        const hoaDon = {
            user_name: fullName,
            phone_number: sdt,
            total_money: tienBatDau,
            money_reduced: giamGia,
            status: status,
            email: email,
            money_ship: phiShip,
            bill_code: `HD-${Date.now()}`,
            transaction_date: date,
            type: "Tại Quầy",
            create_at: date,
            create_by: idNV,
            deleted: false,
            id_account: userKH,
            id_staff: emp
        };

        const response = await fetch('ban-hang-tai-quay/tao-hoa-don', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(hoaDon)
        });

        if (!response.ok) throw new Error('Error adding bill: ' + response.statusText);

        const newHD = await response.json(); // Lấy thông tin hóa đơn mới
        const newIdHD = newHD.id; // Giả sử API trả về ID hóa đơn mới tạo

        // Lưu hóa đơn vào localStorage
        let storedInvoices = JSON.parse(localStorage.getItem('invoices')) || [];
        storedInvoices.push({ id: newIdHD }); // Sử dụng ID vừa tạo
        localStorage.setItem('invoices', JSON.stringify(storedInvoices));
        soLuongHoaDonCho++; // Tăng số lượng hóa đơn chờ

        // Gọi hàm addTab để tạo tab mới cho hóa đơn
        addTab({ id: newIdHD }); // Truyền ID hóa đơn vào addTab

        console.log('ID Hóa Đơn Vừa Tạo:', newIdHD);

    } catch (error) {
        console.error('Error:', error);
    }
}

// Hàm tải hóa đơn từ cơ sở dữ liệu và hiển thị
async function loadInvoicesFromDatabase() {
    try {
        const response = await fetch('/bill/list-hd-cho'); // Thay đổi URL phù hợp với API của bạn
        if (!response.ok) throw new Error('Không thể lấy hóa đơn: ' + response.statusText);

        const invoices = await response.json();

        // Cập nhật số lượng hóa đơn chờ
        soLuongHoaDonCho = invoices.length; // Cập nhật số lượng hóa đơn chờ từ phản hồi

        // Kiểm tra số lượng hóa đơn chờ
       /* if (soLuongHoaDonCho >= maxInvoices) {
            return; // Dừng thực hiện hàm nếu đã đủ
        }
*/
        // Thêm tab cho mỗi hóa đơn
        invoices.forEach(invoice => {
            addTab(invoice);
        });
    } catch (error) {
        console.error('Có lỗi khi tải hóa đơn:', error);
    }
}

// Gọi hàm tải hóa đơn khi trang được tải
window.onload = function () {
    loadInvoicesFromDatabase();
};

// Hàm thêm tab khi nhấn nút thêm hóa đơn
document.getElementById('addInvoiceButton').addEventListener('click', () => {
    if (soLuongHoaDonCho < maxInvoices) {
        taoHoaDonCho();
    } else {
        showToast("Bạn chỉ có thể tạo tối đa 5 hóa đơn chờ!");
    }
});

function capNhatHoaDon() {
    /*event.preventDefault(); */// Ngăn chặn hành động gửi mặc định
    const idNV = 1; // ID nhân viên (có thể lấy từ một ô input nếu cần)
    const fullName = document.getElementById('customerName').value;
    const sdt = document.getElementById('phone').value;
    const email = document.getElementById('email').value;
    const phiShip = 0; // Thay đổi theo cách tính phí ship
    const giamGia = 0; // Thay đổi theo cách tính giảm giá
    const status = "Hoàn Thành"; // Trạng thái mặc định
    const date = new Date().toISOString(); // Lấy thời gian hiện tại
    console.log("khachsachs hang:" + idKH)
    // Gọi đến endpoint để lấy thông tin khách hàng
    fetch(`/khach-hang/thong-tin-kh/${idKH}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể lấy thông tin khách hàng: ' + response.statusText);
            }
            return response.json(); // Phân tích dữ liệu JSON
        })
        .then(userKH => {
            // Gọi đến endpoint để lấy thông tin nhân viên
            return fetch(`/nhanvien/thong-tin-nv/${idNV}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Không thể lấy thông tin nhân viên: ' + response.statusText);
                    }
                    return response.json(); // Phân tích dữ liệu JSON
                })
                .then(emp => {
                    // Tạo đối tượng hóa đơn
                    const hoaDon = {
                        user_name: fullName || userKH.name, // Sử dụng tên khách hàng nếu không có
                        phone_number: sdt || userKH.phone, // Sử dụng số điện thoại nếu không có
                        total_money: tongTien,
                        money_reduced: giamGia,
                        status: status,
                        email: email, // Sử dụng email nếu không có
                        money_ship: phiShip,
                        bill_code: `HD-${Date.now()}`, // Mã hóa đơn
                        transaction_date: date,
                        update_at: date,
                        create_by: idNV, // ID nhân viên (người tạo hóa đơn)
                        id_account: userKH, // Đối tượng khách hàng
                        id_staff: emp // Đối tượng nhân viên
                    };

                    // Gửi yêu cầu tạo hóa đơn
                    return fetch('ban-hang-tai-quay/tao-hoa-don', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(hoaDon) // Gửi đối tượng hóa đơn
                    });
                });
        })
        .then(response => {
            if (response.ok) {
                return response.json(); // Phân tích JSON nếu thành công
            } else {
                throw new Error('Error adding bill: ' + response.statusText);
            }
        })
        .then(data => {
            const hoaDonChiTietPromises = selectedProductIds.map(productId => {
                const quantity = productQuantities[productId] || 1;
                return fetch(`san-pham-chi-tiet/thong-tin-spct/${productId}`)
                    .then(response => {
                        // Kiểm tra phản hồi từ server
                        if (!response.ok) {
                            throw new Error('Network response was not ok: ' + response.statusText);
                        }
                        return response.json();
                    })
                    .then(productDetails => {
                        // Kiểm tra xem productDetails có giá không
                        if (!productDetails || !productDetails.price) {
                            console.error(`Không tìm thấy giá cho sản phẩm ID: ${productId}`);
                            return null; // Có thể trả về null hoặc một đối tượng mặc định
                        }
                        console.log("Giá SPCT: " + productDetails.price);
                        return {
                            quantity: quantity,
                            hoaDon: data,
                            sanPhamChiTiet: productDetails,
                            price: productDetails.price,
                            create_at: new Date().toISOString(),
                            create_by: idNV
                        };
                    });
            });

            // Chờ tất cả các yêu cầu lấy thông tin sản phẩm hoàn tất
            return Promise.all(hoaDonChiTietPromises);
        })
        .then(hoaDonChiTietList => {
            // Chờ tất cả các yêu cầu lấy thông tin sản phẩm hoàn tất
            return fetch('ban-hang-tai-quay/tao-hoa-don-chi-tiet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(hoaDonChiTietList)
            });
        })
        .then(response => {
            if (response.ok) {
                return response.json(); // Phân tích JSON nếu thành công
            } else {
                throw new Error('Error adding bill details: ' + response.statusText);
            }
        })
        .then(data => {
            console.log('Hóa đơn chi tiết đã được tạo:', data);
            showNotification("Thêm hóa đơn và hóa đơn chi tiết thành công!"); // Thông báo thành công
            document.getElementById('hoaDonForm').reset(); // Reset form
            selectedProductIds = []; // Reset danh sách ID sản phẩm đã chọn
            window.location.href = '/ban-hang-tai-quay';
        })
        .catch(error => {
            console.error('Có lỗi:', error);
            showNotification("Thêm hóa đơn thất bại!"); // Thông báo lỗi
        });
}






