/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import daos.DescuentosDAO;
import entidades.Descuentos;
import excepciones.ExcepcionAT;

/**
 *
 * @author yohan
 */
public class InsertarDescuentos {

    public static void main(String[] args) throws ExcepcionAT {

        DescuentosDAO descuentoDAO = new DescuentosDAO();

        try {

            Descuentos t1 = descuentoDAO.obtenerDescuentosPorCodigo("ACCESORIOS25");
            System.out.println("El codigo " + t1.getCodigo() + " tiene el descuento de " + t1.getDescuento() + "%");

        } catch (ExcepcionAT e) {
            throw new ExcepcionAT(e.getMessage());
        }
    }
}
