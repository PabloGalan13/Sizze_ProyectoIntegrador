/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author PERSONAL
 */
public class NegocioException extends Exception {
    
    /**
     * Constructor vacio
     */
    public NegocioException() {
    }

    /**
     * Contructor de la excepcion con el mensaje
     * @param message
     */
    public NegocioException(String message) {
        super(message);
    }

    /**
     * Contructor de la excepcion con el mensaje y la causa
     * @param message
     * @param cause
     */
    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}