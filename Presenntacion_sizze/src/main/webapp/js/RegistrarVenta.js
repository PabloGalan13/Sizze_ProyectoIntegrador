/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    cargarListaProductos();
    configurarEventos();
});

function cargarListaProductos() {
    fetch("ObtenerProductosServlet") // Ajusta la URL según tu configuración
        .then(response => response.json())
        .then(productos => {
            let listaProductos = document.getElementById("listaProductos");
            listaProductos.innerHTML = ""; // Limpiar antes de cargar nuevos
            productos.forEach(producto => {
                let option = document.createElement("option");
                option.value = producto.nombre; // Mostrar nombre del producto
                option.dataset.id = producto.id;
                option.dataset.precio = producto.precio;
                listaProductos.appendChild(option);
            });
        })
        .catch(error => console.error("Error al cargar productos:", error));
}

function configurarEventos() {
    document.getElementById("buscarProducto").addEventListener("change", agregarProducto);
}

function agregarProducto() {
    let inputProducto = document.getElementById("buscarProducto");
    let cantidad = document.getElementById("cantidad").value;

    if (!cantidad || cantidad <= 0) {
        alert("Por favor, ingresa una cantidad válida.");
        return;
    }

    let selectedOption = Array.from(document.getElementById("listaProductos").options)
        .find(option => option.value === inputProducto.value);

    if (!selectedOption) {
        alert("Producto no encontrado.");
        return;
    }

    let productoId = selectedOption.dataset.id;
    let productoNombre = selectedOption.value;
    let precio = parseFloat(selectedOption.dataset.precio);
    let total = precio * cantidad;

    let divProductos = document.querySelector(".productos");
    
    let productoDiv = document.createElement("div");
    productoDiv.classList.add("producto");
    productoDiv.innerHTML = `
        <p><strong>${productoNombre}</strong> - $${precio.toFixed(2)}</p>
        <p>Cantidad: ${cantidad}</p>
        <p>Total: $${total.toFixed(2)}</p>
        <button class="eliminar">✖</button>
    `;

    divProductos.appendChild(productoDiv);

    // Limpiar campos
    inputProducto.value = "";
    document.getElementById("cantidad").value = "";

    // Evento para eliminar el producto
    productoDiv.querySelector(".eliminar").addEventListener("click", function () {
        productoDiv.remove();
    });
}
