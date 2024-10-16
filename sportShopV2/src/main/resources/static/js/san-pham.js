function triggerFileInput(button) {
    var fileInput = button.nextElementSibling;
    fileInput.click();
}

function handleFileSelect(input) {
    var files = input.files;
    var uploadedImagesContainer = input.nextElementSibling;
    uploadedImagesContainer.innerHTML = "";
    for (var i = 0; i < files.length && i < 6; i++) {
        var file = files[i];
        var reader = new FileReader();
        reader.onload = function(e) {
            var imgWrapper = document.createElement("div");
            imgWrapper.classList.add("image-wrapper");
            var img = document.createElement("img");
            img.src = e.target.result;
            imgWrapper.appendChild(img);

            var overlay = document.createElement("div");
            overlay.classList.add("image-overlay");
            var viewIcon = document.createElement("i");
            viewIcon.classList.add("fas", "fa-eye");
            viewIcon.onclick = function() {
                openModal(e.target.result);
            };
            var deleteIcon = document.createElement("i");
            deleteIcon.classList.add("fas", "fa-trash-alt");
            deleteIcon.onclick = function() {
                imgWrapper.remove();
            };
            overlay.appendChild(viewIcon);
            overlay.appendChild(deleteIcon);
            imgWrapper.appendChild(overlay);

            uploadedImagesContainer.appendChild(imgWrapper);
        }
        reader.readAsDataURL(file);
    }
    arrangeImages(uploadedImagesContainer);
}

function arrangeImages(container) {
    var images = container.querySelectorAll(".image-wrapper");
    container.innerHTML = "";
    var row;
    for (var i = 0; i < images.length; i++) {
        if (i % 3 === 0) {
            row = document.createElement("div");
            row.classList.add("image-row");
            container.appendChild(row);
        }
        row.appendChild(images[i]);
    }
}

function openModal(src) {
    var modal = document.getElementById("myModal");
    var modalImg = document.getElementById("img01");
    modal.style.display = "block";
    modalImg.src = src;
}

function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}

document.getElementById("selectAll").addEventListener("change", function() {
    var checkboxes = document.querySelectorAll(".selectItem");
    for (var checkbox of checkboxes) {
        checkbox.checked = this.checked;
    }
});