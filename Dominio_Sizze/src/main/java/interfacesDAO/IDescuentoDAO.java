/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesDAO;

import entidades.Descuentos;
import excepciones.ExcepcionAT;

/**
 *
 * @author yohan
 */
public interface IDescuentoDAO {

    public void registrarDescuento(Descuentos descuento) throws ExcepcionAT;
}
