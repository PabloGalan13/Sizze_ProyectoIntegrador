/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consultaVenta;

import daos.VentaDAO;
import entidades.Venta;
import excepciones.ExcepcionAT;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaVentaBO {

    private final VentaDAO ventaDAO = new VentaDAO();

    public List<Venta> obtenerTodasLasVentas() throws Exception {
        try {
            return ventaDAO.obtenerVentas();
        } catch (Exception ex) {
            Logger.getLogger(ConsultaVentaBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error al consultar las ventas: " + ex.getMessage());
        }
    }

    public List<Venta> obtenerVentasPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return ventaDAO.obtenerPorRangoFechas(inicio, fin);
    }

    public Venta obtenerVentaPorId(Long idVenta) throws Exception {
        return ventaDAO.obtenerVentaPorId(idVenta);
    }

}
