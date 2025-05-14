/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.utils;

import entidades.Venta;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author USER
 */
public class VentaDetalleDTO {
    private Long id;
    private String empleado;
    private String fecha;
    private String metodoPago;
    private double total;
    private List<ProductoDTO> productos;

    public VentaDetalleDTO(Venta venta) {
        this.id = venta.getId();
        this.empleado = venta.getVendedor().getNombre(); // Ajusta según tu clase Empleado
        this.fecha = venta.getFecha().toString();
        this.metodoPago = venta.getMetodoPago().name();
        this.total = venta.getTotal();
        this.productos = venta.getProductos().stream()
            .map(ProductoDTO::new)
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    
}
