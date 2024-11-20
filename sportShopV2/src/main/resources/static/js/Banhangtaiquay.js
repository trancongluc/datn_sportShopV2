let invoiceData = {};
// Hàm để thêm một tab mới
let customerInfoByInvoice = {}; // Đối tượng lưu thông tin khách hàng cho từng hóa đơn
let productDetailByInvoice = {};
let tongTien = 0;
let phiShip = 0;
let thucThu = 0;// Biến để lưu phí ship
let giamGia = 0;
let selectedProductIds = [];
const tabDeliveryData = {};
let idHD;
let idKH;
let address;

function addTab(invoice) {
    const tabContainer = document.getElementById('tabContainer');

    // Tạo thẻ mới cho hóa đơn
    const newTab = document.createElement('div');
    newTab.classList.add('tab-item');
    newTab.innerHTML = `Hóa Đơn ${invoice.id}`;
    newTab.dataset.invoiceId = invoice.id; // Lưu ID hóa đơn

    newTab.setAttribute("onclick", `selectTab(${invoice.id})`);

    // Thêm thẻ mới vào DOM
    tabContainer.insertBefore(newTab, tabContainer.lastElementChild);
}

let tabSelection = {};
let nhanHang;

// Hàm cập nhật phương thức giao hàng


function selectTab(invoiceId) {
    const tabs = document.querySelectorAll('.tab-item');
    tabs.forEach(tab => tab.classList.remove('active'));
    const selectedTab = Array.from(tabs).find(tab => tab.dataset.invoiceId == invoiceId);
    if (selectedTab) {
        selectedTab.classList.add('active');
    }
    idHD = invoiceId;
    console.log("idHD là:" + idHD);
    // Hiển thị thông tin khách hàng cho tab được chọn
    displayCustomerInfoForTab(invoiceId);
    updateCustomerDropdown(invoiceId); // Cập nhật dropdown
    updateSelectedProductsTable(invoiceId);
// Cập nhật phương thức giao hàng và địa chỉ từ tabSelection
}

function updateDeliveryOption() {
    // Lấy tất cả các radio button với name là "deliveryOption"
    const deliveryOptions = document.getElementsByName('deliveryOption');

    // Khởi tạo biến để lưu lựa chọn được chọn
    let selectedOption = '';

    // Duyệt qua từng radio button để tìm ra cái được chọn
    for (const option of deliveryOptions) {
        if (option.checked) {
            selectedOption = option.id; // Lưu id của tùy chọn được chọn
            break; // Thoát vòng lặp khi tìm thấy
        }
    }
    nhanHang = selectedOption;
    // Hiển thị tùy chọn được chọn (hoặc thực hiện các hành động khác)
    console.log('Tùy chọn nhận hàng đang được chọn:', selectedOption);
    const deliveryAddressDiv = document.getElementById('deliveryAddress');
    deliveryAddressDiv.style.display = selectedOption === 'Chuyển Phát' ? 'block' : 'none';
}

// Gọi hàm khi trang được tải để lấy giá trị mặc định
document.addEventListener('DOMContentLoaded', updateDeliveryOption);

function displayCustomerInfoForTab(invoiceId) {
    const customerData = customerInfoByInvoice[invoiceId];
    if (customerData) {
        document.getElementById("customerName").value = customerData.fullName || "";
        document.getElementById("phone").value = customerData.phoneNumber || "";
        document.getElementById("email").value = customerData.email || "";
        document.getElementById("customerCode").value = customerData.code || "";
    } else {
        // Xóa thông tin nếu không có dữ liệu cho tab
        clearCustomerInfo();
    }
}

function clearCustomerInfo() {
    document.getElementById("customerName").value = "";
    document.getElementById("phone").value = "";
    document.getElementById("email").value = "";
    document.getElementById("customerCode").value = "";
}

