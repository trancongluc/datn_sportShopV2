const selectedIds = new Set(); // Biến lưu trữ các idHDCT đã chọn
const addedItems = new Map(); // Biến lưu trữ các idHDCT và số lượng đã thêm vào bảng cart
function formatCurrency(value) {
    return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(value);
}

function parseCurrency(formattedValue) {
    // Loại bỏ ký hiệu tiền tệ và thay thế dấu phân cách hàng ngàn (.) thành rỗng
    const numericValue = formattedValue.replace(/[^\d.-]/g, '').replace(/\./g, '');
    return parseFloat(numericValue); // Chuyển thành số thực
}

// Format giá sản phẩm
document.querySelectorAll('#price').forEach(element => {
    const rawValue = parseFloat(element.textContent); // Lấy giá trị gốc
    if (!isNaN(rawValue)) {
        element.textContent = formatCurrency(rawValue); // Gán lại giá trị đã format
    }
});

// Format tổng tiền
document.querySelectorAll('#totalPayment').forEach(element => {
    const rawValue = parseFloat(element.textContent); // Lấy giá trị gốc
    if (!isNaN(rawValue)) {
        element.textContent = formatCurrency(rawValue); // Gán lại giá trị đã format
    }
});


// Hàm mở modal và xử lý idHDCT
function openModal(id, rowElement) {
    const idHDCT = rowElement.getAttribute('data-idHDCT');
    const quantityHDCT = parseInt(rowElement.querySelector('#totalQuantity').textContent); // Lấy số lượng còn lại

    // Kiểm tra xem sản phẩm đã được chọn chưa
    if (selectedIds.has(idHDCT)) {
        console.log(`idHDCT ${idHDCT} đã được chọn trước đó.`);
    } else {
        selectedIds.add(idHDCT);
        console.log('Danh sách idHDCT đã chọn:', Array.from(selectedIds));
    }

    // Hiển thị modal
    const modal = document.getElementById('myModal');
    modal.style.display = 'block';

    // Lưu idHDCT vào modal để sử dụng sau
    modal.dataset.idHDCT = idHDCT;

    // Cập nhật số lượng tối đa có thể chọn trong modal
    const quantityInput = document.getElementById('quantity');
    quantityInput.setAttribute('max', quantityHDCT); // Đặt max là số lượng còn lại

}

