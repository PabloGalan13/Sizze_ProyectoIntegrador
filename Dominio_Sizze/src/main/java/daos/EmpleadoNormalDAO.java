package daos;

import entidades.EmpleadoNormal;
import excepciones.ExcepcionAT;
import interfacesDAO.IEmpleadoNormalDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;


public class EmpleadoNormalDAO implements IEmpleadoNormalDAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    public EmpleadoNormalDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }

    @Override
    public void registrarEmpleadoNormal(EmpleadoNormal empleadoNormal) throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(empleadoNormal);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al registrar empleado normal: " + e.getMessage());
            throw new ExcepcionAT("Error al registrar empleado normal", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void actualizarEmpleadoNormal(EmpleadoNormal empleadoNormal) throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(empleadoNormal);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al actualizar empleado normal: " + e.getMessage());
            throw new ExcepcionAT("Error al actualizar empleado normal", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void eliminarEmpleadoNormal(EmpleadoNormal empleadoNormal) throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.remove(em.contains(empleadoNormal) ? empleadoNormal : em.merge(empleadoNormal));

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al eliminar empleado normal: " + e.getMessage());
            throw new ExcepcionAT("Error al eliminar empleado normal", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public EmpleadoNormal obtenerEmpleadoNormal(Long id) throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            EmpleadoNormal empleadoNormal = em.find(EmpleadoNormal.class, id);

            em.getTransaction().commit();
            return empleadoNormal;
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al obtener empleado normal: " + e.getMessage());
            throw new ExcepcionAT("Error al obtener empleado normal", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<EmpleadoNormal> obtenerEmpleadosNormales() throws ExcepcionAT {
        List<EmpleadoNormal> empleadosNormales = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            String jpql = "SELECT e FROM EmpleadoNormal e";
            TypedQuery<EmpleadoNormal> query = em.createQuery(jpql, EmpleadoNormal.class);
            empleadosNormales = query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al obtener empleados normales: " + e.getMessage());
            throw new ExcepcionAT("Error al obtener empleados normales", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return empleadosNormales;
    }
}
