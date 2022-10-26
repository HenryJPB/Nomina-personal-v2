/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomCalculo1Dat;
import bean.modelo.entidad.NomCalculo1DatPK;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.Date;
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
public class NomCalculo1DatJpaController implements Serializable {

    /*
     public NomCalculo1DatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    public NomCalculo1DatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomCalculo1Dat nomCalculo1Dat) throws PreexistingEntityException, Exception {
        if (nomCalculo1Dat.getNomCalculo1DatPK() == null) {
            nomCalculo1Dat.setNomCalculo1DatPK(new NomCalculo1DatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomCalculo1Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomCalculo1Dat(nomCalculo1Dat.getNomCalculo1DatPK()) != null) {
                throw new PreexistingEntityException("NomCalculo1Dat " + nomCalculo1Dat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomCalculo1Dat nomCalculo1Dat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomCalculo1Dat = em.merge(nomCalculo1Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomCalculo1DatPK id = nomCalculo1Dat.getNomCalculo1DatPK();
                if (findNomCalculo1Dat(id) == null) {
                    throw new NonexistentEntityException("The nomCalculo1Dat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomCalculo1DatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomCalculo1Dat nomCalculo1Dat;
            try {
                nomCalculo1Dat = em.getReference(NomCalculo1Dat.class, id);
                nomCalculo1Dat.getNomCalculo1DatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomCalculo1Dat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomCalculo1Dat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomCalculo1Dat> findNomCalculo1DatEntities() {
        return findNomCalculo1DatEntities(true, -1, -1);
    }

    public List<NomCalculo1Dat> findNomCalculo1DatEntities(int maxResults, int firstResult) {
        return findNomCalculo1DatEntities(false, maxResults, firstResult);
    }

    private List<NomCalculo1Dat> findNomCalculo1DatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomCalculo1Dat.class));
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

    public NomCalculo1Dat findNomCalculo1Dat(NomCalculo1DatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomCalculo1Dat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomCalculo1DatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomCalculo1Dat> rt = cq.from(NomCalculo1Dat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    Double getTotal(String rifEmpresa, Integer codNomina, Integer nroTrab, Integer codConcepto, Date fechaEnFondo) {
        EntityManager em = null;
        Double total = 0.00;
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY para MySQL :--------------------------------------
            String txtQuery = "SELECT total "
                    + "        FROM   NomCalculo1_Dat "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    codNomina = ?2 "
                    + "        AND    nroTrabajador = ?3 "
                    + "        AND    codConcepto = ?4 "
                    + "        AND    fechaNomina = ?5 ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codNomina);
            query.setParameter(3, nroTrab);
            query.setParameter(4, codConcepto);
            query.setParameter(5, fechaEnFondo);
            Double totalAux = (Double) query.getSingleResult();
            //System.out.println("SE HAN ELIMINADO "+deletedCount+"REGISROS");
            if (totalAux != null) {
                total = totalAux;
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("( NomCalculo1DatJpaController ) ERROR al ejecutar *Typed Query * :?() " + ex);
            Logger.getLogger(NomConceptoHistDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finall
        return (total);
    }  // getTotal()  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    void eliminarCalculo(String rifEmpresa, Integer codNomina, Date fechaCalculo) {
        EntityManager em = null;
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY para MySQL :--------------------------------------
            String txtQuery = "DELETE FROM NomCalculo1_Dat "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    codNomina  = ?2 "
                    + "        AND    fechaNomina = ?3 ";   
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codNomina);
            query.setParameter(3, fechaCalculo);
            int deletedCount = query.executeUpdate();
            //System.out.println("SE HAN ELIMINADO "+deletedCount+"REGISROS");
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("( NomCalculo1DatJpaController ) ERROR al ejecutar *Typed Query * :?() " + ex);
            Logger.getLogger(NomCalculo1DatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally. 
    }  // eliminarConceptos()  

}
