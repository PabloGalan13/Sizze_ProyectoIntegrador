/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "modeloTalla", nullable = false, length = 50)
    private String modeloTalla;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "imagen", nullable = false)
    private String imagen;

    @ManyToOne()
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, String modeloTalla, double precio, int stock, Categoria categoria, String tipo, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modeloTalla = modeloTalla;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.tipo = tipo;
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
