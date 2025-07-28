/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package registrarProducto;

import daos.ProductoDAO;
import entidades.Producto;
import excepciones.ExcepcionAT;

/**
 *
 * @author Gabriel
 */
public class RegistrarProductoBO {

    public boolean registrarProducto(Producto producto) throws ExcepcionAT {
        if (producto != null) {
            ProductoDAO productoDAO = new ProductoDAO();
            return productoDAO.registrarProducto(producto);
        } else {
            throw new ExcepcionAT("Producto no se pudo registrar: " + producto.getNombre());
        }
    }
}
