let lastScrollTop = 0;
window.addEventListener("scroll", function () {
    let navbar = document.querySelector(".navbar");
    let scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    if (scrollTop > lastScrollTop) {
        // Scroll down
        navbar.style.top = "-60px"; // Adjust this value based on your navbar height
    } else {
        // Scroll up
        navbar.style.top = "0";
    }
    lastScrollTop = scrollTop;
});


document.addEventListener("DOMContentLoaded", function() {
    const menuItems = document.querySelectorAll(".navbar .menu a");
    menuItems.forEach(item => {
        item.addEventListener("click", function() {
            menuItems.forEach(i => i.classList.remove("active"));
            this.classList.add("active");
        });
    });
});