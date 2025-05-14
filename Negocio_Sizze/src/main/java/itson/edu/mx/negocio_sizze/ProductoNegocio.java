/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.negocio_sizze;

import daos.ProductoDAO;
import entidades.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
/**
 * Clase encargada de la lógica de negocio para los productos
 */
public class ProductoNegocio {
    
    private ProductoDAO productoDAO;
    
    /**
     * Constructor que inicializa el DAO
     */
    public ProductoNegocio() {
        this.productoDAO = new ProductoDAO();
    }
    
    /**
     * Consulta todos los productos disponibles
     * @return Lista de productos
     * @throws Exception Si ocurre algún error durante la consulta
     */
    public List<Producto> consultarTodosProductos() throws Exception {
        try {
            // Llamar al DAO para obtener todos los productos
            List<Producto> productos = productoDAO.obtenerProductos();
            
            // Aquí se podrían aplicar reglas de negocio adicionales
            // por ejemplo, filtrar productos descontinuados, etc.
            
            return productos;
        } catch (Exception e) {
            // Registrar el error en logs y relanzar
            System.err.println("Error al consultar todos los productos: " + e.getMessage());
            throw new Exception("Error al consultar el inventario: " + e.getMessage());
        }
    }
    
    /**
     * Busca productos que coincidan con el nombre, modeloTalla o descripción proporcionados
     * @param criterio Cualquier palabra o parte de texto para buscar productos
     * @return Lista de productos que coinciden con el criterio de búsqueda
     * @throws Exception Si ocurre algún error durante la búsqueda
     */
    public List<Producto> buscarProducto(String criterio) throws Exception {
        try {
            if (criterio == null || criterio.trim().isEmpty()) {
                throw new Exception("El criterio de búsqueda no puede estar vacío");
            }
            
            // Preprocesar el criterio de búsqueda si es necesario
            criterio = criterio.trim().toLowerCase();
            
            // Llamar al DAO para buscar los productos que coincidan
            List<Producto> productos = productoDAO.buscarProducto(criterio);
            
            // Validar si se encontraron productos
            if (productos == null || productos.isEmpty()) {
                // No se encontraron productos, pero no es un error técnico
                return new ArrayList<>();
            }
            
            return productos;
        } catch (Exception e) {
            // Registrar el error en logs y relanzar
            System.err.println("Error al buscar productos: " + e.getMessage());
            throw new Exception("Error al buscar productos: " + e.getMessage());
        }
    }
}