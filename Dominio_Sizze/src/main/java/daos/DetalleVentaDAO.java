package daos;

import entidades.DetalleVenta;
import excepciones.ExcepcionAT;
import interfacesDAO.IDetalleVentaDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<Object[]> obtenerProductosMasVendidos(LocalDate inicio, LocalDate fin) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT d.producto.nombre, SUM(d.cantidad) as totalVendidos "
                    + "FROM DetalleVenta d "
                    + "JOIN d.venta v "
                    + "WHERE v.fecha BETWEEN :inicio AND :fin "
                    + "GROUP BY d.producto.nombre "
                    + "ORDER BY totalVendidos DESC";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            return query.getResultList();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener productos más vendidos", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