document.getElementById("customerDropdown").addEventListener("change", function () {
    const selectedCustomerId = this.value;
    const activeTab = document.querySelector('.tab-item.active');
    const invoiceId = activeTab ? activeTab.dataset.invoiceId : null; // Lấy ID hóa đơn từ tab đang hoạt động
    idKH = selectedCustomerId;
    console.log("khachsachs hang:" + idKH)
    if (selectedCustomerId !== "all" && invoiceId) {
        fetch("/ban-hang-tai-quay/thong-tin-kh?idKH=" + idKH)
            .then(response => response.json())
            .then(data => {
                // Hiển thị và lưu thông tin
                document.getElementById("customerName").value = data.fullName || "";
                document.getElementById("phone").value = data.phoneNumber || "";
                document.getElementById("email").value = data.email || "";
                document.getElementById("customerCode").value = data.code || "";

                // Lưu vào đối tượng
                saveCustomerInfoForTab(invoiceId, data);
            })
            .catch(error => console.error("Error fetching customer data:", error));
    } else {
        // Nếu chọn "Tất cả" hoặc không có tab nào, xóa thông tin
        clearCustomerInfo();
        if (invoiceId) {
            saveCustomerInfoForTab(invoiceId, null);
        }
    }
});

function saveCustomerInfoForTab(invoiceId, customerData) {
    customerInfoByInvoice[invoiceId] = customerData;
}

function saveProductDetailsForTab(invoiceId, productData) {
    if (!productDetailByInvoice[invoiceId]) {
        productDetailByInvoice[invoiceId] = []; // Tạo danh sách nếu chưa có
    }
    productDetailByInvoice[invoiceId].push(productData); // Lưu sản phẩm vào danh sách của hóa đơn
}

function updateCustomerDropdown(invoiceId) {
    const activeCustomerId = customerInfoByInvoice[invoiceId]
        ? customerInfoByInvoice[invoiceId].id // Giả định bạn có ID khách hàng trong dữ liệu
        : "all"; // Hoặc "all" nếu không có dữ liệu

    document.getElementById("customerDropdown").value = activeCustomerId; // Cập nhật dropdown
}

function updateSelectedProductsTable(invoiceId) {
    const tbody = selectedProductsTable.querySelector('tbody');
    tbody.innerHTML = ""; // Xóa bảng hiện tại

    // Lấy danh sách sản phẩm từ `productDetailByInvoice`
    const products = productDetailByInvoice[invoiceId] || [];
    selectedProductIds = []; // Cập nhật danh sách sản phẩm đã chọn

    products.forEach(product => {
        const selectedProduct = document.createElement('tr');
        selectedProduct.setAttribute('data-id', product.id);

        // Tạo ô hình ảnh
        const imageCell = document.createElement('td');
        const imgElement = document.createElement('img');
        imgElement.src = `/images/${product.anhSanPham[0]?.tenAnh || 'default.jpg'}`;
        imgElement.alt = `Hình sản phẩm ${product.id}`;
        imgElement.style.width = '50px';
        imageCell.appendChild(imgElement);
        selectedProduct.appendChild(imageCell);

        // Tạo ô tên sản phẩm
        const nameCell = document.createElement('td');
        nameCell.innerHTML = `
            <span>${product.sanPham.tenSanPham}</span>
            [ ${product.kichThuoc.tenKichThuoc} - 
            <span style="display: inline-block; background-color: ${product.mauSac.tenMauSac}; width: 20px; height: 20px; border: 1px solid #000;"></span> ]`;
        selectedProduct.appendChild(nameCell);

        // Tạo ô giá
        const priceCell = document.createElement('td');
        priceCell.textContent = formatCurrency(product.gia);
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

            // Đảm bảo số lượng không nhỏ hơn 1
            if (inputQuantity < 1 || isNaN(inputQuantity)) {
                quantityInput.value = 1;
            }
            let quantity = quantityInput.value;
            productQuantities[product.id] = quantity;
            console.log("--SoLuong" + productQuantities[product.id], "idSPCT: " + product.id);
            // Cập nhật tổng tiền
            tongTien = calculateTotal(); // Gọi hàm tính tổng
            updateTotal(); // Cập nhật hiển thị tổng tiền
        });
        // Tạo ô xóa
        const deleteCell = document.createElement('td');
        const deleteIcon = document.createElement('i');
        deleteIcon.className = 'fas fa-trash-alt';
        deleteIcon.style.cursor = 'pointer';
        deleteCell.appendChild(deleteIcon);
        selectedProduct.appendChild(deleteCell);

        // Xử lý sự kiện xóa sản phẩm
        deleteIcon.addEventListener('click', function () {
            selectedProduct.remove();
            productDetailByInvoice[invoiceId] = productDetailByInvoice[invoiceId].filter(p => p.id !== product.id);
            tongTien = calculateTotal();
            updateTotal();
        });

        tbody.appendChild(selectedProduct);
        selectedProductIds.push(product.id); // Cập nhật danh sách sản phẩm đã chọn

    });

    tongTien = calculateTotal(); // Tính tổng tiền
    updateTotal(); // Cập nhật hiển thị tổng tiền
}

