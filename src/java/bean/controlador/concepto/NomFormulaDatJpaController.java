/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomFormulaDat;
import bean.modelo.entidad.NomFormulaDatPK;
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
 * @author henrypb
 */
public class NomFormulaDatJpaController implements Serializable {
    /*
     public NomFormulaDatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */

    public NomFormulaDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomFormulaDat nomFormulaDat) throws PreexistingEntityException, Exception {
        if (nomFormulaDat.getNomFormulaDatPK() == null) {
            nomFormulaDat.setNomFormulaDatPK(new NomFormulaDatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomFormulaDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomFormulaDat(nomFormulaDat.getNomFormulaDatPK()) != null) {
                throw new PreexistingEntityException("NomFormulaDat " + nomFormulaDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomFormulaDat nomFormulaDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomFormulaDat = em.merge(nomFormulaDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomFormulaDatPK id = nomFormulaDat.getNomFormulaDatPK();
                if (findNomFormulaDat(id) == null) {
                    throw new NonexistentEntityException("The nomFormulaDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomFormulaDatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomFormulaDat nomFormulaDat;
            try {
                nomFormulaDat = em.getReference(NomFormulaDat.class, id);
                nomFormulaDat.getNomFormulaDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomFormulaDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomFormulaDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomFormulaDat> findNomFormulaDatEntities() {
        return findNomFormulaDatEntities(true, -1, -1);
    }

    public List<NomFormulaDat> findNomFormulaDatEntities(int maxResults, int firstResult) {
        return findNomFormulaDatEntities(false, maxResults, firstResult);
    }

    private List<NomFormulaDat> findNomFormulaDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomFormulaDat.class));
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

    public NomFormulaDat findNomFormulaDat(NomFormulaDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomFormulaDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomFormulaDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomFormulaDat> rt = cq.from(NomFormulaDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 02 diciembre 2020 14:47. 
    // * Create Dynamic Querie del tipo Native *    
    //----------------------------------------------------------------------------
    public List<NomFormulaDat> getFormulas(String rifEmpresa, Integer codNomina, Integer codConcepto, String codFormula) {
        EntityManager em = null;
        List<NomFormulaDat> lista = new ArrayList<NomFormulaDat>();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            String txtQuery = "SELECT * "
                    + "        FROM   NomFormula_Dat "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    codNomina = ?2 "
                    + "        AND    codConcepto = ?3 "
                    + "        AND    codFormula = ?4 "
                    + "        ORDER  BY correlativo ";
            Query q = em.createNativeQuery(txtQuery);  // OJO: Query q = em.createNamedQuery(txtQuery); <- ERROR, check it is a "Native" Query NOT a "Named" One   
            q.setParameter(1, rifEmpresa);
            q.setParameter(2, codNomina);
            q.setParameter(3, codConcepto);
            q.setParameter(4, codFormula);
            //List l = ( List<NomFormulaDat.class> ) q.getResultList(); 
            List<Object[]> l = q.getResultList();    // manadatory !!!! ;-(
            if (l != null && !l.isEmpty()) {
                /*
                 String accion = null; 
                 Integer correlativo = 0;  
                 String formula, observacion = null;  
                 */
                //System.err.println("SerAdmon..LISTA Con informacion!!!!!!! ");
                for (Object[] o : l) {
                    NomFormulaDatPK pk = new NomFormulaDatPK(rifEmpresa, codNomina, codFormula, codConcepto, (String) o[4], (Integer) o[5]);
                    lista.add(new NomFormulaDat(pk, (String) o[6], (String) o[7]));
                    //System.err.println("Registro Added ");
                } // for 
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomFormulaDatJpaController.getFormulas ) : " + ex);
            // throw ex;  // break el hilo !!!! OJO  
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (lista);
    }  // getFormulas()

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 04 diciembre 2020 14:47. 
    // * Create Dynamic Querie del tipo Native *    
    //----------------------------------------------------------------------------
    public Integer getCorrelativo(String rifEmpresa, Integer codNomina, Integer codConcepto, String codFormula) {
        EntityManager em = null;
        Integer cuantos, ultimo = 0;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //-------------------------
            String txtQuery = "SELECT Max( correlativo ) "
                    + "        FROM   NomFormula_Dat "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    codNomina = ?2 "
                    + "        AND    codConcepto = ?3 "
                    + "        AND    codFormula = ?4 ";
            Query q = em.createNativeQuery(txtQuery);
            q.setParameter(1, rifEmpresa);
            q.setParameter(2, codNomina);
            q.setParameter(3, codConcepto);
            q.setParameter(4, codFormula);
            //Long l = (Long) q.getSingleResult();  
            ultimo = (int) q.getSingleResult();
            //System.err.println("cuantos son="+l+"****");  
            //cuantos = l.intValue();  
            //-------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomFormulaDatJpaController.getCorrelativo ) : " + ex);
            // throw ex;  // break el hilo !!!! OJO  
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (ultimo);
    }  // getCorrelativo().   

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 10 diciembre 2020 15:15. 
    // * Create Dynamic Querie del tipo Native * 
    //----------------------------------------------------------------------------
    public List<NomFormulaDat> getListaFormula(String rifEmpresa, Integer codNomina) {
        EntityManager em = null;
        List<NomFormulaDat> lista = new ArrayList<NomFormulaDat>();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //-------------------------
            String txtQuery = "SELECT * "
                    + "        FROM   NomFormula_Dat "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    codNomina  = ?2 "
                    + "        ORDER  BY codNomina, codConcepto, codFormula, correlativo ";

            Query q = em.createNativeQuery(txtQuery);
            q.setParameter(1, rifEmpresa);
            q.setParameter(2, codNomina);
            List<Object[]> listaObj = q.getResultList();
            if (listaObj != null && !listaObj.isEmpty()) {
                for (Object[] o : listaObj) {  //              rif             codNomnia       codFormula     codConcepto     accion         correlativo 
                    //System.out.println("                       o0=" + o[0] + " o1=" + o[1] + " o2=" + o[2] + " o3=" + o[3] + " o4=" + o[4] + " o5=" + o[5] + " o6=" + o[6] + "");
                    NomFormulaDatPK pk = new NomFormulaDatPK( (String) o[0], (Integer) o[1], (String) o[2], (Integer) o[3], (String) o[4], (Integer) o[5] );
                    lista.add(new NomFormulaDat(pk, (String) o[6], (String) o[7]));
                    //                               formula        observacion 
                }
            }
            //-------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomFormulaDat.getListaFormula ) : " + ex);
            // throw ex;  // break el hilo !!!! OJO  
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (lista);
    }  // getListaFormula().   

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 04 diciembre 2020 14:47. 
    // * Create Dynamic Querie del tipo Native * modelo == NO ESCRIBAS TANTO !!!..     
    //----------------------------------------------------------------------------
    public Integer modelo(String rifEmpresa, Integer codNomina, Integer codConcepto, String codFormula) {
        EntityManager em = null;
        Integer cuantos = 0;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //-------------------------
            //-------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( <Nombre de esta clase>.<Nombre del metodo> ) : " + ex);
            // throw ex;  // break el hilo !!!! OJO  
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (cuantos);
    }  // modelo().   // NO ESCRIBAS TANTO  !!!  :'))

}
