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
/*document.addEventListener('DOMContentLoaded', updateDeliveryOption);*/
document.addEventListener('DOMContentLoaded', function () {
    console.log('Sự kiện DOMContentLoaded đã được gọi.');
    loadInvoicesFromDatabase();
    updateDeliveryOption();
    document.querySelectorAll('.color-name').forEach(function (el) {
        const colorHex = el.innerText.trim(); // Lấy mã hex từ nội dung của phần tử span
        if (colorHex.startsWith('#')) { // Kiểm tra mã có hợp lệ không
            getColorName(colorHex, el); // Gọi hàm để lấy tên màu từ API
        } else {
            el.textContent = "Tên màu không hợp lệ"; // Nếu mã không hợp lệ, hiển thị thông báo lỗi
        }
    });
});

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
        fetch(`/ban-hang-tai-quay/thong-tin-kh/${idKH}`)
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
        const colorHex = product.mauSac.tenMauSac;
        nameCell.innerHTML = `
            <span>${product.sanPham.tenSanPham}</span>
            [ ${product.kichThuoc.tenKichThuoc} - 
            <span style="display: inline-block; background-color: ${colorHex}; width: 20px; height: 20px; border: 1px solid #000;"></span>
            - <span class="color-name" data-color="${colorHex}"></span>]`;
        selectedProduct.appendChild(nameCell);
        const colorNameSpan = nameCell.querySelector('.color-name');
        if (colorHex.startsWith('#')) {
            getColorName(colorHex, colorNameSpan);
        } else {
            colorNameSpan.textContent = "Tên màu không hợp lệ";
        }
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
            if (inputQuantity > product.soLuong) {
                showToast("Số lượng đã vượt quá!")
                quantityInput.value = product.soLuong;
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
                if (fee === 0 || nhanHang === 'Tại Quầy') {
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
    }, 2000); // Sau 3 giây

    // Ẩn toast sau 3.3 giây
    setTimeout(() => {
        toast.classList.remove('show');
        toast.classList.add('hide');
    }, 2000); // Thêm chút thời gian cho hiệu ứng ẩn
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
    console.log("Thuc thu:" + thucThu);
    console.log("phíhip:" + phiShip);
    console.log("tongTien:" + tongTien);
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

                    if (quantityInput.value < spctDTO.soLuong) {
                        quantityInput.value = parseInt(quantityInput.value, 10) + 1; // Tăng số lượng
                    } else {
                        showToast("Số lượng đã vượt quá!")
                        quantityInput.value = spctDTO.soLuong;
                    }
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

                 const colarNameSpan = document.createElement('span');
                 colarNameSpan.textContent = spctDTO.mauSac.tenMauSac; // Tạm thời đặt mã màu
                 colarNameSpan.className = "color-name";
                 nameCell.appendChild(colarNameSpan);

                 const closingBracketSpan = document.createElement('span');
                 closingBracketSpan.textContent = ' ]';
                 nameCell.appendChild(closingBracketSpan);
                 selectedProduct.appendChild(nameCell);
 // Gọi hàm getColorName cho phần tử vừa được thêm
                 if (spctDTO.mauSac.tenMauSac.startsWith('#')) {
                     getColorName(spctDTO.mauSac.tenMauSac, colarNameSpan);
                 } else {
                     colarNameSpan.textContent = "Tên màu không hợp lệ";
                 }
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
                 if (quantityInput.value > spctDTO.soLuong) {
                     showToast("Số lượng đã vượt quá!")
                     quantityInput.value = spctDTO.soLuong;
                 }

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
/*selectedProductsTable.querySelector('tbody').addEventListener('DOMSubtreeModified', function () {
    if (selectedProductsTable.querySelector('tbody').children.length === 0) {
        searchBar.classList.remove('expanded-search-bar');
    }
});*/
// Chọn tbody của bảng sản phẩm đã chọn
const tbody = selectedProductsTable.querySelector('tbody');