function getCurrentInvoiceId() {
    const activeTab = document.querySelector('.tab-item.active');
    return activeTab ? activeTab.dataset.invoiceId : null;
}

// Hàm cập nhật phương thức nhận hàng


$(document).ready(function () {
    // Lấy danh sách tỉnh
    $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
        if (data_tinh.error === 0) {
            // Thêm tỉnh vào dropdown
            $.each(data_tinh.data, function (key_tinh, val_tinh) {
                const selected = val_tinh.id == "${customer.addresses[0].province_id}" ? "selected" : "";
                $("#tinh").append('<option value="' + val_tinh.id + '" ' + selected + '>' + val_tinh.full_name + '</option>');
            });

            // Kích hoạt sự kiện thay đổi tỉnh để tải quận
            $("#tinh").change(function () {
                var idtinh = $(this).val();
                // Lấy danh sách quận
                $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                    $("#quan").empty(); // Xóa danh sách quận trước
                    $.each(data_quan.data, function (key_quan, val_quan) {
                        $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                    });
                    $("#quan").change(); // Kích hoạt tải phường
                });
            }).change(); // Kích hoạt tải quận ban đầu

            // Kích hoạt sự kiện thay đổi quận để tải phường
            $("#quan").change(function () {
                var idquan = $(this).val();
                // Lấy danh sách phường
                $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                    $("#phuong").empty(); // Xóa danh sách phường trước
                    $.each(data_phuong.data, function (key_phuong, val_phuong) {
                        const selected = val_phuong.id == "${customer.addresses[0].ward_id}" ? "selected" : "";
                        $("#phuong").append('<option value="' + val_phuong.id + '" ' + selected + '>' + val_phuong.full_name + '</option>');
                    });
                });
            }).change(); // Kích hoạt tải phường ban đầu
        }
    });

    // Tạo địa chỉ từ các lựa chọn và gọi hàm tính phí ship
    $("#tinh, #quan, #phuong").change(function () {
        const soNha = $("#soNha").val() || ""; // Lấy số nhà
        const xa = $("#phuong").find("option:selected").text(); // Lấy tên phường
        const quan = $("#quan").find("option:selected").text(); // Lấy tên quận
        const tinh = $("#tinh").find("option:selected").text(); // Lấy tên tỉnh
        address = `${xa}, ${quan}, ${tinh}`;
        console.log("Địa chỉ:", address); // Hiển thị địa chỉ trong console

        tinhPhiShipGHTK(tongTien).then(fee => {
            if (fee !== null) {
                console.log("Phí ship:", fee);
                // Cập nhật giá trị phí ship vào phần tử #ship
                if (fee === 0 || nhanHang ==='Tại Quầy') {
                    $("#ship").text("Miễn Phí");
                } else {
                    $("#ship").text(fee + " đ"); // Hiển thị phí ship nếu có
                }
                phiShip = fee;
                updateTotal();
            } else {
                console.error("Không thể tính phí ship.");
                $("#ship").text("Không thể tính phí ship");
            }
        });
    });
});


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


