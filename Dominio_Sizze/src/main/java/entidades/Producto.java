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
    
    @Column(name = "imagen", nullable = true, length = 255)
    private String imagen;

    @Column(name = "precio", nullable = false)
    private double precio;
    
    @Column(name = "talla_modelo", nullable = false)
    private String tallaModelo;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, String imagen, double precio, String tallaModelo, int stock, String tipo, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precio = precio;
        this.tallaModelo = tallaModelo;
        this.stock = stock;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    public Producto(String nombre, String descripcion, double precio, String tallaModelo, int stock, String tipo, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tallaModelo = tallaModelo;
        this.stock = stock;
        this.tipo = tipo;
        this.categoria = categoria;
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

    public String getTallaModelo() {
        return tallaModelo;
    }

    public void setTallaModelo(String tallaModelo) {
        this.tallaModelo = tallaModelo;
    }

    
}
