package daos;

import entidades.Categoria;
import excepciones.ExcepcionAT;
import interfacesDAO.ICategoriaDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CategoriaDAO implements ICategoriaDAO {
    
    private final EntityManagerFactory emf;
    
    public CategoriaDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }
    
    @Override
    public void registrarCategoria(Categoria categoria) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al registrar categoría", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public void actualizarCategoria(Categoria categoria) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al actualizar categoría", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public void eliminarCategoria(Categoria categoria) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            categoria = em.merge(categoria);
            em.remove(categoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al eliminar categoría", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public Categoria obtenerCategoriaPorId(Long id) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(Categoria.class, id);
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener categoría por ID", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public List<Categoria> obtenerCategorias() throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String jpql = "SELECT c FROM Categoria c";
            TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new ExcepcionAT("Error al obtener categorías", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
