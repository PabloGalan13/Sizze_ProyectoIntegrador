package daos;

import entidades.Producto;
import excepciones.ExcepcionAT;
import interfacesDAO.IProductoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoDAO implements IProductoDAO {

    private final EntityManagerFactory emf;

    public ProductoDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
        System.out.println(">> JDBC URL usada: " + emf.getProperties().get("jakarta.persistence.jdbc.url"));
        System.out.println(">> JDBC Driver: " + emf.getProperties().get("jakarta.persistence.jdbc.driver"));
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
                    + "JOIN p.categoria c "
                    + "WHERE LOWER(p.nombre) LIKE :search "
                    + "OR LOWER(p.modeloTalla) LIKE :search "
                    + "OR LOWER(p.descripcion) LIKE :search "
                    + "OR LOWER(c.nombre) LIKE :search";
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

    public List<Producto> buscarProductosConFiltros(String nombre, String precioFiltro, String stockFiltro, String ordenFiltro) throws ExcepcionAT {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");
            Map<String, Object> params = new HashMap<>();

            if (nombre != null && !nombre.isBlank()) {
                jpql.append(" AND (LOWER(p.nombre) LIKE :nombre OR LOWER(p.modeloTalla) LIKE :nombre OR LOWER(p.descripcion) LIKE :nombre)");
                params.put("nombre", "%" + nombre.toLowerCase() + "%");
            }

            if (precioFiltro != null && !precioFiltro.isBlank()) {
                if (precioFiltro.endsWith("+")) {
                    int min = Integer.parseInt(precioFiltro.replace("+", ""));
                    jpql.append(" AND p.precio >= :minPrecio");
                    params.put("minPrecio", min);
                } else {
                    String[] parts = precioFiltro.split("-");
                    jpql.append(" AND p.precio BETWEEN :minPrecio AND :maxPrecio");
                    params.put("minPrecio", Integer.parseInt(parts[0]));
                    params.put("maxPrecio", Integer.parseInt(parts[1]));
                }
            }

            if (stockFiltro != null && !stockFiltro.isBlank()) {
                if (stockFiltro.endsWith("+")) {
                    int min = Integer.parseInt(stockFiltro.replace("+", ""));
                    jpql.append(" AND p.stock >= :minStock");
                    params.put("minStock", min);
                } else {
                    String[] parts = stockFiltro.split("-");
                    jpql.append(" AND p.stock BETWEEN :minStock AND :maxStock");
                    params.put("minStock", Integer.parseInt(parts[0]));
                    params.put("maxStock", Integer.parseInt(parts[1]));
                }
            }

            if (ordenFiltro != null && !ordenFiltro.isBlank()) {
                switch (ordenFiltro) {
                    case "precio_asc":
                        jpql.append(" ORDER BY p.precio ASC");
                        break;
                    case "precio_desc":
                        jpql.append(" ORDER BY p.precio DESC");
                        break;
                    case "stock_asc":
                        jpql.append(" ORDER BY p.stock ASC");
                        break;
                    case "stock_desc":
                        jpql.append(" ORDER BY p.stock DESC");
                        break;
                }
            }

            TypedQuery<Producto> query = em.createQuery(jpql.toString(), Producto.class);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            return query.getResultList();

        } catch (Exception e) {
            throw new ExcepcionAT("Error al buscar productos con filtros", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