// Tạo một instance của MutationObserver
const observer = new MutationObserver(function (mutationsList) {
    for (let mutation of mutationsList) {
        if (mutation.type === 'childList') {
            // Kiểm tra nếu tbody không có con cái
            if (tbody.children.length === 0) {
                searchBar.classList.remove('expanded-search-bar');
            }
        }
    }
});

// Cấu hình để theo dõi các thay đổi
const config = {childList: true, subtree: true};

// Bắt đầu quan sát
observer.observe(tbody, config);

// Nếu cần, bạn có thể ngừng quan sát khi không còn cần thiết
// observer.disconnect();

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
        console.log('Bắt đầu tải hóa đơn từ API...');
        const response = await fetch('/bill/list-hd-cho');

        // Kiểm tra trạng thái phản hồi
        if (!response.ok) {
            throw new Error(`Không thể lấy hóa đơn (HTTP ${response.status}): ${response.statusText}`);
        }

        // Parse dữ liệu JSON
        const invoices = await response.json();
        console.log('Dữ liệu hóa đơn nhận được:', invoices);

        // Kiểm tra dữ liệu trả về có phải là mảng không
        if (!Array.isArray(invoices)) {
            throw new Error('Dữ liệu trả về không phải là một mảng!');
        }

        // Cập nhật số lượng hóa đơn chờ
        let soLuongHoaDonCho = invoices.length;
        console.log(`Số lượng hóa đơn chờ: ${soLuongHoaDonCho}`);

        // Thêm tab cho mỗi hóa đơn
        invoices.forEach(invoice => {
            addTab(invoice); // Đảm bảo addTab đã được định nghĩa
        });

        console.log('Tải hóa đơn hoàn tất.');
    } catch (error) {
        console.error('Có lỗi khi tải hóa đơn:', error.message);
        console.error('Chi tiết lỗi:', error);
    }
}


