/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfacesDAO;

import entidades.Devolucion;
import excepciones.ExcepcionAT;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public interface IDevolucionDAO {

    void registrarDevolucion(Devolucion devolucion) throws ExcepcionAT;

    void actualizarDevolucion(Devolucion devolucion) throws ExcepcionAT;

    void eliminarDevolucion(Devolucion devolucion) throws ExcepcionAT;

    Devolucion obtenerDevolucionPorId(Long id) throws ExcepcionAT;

    List<Devolucion> obtenerDevoluciones() throws ExcepcionAT;

    List<Devolucion> obtenerDevolucionesPorRangoFechas(LocalDate inicio, LocalDate fin) throws ExcepcionAT;
}
