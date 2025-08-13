/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import entidades.ReporteEmpleado;
import excepciones.ExcepcionAT;
import interfacesDAO.IReporteEmpleadoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class ReporteEmpleadoDAO implements IReporteEmpleadoDAO {

    private final EntityManagerFactory emf;
    private EntityManager em;

    public ReporteEmpleadoDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }

    @Override
    public List<ReporteEmpleado> generarReporteEmpleados() throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            String jpql = """
                SELECT new modelos.ReporteEmpleado(
                    CONCAT(e.nombre, ' ', e.apellido),
                    e.rfc,
                    COUNT(a)
                )
                FROM AsistenciaEmpleado a
                JOIN a.empleado e
                GROUP BY e.nombre, e.apellido, e.rfc
            """;

            List<ReporteEmpleado> reporte = em.createQuery(jpql, ReporteEmpleado.class).getResultList();

            em.getTransaction().commit();
            return reporte;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al generar reporte de empleados", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
