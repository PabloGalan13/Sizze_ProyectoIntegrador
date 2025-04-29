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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class InsertarProductos {

    public static void main(String[] args) {
        ProductoDAO dao = new ProductoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        // Crear categorías principales
        Categoria categoriaRopa = new Categoria("Ropa", "Categoría de ropa");
        try {
            categoriaDAO.registrarCategoria(categoriaRopa);
        } catch (ExcepcionAT ex) {
            Logger.getLogger(InsertarProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

        Categoria categoriaMica = new Categoria("Mica", "Protectores de pantalla");
        try {
            categoriaDAO.registrarCategoria(categoriaMica);
        } catch (ExcepcionAT ex) {
            Logger.getLogger(InsertarProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

        Categoria categoriaAccesorios = new Categoria("Accesorios", "Accesorios varios para dispositivos");
        try {
            categoriaDAO.registrarCategoria(categoriaAccesorios);
        } catch (ExcepcionAT ex) {
            Logger.getLogger(InsertarProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Crear lista de productos
        List<Producto> productos = new ArrayList<>();

        // Productos de ROPA (modelo = talla)
        productos.add(new Producto("Remera Oversize", "Remera de algodón 100% con corte oversize y estampa frontal.", "M", 8500.0, 20, categoriaRopa, "Remera", "postImgs/ropa1.jpg"));
        productos.add(new Producto("Campera de Jean", "Campera de jean azul clásico, ideal para media estación.", "L", 18500.0, 10, categoriaRopa, "Campera", "postImgs/ropa2.jpg"));
        productos.add(new Producto("Pantalón Cargo", "Pantalón cargo color verde oliva con múltiples bolsillos.", "Talle Único", 12000.0, 15, categoriaRopa, "Pantalón", "postImgs/ropa3.jpg"));
        productos.add(new Producto("Buzo Hoodie", "Buzo con capucha y bolsillo frontal, tejido de algodón grueso.", "XL", 14000.0, 18, categoriaRopa, "Buzo", "postImgs/ropa4.jpg"));

        // Productos de MICA (modelo = modelo del celular/tablet)
        productos.add(new Producto("Mica Transparente iPhone 14", "Protector de pantalla ultra transparente y resistente a rayaduras.", "iPhone 14", 3000.0, 25, categoriaMica, "Protector Pantalla", "postImgs/mica1.jpg"));
        productos.add(new Producto("Mica Privacidad Samsung S23", "Protector de privacidad que oscurece la pantalla desde ángulos laterales.", "Samsung Galaxy S23", 3500.0, 30, categoriaMica, "Protector Privacidad", "postImgs/mica2.jpg"));
        productos.add(new Producto("Mica Hidrogel Xiaomi Redmi Note 12", "Protector flexible de hidrogel para mejor cobertura de pantalla.", "Redmi Note 12", 2800.0, 20, categoriaMica, "Protector Hidrogel", "postImgs/mica3.jpg"));
        productos.add(new Producto("Mica Antireflejo iPad Pro 12.9", "Protector de pantalla antirreflejo para lectura y uso intensivo.", "iPad Pro 12.9", 5000.0, 12, categoriaMica, "Protector Antireflejo", "postImgs/mica4.jpg"));

        // Productos de ACCESORIOS (modelo = dispositivo compatible)
        productos.add(new Producto("Funda Silicona iPhone 13", "Funda protectora de silicona suave, anti golpes.", "iPhone 13", 4500.0, 22, categoriaAccesorios, "Funda", "postImgs/accesorio1.jpg"));
        productos.add(new Producto("Cargador Inalámbrico 15W", "Base de carga inalámbrica rápida compatible con Qi.", "Universal Qi", 6200.0, 15, categoriaAccesorios, "Cargador", "postImgs/accesorio2.jpg"));
        productos.add(new Producto("Auriculares Bluetooth", "Auriculares inalámbricos con cancelación de ruido activa.", "Universal", 9500.0, 8, categoriaAccesorios, "Auriculares", "postImgs/accesorio3.jpg"));
        productos.add(new Producto("Soporte de Auto Magnético", "Soporte magnético universal para celular en el auto.", "Universal", 3200.0, 18, categoriaAccesorios, "Soporte", "postImgs/accesorio4.jpg"));

        for (Producto producto : productos) {
            try {
                dao.registrarProducto(producto);
            } catch (ExcepcionAT ex) {
                Logger.getLogger(InsertarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
