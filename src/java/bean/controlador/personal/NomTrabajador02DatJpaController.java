/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.personal;

import bean.controlador.personal.exceptions.NonexistentEntityException;
import bean.controlador.personal.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomTrabajador02Dat;
import bean.modelo.entidad.NomTrabajador02DatPK;
import bean.modelo.entidad.TipoContrato;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author hpulgar
 */
public class NomTrabajador02DatJpaController implements Serializable {

    /*
    public NomTrabajador02DatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    */  
    
    public NomTrabajador02DatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomTrabajador02Dat nomTrabajador02Dat) throws PreexistingEntityException, Exception {
        if (nomTrabajador02Dat.getNomTrabajador02DatPK() == null) {
            nomTrabajador02Dat.setNomTrabajador02DatPK(new NomTrabajador02DatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomTrabajador02Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomTrabajador02Dat(nomTrabajador02Dat.getNomTrabajador02DatPK()) != null) {
                throw new PreexistingEntityException("NomTrabajador02Dat " + nomTrabajador02Dat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomTrabajador02Dat nomTrabajador02Dat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomTrabajador02Dat = em.merge(nomTrabajador02Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomTrabajador02DatPK id = nomTrabajador02Dat.getNomTrabajador02DatPK();
                if (findNomTrabajador02Dat(id) == null) {
                    throw new NonexistentEntityException("The nomTrabajador02Dat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomTrabajador02DatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomTrabajador02Dat nomTrabajador02Dat;
            try {
                nomTrabajador02Dat = em.getReference(NomTrabajador02Dat.class, id);
                nomTrabajador02Dat.getNomTrabajador02DatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomTrabajador02Dat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomTrabajador02Dat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomTrabajador02Dat> findNomTrabajador02DatEntities() {
        return findNomTrabajador02DatEntities(true, -1, -1);
    }

    public List<NomTrabajador02Dat> findNomTrabajador02DatEntities(int maxResults, int firstResult) {
        return findNomTrabajador02DatEntities(false, maxResults, firstResult);
    }

    private List<NomTrabajador02Dat> findNomTrabajador02DatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomTrabajador02Dat.class));
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

    public NomTrabajador02Dat findNomTrabajador02Dat(NomTrabajador02DatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomTrabajador02Dat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomTrabajador02DatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomTrabajador02Dat> rt = cq.from(NomTrabajador02Dat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public Boolean existeContrato(String rifEmpresa, Integer nroTrabajador, Integer codNomina ) {
        EntityManager em = null;
        Boolean ok = Boolean.FALSE;  
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY:   ----------------------------------------------
            String txtQuery = "SELECT NomTrabajador02_Dat.nroTrabajador "
                    + "        FROM   NomTrabajador02_Dat "
                    + "        WHERE  NomTrabajador02_Dat.rifEmpresa = ?1 "
                    + "        AND    NomTrabajador02_Dat.nroTrabajador = ?2 "
                    + "        AND    NomTrabajador02_Dat.codNomina =?3 LIMIT 1";  
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, nroTrabajador);
            query.setParameter(3, codNomina);
            Object o = query.getSingleResult();  
            if ( o!=null ) ok = Boolean.TRUE;  
            //*  ---------------------------------------------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomAsigConceptoDatJpaController.existeContrato ) : " + ex);
            //throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally
        return (ok);
    } // existeContrato()
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public List<TipoContrato> getTiposContrato(String rifEmpresa, Integer nroTrabajador ) {
        EntityManager em = null;
        List<TipoContrato> lista = new ArrayList<TipoContrato>();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY:   ----------------------------------------------
            String txtQuery = "SELECT NomTrabajador02_Dat.nroTrabajador, "
                    + "               NomTrabajador02_Dat.codNomina, NomTiposNomina_Dat.nombreNomina, "
                    + "               NomTrabajador02_Dat.turno, NomTrabajador02_Dat.rotacion "
                    + "        FROM   NomTrabajador02_Dat, NomTiposNomina_Dat "
                    + "        WHERE  NomTrabajador02_Dat.rifEmpresa = ?1 "
                    + "        AND    NomTiposNomina_Dat.rifEmpresa  = NomTrabajador02_Dat.rifEmpresa "  
                    + "        AND    NomTrabajador02_Dat.nroTrabajador = ?2 "
                    + "        AND    NomTrabajador02_Dat.codNomina =  NomTiposNomina_Dat.codNomina "  
                    + "        ORDER  BY NomTrabajador02_Dat.codNomina ";  
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, nroTrabajador);
            java.util.Date fechaVigencia;
            Double valor = 0.00;
            List<Object[]> listaObj = query.getResultList();
            if (listaObj != null && !listaObj.isEmpty()) {
                Integer codNomina = 0;
                String  nombreNomina;  
                Integer turno, rotacion = 0;
                for (Object[] obj : listaObj) {
                    // nroTrabajador  = (Integer) obj[0];
                    codNomina     = (Integer) obj[1];
                    nombreNomina  = (String) obj[2];
                    turno         = (Integer) obj[3]; 
                    rotacion      = (Integer) obj[4]; 
                    lista.add( new TipoContrato( codNomina, nombreNomina, turno, rotacion) );  
                }  // for
            }  // if 
            //*  ---------------------------------------------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomAsigConceptoDatJpaController.getTiposContrato ) : " + ex);
            //throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally
        return (lista);
    } // getTiposContrato()
    
}
