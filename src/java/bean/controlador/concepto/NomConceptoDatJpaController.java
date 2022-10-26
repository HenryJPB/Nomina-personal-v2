/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomConceptoDat;
import bean.modelo.entidad.NomConceptoDatPK;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
public class NomConceptoDatJpaController implements Serializable {

    /*
     public NomConceptoDatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    public NomConceptoDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomConceptoDat nomConceptoDat) throws PreexistingEntityException, Exception {
        if (nomConceptoDat.getNomConceptoDatPK() == null) {
            nomConceptoDat.setNomConceptoDatPK(new NomConceptoDatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomConceptoDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomConceptoDat(nomConceptoDat.getNomConceptoDatPK()) != null) {
                throw new PreexistingEntityException("NomConceptoDat " + nomConceptoDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomConceptoDat nomConceptoDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomConceptoDat = em.merge(nomConceptoDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomConceptoDatPK id = nomConceptoDat.getNomConceptoDatPK();
                if (findNomConceptoDat(id) == null) {
                    throw new NonexistentEntityException("The nomConceptoDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomConceptoDatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomConceptoDat nomConceptoDat;
            try {
                nomConceptoDat = em.getReference(NomConceptoDat.class, id);
                nomConceptoDat.getNomConceptoDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomConceptoDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomConceptoDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomConceptoDat> findNomConceptoDatEntities() {
        return findNomConceptoDatEntities(true, -1, -1);
    }

    public List<NomConceptoDat> findNomConceptoDatEntities(int maxResults, int firstResult) {
        return findNomConceptoDatEntities(false, maxResults, firstResult);
    }

    private List<NomConceptoDat> findNomConceptoDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomConceptoDat.class));
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

    public NomConceptoDat findNomConceptoDat(NomConceptoDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomConceptoDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomConceptoDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomConceptoDat> rt = cq.from(NomConceptoDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    //                     ============
    // doc: utilizando JPA NamedQuery() - Arquitectura Java. 
    //                     ============
    //      www.arquitecturajava.com ...
    //--------------------------------------------------------------------------
    public List<String> getConceptos_OLD(String rifEmpresa) {
        EntityManager em = getEntityManager();
        TypedQuery<NomConceptoDat> qLista = em.createNamedQuery("NomConceptoDat.findByRifEmpresa", NomConceptoDat.class);
        qLista.setParameter("rifEmpresa", rifEmpresa);
        List<NomConceptoDat> lista = qLista.getResultList();
        List<String> listaS = new ArrayList<>();
        NomConceptoDat nomConceptoDat = null;
        for (Iterator<NomConceptoDat> item = lista.iterator(); item.hasNext();) {
            nomConceptoDat = item.next();
            listaS.add(nomConceptoDat.getNomConceptoDatPK().getCodConcepto() + "-" + nomConceptoDat.getDescripcion());
        }
        em.close();
        return (listaS);
    } // getConceptos_OLD(). 

    //---------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public List<String> getConceptos(String rifEmpresa) {
        EntityManager em = getEntityManager();
        List<String> listaS = new ArrayList<>();
        //System.out.println("LLEGUE. rifEmpresa="+rifEmpresa+"***codNomina="+codNomina+"***");
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //String txtQuery = "SELECT Distinct concat_ws('-',codConcepto,descripcion) as concepto "
            String txtQuery = "SELECT Distinct codConcepto, descripcion "
                    + "        FROM   NomConcepto_Dat "
                    + "        WHERE  rifEmpresa= ?1 "
                    + "        ORDER  BY codConcepto, codNomina ";
            Query q = em.createNativeQuery(txtQuery);
            q.setParameter(1, rifEmpresa);
            //q.setParameter(2, codNomina);
            Integer intCodConcepto;
            String descripcion;
            List<Object[]> lista = q.getResultList();
            for (Object[] o : lista) {
                intCodConcepto = (Integer) o[0];
                descripcion = (String) o[1];
                listaS.add(intCodConcepto.toString() + "-" + descripcion);
            } // for. 
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomConceptoDatJpaController ) : " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (listaS);
    } // getConceptos(). 

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 03 septiembre 2020 10:09. 
    // Create dynamic native queries: TYPED query. 
    //--------------------------------------------------------------------------
    public String getDescripConcepto(String rifEmpresa, Integer codNomina, Integer codConcepto) {
        EntityManager em = getEntityManager();
        String nombre = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            String txtQuery = "SELECT descripcion "
                    + "        FROM   NomConcepto_Dat "
                    + "        WHERE  rifEmpresa= ?1 AND codNomina = ?2 AND codConcepto = ?3";
            Query q = em.createNativeQuery(txtQuery);
            q.setParameter(1, rifEmpresa);
            q.setParameter(2, codNomina);
            q.setParameter(3, codConcepto);
            List<String> lista = q.getResultList();
            for (String s : lista) {
                //System.out.println(s);  
                nombre = (String) s;
            }  // for
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomConceptoDatJpaController ) : " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (nombre);
    }  // getNombreNomina()

}
