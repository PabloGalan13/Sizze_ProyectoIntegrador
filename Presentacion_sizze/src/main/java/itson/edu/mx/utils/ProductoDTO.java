/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.utils;

import entidades.DetalleVenta;

/**
 *
 * @author USER
 */
public class ProductoDTO {
    private String nombre;
    private int cantidad;
    private double precio;
    private double total;

    public ProductoDTO(DetalleVenta dv) {
        this.nombre = dv.getProducto().getNombre(); // Ajusta si tu DetalleVenta tiene referencia a Producto
        this.cantidad = dv.getCantidad();
        this.precio = dv.getPrecioVendido();
        this.total = dv.getCantidad() * dv.getPrecioVendido();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    
}
