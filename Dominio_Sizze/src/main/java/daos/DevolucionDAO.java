/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import entidades.Devolucion;
import excepciones.ExcepcionAT;
import interfacesDAO.IDevolucionDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class DevolucionDAO implements IDevolucionDAO {

    private final EntityManagerFactory emf;

    public DevolucionDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }

    @Override
    public void registrarDevolucion(Devolucion devolucion) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(devolucion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al registrar devolución", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void actualizarDevolucion(Devolucion devolucion) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(devolucion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al actualizar devolución", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void eliminarDevolucion(Devolucion devolucion) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            devolucion = em.merge(devolucion);
            em.remove(devolucion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al eliminar devolución", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Devolucion obtenerDevolucionPorId(Long id) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(Devolucion.class, id);
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener devolución por ID", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Devolucion> obtenerDevoluciones() throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT d FROM Devolucion d";
            TypedQuery<Devolucion> query = em.createQuery(jpql, Devolucion.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener devoluciones", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Devolucion> obtenerDevolucionesPorRangoFechas(LocalDate inicio, LocalDate fin) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT d FROM Devolucion d WHERE d.fecha BETWEEN :inicio AND :fin";
            TypedQuery<Devolucion> query = em.createQuery(jpql, Devolucion.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            return query.getResultList();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener devoluciones por rango de fechas", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}

