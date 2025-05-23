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

    document.querySelector('.btn.imprimir').addEventListener('click', imprimirTabla);
    document.querySelector('.btn.descargar').addEventListener('click', descargarPDF);
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
    }
}

async function clickBotonBuscar() {
    const fechaInicio = document.getElementById('fechaInicio').value;
    const fechaFin = document.getElementById('fechaFin').value;

    if (!fechaInicio || !fechaFin) {
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

        if (!data || data.length === 0) {
            document.getElementById('tablaVentas').innerHTML = '';
        } else {
            actualizarTabla(data);
        }

    } catch (error) {
        console.error('Error al buscar ventas:', error);
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
             <td class="no-print"><button class="btn detalle detalleBoton" data-id="${venta.id}">Ver Detalle</button></td>
        `;

        tablaBody.appendChild(fila);
    });

    tablaBody.querySelectorAll('.detalleBoton').forEach(boton => {
        boton.addEventListener('click', function () {
            const idVenta = this.getAttribute('data-id');
            window.open(`${BASE_URL}/html/DetalleVenta.html?id=${idVenta}`, '_blank');
        });
    });
}


function imprimirTabla() {
    const tablaHTML = document.querySelector('.table-responsive').innerHTML;

    const ventana = window.open('', '_blank');
    ventana.document.write(`
        <html>
            <head>
                <title>Imprimir Ventas</title>
                <style>
                    body { font-family: Arial, sans-serif; padding: 20px; }
                    table { width: 100%; border-collapse: collapse; }
                    th, td { border: 1px solid #000; padding: 8px; text-align: left; }
                    th { background-color: #f2f2f   2; 
                    .no-print { display: none; }
                </style>
            </head>
            <body>
                <h2>Reporte de Ventas</h2>
                ${tablaHTML}
                <script>
                    window.onload = function () {
                        window.print();
                        window.onafterprint = function () { window.close(); };
                    };
                <\/script>
            </body>
        </html>
    `);
    ventana.document.close();
}

function descargarPDF() {
    const {jsPDF} = window.jspdf;
    const doc = new jsPDF();

    doc.text('Reporte de Ventas', 14, 15);

    const tabla = document.querySelector('table');
    const headers = [...tabla.querySelectorAll('thead th')].map(th => th.textContent);
    const filas = [...tabla.querySelectorAll('tbody tr')].map(tr =>
        [...tr.querySelectorAll('td')].map(td => td.textContent.trim())
    );

    doc.autoTable({
        head: [headers],
        body: filas,
        startY: 20
    });

    doc.save('ventas.pdf');
}
