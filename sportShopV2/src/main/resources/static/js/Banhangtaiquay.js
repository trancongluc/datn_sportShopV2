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
        customer: `Khách hàng của Hóa Đơn ${invoiceCount}`
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

    document.getElementById('productList').textContent = invoiceData[invoiceNumber].products;
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
