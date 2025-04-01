
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Clade que maneja las excepciones del proyecto
 * @author 
 */
public class ExcepcionAT extends Exception{

    /**
     * Constructor vacio
     */
    public ExcepcionAT() {
    }

    /**
     * Contructor de la excepcion con el mensaje
     * @param message
     */
    public ExcepcionAT(String message) {
        super(message);
    }

    /**
     * Contructor de la excepcion con el mensaje y la causa
     * @param message
     * @param cause
     */
    public ExcepcionAT(String message, Throwable cause) {
        super(message, cause);
    }

    
}