// Hàm đóng modal
function closeModal() {
    const modal = document.getElementById('myModal');
    modal.style.display = 'none';
}
function showSummary() {
    const summary = document.getElementById('summary');
    summary.style.display = 'block';
}
// Hàm thêm sản phẩm vào bảng cart-table
function addItem() {
    const modal = document.getElementById('myModal');
    const idHDCT = modal.dataset.idHDCT;
    const quantityInput = document.getElementById('quantity');
    const quantity = parseInt(quantityInput.value);
    const row = document.querySelector(`[data-idHDCT="${idHDCT}"]`);
    const quantityHDCT = parseInt(row.querySelector('#totalQuantity').textContent); // Số lượng còn lại trong order-table

    // Kiểm tra nếu số lượng hợp lệ
    if (!quantity || quantity <= 0) {
        alert("Vui lòng nhập số lượng hợp lệ.");
        return;
    }

    if (quantity > quantityHDCT) {
        alert('Số lượng nhập không được lớn hơn số lượng hiện có.');
        return;
    }

    // Kiểm tra nếu sản phẩm đã được thêm
    if (!addedItems.has(idHDCT)) {
        addedItems.set(idHDCT, 0); // Nếu chưa thêm, khởi tạo với số lượng 0
    }

    // Cập nhật số lượng đã thêm vào cart
    const currentQuantityInCart = addedItems.get(idHDCT);
    const newQuantityInCart = currentQuantityInCart + quantity;
    addedItems.set(idHDCT, newQuantityInCart);

    // Lấy thông tin dòng từ bảng order-table
    const productName = row.querySelector('p[data-idSPCT]').textContent;
    const color = row.querySelector('div').style.backgroundColor;
    const price = row.querySelector('p[style]').textContent.trim().split(" ")[0]; // Lấy giá
    const parsedPrice = parseCurrency(price); // Chuyển đổi giá thành số
    const total = formatCurrency((newQuantityInCart * parsedPrice)); // Tính tổng tiền

    // Cập nhật bảng cart-table
    const cartTableBody = document.getElementById('return-items');
    let cartRow = document.querySelector(`#return-items tr[data-idHDCT="${idHDCT}"]`);

    if (cartRow) {
        // Nếu sản phẩm đã có trong cart-table, cập nhật số lượng và tổng tiền
        cartRow.querySelector('td:nth-child(4)').textContent = newQuantityInCart;
        cartRow.querySelector('td:nth-child(6)').textContent = total;
    } else {
        // Nếu sản phẩm chưa có trong cart-table, tạo một dòng mới
        cartRow = document.createElement('tr');
        cartRow.setAttribute('data-idHDCT', idHDCT);
        cartRow.innerHTML = `
           
            <td>${productName}</td>
            <td>
                <div style="width: 25px; height: 25px; border-radius: 50%; background-color: ${color};"></div>
               
            </td>
            <td id="totalNewQuantity">${newQuantityInCart}</td>
            <td>${price}</td>
            <td class="total-return">${total}</td>
            <td>
                <button onclick="removeItem('${idHDCT}', this)">Xóa</button>
            </td>
        `;
        cartTableBody.appendChild(cartRow);

    }

    // Trừ số lượng trong bảng order-table
    row.querySelector('#totalQuantity').textContent = quantityHDCT - quantity;

    // Cập nhật tổng tiền trong bảng order-table
    const totalPriceElement = row.querySelector('#totalPayment'); // Lấy phần tử tổng tiền trong dòng hiện tại

    // Kiểm tra nếu totalPriceElement không phải null
    if (totalPriceElement) {
        const updatedTotal = (quantityHDCT - quantity) * parsedPrice;
        totalPriceElement.textContent = formatCurrency(updatedTotal); // Cập nhật lại tổng tiền
    } else {
        console.error("Không tìm thấy phần tử tổng tiền trong dòng này.");
    }

    // Kiểm tra nếu số lượng trong order-table = 0, ẩn nút Thêm
    if (quantityHDCT - quantity <= 0) {
        row.querySelector('.action button').disabled = true;
    }
    console.log(addedItems);
    showSummary();
    // Đóng modal
    closeModal();
    tongTienCartTable();
}

// Hàm xóa sản phẩm khỏi bảng cart-table
function removeItem(idHDCT, button) {
    // Lấy dòng hiện tại trong bảng cart-table
    const row = button.parentNode.parentNode;
    const quantityInCart = parseInt(row.querySelector('td:nth-child(3)').textContent); // Lấy số lượng trong cart-table

    // Kiểm tra số lượng trong cart-table
    console.log(`Số lượng trong cart-table khi xóa: ${quantityInCart}`);

    // Xóa dòng khỏi bảng cart-table
    row.parentNode.removeChild(row);

    // Xóa idHDCT khỏi danh sách addedItems
    addedItems.delete(idHDCT);

    // Khôi phục số lượng trong bảng order-table
    const orderRow = document.querySelector(`[data-idHDCT="${idHDCT}"]`);
    if (orderRow) {
        const quantityHDCT = parseInt(orderRow.querySelector('#totalQuantity').textContent);
        const updatedQuantity = quantityHDCT + quantityInCart; // Cộng lại số lượng đã xóa

        // Kiểm tra số lượng trước và sau khi cộng
        console.log(`Số lượng trong order-table trước khi cập nhật: ${quantityHDCT}`);
        console.log(`Số lượng trong order-table sau khi cộng: ${updatedQuantity}`);

        // Cập nhật lại số lượng trong bảng order-table
        orderRow.querySelector('#totalQuantity').textContent = updatedQuantity; // Cập nhật lại số lượng

        // Cập nhật lại tổng tiền trong bảng order-table
        const price = orderRow.querySelector('p[style]').textContent.trim().split(" ")[0]; // Lấy giá
        const parsedPrice = parseCurrency(price); // Hàm parseCurrency dùng để chuyển giá từ string thành số
        const updatedTotal = updatedQuantity * parsedPrice; // Cập nhật tổng tiền

        const totalPriceElement = orderRow.querySelector('#totalPayment');
        totalPriceElement.textContent = formatCurrency(updatedTotal); // Cập nhật lại tổng tiền

        // Kiểm tra lại nút Thêm nếu số lượng trong order-table > 0
        const actionButton = orderRow.querySelector('.action button');
        if (updatedQuantity > 0) {
            actionButton.disabled = false;
        } else {
            actionButton.disabled = true;
        }
    }

    // Cập nhật lại tổng số tiền hoặc các thông tin cần thiết ở phần summary nếu có
    showSummary();
    tongTienCartTable();
}

