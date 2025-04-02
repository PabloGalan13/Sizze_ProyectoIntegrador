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
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;


/**
 *
 * @author USER
 */
@Entity
public class Devolucion implements Serializable {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "fecha")
    private LocalDate fecha;
    @OneToOne
    private Venta ventaDevuelta;

    public Devolucion() {
    }

    public Devolucion(String motivo, LocalDate fecha, Venta ventaDevuelta) {
        this.motivo = motivo;
        this.fecha = fecha;
        this.ventaDevuelta = ventaDevuelta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Venta getVentaDevuelta() {
        return ventaDevuelta;
    }

    public void setVentaDevuelta(Venta ventaDevuelta) {
        this.ventaDevuelta = ventaDevuelta;
    }
    
}
