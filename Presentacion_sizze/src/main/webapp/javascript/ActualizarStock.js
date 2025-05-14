document.addEventListener("DOMContentLoaded", function () {
    const contenedorProductos = document.getElementById("contenedorProductos");

    // Modales y elementos
    const modal = document.getElementById("modalProducto");
    const modalImg = document.getElementById("modalImg");
    const modalInputImg = document.getElementById("modalInputImg");
    const modalNombreInput = document.getElementById("modalNombreInput");
    const modalDescInput = document.getElementById("modalDescInput");
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

    const modalError = document.getElementById("modalError");
    const mensajeError = document.getElementById("mensajeError");
    const cerrarError = document.getElementById("cerrarError");

    let cantidad = 0;
    let productoSeleccionado = null;

    function mostrarError(mensaje) {
        mensajeError.textContent = mensaje;
        modalError.classList.remove("hidden");
    }

    cerrarError.addEventListener("click", () => {
        modalError.classList.add("hidden");
        location.reload();
    });

    // Simulación de productos (reemplazar con fetch después)
    const productosSimulados = [
        {id: 1, nombre: "Martillo", descripcion: "Herramienta de carpintería", stock: 12, imagen: "martillo.png"},
        {id: 2, nombre: "Destornillador", descripcion: "Herramienta para tornillos", stock: 8, imagen: "destornillador.png"},
        {id: 3, nombre: "Serrucho", descripcion: "Herramienta de corte manual", stock: 5, imagen: "serrucho.png"}
    ];

    productosSimulados.forEach(prod => {
        const div = document.createElement("div");
        div.className = "producto";
        div.innerHTML = `<img src="../img/${prod.imagen}" alt="${prod.nombre}"><a href="#">${prod.nombre}</a>`;
        div.addEventListener("click", () => {
            modalImg.src = `../img/${prod.imagen}`;
            modalInputImg.value = "";
            modalNombreInput.value = prod.nombre;
            modalDescInput.value = prod.descripcion || "";
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
        confirmNombre.textContent = modalNombreInput.value;
        confirmCantidad.textContent = cantidad;
        modalConfirmacion.classList.remove("hidden");
    });

    confirmarCambios.addEventListener("click", () => {
        if (!productoSeleccionado) {
            mostrarError("Producto no válido.");
            return;
        }

        const formData = new FormData();
        formData.append("idProducto", productoSeleccionado.id);
        formData.append("nuevoStock", cantidad);
        formData.append("nuevoNombre", modalNombreInput.value);
        formData.append("nuevaDescripcion", modalDescInput.value);

        if (modalInputImg.files.length > 0) {
            formData.append("nuevaImagen", modalInputImg.files[0]);
        }

        fetch("../ActualizarStockServlet", {
            method: "POST",
            body: formData
        })
        .then(res => {
            if (!res.ok) throw new Error("Error en la respuesta del servidor");
            modalConfirmacion.classList.add("hidden");
            modal.classList.add("hidden");
            modalExito.classList.remove("hidden");
        })
        .catch(err => {
            console.error("Error al actualizar:", err);
            mostrarError("No se pudo actualizar el producto. Intenta más tarde.");
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

    // Mostrar vista previa de la imagen seleccionada
    modalInputImg.addEventListener("change", () => {
        const file = modalInputImg.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => {
                modalImg.src = reader.result;
            };
            reader.readAsDataURL(file);
        }
    });
});
