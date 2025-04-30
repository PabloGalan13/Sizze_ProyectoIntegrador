/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package registrarVenta;

import entidades.Producto;
import entidades.Venta;
import excepciones.NegocioException;

/**
 *
 * @author PERSONAL
 */
public interface IRegistrarVentaBO {

    public Producto obtenerProducto(String nombre) throws NegocioException;
    public void registrarVenta(Venta v) throws NegocioException;
    
}
