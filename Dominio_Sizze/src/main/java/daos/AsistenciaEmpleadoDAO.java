/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import entidades.AsistenciaEmpleado;
import excepciones.ExcepcionAT;
import interfacesDAO.IAsistenciaEmpleadoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class AsistenciaEmpleadoDAO implements IAsistenciaEmpleadoDAO{

    private final EntityManagerFactory emf;
    private EntityManager em;

    public AsistenciaEmpleadoDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }

    @Override
    public void registrarAsistencia(AsistenciaEmpleado asistencia) throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(asistencia);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al registrar asistencia", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<AsistenciaEmpleado> obtenerAsistencias() throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            List<AsistenciaEmpleado> lista = em.createQuery("SELECT a FROM AsistenciaEmpleado a", AsistenciaEmpleado.class).getResultList();
            em.getTransaction().commit();
            return lista;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al obtener asistencias", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
