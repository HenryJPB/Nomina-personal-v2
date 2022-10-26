/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.controlador.empresa;

import bean.controlador.empresa.exceptions.NonexistentEntityException;
import bean.controlador.empresa.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomEmpresaDat;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author hpulgar
 */
public class NomEmpresaDatJpaController implements Serializable {

    /*
    public NomEmpresaDatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    */
    
    public NomEmpresaDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomEmpresaDat nomEmpresaDat) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomEmpresaDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomEmpresaDat(nomEmpresaDat.getRif()) != null) {
                throw new PreexistingEntityException("NomEmpresaDat " + nomEmpresaDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomEmpresaDat nomEmpresaDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomEmpresaDat = em.merge(nomEmpresaDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = nomEmpresaDat.getRif();
                if (findNomEmpresaDat(id) == null) {
                    throw new NonexistentEntityException("The nomEmpresaDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomEmpresaDat nomEmpresaDat;
            try {
                nomEmpresaDat = em.getReference(NomEmpresaDat.class, id);
                nomEmpresaDat.getRif();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomEmpresaDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomEmpresaDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomEmpresaDat> findNomEmpresaDatEntities() {
        return findNomEmpresaDatEntities(true, -1, -1);
    }

    public List<NomEmpresaDat> findNomEmpresaDatEntities(int maxResults, int firstResult) {
        return findNomEmpresaDatEntities(false, maxResults, firstResult);
    }

    private List<NomEmpresaDat> findNomEmpresaDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomEmpresaDat.class));
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

    public NomEmpresaDat findNomEmpresaDat(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomEmpresaDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomEmpresaDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomEmpresaDat> rt = cq.from(NomEmpresaDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
