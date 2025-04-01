package interfacesDAO;

import entidades.DetalleVenta;
import excepciones.ExcepcionAT;
import java.util.List;

public interface IDetalleVentaDAO {
    
    void registrarDetalleVenta(DetalleVenta detalleVenta) throws ExcepcionAT;
    
    void actualizarDetalleVenta(DetalleVenta detalleVenta) throws ExcepcionAT;
    
    void eliminarDetalleVenta(DetalleVenta detalleVenta) throws ExcepcionAT;
    
    DetalleVenta obtenerDetalleVentaPorId(Long id) throws ExcepcionAT;
    
    List<DetalleVenta> obtenerDetallesVenta() throws ExcepcionAT;
}