// Lắng nghe sự kiện click vào nút "Trả hàng tất cả"
document.getElementById('return-all-btn').addEventListener('click', function() {
    // Lấy tất cả các dòng chi tiết hóa đơn (HDCT) trong bảng
    const rows = document.querySelectorAll('[data-idHDCT]');

    // Lặp qua tất cả các chi tiết hóa đơn và thêm chúng vào bảng giỏ hàng
    rows.forEach(function(row) {
        const idHDCT = row.getAttribute('data-idHDCT');
        const quantityHDCT = parseInt(row.querySelector('#totalQuantity').textContent); // Số lượng còn lại trong order-table

        // Gọi hàm addItem để thêm từng sản phẩm vào giỏ hàng
        // Giả sử bạn có sẵn hàm addItem và hàm này sẽ xử lý tất cả
        addItemFromRow(row, quantityHDCT);
    });
});

// Hàm thêm một sản phẩm từ dòng HDCT vào giỏ hàng
function addItemFromRow(row, quantityHDCT) {
    const idHDCT = row.getAttribute('data-idHDCT');
    const productName = row.querySelector('p[data-idSPCT]').textContent;
    const color = row.querySelector('div').style.backgroundColor;
    const price = row.querySelector('#price').textContent.trim(); // Lấy giá
    const parsedPrice = parseCurrency(price); // Chuyển đổi giá thành số
    const total = formatCurrency((quantityHDCT * parsedPrice)); // Tính tổng tiền

    // Cập nhật bảng cart-table
    const cartTableBody = document.getElementById('return-items');
    let cartRow = document.querySelector(`#return-items tr[data-idHDCT="${idHDCT}"]`);

    if (cartRow) {
        // Nếu sản phẩm đã có trong cart-table, chỉ cập nhật số lượng và tổng tiền
        const existingQuantity = parseInt(cartRow.querySelector('td:nth-child(3)').textContent); // Lấy số lượng cũ
        const updatedQuantity = existingQuantity + quantityHDCT; // Cập nhật số lượng
        const updatedTotal = formatCurrency(updatedQuantity * parsedPrice); // Tính lại tổng tiền

        // Cập nhật lại số lượng và tổng tiền mà không thay đổi cấu trúc dòng
        cartRow.querySelector('td:nth-child(3)').textContent = updatedQuantity;
        cartRow.querySelector('td:nth-child(6)').textContent = updatedTotal;
    } else {
        // Nếu sản phẩm chưa có trong cart-table, tạo một dòng mới
        cartRow = document.createElement('tr');
        cartRow.setAttribute('data-idHDCT', idHDCT);
        cartRow.innerHTML = `
            <td>${productName}</td>
            <td>
                <div style="width: 25px; height: 25px; border-radius: 50%; background-color: ${color};"></div>
            </td>
            <td>${quantityHDCT}</td>
            <td>${price}</td>
            <td class="total-return">${total}</td>
            <td>
                <button onclick="removeItem('${idHDCT}', this)">Xóa</button>
            </td>
        `;
        cartTableBody.appendChild(cartRow);
    }

    // Cập nhật số lượng trong bảng order-table
    row.querySelector('#totalPayment').textContent = 0;
    row.querySelector('#totalQuantity').textContent = 0;
    const originalQuantity = parseInt(row.querySelector('#totalQuantity').textContent) || 0;
    if (quantityHDCT === originalQuantity) {
        row.setAttribute('data-return-status', 'full'); // Đánh dấu hoàn trả toàn phần
    } else {
        row.setAttribute('data-return-status', 'partial'); // Hoàn trả một phần
    }
    // Ẩn nút Thêm nếu số lượng trong order-table = 0
    row.querySelector('.action button').disabled = true;
    showSummary();
    tongTienCartTable();
}
function showConfirmation() {
    const modal = $('#confirmationModal');
    const modalMessage = $('#modalMessage');
    modalMessage.text(`Bạn có chắc muốn đổi trả hóa đơn?`); // Sử dụng đúng ký tự tiếng Việt
    modal.css('display', 'block');
    setTimeout(() => {
        modal.css('opacity', '1');
        $('.modal-content').css('transform', 'scale(1)');
    }, 10);
}
function closeConfirmationModal(modalId) {
    // Đóng modal
    const modal = document.querySelector(modalId);
    modal.style.display = 'none';

}
// Logic kiểm tra hoàn trả toàn phần
function checkFullReturnStatus() {
    const orderRows = document.querySelectorAll(".order-table tbody tr");  // Lấy tất cả các dòng trong bảng order-table
    let allProductsReturned = true; // Biến kiểm tra xem tất cả sản phẩm có hoàn trả toàn phần hay không

    orderRows.forEach(row => {
        const quantity = parseInt(row.querySelector('#totalQuantity').textContent); // Lấy số lượng của sản phẩm trong bảng hóa đơn

        if (quantity === 0) {
            // Nếu số lượng sản phẩm là 0, đánh dấu hoàn trả toàn phần
            row.setAttribute('data-return-status', 'full'); // Đánh dấu là hoàn trả toàn phần
        } else {
            // Nếu số lượng khác 0, đánh dấu là hoàn trả một phần
            row.setAttribute('data-return-status', 'partial'); // Đánh dấu là hoàn trả một phần
            allProductsReturned = false; // Nếu có sản phẩm chưa hoàn trả hết, đánh dấu không phải toàn phần
        }
    });

    if (allProductsReturned) {
        // Cập nhật hóa đơn là hoàn trả
        hoanTraToanPhan();
    } else {
        // Thực hiện hành động khác nếu không phải hoàn trả toàn phần
        hoanTraMotPhan();
    }
}

