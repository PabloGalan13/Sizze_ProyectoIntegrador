/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "precio_vendido", nullable = false)
    private double precioVendido;
    
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    public DetalleVenta() {
    }

    public DetalleVenta(LocalDate fecha, double precioVendido, int cantidad, Producto producto, Venta venta) {
        this.fecha = fecha;
        this.precioVendido = precioVendido;
        this.cantidad = cantidad;
        this.producto = producto;
        this.venta = venta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPrecioVendido() {
        return precioVendido;
    }

    public void setPrecioVendido(double precioVendido) {
        this.precioVendido = precioVendido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
    
    
    
}
