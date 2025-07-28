/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consultaProducto;

import daos.ProductoDAO;
import entidades.Producto;
import excepciones.ExcepcionAT;

/**
 *
 * @author Gabriel
 */
public class ConsultaProductoBO {

    ProductoDAO productoDAO = new ProductoDAO();

    public Producto obtenerProductoPorId(Long idProducto) throws ExcepcionAT {
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);
        if (producto == null) {
            throw new ExcepcionAT("producto no encontrado con el id : " + idProducto);
        }
        return producto;
    }

    public void actualizarProducto(Producto producto) throws ExcepcionAT {
         if (producto == null) {
            throw new ExcepcionAT("No se pudo actualizar el producto"+ producto.getNombre());
        }
        productoDAO.actualizarProducto(producto);

    }
}
