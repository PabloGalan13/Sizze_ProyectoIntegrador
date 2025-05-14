const APP_CONTEXT = window.location.pathname.split('/')[1];
const BASE_URL = `${window.location.origin}/${APP_CONTEXT}`;

document.addEventListener('DOMContentLoaded', function () {
    inicializarEventos();
});

function inicializarEventos() {
    // Cargar todas las ventas al iniciar la página
    cargarTodasLasVentas();

    // Activar botón de búsqueda
    const botonBuscar = document.getElementById('botonBuscar');
    if (botonBuscar) {
        botonBuscar.addEventListener('click', clickBotonBuscar);
    }
}

async function cargarTodasLasVentas() {
    try {
        const response = await fetch(`${BASE_URL}/ConsultarVentas`, {
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
        console.error('Error al cargar todas las ventas:', error);
        mostrarMensajeError('Error en la comunicación con el servidor');
    }
}

async function clickBotonBuscar() {
    const fechaInicio = document.getElementById('fechaInicio').value;
    const fechaFin = document.getElementById('fechaFin').value;

    if (!fechaInicio || !fechaFin) {
        mostrarMensajeError('Por favor, seleccione ambas fechas.');
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/ConsultarVentas`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({fechaInicio, fechaFin})
        });

        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }

        const data = await response.json();
        ocultarMensajeError();

        if (!data || data.length === 0) {
            mostrarMensajeError('Ventas no encontradas');
            document.getElementById('tablaVentas').innerHTML = '';
        } else {
            actualizarTabla(data);
        }

    } catch (error) {
        console.error('Error al buscar ventas:', error);
        mostrarMensajeError('Error al buscar ventas. Por favor, intente nuevamente.');
    }
}

function actualizarTabla(ventas) {
    const tablaBody = document.getElementById('tablaVentas');
    tablaBody.innerHTML = '';

    ventas.forEach(venta => {
        const fila = document.createElement('tr');

        fila.innerHTML = `
            <td>${venta.id}</td>
            <td>${venta.empleado}</td>
            <td>${venta.fecha}</td>
            <td>$${venta.total.toFixed(2)}</td>
            <td><button class="btn btn-sm btn-info ver-detalle" data-id="${venta.id}">Ver Detalle</button></td>
        `;

        tablaBody.appendChild(fila);  
    });
    tablaBody.querySelectorAll('.ver-detalle').forEach(boton => {
    boton.addEventListener('click', function () {
        const idVenta = this.getAttribute('data-id');
        window.open(`${BASE_URL}/html/DetalleVenta.html?id=${idVenta}`, '_blank');
    });
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
