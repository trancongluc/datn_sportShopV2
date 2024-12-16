document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll("td.discountValue").forEach(td => {
        const formVoucher = td.getAttribute("data-form-voucher");
        let value = parseFloat(td.textContent.replace(",", ".").trim());

        if (isNaN(value)) {
            console.error("Giá trị không hợp lệ:", td.textContent);
            return;
        }

        if (formVoucher === "Phần trăm") {
            td.textContent = value + "%";
        } else if (formVoucher === "Tiền mặt") {
            td.textContent = formatCurrency(value);
        }
    });

    document.querySelectorAll("td.minimumValue").forEach(td => {
        let value = parseFloat(td.textContent.replace(",", ".").trim());

        if (isNaN(value)) {
            console.error("Giá trị không hợp lệ:", td.textContent);
            return;
        }

        td.textContent = formatCurrency(value);
    });
});

function formatCurrency(value) {
    return new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND"
    }).format(value).replace("₫", "").trim() +" đ";
}
