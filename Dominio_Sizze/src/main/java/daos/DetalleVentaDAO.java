package daos;

import entidades.DetalleVenta;
import excepciones.ExcepcionAT;
import interfacesDAO.IDetalleVentaDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DetalleVentaDAO implements IDetalleVentaDAO {
    
    private final EntityManagerFactory emf;
    
    public DetalleVentaDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }
    
    @Override
    public void registrarDetalleVenta(DetalleVenta detalleVenta) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(detalleVenta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al registrar detalle de venta", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public void actualizarDetalleVenta(DetalleVenta detalleVenta) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(detalleVenta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al actualizar detalle de venta", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public void eliminarDetalleVenta(DetalleVenta detalleVenta) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            detalleVenta = em.merge(detalleVenta);
            em.remove(detalleVenta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al eliminar detalle de venta", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public DetalleVenta obtenerDetalleVentaPorId(Long id) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(DetalleVenta.class, id);
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener detalle de venta por ID", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public List<DetalleVenta> obtenerDetallesVenta() throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT d FROM DetalleVenta d";
            TypedQuery<DetalleVenta> query = em.createQuery(jpql, DetalleVenta.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener detalles de venta", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
