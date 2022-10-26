/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.personal;

import bean.controlador.personal.exceptions.NonexistentEntityException;
import bean.controlador.personal.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomTrabajador00Dat;
import bean.modelo.entidad.NomTrabajador00DatPK;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author henrypb
 */
public class NomTrabajador00DatJpaController_noDelete implements Serializable {

    /*
     public NomTrabajador00DatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    public NomTrabajador00DatJpaController_noDelete() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomTrabajador00Dat nomTrabajador00Dat) throws PreexistingEntityException, Exception {
        if (nomTrabajador00Dat.getNomTrabajador00DatPK() == null) {
            nomTrabajador00Dat.setNomTrabajador00DatPK(new NomTrabajador00DatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomTrabajador00Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomTrabajador00Dat(nomTrabajador00Dat.getNomTrabajador00DatPK()) != null) {
                throw new PreexistingEntityException("NomTrabajador00Dat " + nomTrabajador00Dat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomTrabajador00Dat nomTrabajador00Dat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomTrabajador00Dat = em.merge(nomTrabajador00Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomTrabajador00DatPK id = nomTrabajador00Dat.getNomTrabajador00DatPK();
                if (findNomTrabajador00Dat(id) == null) {
                    throw new NonexistentEntityException("The nomTrabajador00Dat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomTrabajador00DatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomTrabajador00Dat nomTrabajador00Dat;
            try {
                nomTrabajador00Dat = em.getReference(NomTrabajador00Dat.class, id);
                nomTrabajador00Dat.getNomTrabajador00DatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomTrabajador00Dat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomTrabajador00Dat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomTrabajador00Dat> findNomTrabajador00DatEntities() {
        return findNomTrabajador00DatEntities(true, -1, -1);
    }

    public List<NomTrabajador00Dat> findNomTrabajador00DatEntities(int maxResults, int firstResult) {
        return findNomTrabajador00DatEntities(false, maxResults, firstResult);
    }

    private List<NomTrabajador00Dat> findNomTrabajador00DatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomTrabajador00Dat.class));
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

    public NomTrabajador00Dat findNomTrabajador00Dat(NomTrabajador00DatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomTrabajador00Dat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomTrabajador00DatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomTrabajador00Dat> rt = cq.from(NomTrabajador00Dat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    // Bqto: 13 de septiembre 2018 14:01 
    // doc: utilizando JPA NamedQuery() - Arquitectura Java. 
    //      www.arquitecturajava.com ...
    //--------------------------------------------------------------------------
    public List<NomTrabajador00Dat> getlistaTrabxEmpresa(String rifEmpresa) {
        EntityManager em = getEntityManager();
        //em.getTransaction().begin();
        //NomTrabajador00Dat nomTrabajador00Dat;
        //nomTrabajador00Dat = em.getReference(NomTrabajador00Dat.class, id);
        //nomTrabajador00Dat.getNomTrabajador00DatPK();
        TypedQuery<NomTrabajador00Dat> consultarTrabxEmpresa = em.createNamedQuery("NomTrabajador00Dat.findByRifEmpresa",NomTrabajador00Dat.class);  
        consultarTrabxEmpresa.setParameter("rifEmpresa",rifEmpresa);  
        List<NomTrabajador00Dat> lista = consultarTrabxEmpresa.getResultList();  
        return (lista);
    } // getlistaTrabxEmpresa()
    
}
