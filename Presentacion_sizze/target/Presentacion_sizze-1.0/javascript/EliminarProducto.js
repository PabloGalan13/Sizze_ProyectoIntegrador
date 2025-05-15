const APP_CONTEXT = window.location.pathname.split('/')[1];
const BASE_URL = `${window.location.origin}/${APP_CONTEXT}`;

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btnBuscar").addEventListener("click", buscarProducto);
    document.getElementById("btnEliminar").addEventListener("click", eliminarProducto);
});

let productoSeleccionado = null;

async function buscarProducto() {
    const nombre = document.getElementById("nombreProducto").value.trim();
    const resultadosDiv = document.getElementById("resultadosBusqueda");
    
    resultadosDiv.innerHTML = '';
    resultadosDiv.className = '';
    
    if (!nombre) {
        mostrarError(resultadosDiv, "Ingrese un nombre de producto válido.");
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/EliminarProducto`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Accept": "application/json"
            },
            body: `accion=buscar&nombre=${encodeURIComponent(nombre)}`
        });

        const contentType = response.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
            throw new Error("Respuesta no es JSON");
        }

        const data = await response.json();
        
        if (data.error) {
            throw new Error(data.error);
        }

        productoSeleccionado = data;
        mostrarProducto(data);
    } catch (error) {
        mostrarError(resultadosDiv, error.message);
    }
}

function mostrarProducto(producto) {
    const detalleDiv = document.getElementById("detalleProducto");
    const productoDiv = document.getElementById("productoEncontrado");
    
    detalleDiv.innerHTML = `
        <p><strong>ID:</strong> ${producto.id}</p>
        <p><strong>Nombre:</strong> ${producto.nombre}</p>
        <p><strong>Precio:</strong> $${producto.precio?.toFixed(2) || 'N/A'}</p>
    `;
    
    productoDiv.style.display = "block";
}

async function eliminarProducto() {
    if (!productoSeleccionado?.id) {
        mostrarError(document.getElementById("resultadosBusqueda"), "Primero busque y seleccione un producto.");
        return;
    }

    if (!confirm("¿Está seguro de eliminar este producto permanentemente?")) {
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/EliminarProducto`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Accept": "application/json"
            },
            body: `accion=eliminar&id=${productoSeleccionado.id}`
        });

        const data = await response.json();
        
        if (data.error) {
            throw new Error(data.error);
        }

        mostrarExito(document.getElementById("resultadosBusqueda"), data.mensaje || "Producto eliminado con éxito.");
        resetearFormulario();
    } catch (error) {
        mostrarError(document.getElementById("resultadosBusqueda"), error.message);
    }
}

function resetearFormulario() {
    document.getElementById("nombreProducto").value = "";
    document.getElementById("detalleProducto").innerHTML = "";
    document.getElementById("productoEncontrado").style.display = "none";
    productoSeleccionado = null;
}

function mostrarError(elemento, mensaje) {
    elemento.innerHTML = mensaje;
    elemento.className = "error";
    elemento.style.display = "block";
}

function mostrarExito(elemento, mensaje) {
    elemento.innerHTML = mensaje;
    elemento.className = "exito";
    elemento.style.display = "block";
}