/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "ventas")
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Empleado vendedor;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "total", nullable = false)
    private double total;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;
    
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> productos;

    public Venta(Empleado vendedor, LocalDate fecha, MetodoPago metodoPago, List<DetalleVenta> productos) {
        this.vendedor = vendedor;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.productos = productos;
    }

    public Venta() {
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empleado getVendedor() {
        return vendedor;
    }

    public void setVendedor(Empleado vendedor) {
        this.vendedor = vendedor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<DetalleVenta> getProductos() {
        return productos;
    }

    public void setProductos(List<DetalleVenta> productos) {
        this.productos = productos;
    }
    
}
