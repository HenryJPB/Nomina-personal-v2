/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomPromedioDat;
import bean.modelo.entidad.NomPromedioHistDat;
import bean.modelo.entidad.NomPromedioHistDatPK;
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
public class NomPromedioHistDatJpaController implements Serializable {

    /*
    public NomPromedioHistDatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    */
    
    public NomPromedioHistDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();  
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomPromedioHistDat nomPromedioHistDat) throws PreexistingEntityException, Exception {
        if (nomPromedioHistDat.getNomPromedioHistDatPK() == null) {
            nomPromedioHistDat.setNomPromedioHistDatPK(new NomPromedioHistDatPK());
        }
        nomPromedioHistDat.getNomPromedioHistDatPK().setCodProm(nomPromedioHistDat.getNomPromedioDat().getNomPromedioDatPK().getCodProm());
        nomPromedioHistDat.getNomPromedioHistDatPK().setRifEmpresa(nomPromedioHistDat.getNomPromedioDat().getNomPromedioDatPK().getRifEmpresa());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomPromedioDat nomPromedioDat = nomPromedioHistDat.getNomPromedioDat();
            if (nomPromedioDat != null) {
                nomPromedioDat = em.getReference(nomPromedioDat.getClass(), nomPromedioDat.getNomPromedioDatPK());
                nomPromedioHistDat.setNomPromedioDat(nomPromedioDat);
            }
            em.persist(nomPromedioHistDat);
            if (nomPromedioDat != null) {
                nomPromedioDat.getNomPromedioHistDatCollection().add(nomPromedioHistDat);
                nomPromedioDat = em.merge(nomPromedioDat);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomPromedioHistDat(nomPromedioHistDat.getNomPromedioHistDatPK()) != null) {
                throw new PreexistingEntityException("NomPromedioHistDat " + nomPromedioHistDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomPromedioHistDat nomPromedioHistDat) throws NonexistentEntityException, Exception {
        nomPromedioHistDat.getNomPromedioHistDatPK().setCodProm(nomPromedioHistDat.getNomPromedioDat().getNomPromedioDatPK().getCodProm());
        nomPromedioHistDat.getNomPromedioHistDatPK().setRifEmpresa(nomPromedioHistDat.getNomPromedioDat().getNomPromedioDatPK().getRifEmpresa());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomPromedioHistDat persistentNomPromedioHistDat = em.find(NomPromedioHistDat.class, nomPromedioHistDat.getNomPromedioHistDatPK());
            NomPromedioDat nomPromedioDatOld = persistentNomPromedioHistDat.getNomPromedioDat();
            NomPromedioDat nomPromedioDatNew = nomPromedioHistDat.getNomPromedioDat();
            if (nomPromedioDatNew != null) {
                nomPromedioDatNew = em.getReference(nomPromedioDatNew.getClass(), nomPromedioDatNew.getNomPromedioDatPK());
                nomPromedioHistDat.setNomPromedioDat(nomPromedioDatNew);
            }
            nomPromedioHistDat = em.merge(nomPromedioHistDat);
            if (nomPromedioDatOld != null && !nomPromedioDatOld.equals(nomPromedioDatNew)) {
                nomPromedioDatOld.getNomPromedioHistDatCollection().remove(nomPromedioHistDat);
                nomPromedioDatOld = em.merge(nomPromedioDatOld);
            }
            if (nomPromedioDatNew != null && !nomPromedioDatNew.equals(nomPromedioDatOld)) {
                nomPromedioDatNew.getNomPromedioHistDatCollection().add(nomPromedioHistDat);
                nomPromedioDatNew = em.merge(nomPromedioDatNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomPromedioHistDatPK id = nomPromedioHistDat.getNomPromedioHistDatPK();
                if (findNomPromedioHistDat(id) == null) {
                    throw new NonexistentEntityException("The nomPromedioHistDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomPromedioHistDatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomPromedioHistDat nomPromedioHistDat;
            try {
                nomPromedioHistDat = em.getReference(NomPromedioHistDat.class, id);
                nomPromedioHistDat.getNomPromedioHistDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomPromedioHistDat with id " + id + " no longer exists.", enfe);
            }
            NomPromedioDat nomPromedioDat = nomPromedioHistDat.getNomPromedioDat();
            if (nomPromedioDat != null) {
                nomPromedioDat.getNomPromedioHistDatCollection().remove(nomPromedioHistDat);
                nomPromedioDat = em.merge(nomPromedioDat);
            }
            em.remove(nomPromedioHistDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomPromedioHistDat> findNomPromedioHistDatEntities() {
        return findNomPromedioHistDatEntities(true, -1, -1);
    }

    public List<NomPromedioHistDat> findNomPromedioHistDatEntities(int maxResults, int firstResult) {
        return findNomPromedioHistDatEntities(false, maxResults, firstResult);
    }

    private List<NomPromedioHistDat> findNomPromedioHistDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomPromedioHistDat.class));
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

    public NomPromedioHistDat findNomPromedioHistDat(NomPromedioHistDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomPromedioHistDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomPromedioHistDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomPromedioHistDat> rt = cq.from(NomPromedioHistDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
