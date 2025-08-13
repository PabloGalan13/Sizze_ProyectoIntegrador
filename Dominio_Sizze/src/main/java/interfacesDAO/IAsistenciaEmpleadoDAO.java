/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfacesDAO;

import entidades.AsistenciaEmpleado;
import excepciones.ExcepcionAT;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public interface IAsistenciaEmpleadoDAO {

    public void registrarAsistencia(AsistenciaEmpleado asistencia) throws ExcepcionAT;
    public List<AsistenciaEmpleado> obtenerAsistencias() throws ExcepcionAT;

}
