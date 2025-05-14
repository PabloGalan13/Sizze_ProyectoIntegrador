const APP_CONTEXT = window.location.pathname.split('/')[1];
const BASE_URL = `${window.location.origin}/${APP_CONTEXT}`;

document.addEventListener('DOMContentLoaded', function () {
    inicializarEventos();
});

function inicializarEventos() {
    // Cargar todos los productos al iniciar la página
    cargarTodosProductos();
    
    // Activar botón de búsqueda
    const botonBuscar = document.getElementById('botonBuscar');
    if (botonBuscar) {
        botonBuscar.addEventListener('click', clickBotonBuscar);
    }
}

async function cargarTodosProductos() {
    try {
        const response = await fetch(`${BASE_URL}/ConsultaInventario`, {
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
        });

        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }

        const dataText = await response.text();
        console.log('Contenido recibido:', dataText);

        try {
            const json = JSON.parse(dataText);
            actualizarTabla(json);
        } catch (error) {
            console.error('No se pudo parsear la respuesta como JSON:', error);
        }

    } catch (error) {
        console.error('Error al realizar la petición:', error);
        mostrarMensajeError('Error en la comunicación con el servidor');
    }
}

async function clickBotonBuscar() {
    const nombreProducto = document.getElementById('busquedaProducto').value.trim();

    if (!nombreProducto) {
        mostrarMensajeError('Por favor, ingrese un nombre de producto para buscar.');
        return;
    }

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

        const data = await response.json();
        ocultarMensajeError();

        if (!data || data.length === 0) {
            mostrarMensajeError('Productos no encontrados');
            document.getElementById('tablaProductos').innerHTML = '';
        } else {
            actualizarTabla(data);
        }

    } catch (error) {
        console.error('Error al buscar productos:', error);
        mostrarMensajeError('Error al buscar productos. Por favor, intente nuevamente.');
    }
}

function actualizarTabla(productos) {
    const tablaBody = document.getElementById('tablaProductos');
    tablaBody.innerHTML = '';

    productos.forEach(producto => {
        const fila = document.createElement('tr');

        // Construir la URL absoluta de la imagen
        const imagenUrl = `${BASE_URL}/${producto.imagen}`;

        fila.innerHTML = `
            <td>${producto.id}</td>
            <td><img src="${imagenUrl}" alt="${producto.nombre}" style="max-width: 100px;"></td>
            <td>${producto.nombre}</td
            <td>${producto.modeloTalla}</td>
            <td>$${producto.precio.toFixed(2)}</td>
            <td>${producto.stock}</td>
            <td>${producto.categoria.nombre}</td>
        `;

        tablaBody.appendChild(fila);
    });
}


function mostrarMensajeError(mensaje) {
    const alertaError = document.getElementById('mensajeError');
    alertaError.textContent = mensaje;
    alertaError.classList.remove('d-none');
}

function ocultarMensajeError() {
    const alertaError = document.getElementById('mensajeError');
    alertaError.classList.add('d-none');
}

