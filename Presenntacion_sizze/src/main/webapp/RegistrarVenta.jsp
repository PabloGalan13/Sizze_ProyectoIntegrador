<%-- 
    Document   : RegistrarVenta
    Created on : 26 mar 2025, 14:15:26
    Author     : Paco
--%>

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
                <label for="articulo">Artículo:</label>
                <input type="text" id="articulo" placeholder="Nombre o Codigo SKU">

                <label for="cantidad">Cantidad</label>
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
                <div class="producto">
                    <img src="https://via.placeholder.com/80x80?text=Funda+marrón" alt="Funda marrón">
                    <div class="info">
                        <p><strong>Funda marrón</strong><br>$180 MXN</p>
                        <span>x1</span>
                    </div>
                    <button class="eliminar">✖</button>
                </div>
                <div class="producto">
                    <img src="https://via.placeholder.com/80x80?text=Funda+negra" alt="Funda negra">
                    <div class="info">
                        <p><strong>Funda negra</strong><br>$150 MXN</p>
                        <span>x1</span>
                    </div>
                    <button class="eliminar">✖</button>
                </div>
                <div class="total">
                    <strong>Total a pagar: $330 MXN</strong>
                </div>
            </div>
        </div>
    </body>
</html>
