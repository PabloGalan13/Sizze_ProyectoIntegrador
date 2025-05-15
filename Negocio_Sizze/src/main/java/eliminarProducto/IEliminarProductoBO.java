package eliminarProducto;

import entidades.Producto;
import excepciones.NegocioException;

public interface IEliminarProductoBO {
    void eliminarProducto(long idProducto) throws NegocioException;
    Producto buscarProducto(String nombre) throws NegocioException;
}