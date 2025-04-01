package interfacesDAO;

import entidades.EmpleadoNormal;
import excepciones.ExcepcionAT;
import java.util.List;

public interface IEmpleadoNormalDAO {

    void registrarEmpleadoNormal(EmpleadoNormal empleadoNormal) throws ExcepcionAT;

    void actualizarEmpleadoNormal(EmpleadoNormal empleadoNormal) throws ExcepcionAT;

    void eliminarEmpleadoNormal(EmpleadoNormal empleadoNormal) throws ExcepcionAT;

    EmpleadoNormal obtenerEmpleadoNormal(Long id) throws ExcepcionAT;

    List<EmpleadoNormal> obtenerEmpleadosNormales() throws ExcepcionAT;
}
