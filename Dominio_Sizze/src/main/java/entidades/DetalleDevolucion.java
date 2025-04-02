/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;


/**
 *
 * @author USER
 */
@Entity
public class DetalleDevolucion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "precio_vendido")
    private double precioVendido;
    @Column(name = "cantidad_devuelta")
    private int cantidadDevuelta;
    @ManyToOne
    private Devolucion devolucion;

    public DetalleDevolucion() {
    }

    public DetalleDevolucion(LocalDate fecha, double precioVendido, int cantidadDevuelta, Devolucion devolucion) {
        this.fecha = fecha;
        this.precioVendido = precioVendido;
        this.cantidadDevuelta = cantidadDevuelta;
        this.devolucion = devolucion;
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

    public int getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(int cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }
    
    
}
