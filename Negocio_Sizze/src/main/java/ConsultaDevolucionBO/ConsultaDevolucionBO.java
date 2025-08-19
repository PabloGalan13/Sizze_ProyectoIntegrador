/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConsultaDevolucionBO;

import daos.DevolucionDAO;
import entidades.Devolucion;
import excepciones.ExcepcionAT;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class ConsultaDevolucionBO {

    private final DevolucionDAO devolucionDAO = new DevolucionDAO();

    public void registrarDevolucion(Devolucion devolucion) throws Exception {
        try {
            devolucionDAO.registrarDevolucion(devolucion);
        } catch (ExcepcionAT ex) {
            Logger.getLogger(ConsultaDevolucionBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error al registrar devolución: " + ex.getMessage());
        }
    }

    public void actualizarDevolucion(Devolucion devolucion) throws Exception {
        try {
            devolucionDAO.actualizarDevolucion(devolucion);
        } catch (ExcepcionAT ex) {
            Logger.getLogger(ConsultaDevolucionBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error al actualizar devolución: " + ex.getMessage());
        }
    }

    public void eliminarDevolucion(Devolucion devolucion) throws Exception {
        try {
            devolucionDAO.eliminarDevolucion(devolucion);
        } catch (ExcepcionAT ex) {
            Logger.getLogger(ConsultaDevolucionBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error al eliminar devolución: " + ex.getMessage());
        }
    }

    public Devolucion obtenerDevolucionPorId(Long id) throws Exception {
        try {
            return devolucionDAO.obtenerDevolucionPorId(id);
        } catch (ExcepcionAT ex) {
            Logger.getLogger(ConsultaDevolucionBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error al obtener devolución por ID: " + ex.getMessage());
        }
    }

    public List<Devolucion> obtenerTodasLasDevoluciones() throws Exception {
        try {
            return devolucionDAO.obtenerDevoluciones();
        } catch (ExcepcionAT ex) {
            Logger.getLogger(ConsultaDevolucionBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error al obtener devoluciones: " + ex.getMessage());
        }
    }

    public List<Devolucion> obtenerDevolucionesPorRangoFechas(LocalDate inicio, LocalDate fin) throws Exception {
        try {
            return devolucionDAO.obtenerDevolucionesPorRangoFechas(inicio, fin);
        } catch (ExcepcionAT ex) {
            Logger.getLogger(ConsultaDevolucionBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error al obtener devoluciones por rango de fechas: " + ex.getMessage());
        }
    }
}
