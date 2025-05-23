let productosAgregados = [];
let totalPago = 0;

const APP_CONTEXT = window.location.pathname.split('/')[1];
const BASE_URL = `${window.location.origin}/${APP_CONTEXT}`;

function actualizarListaProductos() {
    const contenedor = document.getElementById("listaProductosAgregados");
    contenedor.innerHTML = "";

    productosAgregados.forEach((detalle, index) => {
        const div = document.createElement("div");
        div.classList.add("producto-agregado");
        div.innerHTML = `
            <p><strong>${detalle.producto.nombre}</strong> - ${detalle.cantidad} x $${detalle.precioVendido.toFixed(2)}</p>
            <button onclick="eliminarProducto(${index})">Eliminar</button>
        `;
        contenedor.appendChild(div);
    });

    document.getElementById("totalPago").textContent = totalPago.toFixed(2);
}

document.getElementById("btnBuscar").addEventListener("click", async () => {
    const nombre = document.getElementById("buscarProducto").value.trim();
    const resultadosBusqueda = document.getElementById("resultadosBusqueda");

    if (!nombre) {
        resultadosBusqueda.innerText = "Ingresa un nombre de producto.";
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/BuscarProducto`, {
            method: "POST",
            headers: { 
                "Content-Type": "application/x-www-form-urlencoded",
                "Accept": "application/json"
            },
            body: `nombre=${encodeURIComponent(nombre)}`
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Error del servidor: ${response.status} - ${errorText}`);
        }

        const contentType = response.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
            throw new TypeError("La respuesta no es JSON válido");
        }

        const producto = await response.json();

        if (producto.error) {
            resultadosBusqueda.innerText = producto.error;
        } else if (!producto.nombre) {
            resultadosBusqueda.innerText = "Producto no encontrado.";
        } else {
            resultadosBusqueda.innerHTML = `
                <p><strong>Nombre:</strong> ${producto.nombre}</p>
                <p><strong>Precio:</strong> $${producto.precio.toFixed(2)}</p>
                <p><strong>Stock:</strong> ${producto.stock}</p>
            `;
            sessionStorage.setItem("productoSeleccionado", JSON.stringify(producto));
        }
    } catch (error) {
        console.error("Error al buscar producto:", error);
        resultadosBusqueda.innerText = `Error en la búsqueda: ${error.message}`;
    }
});

document.getElementById("agregarProducto").addEventListener("click", () => {
    const productoData = sessionStorage.getItem("productoSeleccionado");
    const cantidadInput = document.getElementById("cantidad");
    const cantidad = parseInt(cantidadInput.value, 10);
    const resultadosBusqueda = document.getElementById("resultadosBusqueda");

    if (!productoData) {
        alert("Primero busca y selecciona un producto.");
        return;
    }

    const producto = JSON.parse(productoData);

    if (cantidad <= 0 || isNaN(cantidad)) {
        alert("Cantidad inválida. Debe ser un número mayor a cero.");
        cantidadInput.focus();
        return;
    }

    if (cantidad > producto.stock) {
        alert(`Stock insuficiente. Solo hay ${producto.stock} unidades disponibles.`);
        cantidadInput.focus();
        return;
    }

    const detalle = {
        producto: producto,
        cantidad: cantidad,
        precioVendido: producto.precio
    };

    productosAgregados.push(detalle);
    totalPago += cantidad * producto.precio;
    actualizarListaProductos();

    sessionStorage.removeItem("productoSeleccionado");
    document.getElementById("buscarProducto").value = "";
    resultadosBusqueda.innerHTML = "";
    cantidadInput.value = 1;
});

window.eliminarProducto = (index) => {
    const detalle = productosAgregados[index];
    totalPago -= detalle.cantidad * detalle.precioVendido;
    productosAgregados.splice(index, 1);
    actualizarListaProductos();
};

document.getElementById("pagar").addEventListener("click", async () => {
    if (productosAgregados.length === 0) {
        alert("Agrega al menos un producto para registrar la venta.");
        return;
    }

    const metodoPagoInput = document.querySelector('input[name="metodoPago"]:checked');
    if (!metodoPagoInput) {
        alert("Selecciona un método de pago.");
        return;
    }

    const metodoPago = metodoPagoInput.value.toUpperCase();
    if (metodoPago !== "EFECTIVO" && metodoPago !== "TRANSFERENCIA") {
        alert("Método de pago inválido.");
        return;
    }

    const venta = {
        vendedor: { id: 2 }, 
        metodoPago: metodoPago,
        productos: productosAgregados.map(item => ({
            productoId: item.producto.id,
            cantidad: item.cantidad,
            precioUnitario: item.precioVendido
        }))
    };

    try {
        const response = await fetch(`${BASE_URL}/RegistrarVenta`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(venta)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Error del servidor: ${response.status} - ${errorText}`);
        }

        const resultado = await response.json();

        if (resultado.exito) {
            alert("Venta registrada con éxito.");
            productosAgregados = [];
            totalPago = 0;
            actualizarListaProductos();
//            document.getElementById("formVenta").reset();
        } else {
            alert(`Error al registrar venta: ${resultado.mensaje || 'Error desconocido'}`);
        }
    } catch (error) {
        console.error("Error al registrar venta:", error);
        alert(`No se pudo completar la venta: ${error.message}`);
    }
});

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("cantidad").value = 1;
});
