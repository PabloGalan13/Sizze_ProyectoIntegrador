/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import entidades.DetalleDevolucion;
import excepciones.ExcepcionAT;
import interfacesDAO.IDetalleDevolucionDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class DetalleDevolucionDAO implements IDetalleDevolucionDAO {

    private final EntityManagerFactory emf;

    public DetalleDevolucionDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }

    @Override
    public void registrarDetalleDevolucion(DetalleDevolucion detalleDevolucion) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(detalleDevolucion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al registrar detalle de devolución", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void actualizarDetalleDevolucion(DetalleDevolucion detalleDevolucion) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(detalleDevolucion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al actualizar detalle de devolución", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void eliminarDetalleDevolucion(DetalleDevolucion detalleDevolucion) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            detalleDevolucion = em.merge(detalleDevolucion);
            em.remove(detalleDevolucion);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al eliminar detalle de devolución", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public DetalleDevolucion obtenerDetalleDevolucionPorId(Long id) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(DetalleDevolucion.class, id);
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener detalle de devolución por ID", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<DetalleDevolucion> obtenerDetallesDevolucion() throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT dd FROM DetalleDevolucion dd";
            TypedQuery<DetalleDevolucion> query = em.createQuery(jpql, DetalleDevolucion.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener detalles de devolución", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
