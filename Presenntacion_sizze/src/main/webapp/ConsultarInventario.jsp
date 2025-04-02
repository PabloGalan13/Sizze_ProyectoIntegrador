<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Consultar Inventario</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/ConsultarInventario.css"> 
    </head>
    <body>
        <div class="ConsultarInventario">
            <h2>Consultar Inventario</h2>

            <div class="search-bar">
                <input type="text" id="search-input" placeholder="🔍 Nombre o Código SKU">
                <button class="filter-btn" onclick="fetchInventario()">⚙️</button>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Imagen</th>
                        <th>Código</th>
                        <th>Categoría</th>
                        <th>Stock Disponible</th>
                        <th>Precio</th>
                    </tr>
                </thead>
                <tbody id="tabla-inventario">
                    <!-- Los datos se cargarán aquí dinámicamente -->
                </tbody>
            </table>

            <div class="button-container">
                <button class="btn regresar">Regresar</button>
                <button class="btn descargar">📊 Descargar</button>
                <button class="btn imprimir">🖨️ Imprimir</button>
            </div>
        </div>
        <!-- Enlazamos el archivo JS -->
        <script src="${pageContext.request.contextPath}/js/ConsultarInventario.js"></script>
    </body>
</html>
