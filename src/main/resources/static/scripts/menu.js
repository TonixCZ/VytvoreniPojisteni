function toggleMenu() {
    var menu = document.getElementById("userMenu");
    if (menu) {
        menu.classList.toggle("show");
    } else {
        console.error("Element s id 'userMenu' nebyl nalezen.");
    }
}