let tongTienHoanTra =0;
function hoanTraToanPhan() {
    const hoaDonId = document.querySelector('input[name="maHoaDon"]').value; // Lấy mã hóa đơn
    const moTa = document.querySelector('#moTa').value.trim(); // Lấy mô tả từ textarea
    // Kiểm tra nếu mô tả không rỗng
    if (moTa === '') {
        alert('Vui lòng nhập mô tả!');
        return;
    }

    // Tạo đối tượng để gửi lên server
    const updatedHoaDon = {
        id: hoaDonId,
        status: 'Hoàn trả', // Trạng thái hoàn trả
        note: moTa // Mô tả
    };

    // Gửi request cập nhật hóa đơn (sử dụng fetch hoặc axios)
    fetch(`/doi-tra/updateHD/${hoaDonId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedHoaDon),
    })
        .then(response => response.json()) // Chuyển đổi phản hồi thành JSON
        .then(data => {
            if (data.status === "success") {
                alert(data.message); // Hiển thị thông báo thành công
                window.location.href = "/doi-tra/view";
            } else {
                alert(data.message); // Hiển thị thông báo lỗi
            }
        })
        .catch(error => {
            console.error('Đã xảy ra lỗi khi cập nhật hóa đơn:', error.message);
            alert('Lỗi khi cập nhật hóa đơn: ' + error.message);
        });
}
function tongTienCartTable() {
    // Lấy tất cả các ô có class "total-price"
    const totalPrices = document.querySelectorAll("#return-items .total-return");
    let total = 0;

    // Duyệt qua các ô và cộng dồn giá trị
    totalPrices.forEach(priceCell => {
        total += parseCurrency(priceCell.textContent.trim());
    });

    // Hiển thị tổng tiền
    console.log("Tổng tiền:", total);
    const tienTraKhachElement = document.getElementById("tien-tra-khach");
    tienTraKhachElement.textContent = formatCurrency(total);
    return total;
}
async function getHoaDonById(oldId) {
    try {

        const response = await fetch(`/ban-hang-tai-quay/hd/${oldId}`);

        // Kiểm tra nội dung của response
        const responseText = await response.text(); // Lấy nội dung thô của response
        console.log("Response nhận được:", responseText); // Hiển thị trong console

        if (!response.ok) throw new Error('Không tìm thấy hóa đơn!');

        // Chuyển đổi response thành JSON
        return JSON.parse(responseText); // Dùng JSON.parse nếu bạn muốn kiểm tra lỗi trong quá trình phân tích
    } catch (error) {
        console.error('Lỗi khi lấy hóa đơn:', error.message);
        throw error;
    }
}

// Hàm tạo hóa đơn mới
async function createNewHoaDon(newHoaDon) {
    try {
        const response = await fetch('/ban-hang-tai-quay/tao-hoa-don', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newHoaDon), // Gửi dữ liệu dưới dạng JSON
        });
        if (!response.ok) throw new Error('Không thể tạo hóa đơn mới!');
        return await response.json(); // Trả về dữ liệu hóa đơn mới
    } catch (error) {
        console.error('Lỗi khi tạo hóa đơn mới:', error.message);
        throw error;
    }
}
// Hàm sao chép hóa đơn
let tongTienHDMoi =0;
async function hoanTraMotPhan() {
    const oldId = document.querySelector('input[name="maHoaDon"]').value;
    console.log("idHD: " + oldId);
    const tongTienHoanTra = tongTienCartTable();
    let transaction_date = null;
    let receive_date = null;
    let status = "Chờ xác nhận";
    const dateNow = new Date();
    const moTa = document.querySelector('#moTa').value.trim();

    if (moTa === '') {
        alert('Vui lòng nhập mô tả!');
        return;
    }

    try {
        // Lấy hóa đơn cũ
        const hoaDonCu = await getHoaDonById(oldId);

        if (hoaDonCu.type === "Tại Quầy") {
            transaction_date = dateNow;
            receive_date = dateNow;
            status = "Hoàn thành";
        }

        // Tạo hóa đơn mới
        const hoaDonMoi = {
            total_money: tongTienHDMoi,
            money_reduced: 0,
            money_ship: 0,
            id_account: hoaDonCu.id_account,
            id_staff: hoaDonCu.id_staff,
            user_name: hoaDonCu.user_name,
            phone_number: hoaDonCu.phone_number,
            status: status,

            address: hoaDonCu.address,
            ship_date: null,
            receive_date: receive_date,
            confirmation_date: null,
            desire_date: null,
            pay_method: null,
            pay_status: null,
            billCode: `HD-${Date.now()}`,
            transaction_date: transaction_date,
            email: hoaDonCu.email,
            type: hoaDonCu.type,
            note: moTa,
        };

        const hoaDonMoiResponse = await createNewHoaDon(hoaDonMoi);

        if (!hoaDonMoiResponse || !hoaDonMoiResponse.id) {
            throw new Error('Hóa đơn mới tạo không hợp lệ!');
        }

        console.log('Hóa đơn mới đã được tạo:', hoaDonMoiResponse);

        // Tạo hóa đơn chi tiết mới
        await taoHDCTMoiTuAddedItems(hoaDonMoiResponse, hoaDonCu);
        alert("Đổi trả thành công!");

    } catch (error) {
        console.error('Lỗi khi hoàn trả:', error.message);
        alert('Có lỗi xảy ra, vui lòng thử lại!');
    }
}

// Cập nhật Số Lượng của HDCT và Tổng Tiền của HD
async function taoHDCTMoiTuAddedItems(hoaDonNew, hoaDonCu) {
    const oldId = document.querySelector('input[name="maHoaDon"]').value;
    let totalPriceNew = 0; // Tổng tiền hóa đơn mới chỉ tính sản phẩm còn hàng
    if (addedItems.size === 0) {
        alert('Không có sản phẩm nào để hoàn trả!');
        return;
    }

    try {
        const idHDCTList = Array.from(addedItems.keys()).map(Number); // Chuyển các key từ string sang number
        console.log("idHDCT", idHDCTList);

        const response = await fetch(`/doi-tra/thong-tin/?idHDCT=${encodeURIComponent(idHDCTList.join(","))}`);
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Không thể lấy danh sách HDCT cũ: ${response.status} - ${errorText}`);
        }

        const hdctCuList = await response.json();
        console.log("Dữ liệu nhận được từ API:", hdctCuList);

        let totalPriceReduction = 0; // Tổng tiền giảm từ sản phẩm hết hàng
        const hdctMoiList = [];

        for (const hdct of hdctCuList) {
            const idKey = hdct.id.toString();
            const quantityToReduce = addedItems.get(idKey);

            if (!quantityToReduce || quantityToReduce <= 0) {
                console.error(`Số lượng không hợp lệ cho key ${idKey}`);
                continue;
            }

            const productId = hdct.sanPhamChiTiet.id;
            const productDetails = await fetch(`/san-pham-chi-tiet/thong-tin-spct/${productId}`).then(handleResponse);
            const newSoLuong = productDetails.soLuong - quantityToReduce;

            if (productDetails.soLuong <= 0 || productDetails.soLuong < quantityToReduce) {
                totalPriceReduction += hdct.price * quantityToReduce;
                const giaGiam = formatCurrency(hdct.price * quantityToReduce);
                alert(`Sản phẩm ID ${productId} không đủ số lượng. Vui lòng trả ${giaGiam} cho khách.`);
                // Cập nhật HDCT cũ và giảm tổng tiền hóa đơn cũ
                await fetch(`/doi-tra/updateHDCT/${hdct.id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ ...hdct, quantity: hdct.quantity - quantityToReduce })
                });
                hoaDonCu.total_money -= totalPriceReduction;
                await fetch(`/doi-tra/update-hoa-don/${hoaDonCu.id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        ...hoaDonCu,
                        total_money: hoaDonCu.total_money,
                        note: "Sản phẩm không đủ số lượng. Hoàn trả tiền!",
                        status: "Hoàn thành"
                    })
                });

                continue; // Bỏ qua sản phẩm này vì hết hàng
            }

            // Tính tổng tiền hóa đơn mới cho sản phẩm còn hàng
            totalPriceNew += hdct.price * quantityToReduce;

            // Cập nhật số lượng sản phẩm chi tiết
            const updateResponse = await fetch(`/san-pham-chi-tiet/cap-nhat-so-luong/${productId}?soLuongNew=${newSoLuong}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' }
            });

            if (!updateResponse.ok) {
                const errorText = await updateResponse.text();
                console.error(`Không thể cập nhật số lượng cho SPCT ${productId}: ${updateResponse.status} - ${errorText}`);
                continue;
            }

            console.log(`Cập nhật số lượng cho SPCT ${productId} thành công`);

            // Tạo HDCT mới
            hdctMoiList.push({
                hoaDon: hoaDonNew,
                sanPhamChiTiet: hdct.sanPhamChiTiet,
                quantity: quantityToReduce,
                price: hdct.price
            });

            // Cập nhật số lượng HDCT cũ
            await fetch(`/doi-tra/updateHDCT/${hdct.id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    ...hdct,
                    quantity: hdct.quantity - quantityToReduce
                })
            });
        }

        // Cập nhật tổng tiền của hóa đơn cũ
        await fetch(`/doi-tra/update-hoa-don/${hoaDonCu.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                ...hoaDonCu,
                total_money: hoaDonCu.total_money -totalPriceNew,
                note: "Hóa đơn có sản phẩm hoàn trả",
                status: "Hoàn thành"
            })
        });

        console.log('Cập nhật tổng tiền của hóa đơn cũ thành công!');

        // Cập nhật tổng tiền hóa đơn mới
        hoaDonNew.total_money = totalPriceNew;
        console.log("Tong tien new",totalPriceNew);
        if (hdctMoiList.length > 0) {
            // Tạo hóa đơn chi tiết mới
            const createResponse = await fetch('/ban-hang-tai-quay/tao-hoa-don-chi-tiet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(hdctMoiList),
            });

            if (!createResponse.ok) {
                const errorText = await createResponse.text();
                throw new Error(`Không thể tạo HDCT mới: ${createResponse.status} - ${errorText}`);
            }

            console.log('Tạo hóa đơn chi tiết mới thành công!');
        }

        // Cập nhật hóa đơn mới
        await fetch(`/doi-tra/update-hoa-don/${hoaDonNew.id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                ...hoaDonNew,
                total_money: hoaDonNew.total_money,
                note: "Đổi trả thành công",
                status: hoaDonNew.type === 'Tại Quầy' ? 'Hoàn thành' : 'Chờ xác nhận'
            })
        });

        closeModal();
        window.location.href = "/doi-tra/view";
    } catch (error) {
        console.error('Lỗi khi tạo HDCT mới:', error.message);
        alert('Có lỗi khi tạo HDCT mới, vui lòng thử lại!');
    }
}

function handleResponse(response) {
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status} - ${response.statusText}`);
    }
    return response.json();
}
//tongSoluongSPCT














