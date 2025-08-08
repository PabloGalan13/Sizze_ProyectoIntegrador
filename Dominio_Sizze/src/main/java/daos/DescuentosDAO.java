/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import entidades.Descuentos;
import entidades.DetalleVenta;
import excepciones.ExcepcionAT;
import interfacesDAO.IDescuentoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author yohan
 */
public class DescuentosDAO implements IDescuentoDAO{
       private final EntityManagerFactory emf;
    
    public DescuentosDAO() {
        emf = Persistence.createEntityManagerFactory("SizzePU");
    }
    
    @Override
    public void registrarDescuento(Descuentos descuento) throws ExcepcionAT {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(descuento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ExcepcionAT("Error al registrar el descuento ", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
     
}
