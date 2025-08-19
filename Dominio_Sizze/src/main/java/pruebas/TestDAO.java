/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import daos.DetalleDevolucionDAO;
import daos.DetalleVentaDAO;
import daos.DevolucionDAO;
import daos.VentaDAO;
import entidades.DetalleDevolucion;
import entidades.DetalleVenta;
import entidades.Devolucion;
import entidades.Venta;
import excepciones.ExcepcionAT;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class TestDAO {

    public static void main(String[] args) {

        try {
            VentaDAO ventaDAO = new VentaDAO();
            DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();
            DevolucionDAO devolucionDAO = new DevolucionDAO();
            DetalleDevolucionDAO detalleDevolucionDAO = new DetalleDevolucionDAO();


            // ---------- 3. Generar Reporte de Ventas ----------
            List<Venta> ventas = ventaDAO.obtenerPorRangoFechas(LocalDate.now().minusDays(7), LocalDate.now());
            System.out.println("📊 Ventas en los últimos 7 días: " + ventas.size());

            // ---------- 4. Consultar Productos más vendidos ----------
            List<Object[]> productosMasVendidos = detalleVentaDAO.obtenerProductosMasVendidos(LocalDate.now().minusDays(30), LocalDate.now());
            for (Object[] fila : productosMasVendidos) {
                String nombreProducto = (String) fila[0];
                Long totalVendidos = (Long) fila[1];
                System.out.println("🔥 Producto: " + nombreProducto + " - Cantidad: " + totalVendidos);
            }

            // ---------- 5. Registrar una Devolución ----------
            Devolucion devolucion = new Devolucion();
            devolucion.setFecha(LocalDate.now());
            devolucion.setMotivo("Producto defectuoso");
            devolucionDAO.registrarDevolucion(devolucion);
            System.out.println("✅ Devolución registrada con ID: " + devolucion.getId());

            // ---------- 6. Registrar un Detalle de Devolución ----------
            DetalleDevolucion detalleDevolucion = new DetalleDevolucion();
            detalleDevolucion.setDevolucion(devolucion);
            detalleDevolucion.setCantidadDevuelta(1);
            // detalleDevolucion.setProducto(producto); // si tienes entidad Producto
            detalleDevolucionDAO.registrarDetalleDevolucion(detalleDevolucion);
            System.out.println("✅ Detalle de devolución registrado");

            // ---------- 7. Consultar Devoluciones por rango de fechas ----------
            List<Devolucion> devoluciones = devolucionDAO.obtenerDevolucionesPorRangoFechas(LocalDate.now().minusDays(7), LocalDate.now());
            System.out.println("📦 Devoluciones en los últimos 7 días: " + devoluciones.size());

        } catch (ExcepcionAT e) {
            e.printStackTrace();
        }
    }
}
