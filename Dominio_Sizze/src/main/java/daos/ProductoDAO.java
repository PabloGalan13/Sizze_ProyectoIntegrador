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
    public List<Producto> obtenerProductos() throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT p FROM Producto p";
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // te puede mostrar la causa real en la consola
            throw new ExcepcionAT("Error al obtener productos desde base de datos", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca productos por nombre, modeloTalla o descripción utilizando JPA
     *
     * @param nombreProducto Nombre o parte del texto a buscar
     * @return Lista de productos que coinciden con el criterio de búsqueda
     * @throws ExcepcionAT Si ocurre algún error en la búsqueda
     */
    public List<Producto> buscarProducto(String nombreProducto) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT p FROM Producto p "
                    + "WHERE LOWER(p.nombre) LIKE :search "
                    + "OR LOWER(p.modeloTalla) LIKE :search "
                    + "OR LOWER(p.descripcion) LIKE :search";
            return em.createQuery(jpql, Producto.class)
                    .setParameter("search", "%" + nombreProducto.toLowerCase() + "%")
                    .getResultList();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al buscar productos", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Producto obtenerProductoPorNombre(String nombre) throws ExcepcionAT {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
