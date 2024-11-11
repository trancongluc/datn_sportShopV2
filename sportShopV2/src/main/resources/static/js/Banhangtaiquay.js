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
    const toastMessage = document.getElementById('toastMessage');

    toastMessage.textContent = message;
    toast.classList.remove('hide');
    toast.classList.add('show');

    // Ẩn thông báo sau 3 giây
    setTimeout(() => {
        toast.classList.remove('show');
        toast.classList.add('hide');
    }, 3000);
}

const inputField = document.getElementById('sanPhamChiTiet');
const productList = document.getElementById('product-list');
const productRows = productList.getElementsByTagName('tr');

inputField.addEventListener('focus', function () {
    productList.style.display = 'block'; // Hiển thị danh sách sản phẩm
    if (productRows.length === 0) {
        document.getElementById('productList').style.display = 'block'; // Hiện thông báo "Chưa có dữ liệu" nếu không có sản phẩm
    } else {
        document.getElementById('productList').style.display = 'none'; // Ẩn thông báo nếu có sản phẩm
    }
});

// Giấu danh sách sản phẩm nếu người dùng nhấp ra ngoài ô input
document.addEventListener('click', function(event) {
    if (!inputField.contains(event.target) && !productList.contains(event.target)) {
        productList.style.display = 'none'; // Ẩn danh sách sản phẩm
    }
});



