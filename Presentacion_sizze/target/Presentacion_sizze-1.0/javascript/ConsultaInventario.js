/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const APP_CONTEXT = window.location.pathname.split('/')[1];
const BASE_URL = `${window.location.origin}/${APP_CONTEXT}`;


document.addEventListener('DOMContentLoaded', function () {
    inicializarEventos();
});

function inicializarEventos() {
    // Cargar todos los productos al iniciar la página
    cargarTodosProductos();
}

function cargarTodosProductos() {
    fetch(`http://localhost:8080/Presentacion_sizze/ConsultaInventario`, 
    { method: 'GET', headers: { 'Content-Type': 'application/json' } })
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.text(); // Cambiar a .text() para verificar qué contenido llega
    })
    .then(data => {
        console.log('Contenido recibido:', data);
        try {
            const json = JSON.parse(data); // Intentar parsear el JSON
            actualizarTabla(json);
        } catch (error) {
            console.error('No se pudo parsear la respuesta como JSON:', error);
        }
    })
    .catch(error => {
        console.error('Error al realizar la petición:', error);
        mostrarMensajeError('Error en la comunicación con el servidor');
    });
}

function clickBotonBuscar() {
    // Obtener el valor de búsqueda
    const nombreProducto = document.getElementById('busquedaProducto').value.trim();

    if (!nombreProducto) {
        mostrarMensajeError('Por favor, ingrese un nombre de producto para buscar.');
        return;
    }

    // Realizar petición POST para buscar productos específicos
    fetch('ConsultaInventario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({nombreProducto: nombreProducto})
    })
            .then(response => response.json())
            .then(data => {
                ocultarMensajeError();

                if (!data || data.length === 0) {
                    mostrarMensajeError('Productos no encontrados');
                    // Limpiar la tabla cuando no hay resultados
                    document.getElementById('tablaProductos').innerHTML = '';
                } else {
                    actualizarTabla(data);
                }
            })
            .catch(error => {
                console.error('Error al buscar productos:', error);
                mostrarMensajeError('Error al buscar productos. Por favor, intente nuevamente.');
            });
}

function actualizarTabla(productos) {
    const tablaBody = document.getElementById('tablaProductos');
    tablaBody.innerHTML = '';

    productos.forEach(producto => {
        const fila = document.createElement('tr');

        fila.innerHTML = `
            <td>${producto.id}</td>
            <td>${producto.nombre}</td>
            <td>${producto.imagen}</td>
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
    document.getElementById('mensajeError').classList.add('d-none');
}