// Hàm để cập nhật tổng tiền
function updateTotal() {
    const totalCell = document.getElementById('tongTien');
    console.log('Cập nhật tổng tiền:', tongTien); // Kiểm tra giá trị `tongTien`
    totalCell.textContent = formatCurrency(tongTien); // Định dạng và cập nhật tổng tiền
    updateThucThu(); // Nếu có hàm liên quan
}


function updateThucThu() {
    const thucThuCell = document.getElementById('thucThu');
    thucThu = phiShip + tongTien;
    console.log("Thuc thu:"+thucThu);
    console.log("phíhip:"+phiShip);
    console.log("tongTien:"+tongTien);
    thucThuCell.textContent = formatCurrency(thucThu);
}


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
                // Kiểm tra xem sản phẩm đã tồn tại trong bảng chưa
                const existingRow = selectedProductsTable.querySelector(`tr[data-id='${spctDTO.id}']`);
                if (existingRow) {
                    // Nếu sản phẩm đã tồn tại, cập nhật số lượng
                    const quantityInput = existingRow.querySelector('input[type="number"]');
                    quantityInput.value = parseInt(quantityInput.value, 10) + 1; // Tăng số lượng
                    const giaSP = spctDTO.gia;
                    tongTien = calculateTotal(); // Tính tổng tiền
                    updateTotal();// Cập nhật hiển thị tổng tiền
                    return; // Thoát hàm để không thêm hàng mới
                }

                // Nếu sản phẩm chưa tồn tại, tạo hàng mới
                const selectedProduct = document.createElement('tr');
                selectedProduct.setAttribute('data-id', spctDTO.id);

                // Tạo ô hình ảnh
                const imageCell = document.createElement('td');
                if (Array.isArray(spctDTO.anhSanPham) && spctDTO.anhSanPham.length > 0) {
                    const imgElement = document.createElement('img');
                    imgElement.src = `/images/${spctDTO.anhSanPham[0].tenAnh}`;
                    imgElement.alt = `Hình sản phẩm ${spctDTO.id}`;
                    imgElement.style.width = '50px';
                    imageCell.appendChild(imgElement);
                } else {
                    imageCell.textContent = 'Không có hình ảnh';
                }
                selectedProduct.appendChild(imageCell);

                // Tạo ô tên sản phẩm
                const nameCell = document.createElement('td');
                const nameSpan = document.createElement('span');
                nameSpan.textContent = spctDTO.sanPham.tenSanPham;
                nameCell.appendChild(nameSpan);

                const sizeSpan = document.createElement('span');
                sizeSpan.textContent = `[ ${spctDTO.kichThuoc.tenKichThuoc} - `;
                nameCell.appendChild(sizeSpan);

                const colorSpan = document.createElement('span');
                colorSpan.style.display = 'inline-block';
                colorSpan.style.backgroundColor = spctDTO.mauSac.tenMauSac;
                colorSpan.style.width = '20px';
                colorSpan.style.height = '20px';
                colorSpan.style.border = '1px solid #000';
                nameCell.appendChild(colorSpan);

                const closingBracketSpan = document.createElement('span');
                closingBracketSpan.textContent = ' ]';
                nameCell.appendChild(closingBracketSpan);
                selectedProduct.appendChild(nameCell);

                // Tạo ô giá
                const priceCell = document.createElement('td');
                const giaSP = spctDTO.gia;
                priceCell.textContent = formatCurrency(giaSP);
                selectedProduct.appendChild(priceCell);

                // Tạo ô số lượng
                const quantityCell = document.createElement('td');
                const quantityInput = document.createElement('input');
                quantityInput.type = 'number';
                quantityInput.value = 1; // Khởi tạo số lượng là 1
                quantityInput.min = 1;
                quantityInput.style.width = '60px';
                quantityCell.appendChild(quantityInput);
                selectedProduct.appendChild(quantityCell);
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
                    tongTien = calculateTotal();
                    updateTotal();
                });

                // Thêm hàng sản phẩm vào bảng đã chọn
                selectedProductsTable.querySelector('tbody').appendChild(selectedProduct);
                const currentInvoiceId = getCurrentInvoiceId();
                saveProductDetailsForTab(currentInvoiceId, spctDTO);
                updateSelectedProductsTable(currentInvoiceId);
                //tongTien += giaSP; // Cập nhật tổng tiền khi thêm sản phẩm
                let quantity = quantityInput.value;
                productQuantities[productId] = quantity;
                console.log("SoLuong" + productQuantities[productId], "idSPCT: " + productId); // Kiểm tra giá trị
                tongTien = calculateTotal();
                updateTotal();// Cập nhật hiển thị tổng tiền
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
        let quantityInput = row.querySelector('input[type="number"]');
        const priceCell = row.querySelector('td:nth-child(3)'); // Giả sử giá là ô thứ 3
        const price = parseCurrency(priceCell.textContent); // Chuyển đổi giá trị về số
        const quantity = parseInt(quantityInput.value, 10);

        total += price * quantity; // Tính tổng
    });
    console.log('Tổng tiền:', total); // Kiểm tra tổng tiền
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
            billCode: `HD-${Date.now()}`,
            transaction_date: date,
            type: "Tại Quầy",
            createAt: date,
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
        storedInvoices.push({id: newIdHD}); // Sử dụng ID vừa tạo
        localStorage.setItem('invoices', JSON.stringify(storedInvoices));
        soLuongHoaDonCho++; // Tăng số lượng hóa đơn chờ

        // Gọi hàm addTab để tạo tab mới cho hóa đơn
        addTab({id: newIdHD}); // Truyền ID hóa đơn vào addTab
        showToast("Tạo hóa đơn chờ thành công!");
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

