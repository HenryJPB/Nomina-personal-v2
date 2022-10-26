/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.geopolitica;

import bean.controlador.geopolitica.exceptions.NonexistentEntityException;
import bean.controlador.geopolitica.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomPaisDat;
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
public class NomPaisDatJpaController implements Serializable {
    
    /*
    public NomPaisDatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    */
    
    public NomPaisDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomPaisDat nomPaisDat) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomPaisDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomPaisDat(nomPaisDat.getCodPais()) != null) {
                throw new PreexistingEntityException("NomPaisDat " + nomPaisDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomPaisDat nomPaisDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomPaisDat = em.merge(nomPaisDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nomPaisDat.getCodPais();
                if (findNomPaisDat(id) == null) {
                    throw new NonexistentEntityException("The nomPaisDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomPaisDat nomPaisDat;
            try {
                nomPaisDat = em.getReference(NomPaisDat.class, id);
                nomPaisDat.getCodPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomPaisDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomPaisDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomPaisDat> findNomPaisDatEntities() {
        return findNomPaisDatEntities(true, -1, -1);
    }

    public List<NomPaisDat> findNomPaisDatEntities(int maxResults, int firstResult) {
        return findNomPaisDatEntities(false, maxResults, firstResult);
    }

    private List<NomPaisDat> findNomPaisDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomPaisDat.class));
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

    public NomPaisDat findNomPaisDat(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomPaisDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomPaisDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomPaisDat> rt = cq.from(NomPaisDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