// Gọi hàm tải hóa đơn khi trang được tải
window.onload = function () {
    console.log('Sự kiện window.onload đã được gọi.');
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
        var status = "Chờ xác nhận"; // Trạng thái mặc định
        const date = new Date().toISOString(); // Lấy thời gian hiện tại
        const soNha = document.getElementById('soNha').value;
        var payStatus = null;
        var soLuongNew;
        const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').id === 'tienMat'
            ? 'Tiền Mặt'
            : 'Chuyển Khoản';
        payStatus = document.querySelector('input[name="phuongThucNhanHang"]:checked').id === 'thanhToanTruoc'
            ? 'Thanh toán trước'
            : 'Thanh toán khi nhận';
        if (nhanHang == 'Tại Quầy') {
            address = null;
            payStatus = null;
            status = "Hoàn Thành";
        }
        var diaChiChiTiet = (nhanHang === 'Tại Quầy') ? null : `${soNha}, ${address}`;
        const currentInvoice = await fetch(`ban-hang-tai-quay/hd/${idHD}`).then(handleResponse);

        // Lấy thông tin khách hàng
        const userKH = await fetch(`/ban-hang-tai-quay/tk/${idKH}`).then(handleResponse);

        // Lấy thông tin nhân viên
        const emp = await fetch(`/ban-hang-tai-quay/tk/${idNV}`).then(handleResponse);

        // Nếu phương thức thanh toán là "Chuyển Khoản", chuyển hướng đến VNPay
        if (paymentMethod === 'Chuyển Khoản') {
            const redirectToVNPay = async () => {
                try {
                    localStorage.setItem('thucThu', thucThu);  // Lưu số tiền
                    localStorage.setItem('billCode', currentInvoice.billCode);
                    window.location.href = '/VNPAY-demo';
                } catch (error) {
                    console.error('Lỗi khi chuyển hướng đến VNPay:', error);
                    showToast('Thanh toán thất bại!');
                }
            };
            await redirectToVNPay();
            return;// Dừng hàm `capNhatHoaDon` khi chuyển hướng đến VNPay
        }
        const paymentStatus = await checkPaymentStatusFromVNPay();
        // Nếu không phải thanh toán qua VNPay, tiếp tục cập nhật hóa đơn
        /*  if (paymentStatus.success) {*/
        // Tiến hành cập nhật hóa đơn khi thanh toán thành công
        const hoaDon = {
            user_name: fullName || userKH.name,
            phone_number: sdt || userKH.phone_number,
            total_money: thucThu,
            money_reduced: giamGia,
            status: status,
            email: email,
            money_ship: phiShip,
            billCode: currentInvoice.billCode,
            transaction_date: date,
            type: nhanHang,
            address: diaChiChiTiet,
            updateAt: date,
            createAt: currentInvoice.createAt,
            create_by: idNV,
            id_account: userKH,
            id_staff: emp,
            pay_method: paymentMethod,
            pay_status: payStatus,
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
                const quantity = productQuantities[productDetails.id]; // Lấy số lượng từ productQuantities
                console.log("SL SP:" + quantity)
                if (!quantity || quantity <= 0) {
                    console.warn(`Số lượng không hợp lệ cho sản phẩm ID: ${productId}`);
                    return null;
                }
                // if (nhanHang == 'Tại Quầy') {
                soLuongNew = productDetails.soLuong - quantity;
                fetch(`/san-pham-chi-tiet/cap-nhat-so-luong/${productId}?soLuongNew=${soLuongNew}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Cập nhật thất bại');
                        }
                        return response.text(); // Lấy thông báo từ server
                    })
                    .then(message => {
                        console.log("Cập nhật số lượng thành công!");
                    })
                    .catch(error => {
                        console.error("Cập nhật thất bại. Vui lòng thử lại!");
                    });
                // }
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
        console.log(validHoaDonChiTietList);
        showToast("Tạo Hóa Đơn Thành Công!");
        selectedProductIds = [];
        /* window.location.href = '/ban-hang-tai-quay';*/
    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
        showToast("Thêm hóa đơn thất bại!");
    }
}

async function checkPaymentStatusFromVNPay() {
    const orderInfo = localStorage.getItem('orderInfo');  // Hoặc lấy từ URL nếu có
    try {
        // Gửi yêu cầu kiểm tra thanh toán từ VNPay
        const response = await fetch(`/vnpay-payment-return?vnp_OrderInfo=${orderInfo}`, {
            method: 'GET',
        });

        if (response.ok) {
            const result = await response.json();  // Đọc dữ liệu trả về từ server

            // Kiểm tra trạng thái thanh toán
            if (result.paymentStatus === 1) {
                // Thanh toán thành công, tiếp tục thực hiện hàm cập nhật hóa đơn
                capNhatHoaDonChay(); // Gọi hàm capNhatHoaDonChay nếu thanh toán thành công
            } else {
                showToast("Thanh toán thất bại!"); // Hiển thị thông báo thất bại
            }
        } else {
            const errorResult = await response.json();  // Đọc lỗi từ server
            console.error("Lỗi từ server: ", errorResult.message);  // Log lỗi
            showToast("Lỗi khi kiểm tra trạng thái thanh toán!");
        }
    } catch (error) {
        // Nếu có lỗi khi gọi API
        console.error('Lỗi kết nối: ', error);
        showToast("Lỗi khi kết nối với server!");
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

function openAddCustomerModal() {
    var modal = new bootstrap.Modal(document.getElementById('addCustomerModal'));
    modal.show();
}

//ThemKhachHang
async function addCustomer() {
    const customerName = document.getElementById("customerName2").value;
    const customerPhone = document.getElementById("customerPhone").value;
    const customerEmail = document.getElementById("customerEmail").value;
    const date = new Date().toISOString();
    // Kiểm tra dữ liệu
    if (!customerName || !customerPhone || !customerEmail) {
        alert("Vui lòng điền đầy đủ thông tin!");
        return;
    }
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(customerEmail)) {
        alert("Email không hợp lệ!");
        return;
    }
    const customerData = {
        full_name: customerName,
        phone_number: customerPhone,
        email: customerEmail,
        create_at: date,
        create_by: "Admin",
        deleted: false
    };
    try {
        // Gửi dữ liệu đến server (API endpoint của bạn)
        const response = await fetch("/khach-hang/them", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(customerData)
        });

        if (response.ok) {
            const customer = await response.json(); // Giả sử server trả về thông tin khách hàng với id
            const customerId = customer.id;
            const userKH = await fetch(`/ban-hang-tai-quay/thong-tin-kh/${customerId}`).then(handleResponse);
            const accountData = {
                username: customerEmail,
                role: "Employee",
                create_at: date,
                create_by: "Admin",
                email: customerEmail,  // Dùng email của khách hàng làm tài khoản
                password: "12345",
                deleted: false,
                status: "Active",
                nguoiDung: userKH // Thêm idKhachHang vào thông tin tài khoản
            };

            try {
                // Gửi dữ liệu tài khoản đến server
                const accountResponse = await fetch("/ban-hang-tai-quay/tao-tai-khoan", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(accountData)
                });

                if (accountResponse.ok) {
                    console.log("Thêm tk thành công");
                } else {
                    console.log("Thêm tk thất bại");
                }
            } catch (error) {
                console.error("Lỗi khi tạo tài khoản:", error);
            }
            // Đóng modal sau khi thêm khách hàng thành công

            $('#addCustomerModal').modal('hide');
            // Reset form sau khi thêm khách hàng
            document.getElementById("addCustomerForm").reset();
            await reloadCustomerComboBox();
            showToast("Thêm Khách Hàng Thành Công!");

        } else {
            alert("Có lỗi khi thêm khách hàng!");
        }
    } catch (error) {
        console.error("Lỗi khi thêm khách hàng:", error);
        alert("Lỗi hệ thống, vui lòng thử lại!");
    }
}

//Load combobox KH
async function reloadCustomerComboBox() {
    try {
        // Gửi yêu cầu đến API để lấy danh sách khách hàng mới
        const response = await fetch("/khach-hang/kh-cbo");
        if (response.ok) {
            const customers = await response.json(); // Giả sử server trả về danh sách khách hàng
            const customerDropdown = document.getElementById("customerDropdown");

            // Xóa toàn bộ option cũ
            customerDropdown.innerHTML = "";

            // Thêm tùy chọn "Tất cả"
            const defaultOption = document.createElement("option");
            defaultOption.value = "all";
            defaultOption.textContent = "Tất cả";
            customerDropdown.appendChild(defaultOption);

            // Thêm lại các option mới từ danh sách khách hàng
            customers.forEach(customer => {
                const option = document.createElement("option");
                option.value = customer.id; // Giá trị tương ứng từ server
                option.textContent = customer.full_name; // Hiển thị tên khách hàng
                customerDropdown.appendChild(option);
            });

            console.log("Combobox khách hàng đã được cập nhật.");
        } else {
            console.error("Không thể tải danh sách khách hàng.");
        }
    } catch (error) {
        console.error("Lỗi khi tải lại danh sách khách hàng:", error);
    }
}

//Đổ tên màu:


// Hàm gọi API để lấy tên màu
function getColorName(colorCode, el) {
    fetch(`https://www.thecolorapi.com/id?hex=${colorCode.substring(1)}`) // Loại bỏ ký tự '#' trước khi gửi API
        .then(response => response.json())
        .then(data => {
            el.textContent = data.name.value; // Cập nhật tên màu vào phần tử span
        })
        .catch(error => {
            console.error("Error fetching color name:", error);
            el.textContent = "Không tìm thấy tên màu"; // Hiển thị lỗi nếu không lấy được tên màu
        });
}












