package eliminarProducto;

import daos.ProductoDAO;
import entidades.Producto;
import excepciones.NegocioException;
import interfacesDAO.IProductoDAO;

public class EliminarProductoBO implements IEliminarProductoBO {
    private final IProductoDAO productoDAO;
    
    public EliminarProductoBO() {
        this.productoDAO = new ProductoDAO();
    }

    @Override
    public void eliminarProducto(long idProducto) throws NegocioException {
        try {
            Producto producto = productoDAO.obtenerProductoPorId(idProducto);
            if (producto == null) {
                throw new NegocioException("El producto no existe en el sistema.");
            }
            productoDAO.eliminarProducto(producto);
        } catch (Exception e) {
            throw new NegocioException("Error al eliminar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Producto buscarProducto(String nombre) throws NegocioException {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new NegocioException("El nombre no puede estar vacío.");
            }
            return productoDAO.obtenerProductoPorNombre(nombre);
        } catch (Exception e) {
            throw new NegocioException("Error al buscar producto: " + e.getMessage(), e);
        }
    }
}
