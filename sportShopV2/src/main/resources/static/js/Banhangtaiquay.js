

// Nếu cần thêm các chức năng JavaScript, bạn có thể viết vào đây
// Ví dụ, xử lý sự kiện cho các nút

document.querySelectorAll('.invoice-btn').forEach(button => {
    button.addEventListener('click', () => {
        document.querySelectorAll('.invoice-btn').forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');
        // Thêm logic để chuyển đổi giữa các hóa đơn
    });
});




let invoices = []; // Mảng chứa các hóa đơn
let currentInvoiceId = null; // ID của hóa đơn hiện tại

document.getElementById('create-invoice-btn').addEventListener('click', createInvoice);
// document.getElementById('add-product-btn').addEventListener('click', addProduct);

// Hàm tạo hóa đơn mới
function createInvoice() {
    const invoiceId = `invoice-${invoices.length + 1}`;
    invoices.push({ id: invoiceId, products: [] });

    // Tạo nút hóa đơn mới
    const invoiceBtn = document.createElement('button');
    invoiceBtn.innerText = `Hóa đơn ${invoices.length}`;
    invoiceBtn.classList.add('invoice');
    invoiceBtn.setAttribute('data-id', invoiceId);
    invoiceBtn.addEventListener('click', () => selectInvoice(invoiceId));

    // Tạo nút xóa hóa đơn
    // Thay thế tạo button xóa bằng một SVG icon
    const deleteBtn = document.createElement('button');
    deleteBtn.classList.add('delete-btn');

// Sử dụng SVG icon trash
    deleteBtn.innerHTML = `
    <svg xmlns="http://www.w3.org/2000/svg" height="15" viewBox="0 0 24 24" width="15">
    <path d="M18.3 5.71L12 12.01 5.7 5.71a1 1 0 0 0-1.42 1.42l6.3 6.3-6.3 6.3a1 1 0 0 0 1.42 1.42l6.3-6.3 6.3 6.3a1 1 0 0 0 1.42-1.42l-6.3-6.3 6.3-6.3a1 1 0 0 0-1.42-1.42z"/>
</svg>
`;
    deleteBtn.addEventListener('click', (event) => {
        event.stopPropagation(); // Ngăn không kích hoạt chọn hóa đơn khi bấm "Xóa"
        deleteInvoice(invoiceId);
    });

    const container = document.createElement('div');
    container.appendChild(invoiceBtn);
    container.appendChild(deleteBtn);

    document.getElementById('invoices-container').appendChild(container);

    selectInvoice(invoiceId); // Tự động chọn hóa đơn vừa tạo
}

// Hàm chọn hóa đơn
function selectInvoice(invoiceId) {
    currentInvoiceId = invoiceId;
    document.querySelectorAll('.invoice').forEach(btn => btn.classList.remove('active'));
    document.querySelector(`[data-id="${invoiceId}"]`).classList.add('active');

    renderProducts();
}

// Hàm thêm sản phẩm vào hóa đơn hiện tại
function addProduct() {
    if (!currentInvoiceId) {
        alert('Vui lòng chọn hóa đơn trước!');
        return;
    }

    const productName = prompt('Nhập tên sản phẩm:');
    if (productName) {
        const invoice = invoices.find(inv => inv.id === currentInvoiceId);
        invoice.products.push(productName);
        renderProducts();
    }
}

// Hàm hiển thị sản phẩm trong hóa đơn hiện tại
function renderProducts() {
    const invoice = invoices.find(inv => inv.id === currentInvoiceId);
    const productList = document.getElementById('product-list');
    productList.innerHTML = ''; // Xóa danh sách cũ

    invoice.products.forEach(product => {
        const productItem = document.createElement('div');
        productItem.classList.add('product-item');
        productItem.innerText = product;
        productList.appendChild(productItem);
    });
}

// Hàm xóa hóa đơn
function deleteInvoice(invoiceId) {
    invoices = invoices.filter(inv => inv.id !== invoiceId);
    document.querySelector(`[data-id="${invoiceId}"]`).parentElement.remove();

    if (currentInvoiceId === invoiceId) {
        currentInvoiceId = null;
        document.getElementById('product-list').innerHTML = ''; // Xóa sản phẩm khi không có hóa đơn nào được chọn
    }
}



