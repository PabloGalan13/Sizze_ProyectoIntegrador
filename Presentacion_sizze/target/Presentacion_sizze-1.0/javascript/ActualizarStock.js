document.addEventListener("DOMContentLoaded",async  () => {
    await window.layoutCargado; // ← Espera que el layout se cargue primero

    // --- Elementos DOM --- (excepto sidebar)
    const contenedorProductos = document.getElementById("contenedorProductos");
    const botonBuscar = document.getElementById("botonBuscar");

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

    // --- Variables de estado ---
    let cantidad = 0;
    let productoSeleccionado = null;
    const APP_CONTEXT = window.location.pathname.split('/')[1];
    const BASE_URL = `${window.location.origin}/${APP_CONTEXT}`;

    // --- Funciones de UI ---
    const mostrarError = (mensaje) => {
        mensajeError.textContent = mensaje;
        modalError.classList.remove("hidden");
    };

    const crearElementoProducto = (prod) => {
        const div = document.createElement("div");
        div.className = "producto";
        const imagenUrl = `${BASE_URL}/${prod.imagen}`;
        div.innerHTML = `<img src="${imagenUrl}" alt="${prod.nombre}"><a href="#">${prod.nombre}</a>`;
        div.addEventListener("click", () => {
            modalImg.src = imagenUrl;
            modalInputImg.value = "";
            modalNombreInput.value = prod.nombre;
            modalDescInput.value = prod.descripcion || "";
            cantidad = prod.stock;
            cantidadSpan.textContent = cantidad;
            productoSeleccionado = prod;
            modal.classList.remove("hidden");
        });
        contenedorProductos.appendChild(div);
    };

    

    // --- Carga inicial de productos ---
    fetch(`${BASE_URL}/ConsultaInventario`)
            .then(res => {
                if (!res.ok)
                    throw new Error('Error en la carga de productos');
                return res.json();
            })
            .then(productos => productos.forEach(crearElementoProducto))
            .catch(err => {
                console.error(err);
                mostrarError("No se pudieron cargar los productos.");
            });

    // --- Búsqueda de productos ---
    const clickBotonBuscar = async () => {
        const nombreProducto = document.getElementById('busquedaProducto').value.trim();
        contenedorProductos.innerHTML = '';

        const metodo = nombreProducto ? 'POST' : 'GET';
        const cuerpo = nombreProducto ? JSON.stringify({nombreProducto}) : null;

        try {
            const res = await fetch(`${BASE_URL}/ConsultaInventario`, {
                method: metodo,
                headers: {'Content-Type': 'application/json'},
                body: cuerpo
            });

            if (!res.ok)
                throw new Error('Error en la búsqueda');
            const productos = await res.json();

            if (!productos || productos.length === 0) {
                mostrarError(nombreProducto ? 'Producto no encontrado' : 'No hay productos disponibles');
                return;
            }

            productos.forEach(crearElementoProducto);
        } catch (err) {
            console.error(err);
            mostrarError("Error al buscar productos.");
        }
    };

    if (botonBuscar) {
        botonBuscar.addEventListener("click", clickBotonBuscar);
    }

    // --- Acciones en modal de producto ---
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
        if (!productoSeleccionado)
            return mostrarError("No hay producto seleccionado.");
        confirmNombre.textContent = modalNombreInput.value;
        confirmCantidad.textContent = cantidad;
        modalConfirmacion.classList.remove("hidden");
    });

    confirmarCambios.addEventListener("click", () => {
        if (!productoSeleccionado)
            return mostrarError("Producto no válido.");

        const formData = new FormData();
        formData.append("idProducto", productoSeleccionado.id);
        formData.append("nombre", modalNombreInput.value);
        formData.append("descripcion", modalDescInput.value);
        formData.append("stock", cantidad);

        if (modalInputImg.files.length > 0) {
            formData.append("imgPortada", modalInputImg.files[0]);
        }

        fetch("../ActualizarStockServlet", {
            method: "POST",
            body: formData
        })
                .then(res => {
                    if (!res.ok)
                        throw new Error("Error al actualizar");
                    modalConfirmacion.classList.add("hidden");
                    modal.classList.add("hidden");
                    modalExito.classList.remove("hidden");
                })
                .catch(err => {
                    console.error(err);
                    mostrarError("No se pudo actualizar el producto.");
                });
    });

    cancelarCambios.addEventListener("click", () => {
        modalConfirmacion.classList.add("hidden");
    });

    closeBtn.addEventListener("click", () => {
        modal.classList.add("hidden");
    });

    cerrarExito.addEventListener("click", () => {
        modalExito.classList.add("hidden");
        location.reload();
    });

    cerrarError.addEventListener("click", () => {
        modalError.classList.add("hidden");
        location.reload();
    });

    // --- Previsualización de imagen ---
    modalInputImg.addEventListener("change", () => {
        const file = modalInputImg.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = () => modalImg.src = reader.result;
            reader.readAsDataURL(file);
        }
    });
});
