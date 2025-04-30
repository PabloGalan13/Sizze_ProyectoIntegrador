/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package registrarVenta;

import daos.ProductoDAO;
import daos.VentaDAO;
import entidades.DetalleVenta;
import entidades.Producto;
import entidades.Venta;
import excepciones.NegocioException;
import interfacesDAO.IProductoDAO;
import interfacesDAO.IVentaDAO;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase que implementa la lógica de negocio para el registro de ventas.
 * Se encarga de validar los productos, calcular totales y registrar las ventas
 * con su respectivo detalle, interactuando con las capas DAO.
 * 
 * @author PERSONAL
 */
public class RegistrarVentaBO implements IRegistrarVentaBO {

    private IProductoDAO prodDAO;
    private IVentaDAO ventaDAO;

    /**
     * Constructor que inicializa las dependencias DAO.
     */
    public RegistrarVentaBO() {
        prodDAO = new ProductoDAO();
        ventaDAO = new VentaDAO();
    }
    
    /**
     * Obtiene un producto por su nombre.
     * 
     * @param nombre Nombre del producto a buscar
     * @return Producto encontrado
     * @throws NegocioException Si ocurre un error en la búsqueda o el producto no existe
     */
    @Override
    public Producto obtenerProducto(String nombre) throws NegocioException {
        try {
            if (nombre == null || nombre.isEmpty()) {
                throw new NegocioException("El nombre del producto no puede estar vacío");
            }
            
            Producto productoEncontrado = prodDAO.obtenerProductoPorNombre(nombre);
            
            if (productoEncontrado == null) {
                throw new NegocioException("Producto no encontrado");
            }
            
            return productoEncontrado;
        } catch (Exception e) {
            throw new NegocioException("Error al obtener producto: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si hay suficiente stock para un producto.
     * 
     * @param producto Producto a verificar
     * @param cantidad Cantidad requerida
     * @return true si hay suficiente stock, false en caso contrario
     * @throws NegocioException Si ocurre un error en la verificación
     */
    private boolean verificarStock(Producto producto, int cantidad) throws NegocioException {
        try {
            if (producto == null) {
                throw new NegocioException("El producto no puede ser nulo");
            }
            
            if (cantidad <= 0) {
                throw new NegocioException("La cantidad debe ser mayor que cero");
            }
            
            Producto productoBD = prodDAO.obtenerProductoPorNombre(producto.getNombre());
            
            if (productoBD == null) {
                throw new NegocioException("Producto no encontrado");
            }
            
            return productoBD.getStock() >= cantidad;
        } catch (Exception e) {
            throw new NegocioException("Error al verificar stock: " + e.getMessage(), e);
        }
    }

    /**
     * Registra una nueva venta con sus detalles después de validar los productos.
     * 
     * @param venta Venta a registrar
     * @throws NegocioException Si ocurre un error en el registro o validación
     */
    @Override
    public void registrarVenta(Venta venta) throws NegocioException {
        try {
            if (venta == null) {
                throw new NegocioException("La venta no puede ser nula");
            }
            
            if (venta.getProductos() == null || venta.getProductos().isEmpty()) {
                throw new NegocioException("La venta debe contener al menos un producto");
            }
            
            if (venta.getVendedor() == null) {
                throw new NegocioException("La venta debe tener un vendedor asociado");
            }
            
            // Validar stock para cada producto en los detalles de venta
            for (DetalleVenta detalle : venta.getProductos()) {
                if (!verificarStock(detalle.getProducto(), detalle.getCantidad())) {
                    throw new NegocioException("Stock insuficiente para el producto: " + 
                            detalle.getProducto().getNombre());
                }
            }
            
            // Establecer fecha actual si no viene especificada
            if (venta.getFecha() == null) {
                venta.setFecha(LocalDate.now());
            }
            
            // Calcular total de la venta sumando los detalles
            double total = venta.getProductos().stream()
                .mapToDouble(detalle -> detalle.getPrecioVendido() * detalle.getCantidad())
                .sum();
            venta.setTotal(total);
            
            // Establecer la venta en cada detalle
            for (DetalleVenta detalle : venta.getProductos()) {
                detalle.setVenta(venta);
                detalle.setFecha(venta.getFecha());
            }
            
            // Registrar la venta (esto guardará en cascada los detalles)
            ventaDAO.registrarVenta(venta);
            
            // Actualizar stock de productos vendidos
            for (DetalleVenta detalle : venta.getProductos()) {
                Producto producto = detalle.getProducto();
                Producto productoBD = prodDAO.obtenerProductoPorNombre(producto.getNombre());
                productoBD.setStock(productoBD.getStock() - detalle.getCantidad());
                prodDAO.actualizarProducto(productoBD);
            }
            
        } catch (Exception e) {
            throw new NegocioException("Error al registrar venta: " + e.getMessage(), e);
        }
    }
    
    /**
     * Valida los detalles de una venta antes de registrarla.
     * 
     * @param detalles Lista de detalles de venta a validar
     * @throws NegocioException Si algún detalle no es válido o no hay suficiente stock
     */
    private void validarDetallesVenta(List<DetalleVenta> detalles) throws NegocioException {
        if (detalles == null || detalles.isEmpty()) {
            throw new NegocioException("La venta debe contener al menos un producto");
        }
        
        for (DetalleVenta detalle : detalles) {
            if (detalle.getProducto() == null) {
                throw new NegocioException("El detalle de venta debe tener un producto asociado");
            }
            
            if (detalle.getCantidad() <= 0) {
                throw new NegocioException("La cantidad debe ser mayor que cero para el producto: " + 
                        detalle.getProducto().getNombre());
            }
            
            if (!verificarStock(detalle.getProducto(), detalle.getCantidad())) {
                throw new NegocioException("Stock insuficiente para el producto: " + 
                        detalle.getProducto().getNombre());
            }
        }
    }
}