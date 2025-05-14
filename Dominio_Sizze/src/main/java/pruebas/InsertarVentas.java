package pruebas;

import daos.EmpleadoNormalDAO;
import daos.HorarioDAO;
import daos.ProductoDAO;
import daos.VentaDAO;
import entidades.*;
import excepciones.ExcepcionAT;
import java.time.DayOfWeek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertarVentas {

    public static void main(String[] args) {
        EmpleadoNormalDAO empleadoDAO = new EmpleadoNormalDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        HorarioDAO horarioDAO = new HorarioDAO();
        VentaDAO ventaDAO = new VentaDAO();

        try {
            // Crear lista de días
            List<DayOfWeek> diasLaborales = Arrays.asList(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY
            );

            // Crear empleado (sin horario aún)
            EmpleadoNormal vendedor = new EmpleadoNormal("Carlos", "Pérez", "RFC123456", "contrasena123", null);

            // Crear horario con referencia al empleado
            Horario horario = new Horario(diasLaborales, vendedor, LocalTime.of(9, 0), LocalTime.of(18, 0));

            // Asociar horario al empleado
            vendedor.setHorario(horario);

            // Registrar empleado (con horario en cascada)
            empleadoDAO.registrarEmpleadoNormal(vendedor);

            // Obtener productos
            Producto producto1 = productoDAO.obtenerProductoPorNombre("Remera Oversize");
            Producto producto2 = productoDAO.obtenerProductoPorNombre("Mica Transparente iPhone 14");
            Producto producto3 = productoDAO.obtenerProductoPorNombre("Funda Silicona iPhone 13");

            // Venta 1
            Venta venta1 = new Venta();
            venta1.setVendedor(vendedor);
            venta1.setFecha(LocalDate.now());
            venta1.setMetodoPago(MetodoPago.EFECTIVO);
            List<DetalleVenta> detalles1 = new ArrayList<>();
            detalles1.add(new DetalleVenta(LocalDate.now(), producto1.getPrecio(), 2, producto1, venta1));
            venta1.setProductos(detalles1);
            venta1.setTotal(detalles1.stream().mapToDouble(d -> d.getPrecioVendido() * d.getCantidad()).sum());

            // Venta 2
            Venta venta2 = new Venta();
            venta2.setVendedor(vendedor);
            venta2.setFecha(LocalDate.now().minusDays(1));
            venta2.setMetodoPago(MetodoPago.TRANSFERENCIA);
            List<DetalleVenta> detalles2 = new ArrayList<>();
            detalles2.add(new DetalleVenta(LocalDate.now().minusDays(1), producto2.getPrecio(), 1, producto2, venta2));
            detalles2.add(new DetalleVenta(LocalDate.now().minusDays(1), producto3.getPrecio(), 1, producto3, venta2));
            venta2.setProductos(detalles2);
            venta2.setTotal(detalles2.stream().mapToDouble(d -> d.getPrecioVendido() * d.getCantidad()).sum());

            // Venta 3
            Venta venta3 = new Venta();
            venta3.setVendedor(vendedor);
            venta3.setFecha(LocalDate.now().minusDays(2));
            venta3.setMetodoPago(MetodoPago.TRANSFERENCIA);
            List<DetalleVenta> detalles3 = new ArrayList<>();
            detalles3.add(new DetalleVenta(LocalDate.now().minusDays(2), producto1.getPrecio(), 1, producto1, venta3));
            venta3.setProductos(detalles3);
            venta3.setTotal(detalles3.stream().mapToDouble(d -> d.getPrecioVendido() * d.getCantidad()).sum());

            // Guardar ventas
            ventaDAO.registrarVenta(venta1);
            ventaDAO.registrarVenta(venta2);
            ventaDAO.registrarVenta(venta3);

            System.out.println("Ventas registradas correctamente.");

        } catch (ExcepcionAT ex) {
            Logger.getLogger(InsertarVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
