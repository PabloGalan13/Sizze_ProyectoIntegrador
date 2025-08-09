
import daos.DescuentosDAO;
import entidades.Descuentos;
import excepciones.ExcepcionAT;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Gabriel
 */
public class consultaDescuentos {

    DescuentosDAO descuentosDAO = new DescuentosDAO();

    public Descuentos obtenerDescuentoPorCodifo(String CodigoDescuento) throws ExcepcionAT {
        Descuentos descuento = descuentosDAO.obtenerDescuentosPorCodigo(CodigoDescuento);
        if (descuento == null) {
            throw new ExcepcionAT("producto no encontrado con el codigo : " + descuento.getCodigo());
        }
        return descuento;
    }
}
