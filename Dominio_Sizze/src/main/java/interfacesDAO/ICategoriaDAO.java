package interfacesDAO;

import entidades.Categoria;
import excepciones.ExcepcionAT;
import java.util.List;

public interface ICategoriaDAO {

    void registrarCategoria(Categoria categoria) throws ExcepcionAT;

    void actualizarCategoria(Categoria categoria) throws ExcepcionAT;

    void eliminarCategoria(Categoria categoria) throws ExcepcionAT;

    Categoria obtenerCategoriaPorId(Long id) throws ExcepcionAT;

    Categoria obtenerCategoriaPorNombre(String nombre) throws ExcepcionAT;

    List<Categoria> obtenerCategorias() throws ExcepcionAT;
}