document.getElementById("select-customer-btn").addEventListener("click", function() {
    // Giả sử bạn đã tìm thấy khách hàng, hiển thị thông tin khách hàng
    var customerName = document.getElementById("search-customer").value;

    // Nếu đã chọn khách hàng (dữ liệu hợp lệ), hiển thị thông tin
    if (customerName) {
        document.getElementById("customer-info").style.display = "block";
        document.getElementById("customer-name").innerText = customerName;

        // Ở đây bạn có thể thêm các giá trị cho email và số điện thoại tương ứng
        document.getElementById("customer-email").innerText = "DungNA29@gmail.com";
        document.getElementById("customer-phone").innerText = "0387811111";
    } else {
        // Nếu chưa chọn khách hàng, ẩn thông tin khách hàng
        document.getElementById("customer-info").style.display = "none";
    }
});

// Lắng nghe sự kiện khi chọn hoặc bỏ chọn checkbox giao hàng
document.getElementById("delivery").addEventListener("change", function() {
    var customerInfoRight = document.getElementById("customer-info-right");

    // Nếu chọn giao hàng, hiển thị thông tin khách hàng bên phải
    if (this.checked) {
        customerInfoRight.style.display = "block";
    } else {
        // Nếu bỏ chọn, ẩn thông tin khách hàng
        customerInfoRight.style.display = "none";
    }
});









// Hiện modal sản phẩm khi nhấn nút "Thêm sản phẩm"
document.querySelector('.add-product-btn').addEventListener('click', function() {
    document.getElementById('product-modal').style.display = 'flex';
});

// Đóng modal khi nhấn nút đóng
document.querySelector('.close').addEventListener('click', function() {
    document.getElementById('product-modal').style.display = 'none';
});

// Đóng modal khi nhấn ra ngoài nó
window.onclick = function(event) {
    const modal = document.getElementById('product-modal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
};

// Thêm sự kiện cho các sản phẩm
document.querySelectorAll('.product-item').forEach(function(item) {
    item.addEventListener('click', function() {
        const productId = this.getAttribute('data-product-id');
        const productName = this.textContent;

        // Thêm sản phẩm đã chọn vào danh sách hóa đơn
        const invoiceList = document.getElementById('invoice-list');
        const li = document.createElement('li');
        li.textContent = productName + " (ID: " + productId + ")";
        invoiceList.appendChild(li);

        // Đóng modal sau khi chọn
        document.getElementById('product-modal').style.display = 'none';
    });
});













document.addEventListener("DOMContentLoaded", function() {
    // Nút thêm khách hàng mới
    const addNewCustomerBtn = document.getElementById("add-new-customer-btn");
    const newCustomerForm = document.getElementById("new-customer-form");
    const customerForm = document.getElementById("customer-form");

    // Kiểm tra sự tồn tại của nút và form
    if (addNewCustomerBtn && newCustomerForm) {
        addNewCustomerBtn.addEventListener("click", function() {
            // Hiển thị form nếu đang ẩn
            if (newCustomerForm.style.display === "none" || newCustomerForm.style.display === "") {
                newCustomerForm.style.display = "block";
            } else {
                newCustomerForm.style.display = "none";
            }
        });
    } else {
        console.error("Nút hoặc form không tồn tại!");
    }

    // Xử lý việc lưu thông tin khách hàng mới (giả lập lưu vào console)
    if (customerForm) {
        customerForm.addEventListener("submit", function(event) {
            event.preventDefault();  // Ngăn form submit mặc định

            // Lấy thông tin từ form
            const hoTen = document.getElementById("hoTen").value;
            const email = document.getElementById("email").value;
            const ngaySinh = document.getElementById("ngaySinh").value;
            const gioiTinh = document.getElementById("gioiTinh").value;
            const soDienThoai = document.getElementById("soDienThoai").value;
            const soNha = document.getElementById("soNha").value;

            // Giả lập lưu khách hàng mới (ví dụ: in ra console)
            console.log("Khách hàng mới:", {
                hoTen: hoTen,
                email: email,
                ngaySinh: ngaySinh,
                gioiTinh: gioiTinh,
                soDienThoai: soDienThoai,
                soNha: soNha
            });

            // Sau khi lưu, ẩn form
            newCustomerForm.style.display = "none";
        });
    } else {
        console.error("Form khách hàng không tồn tại!");
    }

    // Hàm hiển thị hình ảnh preview khi chọn tệp
    function loadImage(event) {
        const image = document.getElementById('image-preview');
        if (image) {
            image.src = URL.createObjectURL(event.target.files[0]);
        } else {
            console.error("Phần tử hình ảnh không tồn tại!");
        }
    }

    // Thêm sự kiện cho input file
    const fileInput = document.getElementById('file-input');
    if (fileInput) {
        fileInput.addEventListener('change', loadImage);
    } else {
        console.error("Input file không tồn tại!");
    }
});
    