async function capNhatHoaDon() {
    try {
        const idNV = 1; // ID nhân viên (có thể lấy từ một ô input nếu cần)
        const fullName = document.getElementById('customerName').value;
        const sdt = document.getElementById('phone').value;
        const email = document.getElementById('email').value;
        const giamGia = 0; // Thay đổi theo cách tính giảm giá
        const status = "Hoàn Thành"; // Trạng thái mặc định
        const date = new Date().toISOString(); // Lấy thời gian hiện tại
        const soNha = document.getElementById('soNha').value;
        console.log(nhanHang);
        if (nhanHang == 'Tại Quầy') {
            address = null;
        }
        var diaChiChiTiet = (nhanHang === 'Tại Quầy') ? null : `${soNha}, ${address}`;
        const currentInvoice = await fetch(`ban-hang-tai-quay/hd/${idHD}`).then(handleResponse)
        // Lấy thông tin khách hàng
        const userKH = await fetch(`/ban-hang-tai-quay/tk/${idKH}`).then(handleResponse);

        // Lấy thông tin nhân viên
        const emp = await fetch(`/ban-hang-tai-quay/tk/${idNV}`).then(handleResponse);
        console.log("HoaDOn:", currentInvoice);
        console.log("KH:", userKH);
        console.log("NV:", emp);
        // Tạo đối tượng hóa đơn
        const hoaDon = {
            user_name: fullName || userKH.name,
            phone_number: sdt || userKH.phone_number,
            total_money: tongTien,
            money_reduced: giamGia,
            status: status,
            email: email,
            money_ship: phiShip,
            bill_code: currentInvoice.bill_code,
            transaction_date: date,
            type: nhanHang,
            address: diaChiChiTiet,
            updateAt: date,
            create_at: currentInvoice.create_at,
            create_by: idNV,
            id_account: userKH,
            id_staff: emp,
            deleted: 0
        };
        // Cập nhật hóa đơn
        const updatedHoaDon = await fetch(`ban-hang-tai-quay/update-hoa-don/${idHD}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(hoaDon),
        }).then(handleResponse);
        // Lấy thông tin chi tiết sản phẩm và tạo hóa đơn chi tiết
        const hoaDonChiTietList = await Promise.all(
            selectedProductIds.map(async (productId) => {
                const productDetails = await fetch(`san-pham-chi-tiet/thong-tin-spct/${productId}`).then(handleResponse);
                if (!productDetails || !productDetails.gia) {
                    console.warn(`Không tìm thấy giá cho sản phẩm ID: ${productId}`);
                    return null;
                }
                const quantity = productQuantities[productId]; // Lấy số lượng từ productQuantities
                if (!quantity || quantity <= 0) {
                    console.warn(`Số lượng không hợp lệ cho sản phẩm ID: ${productId}`);

                    return null;
                }
                return {
                    quantity: quantity,
                    hoaDon: updatedHoaDon,
                    sanPhamChiTiet: productDetails,
                    price: productDetails.gia,
                    create_at: date,
                    create_by: idNV,
                };
            })
        );

        // Loại bỏ các phần tử null nếu có lỗi trong danh sách sản phẩm
        const validHoaDonChiTietList = hoaDonChiTietList.filter(item => item !== null);

        // Gửi yêu cầu tạo hóa đơn chi tiết
        await fetch('ban-hang-tai-quay/tao-hoa-don-chi-tiet', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(validHoaDonChiTietList),
        });
        showToast("Tạo Hóa Đơn Thành Công!");
        selectedProductIds = [];
        window.location.href = '/ban-hang-tai-quay';
        // Thông báo thành công và reset form

    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
        showToast("Thêm hóa đơn thất bại!");
    }
}

// Hàm xử lý phản hồi từ API
function handleResponse(response) {
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status} - ${response.statusText}`);
    }
    return response.json();
}

