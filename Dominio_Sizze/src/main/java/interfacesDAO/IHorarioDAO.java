package interfacesDAO;

import entidades.Horario;
import excepciones.ExcepcionAT;
import java.util.List;

public interface IHorarioDAO {

    void registrarHorario(Horario horario) throws ExcepcionAT;

    void actualizarHorario(Horario horario) throws ExcepcionAT;

    void eliminarHorario(Horario horario) throws ExcepcionAT;

    Horario obtenerHorario(Long id) throws ExcepcionAT;

    List<Horario> obtenerHorarios() throws ExcepcionAT;
}
