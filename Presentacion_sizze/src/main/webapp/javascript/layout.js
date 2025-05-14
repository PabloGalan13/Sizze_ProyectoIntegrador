document.addEventListener("DOMContentLoaded", async function () {
    await cargarLayout();
});

async function cargarLayout() {
    const headerContainer = document.getElementById("header-container");
    const sidebarContainer = document.getElementById("sidebar-container");

    if (headerContainer) {
        const header = await fetch("../html/header.html");
        headerContainer.innerHTML = await header.text();
    }

    if (sidebarContainer) {
        const sidebar = await fetch("../html/sidebar.html");
        sidebarContainer.innerHTML = await sidebar.text();
    }

    // ✅ Ejecutar esto después de cargar el layout
    configurarSidebar();
    marcarEnlaceActivo();
}

function configurarSidebar() {
    const btnAbrir = document.querySelector(".menu i");
    const btnCerrar = document.getElementById("btnCerrarSidebar");
    const sidebar = document.getElementById("sidebar");
    const overlay = document.getElementById("overlay");

    if (btnAbrir) {
        btnAbrir.addEventListener("click", () => {
            sidebar.classList.add("visible");
            overlay.classList.add("visible");
            overlay.classList.remove("hidden");
        });
    }

    if (btnCerrar) {
        btnCerrar.addEventListener("click", () => {
            sidebar.classList.remove("visible");
            overlay.classList.remove("visible");
            overlay.classList.add("hidden");
        });
    }

    if (overlay) {
        overlay.addEventListener("click", () => {
            sidebar.classList.remove("visible");
            overlay.classList.remove("visible");
            overlay.classList.add("hidden");
        });
    }
}

// ✅ Marca el enlace actual como activo
function marcarEnlaceActivo() {
    setTimeout(() => {
        const currentPage = window.location.pathname.split("/").pop();
        const links = document.querySelectorAll(".sidebar ul li a");

        links.forEach(link => {
            if (link.getAttribute("href") === currentPage) {
                link.classList.add("activo");
            }
        });
    }, 0);
}
