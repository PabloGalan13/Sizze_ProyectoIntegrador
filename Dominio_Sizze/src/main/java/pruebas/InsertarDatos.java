package pruebas;

import daos.*;
import entidades.*;
import excepciones.ExcepcionAT;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertarDatos {

    public static void main(String[] args) {
        try {
            // 1. Insertar Horario primero
            HorarioDAO horarioDAO = new HorarioDAO();
            Calendar calendario = Calendar.getInstance();
            calendario.set(1990, Calendar.MARCH, 25);
            Horario horario = new Horario(
                    Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
                    null, // Empleado se asignará después
                    LocalTime.of(9, 0),
                    LocalTime.of(18, 0)
            );
            horarioDAO.registrarHorario(horario); // Persistir primero el horario
            System.out.println("Horario registrado exitosamente.");

            // 2. Insertar EmpleadoNormal
            EmpleadoNormalDAO empleadoNormalDAO = new EmpleadoNormalDAO();
            EmpleadoNormal empleadoNormal = new EmpleadoNormal("Juan", "Pérez", "JPR12346", "contraseña123", horario); // RFC único
            horario.setEmpleado(empleadoNormal);  // Asignar el horario al empleado normal
            empleadoNormalDAO.registrarEmpleadoNormal(empleadoNormal);
            System.out.println("Empleado Normal registrado exitosamente.");

            // 3. Insertar Producto con Categoría
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            ProductoDAO productoDAO = new ProductoDAO();

            Categoria categoria = new Categoria("Electrónica", "Dispositivos electrónicos y accesorios");
            categoriaDAO.registrarCategoria(categoria);
            System.out.println("Categoría registrada exitosamente.");

            Producto producto = new Producto("Smartphone", "Teléfono móvil de última generación", "xed", 499.99, 20,categoria,"telefono", "xd");
            productoDAO.registrarProducto(producto);
            System.out.println("Producto registrado exitosamente.");

            // 4. Insertar Venta y DetalleVenta
            VentaDAO ventaDAO = new VentaDAO();
            DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();


            Venta venta = new Venta(empleadoNormal, LocalDate.now(), MetodoPago.TRANSFERENCIA, new ArrayList<>());
            ventaDAO.registrarVenta(venta);
            System.out.println("Venta registrada exitosamente.");

            // Insertar DetalleVenta
            DetalleVenta detalleVenta = new DetalleVenta(LocalDate.now(), producto.getPrecio(), 2, producto, venta);
            detalleVentaDAO.registrarDetalleVenta(detalleVenta);
            System.out.println("Detalle de Venta registrado exitosamente.");
            
        } catch (ExcepcionAT ex) {
            Logger.getLogger(InsertarDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
