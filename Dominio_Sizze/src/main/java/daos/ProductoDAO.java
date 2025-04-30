package daos;

import entidades.Producto;
import excepciones.ExcepcionAT;
import interfacesDAO.IProductoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProductoDAO implements IProductoDAO {

    private final EntityManagerFactory emf;

    public ProductoDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }

    @Override
    public boolean registrarProducto(Producto producto) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al registrar producto", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void actualizarProducto(Producto producto) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al actualizar producto", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void eliminarProducto(Producto producto) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            producto = em.merge(producto);
            em.remove(producto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al eliminar producto", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Producto obtenerProductoPorNombre(String nombre) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT p FROM Producto p WHERE p.nombre = :nombre";
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            query.setParameter("nombre", nombre);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener producto por nombre", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Producto obtenerProductoPorId(long id) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(Producto.class, id);
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener producto por ID", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Producto> obtenerProductos() throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT p FROM Producto p";
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener productos", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
