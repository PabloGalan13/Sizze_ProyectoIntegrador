/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener("DOMContentLoaded", function () {
    fetchInventario();
});

function fetchInventario() {
    fetch('ConsultarInventario')
        .then(response => response.json())
        .then(data => {
            let tbody = document.getElementById("tabla-inventario");
            tbody.innerHTML = ""; 

            data.forEach(producto => {
                let row = `<tr>
                    <td>${producto.nombre}</td>
                    <td>${producto.imagen}</td>
                    <td>${producto.codigo}</td>
                    <td>${producto.categoria}</td>
                    <td>${producto.stock}</td>
                    <td>$${producto.precio}</td>
                </tr>`;
                tbody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error al obtener datos:", error));
}


