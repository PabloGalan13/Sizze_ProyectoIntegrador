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
    document.querySelector('.btn.imprimir').addEventListener('click', imprimirTabla);
    document.querySelector('.btn.descargar').addEventListener('click', descargarPDF);
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
    }
}

async function clickBotonBuscar() {
    const nombreProducto = document.getElementById('busquedaProducto').value.trim();

    if (nombreProducto=="") {
        cargarTodosProductos();
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

        if (!data || data.length === 0) {
            document.getElementById('tablaProductos').innerHTML = '';
        } else {
            actualizarTabla(data);
        }

    } catch (error) {
        console.error('Error al buscar productos:', error);
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
            <td>${producto.nombre}</td>
            <td>${producto.modeloTalla}</td>
            <td>$${producto.precio.toFixed(2)}</td>
            <td>${producto.stock}</td>
            <td>${producto.categoria.nombre}</td>
        `;

        tablaBody.appendChild(fila);
    });
}




function imprimirTabla() {
    const tablaHTML = document.querySelector('.table-responsive').innerHTML;

    const ventana = window.open('', '_blank');
    ventana.document.write(`
        <html>
            <head>
                <title>Imprimir Inventario</title>
                <style>
                    body { font-family: Arial, sans-serif; padding: 20px; }
                    table { width: 100%; border-collapse: collapse; }
                    th, td { border: 1px solid #000; padding: 8px; text-align: left; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h2>Reporte de Inventario </h2>
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
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    doc.text('Reporte de Inventario', 14, 15);

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

    doc.save('Inventario.pdf');
}