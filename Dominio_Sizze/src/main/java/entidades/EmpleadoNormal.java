/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;


/**
 *
 * @author USER
 */
@Entity
@Table(name = "empleados_normales")
public class EmpleadoNormal extends Empleado implements Serializable {

    public EmpleadoNormal() {
    }

    public EmpleadoNormal(String nombre, String apellido, String rfc, String contrasena, Horario horario) {
        super(nombre, apellido, rfc, contrasena, horario);
    }
    
}
