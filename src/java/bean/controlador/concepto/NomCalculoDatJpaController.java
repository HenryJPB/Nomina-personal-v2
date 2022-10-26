/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomCalculoDat;
import bean.modelo.entidad.NomCalculoDatPK;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class NomCalculoDatJpaController implements Serializable {

    /*
    public NomCalculoDatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    */
    
    public NomCalculoDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomCalculoDat nomCalculoDat) throws PreexistingEntityException, Exception {
        if (nomCalculoDat.getNomCalculoDatPK() == null) {
            nomCalculoDat.setNomCalculoDatPK(new NomCalculoDatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomCalculoDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomCalculoDat(nomCalculoDat.getNomCalculoDatPK()) != null) {
                throw new PreexistingEntityException("NomCalculoDat " + nomCalculoDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomCalculoDat nomCalculoDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomCalculoDat = em.merge(nomCalculoDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomCalculoDatPK id = nomCalculoDat.getNomCalculoDatPK();
                if (findNomCalculoDat(id) == null) {
                    throw new NonexistentEntityException("The nomCalculoDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomCalculoDatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomCalculoDat nomCalculoDat;
            try {
                nomCalculoDat = em.getReference(NomCalculoDat.class, id);
                nomCalculoDat.getNomCalculoDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomCalculoDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomCalculoDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomCalculoDat> findNomCalculoDatEntities() {
        return findNomCalculoDatEntities(true, -1, -1);
    }

    public List<NomCalculoDat> findNomCalculoDatEntities(int maxResults, int firstResult) {
        return findNomCalculoDatEntities(false, maxResults, firstResult);
    }

    private List<NomCalculoDat> findNomCalculoDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomCalculoDat.class));
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

    public NomCalculoDat findNomCalculoDat(NomCalculoDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomCalculoDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomCalculoDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomCalculoDat> rt = cq.from(NomCalculoDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    // Bqto: 21 septiembre 2020 09:11   
    //                     =============
    // doc: utilizando JPA Typed Query() - Arquitectura Java. 
    //                     =============
    //      www.arquitecturajava.com ...
    //  *NOTA IMPORTANTE*: 
    //--------------------------------------------------------------------------
    // USANDO POLIFORMISMO,... BQTO. 12 octubre 2020. 
    //--------------------------------------------------------------------------
    public void eliminarCalculo(String rifEmpresa, java.util.Date fechaCalculo) {
        EntityManager em = null;
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY para MySQL :--------------------------------------
            String txtQuery = "DELETE FROM NomCalculo_Dat "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    fechaNomina  = ?2 ";  
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, fechaCalculo);
            int deletedCount = query.executeUpdate(); 
            //System.out.println("SE HAN ELIMINADO "+deletedCount+"REGISROS");
            em.getTransaction().commit();
        }  catch (Exception ex ) {
            System.err.println("( NomCalculoDatJpaController ) ERROR al ejecutar *Typed Query * :?() " + ex);
            Logger.getLogger(NomCalculoDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally. 
    } // eliminarCalculo().
    
    //--------------------------------------------------------------------------
    // USANDO POLIFORMISMO,... BQTO. 12 octubre 2020. 
    //--------------------------------------------------------------------------
    public void eliminarCalculo(String rifEmpresa, Integer codNomina) {
        EntityManager em = null;
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY para MySQL :--------------------------------------
            String txtQuery = "DELETE FROM NomCalculo_Dat "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    codNomina  = ?2 ";  
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codNomina);
            int deletedCount = query.executeUpdate(); 
            //System.out.println("SE HAN ELIMINADO "+deletedCount+"REGISROS");
            em.getTransaction().commit();
        }  catch (Exception ex ) {
            System.err.println("( NomCalculoDatJpaController ) ERROR al ejecutar *Typed Query * :?() " + ex);
            Logger.getLogger(NomCalculoDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally. 
    } // eliminarCalculo().
}
