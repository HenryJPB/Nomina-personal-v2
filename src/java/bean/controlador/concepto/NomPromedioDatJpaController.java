/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.IllegalOrphanException;
import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomPromedioDat;
import bean.modelo.entidad.NomPromedioDatPK;
import bean.modelo.entidad.NomPromedioHistDat;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author hpulgar
 */
public class NomPromedioDatJpaController implements Serializable {

    /*
    public NomPromedioDatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    */
    
    public NomPromedioDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomPromedioDat nomPromedioDat) throws PreexistingEntityException, Exception {
        if (nomPromedioDat.getNomPromedioDatPK() == null) {
            nomPromedioDat.setNomPromedioDatPK(new NomPromedioDatPK());
        }
        if (nomPromedioDat.getNomPromedioHistDatCollection() == null) {
            nomPromedioDat.setNomPromedioHistDatCollection(new ArrayList<NomPromedioHistDat>());
        }
        nomPromedioDat.getNomPromedioDatPK().setRifEmpresa(nomPromedioDat.getNomEmpresaDat().getRif());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<NomPromedioHistDat> attachedNomPromedioHistDatCollection = new ArrayList<NomPromedioHistDat>();
            for (NomPromedioHistDat nomPromedioHistDatCollectionNomPromedioHistDatToAttach : nomPromedioDat.getNomPromedioHistDatCollection()) {
                nomPromedioHistDatCollectionNomPromedioHistDatToAttach = em.getReference(nomPromedioHistDatCollectionNomPromedioHistDatToAttach.getClass(), nomPromedioHistDatCollectionNomPromedioHistDatToAttach.getNomPromedioHistDatPK());
                attachedNomPromedioHistDatCollection.add(nomPromedioHistDatCollectionNomPromedioHistDatToAttach);
            }
            nomPromedioDat.setNomPromedioHistDatCollection(attachedNomPromedioHistDatCollection);
            em.persist(nomPromedioDat);
            for (NomPromedioHistDat nomPromedioHistDatCollectionNomPromedioHistDat : nomPromedioDat.getNomPromedioHistDatCollection()) {
                NomPromedioDat oldNomPromedioDatOfNomPromedioHistDatCollectionNomPromedioHistDat = nomPromedioHistDatCollectionNomPromedioHistDat.getNomPromedioDat();
                nomPromedioHistDatCollectionNomPromedioHistDat.setNomPromedioDat(nomPromedioDat);
                nomPromedioHistDatCollectionNomPromedioHistDat = em.merge(nomPromedioHistDatCollectionNomPromedioHistDat);
                if (oldNomPromedioDatOfNomPromedioHistDatCollectionNomPromedioHistDat != null) {
                    oldNomPromedioDatOfNomPromedioHistDatCollectionNomPromedioHistDat.getNomPromedioHistDatCollection().remove(nomPromedioHistDatCollectionNomPromedioHistDat);
                    oldNomPromedioDatOfNomPromedioHistDatCollectionNomPromedioHistDat = em.merge(oldNomPromedioDatOfNomPromedioHistDatCollectionNomPromedioHistDat);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomPromedioDat(nomPromedioDat.getNomPromedioDatPK()) != null) {
                throw new PreexistingEntityException("NomPromedioDat " + nomPromedioDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomPromedioDat nomPromedioDat) throws IllegalOrphanException, NonexistentEntityException, Exception {
        nomPromedioDat.getNomPromedioDatPK().setRifEmpresa(nomPromedioDat.getNomEmpresaDat().getRif());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomPromedioDat persistentNomPromedioDat = em.find(NomPromedioDat.class, nomPromedioDat.getNomPromedioDatPK());
            Collection<NomPromedioHistDat> nomPromedioHistDatCollectionOld = persistentNomPromedioDat.getNomPromedioHistDatCollection();
            Collection<NomPromedioHistDat> nomPromedioHistDatCollectionNew = nomPromedioDat.getNomPromedioHistDatCollection();
            List<String> illegalOrphanMessages = null;
            for (NomPromedioHistDat nomPromedioHistDatCollectionOldNomPromedioHistDat : nomPromedioHistDatCollectionOld) {
                if (!nomPromedioHistDatCollectionNew.contains(nomPromedioHistDatCollectionOldNomPromedioHistDat)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NomPromedioHistDat " + nomPromedioHistDatCollectionOldNomPromedioHistDat + " since its nomPromedioDat field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<NomPromedioHistDat> attachedNomPromedioHistDatCollectionNew = new ArrayList<NomPromedioHistDat>();
            for (NomPromedioHistDat nomPromedioHistDatCollectionNewNomPromedioHistDatToAttach : nomPromedioHistDatCollectionNew) {
                nomPromedioHistDatCollectionNewNomPromedioHistDatToAttach = em.getReference(nomPromedioHistDatCollectionNewNomPromedioHistDatToAttach.getClass(), nomPromedioHistDatCollectionNewNomPromedioHistDatToAttach.getNomPromedioHistDatPK());
                attachedNomPromedioHistDatCollectionNew.add(nomPromedioHistDatCollectionNewNomPromedioHistDatToAttach);
            }
            nomPromedioHistDatCollectionNew = attachedNomPromedioHistDatCollectionNew;
            nomPromedioDat.setNomPromedioHistDatCollection(nomPromedioHistDatCollectionNew);
            nomPromedioDat = em.merge(nomPromedioDat);
            for (NomPromedioHistDat nomPromedioHistDatCollectionNewNomPromedioHistDat : nomPromedioHistDatCollectionNew) {
                if (!nomPromedioHistDatCollectionOld.contains(nomPromedioHistDatCollectionNewNomPromedioHistDat)) {
                    NomPromedioDat oldNomPromedioDatOfNomPromedioHistDatCollectionNewNomPromedioHistDat = nomPromedioHistDatCollectionNewNomPromedioHistDat.getNomPromedioDat();
                    nomPromedioHistDatCollectionNewNomPromedioHistDat.setNomPromedioDat(nomPromedioDat);
                    nomPromedioHistDatCollectionNewNomPromedioHistDat = em.merge(nomPromedioHistDatCollectionNewNomPromedioHistDat);
                    if (oldNomPromedioDatOfNomPromedioHistDatCollectionNewNomPromedioHistDat != null && !oldNomPromedioDatOfNomPromedioHistDatCollectionNewNomPromedioHistDat.equals(nomPromedioDat)) {
                        oldNomPromedioDatOfNomPromedioHistDatCollectionNewNomPromedioHistDat.getNomPromedioHistDatCollection().remove(nomPromedioHistDatCollectionNewNomPromedioHistDat);
                        oldNomPromedioDatOfNomPromedioHistDatCollectionNewNomPromedioHistDat = em.merge(oldNomPromedioDatOfNomPromedioHistDatCollectionNewNomPromedioHistDat);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomPromedioDatPK id = nomPromedioDat.getNomPromedioDatPK();
                if (findNomPromedioDat(id) == null) {
                    throw new NonexistentEntityException("The nomPromedioDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomPromedioDatPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomPromedioDat nomPromedioDat;
            try {
                nomPromedioDat = em.getReference(NomPromedioDat.class, id);
                nomPromedioDat.getNomPromedioDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomPromedioDat with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<NomPromedioHistDat> nomPromedioHistDatCollectionOrphanCheck = nomPromedioDat.getNomPromedioHistDatCollection();
            for (NomPromedioHistDat nomPromedioHistDatCollectionOrphanCheckNomPromedioHistDat : nomPromedioHistDatCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This NomPromedioDat (" + nomPromedioDat + ") cannot be destroyed since the NomPromedioHistDat " + nomPromedioHistDatCollectionOrphanCheckNomPromedioHistDat + " in its nomPromedioHistDatCollection field has a non-nullable nomPromedioDat field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(nomPromedioDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomPromedioDat> findNomPromedioDatEntities() {
        return findNomPromedioDatEntities(true, -1, -1);
    }

    public List<NomPromedioDat> findNomPromedioDatEntities(int maxResults, int firstResult) {
        return findNomPromedioDatEntities(false, maxResults, firstResult);
    }

    private List<NomPromedioDat> findNomPromedioDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomPromedioDat.class));
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

    public NomPromedioDat findNomPromedioDat(NomPromedioDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomPromedioDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomPromedioDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomPromedioDat> rt = cq.from(NomPromedioDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    // Bqto: 07 Noviembre 2018 10:44 
    // doc: utilizando JPA NamedQuery() - Arquitectura Java. 
    //      www.arquitecturajava.com ...
    //--------------------------------------------------------------------------
    public List<NomPromedioDat> getListaPromedios(String rifEmpresa) {
        EntityManager em = getEntityManager(); 
        TypedQuery<NomPromedioDat> consultarPromediosEmpresa = em.createNamedQuery("NomPromedioDat.findByRifEmpresa", NomPromedioDat.class );  
        consultarPromediosEmpresa.setParameter("rifEmpresa", rifEmpresa ); 
        List<NomPromedioDat> lista = consultarPromediosEmpresa.getResultList();  
        return (lista);
    } // getListaPromedios().  
    
    
}
