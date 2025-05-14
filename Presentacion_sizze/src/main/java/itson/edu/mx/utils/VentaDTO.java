/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.utils;

import entidades.Venta;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author USER
 */
public class VentaDTO {
    private Long id;
    private LocalDate fecha;
    private double total;
    private String empleado;

    public VentaDTO(Venta venta) {
        this.id = venta.getId();
        this.fecha = venta.getFecha();
        this.empleado = venta.getVendedor().getNombre(); // Asumiendo esto
        this.total = venta.getTotal(); // Asegúrate que esto exista
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    
    
    
}