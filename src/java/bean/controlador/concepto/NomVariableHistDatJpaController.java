/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomVariableDat;
import bean.modelo.entidad.NomVariableHistDat;
import bean.modelo.entidad.NomVariableHistDatPK;
import bean.modelo.entidad.VariableHist;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.ArrayList;
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
public class NomVariableHistDatJpaController implements Serializable {

    /*
     public NomVariableHistDatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    public NomVariableHistDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomVariableHistDat nomVariableHistDat) throws PreexistingEntityException, Exception {
        if (nomVariableHistDat.getNomVariableHistDatPK() == null) {
            nomVariableHistDat.setNomVariableHistDatPK(new NomVariableHistDatPK());
        }
        nomVariableHistDat.getNomVariableHistDatPK().setCodVar(nomVariableHistDat.getNomVariableDat().getNomVariableDatPK().getCodVar());
        nomVariableHistDat.getNomVariableHistDatPK().setRifEmpresa(nomVariableHistDat.getNomVariableDat().getNomVariableDatPK().getRifEmpresa());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomVariableDat nomVariableDat = nomVariableHistDat.getNomVariableDat();
            if (nomVariableDat != null) {
                nomVariableDat = em.getReference(nomVariableDat.getClass(), nomVariableDat.getNomVariableDatPK());
                nomVariableHistDat.setNomVariableDat(nomVariableDat);
            }
            em.persist(nomVariableHistDat);
            if (nomVariableDat != null) {
                nomVariableDat.getNomVariableHistDatCollection().add(nomVariableHistDat);
                nomVariableDat = em.merge(nomVariableDat);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomVariableHistDat(nomVariableHistDat.getNomVariableHistDatPK()) != null) {
                throw new PreexistingEntityException("NomVariableHistDat " + nomVariableHistDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomVariableHistDat nomVariableHistDat) throws NonexistentEntityException, Exception {
        nomVariableHistDat.getNomVariableHistDatPK().setCodVar(nomVariableHistDat.getNomVariableDat().getNomVariableDatPK().getCodVar());
        nomVariableHistDat.getNomVariableHistDatPK().setRifEmpresa(nomVariableHistDat.getNomVariableDat().getNomVariableDatPK().getRifEmpresa());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomVariableHistDat persistentNomVariableHistDat = em.find(NomVariableHistDat.class, nomVariableHistDat.getNomVariableHistDatPK());
            NomVariableDat nomVariableDatOld = persistentNomVariableHistDat.getNomVariableDat();
            NomVariableDat nomVariableDatNew = nomVariableHistDat.getNomVariableDat();
            if (nomVariableDatNew != null) {
                nomVariableDatNew = em.getReference(nomVariableDatNew.getClass(), nomVariableDatNew.getNomVariableDatPK());
                nomVariableHistDat.setNomVariableDat(nomVariableDatNew);
            }
            nomVariableHistDat = em.merge(nomVariableHistDat);
            if (nomVariableDatOld != null && !nomVariableDatOld.equals(nomVariableDatNew)) {
                nomVariableDatOld.getNomVariableHistDatCollection().remove(nomVariableHistDat);
                nomVariableDatOld = em.merge(nomVariableDatOld);
            }
            if (nomVariableDatNew != null && !nomVariableDatNew.equals(nomVariableDatOld)) {
                nomVariableDatNew.getNomVariableHistDatCollection().add(nomVariableHistDat);
                nomVariableDatNew = em.merge(nomVariableDatNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomVariableHistDatPK id = nomVariableHistDat.getNomVariableHistDatPK();
                if (findNomVariableHistDat(id) == null) {
                    throw new NonexistentEntityException("The nomVariableHistDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomVariableHistDatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomVariableHistDat nomVariableHistDat;
            try {
                nomVariableHistDat = em.getReference(NomVariableHistDat.class, id);
                nomVariableHistDat.getNomVariableHistDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomVariableHistDat with id " + id + " no longer exists.", enfe);
            }
            NomVariableDat nomVariableDat = nomVariableHistDat.getNomVariableDat();
            if (nomVariableDat != null) {
                nomVariableDat.getNomVariableHistDatCollection().remove(nomVariableHistDat);
                nomVariableDat = em.merge(nomVariableDat);
            }
            em.remove(nomVariableHistDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomVariableHistDat> findNomVariableHistDatEntities() {
        return findNomVariableHistDatEntities(true, -1, -1);
    }

    public List<NomVariableHistDat> findNomVariableHistDatEntities(int maxResults, int firstResult) {
        return findNomVariableHistDatEntities(false, maxResults, firstResult);
    }

    private List<NomVariableHistDat> findNomVariableHistDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomVariableHistDat.class));
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

    public NomVariableHistDat findNomVariableHistDat(NomVariableHistDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomVariableHistDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomVariableHistDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomVariableHistDat> rt = cq.from(NomVariableHistDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    // Bqto: 29 Enerro 2019 14:02 
    // doc: utilizando JPA NamedQuery() - Arquitectura Java. 
    //      www.arquitecturajava.com ...
    //--------------------------------------------------------------------------
    public List<VariableHist> getListaVariableHist(String rifEmpresa, String codVar) {
        EntityManager em = null;
        List<VariableHist> lista = new ArrayList<VariableHist>();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY:   ----------------------------------------------
            String txtQuery = "SELECT fechaVigencia, valor "
                    + "        FROM   NomVariableHist_Dat "
                    + "        WHERE  rifEmpresa = ? and codVar= ? "
                    + "        ORDER BY fechaVigencia desc ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codVar);
            java.util.Date fechaVigencia;
            Double valor = 0.00;
            List<Object[]> listaObj = query.getResultList();
            for (Object[] obj : listaObj) {
                fechaVigencia = (java.util.Date) obj[0];
                valor = (Double) obj[1];
                lista.add(new VariableHist(fechaVigencia, valor));
            }  // for
            //*  ---------------------------------------------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomVariableHistDatJpaController ) : " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally
        return (lista);
    }  // getListaVariables(). 

}
