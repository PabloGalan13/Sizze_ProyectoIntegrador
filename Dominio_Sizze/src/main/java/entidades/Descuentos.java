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
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author yohan
 */
@Entity
@Table(name = "Descuentos")

public class Descuentos implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String Codigo;
    @Column(name = "descuento")
    private Float Descuento;

    public Descuentos() {
    }

    public Descuentos(String Codigo, Float Descuento) {

        this.Codigo = Codigo;
        this.Descuento = Descuento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public Float getDescuento() {
        return Descuento;
    }

    public void setDescuento(Float Descuento) {
        this.Descuento = Descuento;
    }

}
