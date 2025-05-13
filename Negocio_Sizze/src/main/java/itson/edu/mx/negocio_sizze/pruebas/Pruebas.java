/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package itson.edu.mx.negocio_sizze.pruebas;

import daos.ProductoDAO;
import entidades.Producto;
import itson.edu.mx.negocio_sizze.ProductoNegocio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ProductoNegocio productoNegocio= new ProductoNegocio();
            List<Producto> productos = productoNegocio.consultarTodosProductos();
            for (Producto producto : productos) {
                System.out.println(producto.getNombre());
            }
        } catch (Exception ex) {
            Logger.getLogger(Pruebas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
