/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
const APP_CONTEXT = window.location.pathname.split('/')[1];
const BASE_URL = `${window.location.origin}/${APP_CONTEXT}`;

document.addEventListener('DOMContentLoaded', () => {
    const idVenta = obtenerIdVentaDesdeURL();
    if (idVenta) {
        consultarVentaPorId(idVenta);
    } else {
        alert("ID de venta no proporcionado");
    }
    document.querySelector('.btn.regresar').addEventListener('click', function () {
        window.location.href = `${BASE_URL}/html/ConsultarVentas.html`;
    });
});

function obtenerIdVentaDesdeURL() {
    const params = new URLSearchParams(window.location.search);
    return params.get('id');
}

async function consultarVentaPorId(idVenta) {
    try {
        const response = await fetch(`${BASE_URL}/DetalleVenta`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({idVenta})
        });

        if (!response.ok)
            throw new Error('Error al consultar la venta');

        const venta = await response.json();
        llenarFormularioVenta(venta);
    } catch (error) {
        console.error('Error:', error);
        alert("No se pudo cargar la información de la venta.");
    }
}

function llenarFormularioVenta(venta) {
    // Asigna los datos a los inputs de la sección Datos de Venta
    const inputs = document.querySelectorAll('.info-item input');
    inputs[0].value = venta.id;
    inputs[1].value = venta.empleado;
    inputs[2].value = venta.fecha;
    inputs[3].value = venta.metodoPago;

    // Llenar la tabla de productos
    const contenedorTabla = document.querySelector('.tabla-productos');

    // Eliminar filas anteriores (excepto encabezado)
    const filas = contenedorTabla.querySelectorAll('.fila:not(.encabezado)');
    filas.forEach(fila => fila.remove());

    venta.productos.forEach(p => {
        const fila = document.createElement('div');
        fila.classList.add('fila');
        fila.innerHTML = `
            <div>${p.nombre}</div>
            <div><input type="text" value="${p.cantidad}" readonly></div>
            <div><input type="text" value="${p.precio}" readonly></div>
            <div><input type="text" value="${p.total}" readonly></div>
        `;
        contenedorTabla.appendChild(fila);
    });

    // Total final
    document.querySelector('.total-final input').value = venta.total;
}


