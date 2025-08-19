package interfacesDAO;

import entidades.Venta;
import excepciones.ExcepcionAT;
import java.time.LocalDate;
import java.util.List;

public interface IVentaDAO {

    void registrarVenta(Venta venta) throws ExcepcionAT;

    void actualizarVenta(Venta venta) throws ExcepcionAT;

    void eliminarVenta(Venta venta) throws ExcepcionAT;

    Venta obtenerVentaPorId(Long id) throws ExcepcionAT;

    List<Venta> obtenerVentas() throws ExcepcionAT;

    List<Venta> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) throws ExcepcionAT;
}
