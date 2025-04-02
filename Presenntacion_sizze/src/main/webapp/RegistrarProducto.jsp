<%-- 
    Document   : RegistrarProducto
    Created on : 26 mar 2025, 11:57:37 a.m.
    Author     : Gabriel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Producto</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/RegistrarProducto.css"> 
        <script type="text/javascript">
            function actualizarTipo() {
                var categoria = document.getElementById('categoria').value;
                var tipoSelect = document.getElementById('tipo');
                tipoSelect.innerHTML = '';
                var opciones = [];

                if (categoria === 'Ropa') {
                    opciones = ['Camiseta', 'Pantalón', 'Zapatos'];
                } else if (categoria === 'Micas') {
                    opciones = ['Mica protectora', 'Mica curvada', 'Mica completa'];
                } else if (categoria === 'Accesorios') {
                    opciones = ['Funda', 'Cargador', 'Audífonos'];
                }

                opciones.forEach(function (opcion) {
                    var optionElement = document.createElement('option');
                    optionElement.textContent = opcion;
                    optionElement.value = opcion;
                    tipoSelect.appendChild(optionElement);
                });

                actualizarPlaceholder();
            }
            window.onload = function () {
                actualizarTipo();
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.has('mensaje')) {
                    let mensaje = urlParams.get('mensaje');
                    if (mensaje === 'exito') {
                        alert('Producto registrado exitosamente.');
                    } else if (mensaje === 'error') {
                        alert('Hubo un error al registrar el producto. Inténtalo de nuevo.');
                    }
                }
            };
            function actualizarPlaceholder() {
                var categoria = document.getElementById('categoria').value;
                var tipo = document.getElementById('tipo').value;

                var nombre = document.getElementById('nombre');
                var cantidad = document.getElementById('cantidad');
                var talla = document.getElementById('talla');
                var costo = document.getElementById('costo');

                if (categoria === 'Ropa') {
                    nombre.placeholder = "Ej: Camiseta blanca, Jeans";
                    cantidad.placeholder = "Ej: 10 unidades";
                    talla.placeholder = "Ej: S, M, L, XL";
                    costo.placeholder = "Ej: 250.00 MXN";
                } else if (categoria === 'Micas') {
                    nombre.placeholder = "Ej: Mica 9H para iPhone";
                    cantidad.placeholder = "Ej: 20 piezas";
                    talla.placeholder = "Ej: iPhone 13, Galaxy S22";
                    costo.placeholder = "Ej: 150.00 MXN";
                } else if (categoria === 'Accesorios') {
                    if (tipo === 'Funda') {
                        nombre.placeholder = "Ej: Funda de silicona roja";
                        cantidad.placeholder = "Ej: 15 unidades";
                        talla.placeholder = "Ej: iPhone 13, Xiaomi Redmi Note 11";
                        costo.placeholder = "Ej: 180.00 MXN";
                    } else if (tipo === 'Cargador') {
                        nombre.placeholder = "Ej: Cargador rápido 20W";
                        cantidad.placeholder = "Ej: 5 unidades";
                        talla.placeholder = "Ej: Tipo C, Micro USB, Lightning";
                        costo.placeholder = "Ej: 300.00 MXN";
                    } else if (tipo === 'Audífonos') {
                        nombre.placeholder = "Ej: Audífonos inalámbricos Bluetooth";
                        cantidad.placeholder = "Ej: 8 pares";
                        talla.placeholder = "Ej: Inalámbricos, Alámbricos";
                        costo.placeholder = "Ej: 500.00 MXN";
                    } else {
                        // Placeholder por defecto si no se ha seleccionado un tipo
                        nombre.placeholder = "Ej: Accesorio para teléfono";
                        cantidad.placeholder = "Ej: 10 unidades";
                        talla.placeholder = "Ej: Modelo del dispositivo";
                        costo.placeholder = "Ej: 200.00 MXN";
                    }
                }
            }

            window.onload = function () {
                actualizarTipo();
            };
            document.querySelector('.confirmar').addEventListener('click', function (event) {
                var nombre = document.getElementById('nombre').value.trim();
                var cantidad = document.getElementById('cantidad').value.trim();
                var talla = document.getElementById('talla').value.trim();
                var costo = document.getElementById('costo').value.trim();
                var tipo = document.getElementById('tipo').value.trim();
                var categoria = document.getElementById('categoria').value.trim();
                var imagen = document.getElementById('imgPortada').files.length;

                if (!nombre || !cantidad || !talla || !costo || !tipo || !categoria || imagen === 0) {
                    alert('Por favor, completa todos los campos antes de continuar.');
                    event.preventDefault();
                }
            });
        </script>
    </head>
    <body>     
        <form action="ProductoServlet" method="post" enctype="multipart/form-data">

            <div class="RegistrarProducto">

                <h2>Registrar Producto</h2>
                <div class="container">

                    <div class="field-group">
                        <label for="nombre">Nombre</label>
                        <input type="text" id="nombre" name="nombre" placeholder="Ej: Producto">
                    </div>

                    <div class="field-group">
                        <label for="categoria">Categoría</label>
                        <select id="categoria" name="categoria" onchange="actualizarTipo()">
                            <option value="Ropa" selected>Ropa</option>
                            <option value="Micas">Micas</option>
                            <option value="Accesorios">Accesorios</option>
                        </select>
                    </div>

                    <div class="field-group">
                        <label for="tipo">Tipo</label>
                        <select id="tipo" name="tipo" onchange="actualizarPlaceholder()">
                        </select>
                    </div>

                    <div class="field-group">
                        <label for="cantidad">Cantidad</label>
                        <input type="number" id="cantidad" name="stock" placeholder="Ej: 10">
                    </div>

                    <div class="field-group">
                        <label for="talla">Talla/Modelo</label>
                        <input type="text" id="talla" name="talla" placeholder="Ej: M, L, XL">
                    </div>

                    <div class="field-group">
                        <label for="costo">Costo</label>
                        <input type="text" id="costo" name="precio" placeholder="Ej: 200.00">
                    </div>
                    <div class="imgs-post">
                        <label for="imgPortada">Producto:</label>
                        <input type="file" id="imgPortada" name="imgPortada" accept="image/*" required>
                    </div> 
                </div>
                <div class="buttons">
                    <button type="button" class="cancelar" onclick="document.querySelector('form').reset();
                            actualizarPlaceholder();">Cancelar</button>
                    <button class="confirmar">Confirmar</button>
                </div>
            </div>
        </form>

    </body>
</html>