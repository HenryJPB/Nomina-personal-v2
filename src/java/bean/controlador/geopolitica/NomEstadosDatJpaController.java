/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.geopolitica;

import bean.controlador.geopolitica.exceptions.NonexistentEntityException;
import bean.controlador.geopolitica.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomEstadosDat;
import bean.modelo.entidad.NomEstadosDatPK;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author henrypb
 */
public class NomEstadosDatJpaController implements Serializable {

    /*
    public NomEstadosDatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    */
    
    public NomEstadosDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomEstadosDat nomEstadosDat) throws PreexistingEntityException, Exception {
        if (nomEstadosDat.getNomEstadosDatPK() == null) {
            nomEstadosDat.setNomEstadosDatPK(new NomEstadosDatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomEstadosDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomEstadosDat(nomEstadosDat.getNomEstadosDatPK()) != null) {
                throw new PreexistingEntityException("NomEstadosDat " + nomEstadosDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomEstadosDat nomEstadosDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomEstadosDat = em.merge(nomEstadosDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomEstadosDatPK id = nomEstadosDat.getNomEstadosDatPK();
                if (findNomEstadosDat(id) == null) {
                    throw new NonexistentEntityException("The nomEstadosDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomEstadosDatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomEstadosDat nomEstadosDat;
            try {
                nomEstadosDat = em.getReference(NomEstadosDat.class, id);
                nomEstadosDat.getNomEstadosDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomEstadosDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomEstadosDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomEstadosDat> findNomEstadosDatEntities() {
        return findNomEstadosDatEntities(true, -1, -1);
    }

    public List<NomEstadosDat> findNomEstadosDatEntities(int maxResults, int firstResult) {
        return findNomEstadosDatEntities(false, maxResults, firstResult);
    }

    private List<NomEstadosDat> findNomEstadosDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomEstadosDat.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public NomEstadosDat findNomEstadosDat(NomEstadosDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomEstadosDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomEstadosDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomEstadosDat> rt = cq.from(NomEstadosDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
