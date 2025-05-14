document.addEventListener("DOMContentLoaded", function () {
    const contenedorProductos = document.getElementById("contenedorProductos");
    const botonBuscar = document.getElementById('botonBuscar');
    if (botonBuscar) {
        botonBuscar.addEventListener('click', clickBotonBuscar);
    }

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

    const APP_CONTEXT = window.location.pathname.split('/')[1];
    const BASE_URL = `${window.location.origin}/${APP_CONTEXT}`;
    document.getElementById("botonBuscar").addEventListener("click", clickBotonBuscar);

    fetch(`${BASE_URL}/ConsultaInventario`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
            .then(res => {
                if (!res.ok)
                    throw new Error('Error en la carga de productos');
                return res.json();
            })
            .then(productos => {
                productos.forEach(prod => {
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
                });
            })
            .catch(err => {
                console.error("Error al cargar productos:", err);
                mostrarError("No se pudieron cargar los productos.");
            });

    async function clickBotonBuscar() {
        const nombreProducto = document.getElementById('busquedaProducto').value.trim();

        contenedorProductos.innerHTML = ''; // Siempre limpiamos primero

        if (!nombreProducto) {
            // Si no hay texto, volvemos a cargar todos los productos con GET
            try {
                const res = await fetch(`${BASE_URL}/ConsultaInventario`, {
                    method: 'GET',
                    headers: {'Content-Type': 'application/json'}
                });

                if (!res.ok)
                    throw new Error('Error al recargar productos');

                const productos = await res.json();

                if (!productos || productos.length === 0) {
                    mostrarError('No hay productos disponibles');
                    return;
                }

                productos.forEach(crearElementoProducto);
            } catch (error) {
                console.error("Error al recargar productos:", error);
                mostrarError("No se pudieron recargar los productos.");
            }

            return; // Fin de la función si estaba vacío
        }

        // Si sí hay texto, se hace búsqueda POST
        try {
            const response = await fetch(`${BASE_URL}/ConsultaInventario`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({nombreProducto})
            });

            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }

            const productos = await response.json();

            if (!productos || productos.length === 0) {
                mostrarError('Producto no encontrado');
                return;
            }

            productos.forEach(crearElementoProducto);
        } catch (error) {
            console.error('Error al buscar productos:', error);
            mostrarError('Error al buscar productos. Por favor, intente nuevamente.');
        }
    }
    function crearElementoProducto(prod) {
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
    }


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
        formData.append("nombre", modalNombreInput.value);           // <- correcto
        formData.append("descripcion", modalDescInput.value);        // <- correcto
        formData.append("stock", cantidad);                          // <- correcto

        if (modalInputImg.files.length > 0) {
            formData.append("imgPortada", modalInputImg.files[0]);   // <- correcto
        }


        fetch("../ActualizarStockServlet", {
            method: "POST",
            body: formData
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
