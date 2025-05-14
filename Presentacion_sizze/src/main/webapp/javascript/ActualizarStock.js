document.addEventListener("DOMContentLoaded", function () {
    const contenedorProductos = document.getElementById("contenedorProductos");
    const modal = document.getElementById("modalProducto");
    const modalImg = document.getElementById("modalImg");
    const modalNombre = document.getElementById("modalNombre");
    const modalDesc = document.getElementById("modalDesc");
    const cantidadSpan = document.getElementById("cantidad");
    const btnSumar = document.getElementById("btnSumar");
    const btnRestar = document.getElementById("btnRestar");
    const btnActualizar = document.getElementById("btnActualizar");
    const closeBtn = modal.querySelector(".close");

    const modalConfirmacion = document.getElementById("modalConfirmacion");
    const confirmNombre = document.getElementById("confirmNombre");
    const confirmCantidad = document.getElementById("confirmCantidad");
    const confirmarCambios = document.getElementById("confirmarCambios");
    const cancelarCambios = document.getElementById("cancelarCambios");

    const modalExito = document.getElementById("modalExito");
    const cerrarExito = document.getElementById("cerrarExito");

    let cantidad = 0;
    let productoSeleccionado = null;
    const modalError = document.getElementById("modalError");
    const mensajeError = document.getElementById("mensajeError");
    const cerrarError = document.getElementById("cerrarError");

    // Mostrar modal de error
    function mostrarError(mensaje) {
        mensajeError.textContent = mensaje;
        modalError.classList.remove("hidden");
    }

    // Cerrar modal de error
    cerrarError.addEventListener("click", () => {
        modalError.classList.add("hidden");
        location.reload();
    });

//    // Cargar productos desde el backend
//    fetch("../ConsultaInventario")
//            .then(res => res.json())
//            .then(data => {
//                data.forEach(prod => {
//                    const div = document.createElement("div");
//                    div.className = "producto";
//                    div.innerHTML = `
//          <img src="../img/${prod.imagen}" alt="${prod.nombre}">
//          <a href="#">${prod.nombre}</a>
//        `;
//                    div.addEventListener("click", () => {
//                        modalImg.src = `../img/${prod.imagen}`;
//                        modalNombre.textContent = prod.nombre;
//                        modalDesc.textContent = prod.descripcion || "Descripción del producto.";
//                        cantidad = prod.stock;
//                        cantidadSpan.textContent = cantidad;
//                        productoSeleccionado = prod;
//                        modal.classList.remove("hidden");
//                    });
//                    contenedorProductos.appendChild(div);
//                });
//            })
//            .catch(err => {
//                console.error("Error cargando productos:", err);
//                mostrarError("No se pudo actualizar el stock. Intenta más tarde.");
//            });

    // ⚠️ Usa esto solo temporalmente hasta que tu compañero termine el servlet
    const productosSimulados = [
        {id: 1, nombre: "Martillo", descripcion: "Herramienta de carpintería", stock: 12, imagen: "martillo.png"},
        {id: 2, nombre: "Destornillador", descripcion: "Herramienta para tornillos", stock: 8, imagen: "destornillador.png"},
        {id: 3, nombre: "Serrucho", descripcion: "Herramienta de corte manual", stock: 5, imagen: "serrucho.png"}
    ];

    // Puedes reemplazar esta parte con fetch cuando esté listo el backend
    productosSimulados.forEach(prod => {
        const div = document.createElement("div");
        div.className = "producto";
        div.innerHTML = `<img src="../img/${prod.imagen}" alt="${prod.nombre}"><a href="#">${prod.nombre}</a>`;
        div.addEventListener("click", () => {
            modalImg.src = `../img/${prod.imagen}`;
            modalNombre.textContent = prod.nombre;
            modalDesc.textContent = prod.descripcion || "Descripción del producto.";
            cantidad = prod.stock;
            cantidadSpan.textContent = cantidad;
            productoSeleccionado = prod;
            modal.classList.remove("hidden");
        });
        contenedorProductos.appendChild(div);
    });

    btnSumar.addEventListener("click", () => {
        cantidad++;
        cantidadSpan.textContent = cantidad;
    });

    btnRestar.addEventListener("click", () => {
        if (cantidad > 0) {
            cantidad--;
            cantidadSpan.textContent = cantidad;
        }
    });

    btnActualizar.addEventListener("click", () => {
        if (!productoSeleccionado) {
            mostrarError("No hay producto seleccionado.");
            return;
        }
        confirmNombre.textContent = productoSeleccionado.nombre;
        confirmCantidad.textContent = cantidad;
        modalConfirmacion.classList.remove("hidden");
    });

    confirmarCambios.addEventListener("click", () => {
        fetch("../ActualizarStockServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `idProducto=${productoSeleccionado.id}&nuevoStock=${cantidad}`
        })
                .then(res => {
                    if (!res.ok)
                        throw new Error("Error en la respuesta del servidor");
                    modalConfirmacion.classList.add("hidden");
                    modal.classList.add("hidden");
                    modalExito.classList.remove("hidden");
                })
                .catch(err => {
                    console.error("Error al actualizar:", err);
                    mostrarError("No se pudo actualizar el stock. Intenta más tarde.");
                });
    });

    cerrarExito.addEventListener("click", () => {
        modalExito.classList.add("hidden");
        location.reload();
    });

    cancelarCambios.addEventListener("click", () => {
        modalConfirmacion.classList.add("hidden");
    });

    closeBtn.addEventListener("click", () => {
        modal.classList.add("hidden");
    });
});