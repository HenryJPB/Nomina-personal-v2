/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.configGeneral;

import bean.controlador.configGeneral.exceptions.NonexistentEntityException;
import bean.controlador.configGeneral.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomConfigDat;
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
public class NomConfigDatJpaController_noDelete implements Serializable {

    /*
     public NomConfigDatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    public NomConfigDatJpaController_noDelete() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomConfigDat nomConfigDat) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomConfigDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomConfigDat(nomConfigDat.getRifEmpresa()) != null) {
                throw new PreexistingEntityException("NomConfigDat " + nomConfigDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomConfigDat nomConfigDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomConfigDat = em.merge(nomConfigDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = nomConfigDat.getRifEmpresa();
                if (findNomConfigDat(id) == null) {
                    throw new NonexistentEntityException("The nomConfigDat with id " + id + " no longer exists.");
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
            NomConfigDat nomConfigDat;
            try {
                nomConfigDat = em.getReference(NomConfigDat.class, id);
                nomConfigDat.getRifEmpresa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomConfigDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomConfigDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomConfigDat> findNomConfigDatEntities() {
        return findNomConfigDatEntities(true, -1, -1);
    }

    public List<NomConfigDat> findNomConfigDatEntities(int maxResults, int firstResult) {
        return findNomConfigDatEntities(false, maxResults, firstResult);
    }

    private List<NomConfigDat> findNomConfigDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomConfigDat.class));
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

    public NomConfigDat findNomConfigDat(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomConfigDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomConfigDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomConfigDat> rt = cq.from(NomConfigDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //------------------o-----------TESTING!!!!--o------------------------------
    
    //--------------------------------------------------------------------------
    // Henry J. Pulgar B. Bqto: 17 septiembre 2018. 15:34
    //  *************PROBANDO*****************=> Eeeeexiiiitooooo..!!!
    //--------------------------------------------------------------------------
    public void insertRegistro() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            addRecord(em);  // ** em.persist(nomConfigDat); **
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomConfigDatJpaController ) al insertar registro: " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    } // insertRegistro()

    //--------------------------------------------------------------------------
    // Henry J. Pulgar B. Bqto: 17 septiembre 2018. 15:34
    //  *************PROBANDO*****************=> Eeeeexiiiitooooo..!!!
    //--------------------------------------------------------------------------
    private void addRecord(EntityManager em) {
        //EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("insert into NomConfig_Dat(rifEmpresa,rutaAbsFoto) values(?,?)");
        query.setParameter(1, "J-PRUEBA-7");
        query.setParameter(2, "/miRuta/");
        query.executeUpdate();
    }  // addRecord() 

    //--------------------------------------------------------------------------
    // Henry J. Pulgar B. Bqto: 18 septiembre 2018. 08:25
    //  *************PROBANDO*****************=> Eeeeexiiiitooooo..!!!
    //--------------------------------------------------------------------------
    public void actualizarRegistro() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            updateRecord(em);  // ** em.persist(nomConfigDat); **
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomConfigDatJpaController ) al actauliazar registro: " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    } // insertRegistro()

    //--------------------------------------------------------------------------
    // Henry J. Pulgar B. Bqto: 17 septiembre 2018. 
    //  *************PROBANDO*****************=> Eeeeexiiiitooooo..!!!
    //--------------------------------------------------------------------------
    private void updateRecord(EntityManager em) {
        //EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("update NomConfig_Dat set rutaAbsFot=?");
        query.setParameter(1, "/miRuta/");
        query.executeUpdate();
    }  // addRecord() 
    
    
}
