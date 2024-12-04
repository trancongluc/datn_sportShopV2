function showQuantityInput(itemId, productName, productImage, productColor, productPrice) {
    // Ẩn tất cả các ô nhập số lượng khác
    const inputs = document.querySelectorAll('[id^="quantity-input-"]');
    inputs.forEach(input => {
        input.style.display = 'none';
    });

    // Hiển thị ô nhập số lượng cho sản phẩm được chọn
    const quantityInput = document.getElementById(`quantity-input-${itemId}`);
    if (quantityInput) {
        quantityInput.style.display = 'block';
    }
    // Lưu thông tin sản phẩm vào thuộc tính của ô nhập số lượng
    quantityInput.setAttribute('data-product-name', productName);
    quantityInput.setAttribute('data-product-image', productImage);
    quantityInput.setAttribute('data-product-color', productColor);
    quantityInput.setAttribute('data-product-price', productPrice);
}

function addReturnItem(itemId) {
    const quantityInput = document.getElementById(`quantity-input-${itemId}-input`);
    const quantity = quantityInput.value;

    if (quantity && quantity > 0) {
        // Lấy thông tin sản phẩm từ thuộc tính của ô nhập số lượng
        const productName = quantityInput.getAttribute('data-product-name');
        const productImage = quantityInput.getAttribute('data-product-image');
        const productColor = quantityInput.getAttribute('data-product-color');
        const productPrice = quantityInput.getAttribute('data-product-price');

        // Thêm thông tin sản phẩm vào bảng trả hàng
        const returnItemsTable = document.getElementById('return-items');
        const newRow = returnItemsTable.insertRow();

        newRow.innerHTML = `
            <td>${returnItemsTable.rows.length}</td>
            <td><img src="${productImage}" alt="Product Image" /></td>
            <td>${productName}</td>
            <td><div style="width: 25px; height: 25px; border-radius: 50%; background-color: ${productColor};"></div></td>
            <td>${quantity}</td>
            <td>${productPrice} VND</td>
            <td>${(productPrice * quantity)} VND</td>
            <td><button onclick="removeItem(this)">Xóa</button></td>
        `;

        // Reset ô nhập số lượng
        quantityInput.value = '';
        quantityInput.parentElement.style.display = 'none';
    } else {
        alert('Vui lòng nhập số lượng hợp lệ.');
    }
}
function removeItem(button) {
    // Xóa hàng trong bảng trả hàng
    const row = button.parentElement.parentElement;
    row.parentElement.removeChild(row);
}