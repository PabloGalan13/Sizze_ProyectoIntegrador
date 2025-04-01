package daos;

import entidades.Horario;
import excepciones.ExcepcionAT;
import interfacesDAO.IHorarioDAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class HorarioDAO implements IHorarioDAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    public HorarioDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }

    @Override
    public void registrarHorario(Horario horario) throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(horario);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al registrar horario: " + e.getMessage());
            throw new ExcepcionAT("Error al registrar horario", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void actualizarHorario(Horario horario) throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(horario);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al actualizar horario: " + e.getMessage());
            throw new ExcepcionAT("Error al actualizar horario", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void eliminarHorario(Horario horario) throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.remove(em.contains(horario) ? horario : em.merge(horario));

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al eliminar horario: " + e.getMessage());
            throw new ExcepcionAT("Error al eliminar horario", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Horario obtenerHorario(Long id) throws ExcepcionAT {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Horario horario = em.find(Horario.class, id);

            em.getTransaction().commit();
            return horario;
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al obtener horario: " + e.getMessage());
            throw new ExcepcionAT("Error al obtener horario", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Horario> obtenerHorarios() throws ExcepcionAT {
        List<Horario> horarios = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            String jpql = "SELECT h FROM Horario h";
            TypedQuery<Horario> query = em.createQuery(jpql, Horario.class);
            horarios = query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al obtener horarios: " + e.getMessage());
            throw new ExcepcionAT("Error al obtener horarios", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return horarios;
    }
}
