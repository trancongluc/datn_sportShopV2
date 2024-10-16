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
document.getElementById('add-product-btn').addEventListener('click', addProduct);

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
