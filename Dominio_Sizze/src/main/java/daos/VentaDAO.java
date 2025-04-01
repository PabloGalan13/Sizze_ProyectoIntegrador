package daos;

import entidades.Venta;
import excepciones.ExcepcionAT;
import interfacesDAO.IVentaDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class VentaDAO implements IVentaDAO {
    
    private final EntityManagerFactory emf;
    
    public VentaDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }
    
    @Override
    public void registrarVenta(Venta venta) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(venta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al registrar venta", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public void actualizarVenta(Venta venta) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(venta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al actualizar venta", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public void eliminarVenta(Venta venta) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            venta = em.merge(venta);
            em.remove(venta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al eliminar venta", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public Venta obtenerVentaPorId(Long id) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(Venta.class, id);
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener venta por ID", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public List<Venta> obtenerVentas() throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT v FROM Venta v";
            TypedQuery<Venta> query = em.createQuery(jpql, Venta.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener ventas", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
