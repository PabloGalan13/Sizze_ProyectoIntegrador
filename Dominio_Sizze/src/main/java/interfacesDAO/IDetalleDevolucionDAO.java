/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfacesDAO;

import entidades.DetalleDevolucion;
import excepciones.ExcepcionAT;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public interface IDetalleDevolucionDAO {

    void registrarDetalleDevolucion(DetalleDevolucion detalleDevolucion) throws ExcepcionAT;

    void actualizarDetalleDevolucion(DetalleDevolucion detalleDevolucion) throws ExcepcionAT;

    void eliminarDetalleDevolucion(DetalleDevolucion detalleDevolucion) throws ExcepcionAT;

    DetalleDevolucion obtenerDetalleDevolucionPorId(Long id) throws ExcepcionAT;

    List<DetalleDevolucion> obtenerDetallesDevolucion() throws ExcepcionAT;
}
