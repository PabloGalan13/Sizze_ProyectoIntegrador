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

            // Crear descuentos
            Descuentos d1 = new Descuentos( "PROMO10", 10.0f); // 10%
            descuentoDAO.registrarDescuento(d1);
            System.out.println("Descuento  registrado exitosamente.");

            Descuentos d2 = new Descuentos("BLACKFRIDAY", 50.0f); // 50% en blackfriday
            descuentoDAO.registrarDescuento(d2);
            System.out.println("Descuento  registrado exitosamente.");

            Descuentos d3 = new Descuentos( "NAVIDAD25", 25.0f); // 25% en navidad
            descuentoDAO.registrarDescuento(d3);
            System.out.println("Descuento  registrado exitosamente.");

            Descuentos d4 = new Descuentos( "FUNDA15", 15.0f);     // 15% en fundas
            descuentoDAO.registrarDescuento(d4);
            System.out.println("Descuento  registrado exitosamente.");

            Descuentos d5 = new Descuentos( "MICA20", 20.0f);       // 20% en micas
            descuentoDAO.registrarDescuento(d5);
            System.out.println("Descuento  registrado exitosamente.");

            Descuentos d6 = new Descuentos( "ROPAVERANO10", 10.0f); // 10% en verano
            descuentoDAO.registrarDescuento(d6);
            System.out.println("Descuento  registrado exitosamente.");

            Descuentos d7 = new Descuentos( "INVIERNO30", 30.0f);   // 30% en           invierno
            descuentoDAO.registrarDescuento(d7);
            System.out.println("Descuento  registrado exitosamente.");

            Descuentos d8 = new Descuentos( "ACCESORIOS25", 25.0f); // 25% en accesorios
            descuentoDAO.registrarDescuento(d8);
            System.out.println("Descuento  registrado exitosamente.");

        } catch (ExcepcionAT e) {
            throw new ExcepcionAT(e.getMessage());
        }
    }
}
