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
                <input type="text" placeholder="🔍 Nombre o Código SKU">
                <button class="filter-btn">⚙️</button>
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
                <tbody>
                    <tr>
                        <td>Funda Antigolpes</td>
                        <td>📱</td>
                        <td>IA100</td>
                        <td>Fundas</td>
                        <td>25</td>
                        <td>$150</td>
                    </tr>
                    <tr>
                        <td>Camiseta Casual</td>
                        <td>👕</td>
                        <td>CC200</td>
                        <td>Ropa</td>
                        <td>40</td>
                        <td>$300</td>
                    </tr>
                    <tr>
                        <td>Mica Templada HD</td>
                        <td>🛡️</td>
                        <td>MT300</td>
                        <td>Micas</td>
                        <td>35</td>
                        <td>$100</td>
                    </tr>
                </tbody>
            </table>

            <div class="button-container">
                <button class="btn regresar">Regresar</button>
                <button class="btn descargar">📊 Descargar</button>
                <button class="btn imprimir">🖨️ Imprimir</button>
            </div>
        </div>
    </body>
</html>