function showConfirmation() {
    const modal = $('#confirmationModal');
    const modalMessage = $('#modalMessage');
    modalMessage.text(`Bạn có chắc muốn thanh toán hóa đơn?`); // Sử dụng đúng ký tự tiếng Việt
    modal.css('display', 'block');
    setTimeout(() => {
        modal.css('opacity', '1');
        $('.modal-content').css('transform', 'scale(1)');
    }, 10);
}

function closeModal() {
// Đóng tất cả các modal
    $('#confirmationModal').css('display', 'none'); // Đóng modal xác nhận
    const modal = document.getElementById('confirmationModal');
    modal.style.opacity = '0';
    document.querySelector('.modal-content').style.transform = 'scale(0.7)';
    setTimeout(() => {
        modal.style.display = 'none';
    }, 500);
}
async function tinhPhiShipGHTK(tongTienTinhShip) {
    const pickProvince = "Hà Nội";
    const pickDistrict = "Đan Phượng";
    const pickWard = "Xã Thọ Xuân";

    const province = $("#tinh").find("option:selected").text();
    const district = $("#quan").find("option:selected").text();
    const ward = $("#phuong").find("option:selected").text();
    const weight = 500;

    if (!province || !district || !ward) {
        console.error("Thiếu thông tin địa chỉ nhận hàng!");
        return null;
    }

    const params = new URLSearchParams({
        pick_province: pickProvince,
        pick_district: pickDistrict,
        pick_ward: pickWard,
        province: province,
        district: district,
        ward: ward,
        weight: weight,
        value: tongTienTinhShip || 0,
        deliver_option: "none"
    });

    // In URL để kiểm tra
    console.log("Yêu cầu gửi tới backend:", `/api/proxy/calculate-fee?${params.toString()}`);

    try {
        const response = await fetch(`/api/proxy/calculate-fee?${params.toString()}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            console.error(`HTTP error! Status: ${response.status}`);
            return null;
        }

        const result = await response.json();

        if (result.success) {
            console.log("Phí ship tính toán thành công:", result.fee.options.shipMoney);
            return result.fee.options.shipMoney;
        } else {
            console.error("API trả về lỗi:", result.message);
            return null;
        }
    } catch (error) {
        console.error("Lỗi kết nối hoặc xử lý API:", error);
        return null;
    }
}










