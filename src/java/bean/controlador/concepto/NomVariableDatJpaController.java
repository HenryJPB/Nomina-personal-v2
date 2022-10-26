/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.IllegalOrphanException;
import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomVariableDat;
import bean.modelo.entidad.NomVariableDatPK;
import bean.modelo.entidad.NomVariableHistDat;
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
public class NomVariableDatJpaController implements Serializable {

    /*
    public NomVariableDatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    */  
    
    public NomVariableDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();  
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomVariableDat nomVariableDat) throws PreexistingEntityException, Exception {
        if (nomVariableDat.getNomVariableDatPK() == null) {
            nomVariableDat.setNomVariableDatPK(new NomVariableDatPK());
        }
        if (nomVariableDat.getNomVariableHistDatCollection() == null) {
            nomVariableDat.setNomVariableHistDatCollection(new ArrayList<NomVariableHistDat>());
        }
        nomVariableDat.getNomVariableDatPK().setRifEmpresa(nomVariableDat.getNomEmpresaDat().getRif());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<NomVariableHistDat> attachedNomVariableHistDatCollection = new ArrayList<NomVariableHistDat>();
            for (NomVariableHistDat nomVariableHistDatCollectionNomVariableHistDatToAttach : nomVariableDat.getNomVariableHistDatCollection()) {
                nomVariableHistDatCollectionNomVariableHistDatToAttach = em.getReference(nomVariableHistDatCollectionNomVariableHistDatToAttach.getClass(), nomVariableHistDatCollectionNomVariableHistDatToAttach.getNomVariableHistDatPK());
                attachedNomVariableHistDatCollection.add(nomVariableHistDatCollectionNomVariableHistDatToAttach);
            }
            nomVariableDat.setNomVariableHistDatCollection(attachedNomVariableHistDatCollection);
            em.persist(nomVariableDat);
            for (NomVariableHistDat nomVariableHistDatCollectionNomVariableHistDat : nomVariableDat.getNomVariableHistDatCollection()) {
                NomVariableDat oldNomVariableDatOfNomVariableHistDatCollectionNomVariableHistDat = nomVariableHistDatCollectionNomVariableHistDat.getNomVariableDat();
                nomVariableHistDatCollectionNomVariableHistDat.setNomVariableDat(nomVariableDat);
                nomVariableHistDatCollectionNomVariableHistDat = em.merge(nomVariableHistDatCollectionNomVariableHistDat);
                if (oldNomVariableDatOfNomVariableHistDatCollectionNomVariableHistDat != null) {
                    oldNomVariableDatOfNomVariableHistDatCollectionNomVariableHistDat.getNomVariableHistDatCollection().remove(nomVariableHistDatCollectionNomVariableHistDat);
                    oldNomVariableDatOfNomVariableHistDatCollectionNomVariableHistDat = em.merge(oldNomVariableDatOfNomVariableHistDatCollectionNomVariableHistDat);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomVariableDat(nomVariableDat.getNomVariableDatPK()) != null) {
                throw new PreexistingEntityException("NomVariableDat " + nomVariableDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomVariableDat nomVariableDat) throws IllegalOrphanException, NonexistentEntityException, Exception {
        nomVariableDat.getNomVariableDatPK().setRifEmpresa(nomVariableDat.getNomEmpresaDat().getRif());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomVariableDat persistentNomVariableDat = em.find(NomVariableDat.class, nomVariableDat.getNomVariableDatPK());
            Collection<NomVariableHistDat> nomVariableHistDatCollectionOld = persistentNomVariableDat.getNomVariableHistDatCollection();
            Collection<NomVariableHistDat> nomVariableHistDatCollectionNew = nomVariableDat.getNomVariableHistDatCollection();
            List<String> illegalOrphanMessages = null;
            for (NomVariableHistDat nomVariableHistDatCollectionOldNomVariableHistDat : nomVariableHistDatCollectionOld) {
                if (!nomVariableHistDatCollectionNew.contains(nomVariableHistDatCollectionOldNomVariableHistDat)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain NomVariableHistDat " + nomVariableHistDatCollectionOldNomVariableHistDat + " since its nomVariableDat field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<NomVariableHistDat> attachedNomVariableHistDatCollectionNew = new ArrayList<NomVariableHistDat>();
            for (NomVariableHistDat nomVariableHistDatCollectionNewNomVariableHistDatToAttach : nomVariableHistDatCollectionNew) {
                nomVariableHistDatCollectionNewNomVariableHistDatToAttach = em.getReference(nomVariableHistDatCollectionNewNomVariableHistDatToAttach.getClass(), nomVariableHistDatCollectionNewNomVariableHistDatToAttach.getNomVariableHistDatPK());
                attachedNomVariableHistDatCollectionNew.add(nomVariableHistDatCollectionNewNomVariableHistDatToAttach);
            }
            nomVariableHistDatCollectionNew = attachedNomVariableHistDatCollectionNew;
            nomVariableDat.setNomVariableHistDatCollection(nomVariableHistDatCollectionNew);
            nomVariableDat = em.merge(nomVariableDat);
            for (NomVariableHistDat nomVariableHistDatCollectionNewNomVariableHistDat : nomVariableHistDatCollectionNew) {
                if (!nomVariableHistDatCollectionOld.contains(nomVariableHistDatCollectionNewNomVariableHistDat)) {
                    NomVariableDat oldNomVariableDatOfNomVariableHistDatCollectionNewNomVariableHistDat = nomVariableHistDatCollectionNewNomVariableHistDat.getNomVariableDat();
                    nomVariableHistDatCollectionNewNomVariableHistDat.setNomVariableDat(nomVariableDat);
                    nomVariableHistDatCollectionNewNomVariableHistDat = em.merge(nomVariableHistDatCollectionNewNomVariableHistDat);
                    if (oldNomVariableDatOfNomVariableHistDatCollectionNewNomVariableHistDat != null && !oldNomVariableDatOfNomVariableHistDatCollectionNewNomVariableHistDat.equals(nomVariableDat)) {
                        oldNomVariableDatOfNomVariableHistDatCollectionNewNomVariableHistDat.getNomVariableHistDatCollection().remove(nomVariableHistDatCollectionNewNomVariableHistDat);
                        oldNomVariableDatOfNomVariableHistDatCollectionNewNomVariableHistDat = em.merge(oldNomVariableDatOfNomVariableHistDatCollectionNewNomVariableHistDat);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomVariableDatPK id = nomVariableDat.getNomVariableDatPK();
                if (findNomVariableDat(id) == null) {
                    throw new NonexistentEntityException("The nomVariableDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomVariableDatPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomVariableDat nomVariableDat;
            try {
                nomVariableDat = em.getReference(NomVariableDat.class, id);
                nomVariableDat.getNomVariableDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomVariableDat with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<NomVariableHistDat> nomVariableHistDatCollectionOrphanCheck = nomVariableDat.getNomVariableHistDatCollection();
            for (NomVariableHistDat nomVariableHistDatCollectionOrphanCheckNomVariableHistDat : nomVariableHistDatCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This NomVariableDat (" + nomVariableDat + ") cannot be destroyed since the NomVariableHistDat " + nomVariableHistDatCollectionOrphanCheckNomVariableHistDat + " in its nomVariableHistDatCollection field has a non-nullable nomVariableDat field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(nomVariableDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomVariableDat> findNomVariableDatEntities() {
        return findNomVariableDatEntities(true, -1, -1);
    }

    public List<NomVariableDat> findNomVariableDatEntities(int maxResults, int firstResult) {
        return findNomVariableDatEntities(false, maxResults, firstResult);
    }

    private List<NomVariableDat> findNomVariableDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomVariableDat.class));
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

    public NomVariableDat findNomVariableDat(NomVariableDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomVariableDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomVariableDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomVariableDat> rt = cq.from(NomVariableDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    // Bqto: 06 Noviembre 2018 12:24 
    // doc: utilizando JPA NamedQuery() - Arquitectura Java. 
    //      www.arquitecturajava.com ...
    //--------------------------------------------------------------------------
    public List<NomVariableDat> getListaVariables(String rifEmpresa) {
        EntityManager em = getEntityManager(); 
        TypedQuery<NomVariableDat> consultarVariablesEmpresa = em.createNamedQuery("NomVariableDat.findByRifEmpresa", NomVariableDat.class);  
        consultarVariablesEmpresa.setParameter("rifEmpresa", rifEmpresa); 
        List<NomVariableDat> lista = consultarVariablesEmpresa.getResultList();  
        return (lista);
    }  // getListaVariables()
}
