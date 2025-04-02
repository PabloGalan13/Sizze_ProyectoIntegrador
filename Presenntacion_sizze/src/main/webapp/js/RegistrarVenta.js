/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Realizar Venta</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/RegistrarVenta.css">
</head>
<body>
    <div class="container">
        <div class="venta">
            <h2>Realizar Venta</h2>
            
            <label for="buscarProducto">Buscar Producto:</label>
            <input type="text" id="buscarProducto" list="listaProductos" placeholder="Nombre o Código SKU">
            <datalist id="listaProductos"></datalist>

            <label for="cantidad">Cantidad:</label>
            <input type="number" id="cantidad" placeholder="Cantidad del Producto a Vender">

            <h3>Métodos de Pago</h3>
            <div class="metodos-pago">
                <label class="metodo">
                    <img src="https://img.icons8.com/ios-filled/50/000000/cash-in-hand.png"/>
                    <input type="checkbox" checked> Efectivo
                </label>
                <label class="metodo">
                    <img src="https://img.icons8.com/ios-filled/50/000000/bank-card-back-side.png"/>
                    <input type="checkbox"> Transferencia
                </label>
            </div>

            <button class="pagar">Pagar Productos</button>
        </div>

        <div class="productos">
            <h2>Productos</h2>
            <div id="listaProductosAgregados"></div>
            <div class="total">
                <strong>Total a pagar: $<span id="totalPago">0.00</span> MXN</strong>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/RegistrarVenta.js"></script>
</body>
</html>
