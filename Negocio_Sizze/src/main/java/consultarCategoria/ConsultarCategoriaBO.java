/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package consultarCategoria;

import daos.CategoriaDAO;
import entidades.Categoria;
import excepciones.ExcepcionAT;

/**
 *
 * @author Gabriel
 */
public class ConsultarCategoriaBO {

    public Categoria obtenerCategoriaPorNombre(String categoriaNombre) throws ExcepcionAT {
        CategoriaDAO dao = new CategoriaDAO();
        Categoria categoria = dao.obtenerCategoriaPorNombre(categoriaNombre);
        if (categoria == null) {
            throw new ExcepcionAT("Categoría no encontrada: " + categoriaNombre);
        }
        return categoria;
    }
    
}
