/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import daos.CategoriaDAO;
import daos.ProductoDAO;
import entidades.Categoria;
import entidades.Producto;
import excepciones.ExcepcionAT;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class InsertarProductos {

    public static void main(String[] args) {
        try {
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            ProductoDAO productoDAO = new ProductoDAO();

            // Categorías
            Categoria categoriaMicas = new Categoria("Micas", "Protectores de pantalla para dispositivos");
            categoriaDAO.registrarCategoria(categoriaMicas);
            Categoria categoriaRopa = new Categoria("Ropa", "Prendas de vestir para diferentes estilos");
            categoriaDAO.registrarCategoria(categoriaRopa);
            Categoria categoriaFundas = new Categoria("Fundas", "Fundas protectoras para dispositivos");
            categoriaDAO.registrarCategoria(categoriaFundas);

            // Productos para la categoría Micas
            Producto mica1 = new Producto("Mica de Vidrio 9H", "Mica de vidrio templado resistente a rayones","userImgs/mica1.jpg", 10.99, "Universal", 50, "proteccion", categoriaMicas);
            Producto mica2 = new Producto("Mica Mate", "Protector de pantalla mate antirreflejo","userImgs/mica2.jpg", 12.50, "iPhone 13", 40, "proteccion", categoriaMicas);
            Producto mica3 = new Producto("Mica Full Cover", "Cubre toda la pantalla sin dejar bordes","userImgs/mica3.jpg", 14.99, "Samsung S22", 30, "proteccion", categoriaMicas);
            Producto mica4 = new Producto("Mica Hidrogel", "Flexible y auto-regenerativa","userImgs/mica4.jpg", 16.00, "Xiaomi Mi 11", 25, "proteccion", categoriaMicas);
            Producto mica5 = new Producto("Mica Antiespía", "Protege tu privacidad con ángulos reducidos de visión","userImgs/mica5.jpg", 18.50, "iPhone 12", 35, "proteccion", categoriaMicas);

            // Productos para la categoría Ropa
            Producto ropa1 = new Producto("Camiseta Básica", "Camiseta de algodón en varios colores","userImgs/ropa1.jpg", 15.99, "M", 100, "camisa", categoriaRopa);
            Producto ropa2 = new Producto("Jeans Slim Fit", "Pantalón de mezclilla con diseño moderno","userImgs/ropa2.jpg", 29.99, "32", 80, "pantalon", categoriaRopa);
            Producto ropa3 = new Producto("Sudadera con Capucha", "Abrigo cómodo para climas fríos","userImgs/ropa3.jpg", 39.99, "L", 50, "sudadera", categoriaRopa);
            Producto ropa4 = new Producto("Chaqueta de Cuero", "Chaqueta elegante y duradera","userImgs/ropa4.jpg", 89.99, "XL", 20, "chaqueta", categoriaRopa);
            Producto ropa5 = new Producto("Vestido Casual", "Vestido ligero ideal para el verano","userImgs/ropa5.jpg", 24.99, "S", 60, "vestido", categoriaRopa);

            // Productos para la categoría Fundas
            Producto funda1 = new Producto("Funda Transparente", "Funda de silicona flexible y resistente","userImgs/funda1.jpeg", 9.99, "iPhone 14", 100, "accesorio", categoriaFundas);
            Producto funda2 = new Producto("Funda Antigolpes", "Funda con protección reforzada en esquinas","userImgs/funda2.jpg", 14.50, "Samsung S21", 80, "accesorio", categoriaFundas);

            // Registro de productos
            ArrayList<Producto> todosLosProductos = new ArrayList<>();
            todosLosProductos.add(mica1);
            todosLosProductos.add(mica2);
            todosLosProductos.add(mica3);
            todosLosProductos.add(mica4);
            todosLosProductos.add(mica5);
            todosLosProductos.add(ropa1);
            todosLosProductos.add(ropa2);
            todosLosProductos.add(ropa3);
            todosLosProductos.add(ropa4);
            todosLosProductos.add(ropa5);
            todosLosProductos.add(funda1);
            todosLosProductos.add(funda2);
            for (Producto producto : todosLosProductos) {
                productoDAO.registrarProducto(producto);
                System.out.println("Producto registrado: " + producto.getNombre());
            }
        } catch (ExcepcionAT ex) {
            Logger.getLogger(InsertarProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

