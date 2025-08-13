/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Gabriel
 */
public class ReporteEmpleado {

    private String nombreCompleto;
    private String rfc;
    private long totalAsistencias;

    public ReporteEmpleado(String nombreCompleto, String rfc, long totalAsistencias) {
        this.nombreCompleto = nombreCompleto;
        this.rfc = rfc;
        this.totalAsistencias = totalAsistencias;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public long getTotalAsistencias() {
        return totalAsistencias;
    }

    public void setTotalAsistencias(long totalAsistencias) {
        this.totalAsistencias = totalAsistencias;
    }
}
