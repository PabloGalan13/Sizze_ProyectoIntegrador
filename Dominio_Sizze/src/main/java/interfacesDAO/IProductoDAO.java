package interfacesDAO;

import entidades.Producto;
import excepciones.ExcepcionAT;
import java.util.List;

public interface IProductoDAO {
    
    boolean registrarProducto(Producto producto) throws ExcepcionAT;
    
    void actualizarProducto(Producto producto) throws ExcepcionAT;
    
    void eliminarProducto(Producto producto) throws ExcepcionAT;
    
    Producto obtenerProductoPorNombre(String nombre) throws ExcepcionAT;
    
    Producto obtenerProductoPorId(long id) throws ExcepcionAT;
    
    List<Producto> obtenerProductos() throws ExcepcionAT;
}
