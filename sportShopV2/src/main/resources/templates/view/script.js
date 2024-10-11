document.addEventListener('DOMContentLoaded', () => {
    const customerData = [
        { stt: 1, maKH: 'HO00014', tenKH: 'Nguyen Van A', dienThoai: '0556255653', trangThai: 'Chờ xử lý', giaTri: '4,540,000' },
        { stt: 2, maKH: 'HO00015', tenKH: 'Tran Thi B', dienThoai: '0999000658', trangThai: 'Đang giao', giaTri: '2,798,000' },
        // Add more rows as needed
    ];

    const tableBody = document.querySelector('#customerTable tbody');

    customerData.forEach(customer => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${customer.stt}</td>
            <td>${customer.maKH}</td>
            <td>${customer.tenKH}</td>
            <td>${customer.dienThoai}</td>
            <td>${customer.trangThai}</td>
            <td>${customer.giaTri}</td>
        `;
        tableBody.appendChild(row);
    });
});
