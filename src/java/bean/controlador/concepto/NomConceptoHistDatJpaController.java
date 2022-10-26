/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.AsigConcepto;
import bean.modelo.entidad.NomConceptoHistDat;
import bean.modelo.entidad.NomConceptoHistDatPK;
import bean.utilitario.controlador.JpaUtilEMF;
import bean.utilitario.libreria.LibreriaHP;
import java.io.Serializable;
import java.util.ArrayList;
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
public class NomConceptoHistDatJpaController implements Serializable {

    /*
     public NomConceptoHistDatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    public NomConceptoHistDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomConceptoHistDat nomConceptoHistDat) throws PreexistingEntityException, Exception {
        if (nomConceptoHistDat.getNomConceptoHistDatPK() == null) {
            nomConceptoHistDat.setNomConceptoHistDatPK(new NomConceptoHistDatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomConceptoHistDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomConceptoHistDat(nomConceptoHistDat.getNomConceptoHistDatPK()) != null) {
                throw new PreexistingEntityException("NomConceptoHistDat " + nomConceptoHistDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomConceptoHistDat nomConceptoHistDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomConceptoHistDat = em.merge(nomConceptoHistDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomConceptoHistDatPK id = nomConceptoHistDat.getNomConceptoHistDatPK();
                if (findNomConceptoHistDat(id) == null) {
                    throw new NonexistentEntityException("The nomConceptoHistDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomConceptoHistDatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomConceptoHistDat nomConceptoHistDat;
            try {
                nomConceptoHistDat = em.getReference(NomConceptoHistDat.class, id);
                nomConceptoHistDat.getNomConceptoHistDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomConceptoHistDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomConceptoHistDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomConceptoHistDat> findNomConceptoHistDatEntities() {
        return findNomConceptoHistDatEntities(true, -1, -1);
    }

    public List<NomConceptoHistDat> findNomConceptoHistDatEntities(int maxResults, int firstResult) {
        return findNomConceptoHistDatEntities(false, maxResults, firstResult);
    }

    private List<NomConceptoHistDat> findNomConceptoHistDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomConceptoHistDat.class));
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

    public NomConceptoHistDat findNomConceptoHistDat(NomConceptoHistDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomConceptoHistDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomConceptoHistDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomConceptoHistDat> rt = cq.from(NomConceptoHistDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    // Bqto: 01 septiember 2020 15:57   
    //                     =============
    // doc: utilizando JPA Typed Query() - Arquitectura Java. 
    //                     =============
    //      www.arquitecturajava.com ...
    //  *NOTA IMPORTANTE*: 
    //--------------------------------------------------------------------------
    public List<AsigConcepto> listaAsigConceptos(String rifEmpresa) {
        EntityManager em = null;
        List<AsigConcepto> lista = new ArrayList<>();
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY para MySQL :--------------------------------------
            String txtQuery = "SELECT NomConceptoHist_Dat.codNomina AS codNomina, "
                    + "               NomConceptoHist_Dat.nroTrabajador AS nroTrabajador, "
                    + "               concat_ws(' ',nombre1,nombre2,apellido1,apellido2) AS nombre, "
                    + "               NomConceptoHist_Dat.codConcepto AS codConcepto, descripcion, "
                    + "               fechaNomina "
                    + "        FROM   NomConceptoHist_Dat, NomTrabajador00_Dat, NomConcepto_Dat "
                    + "        WHERE  NomConceptoHist_Dat.rifEmpresa = ? "
                    + "        AND    NomTrabajador00_Dat.rifEmpresa = NomConceptoHist_Dat.rifEmpresa "
                    + "        AND    NomTrabajador00_Dat.nroTrabajador = NomConceptoHist_Dat.nroTrabajador "
                    + "        AND    NomConcepto_Dat.rifEmpresa = NomConceptoHist_Dat.rifEmpresa "
                    + "        AND    NomConcepto_Dat.codNomina = NomConceptoHist_Dat.codNomina "
                    + "        AND    NomConcepto_Dat.codConcepto = NomConceptoHist_Dat.codConcepto "
                    + "        ORDER  BY NomConceptoHist_Dat.codNomina, NomConcepto_Dat.codConcepto ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            //-----
            //BigDecimal totalCosto = BigDecimal.ZERO;
            //BigDecimal existencia = BigDecimal.ZERO;
            //-----
            List<Object[]> listaObj = query.getResultList();
            if (!listaObj.isEmpty()) {
                /*
                 System.out.println("**************QUUERY EJECUTADO CORRECTAMENET y c/valores ******");
                 for ( Object[] o : listaObj ) {
                 System.out.println("cod nomina="+o[0]+"***"); 
                 System.out.println("ficha="+o[1]+"***");
                 System.out.println("nombre="+o[2]+"***");
                 } // for
                 */
                for (Object[] o : listaObj) {
                    String fechaNominaS = new LibreriaHP().formatoFecha.format(o[5]);
                    lista.add(new AsigConcepto((Integer) o[0], (Integer) o[1], (String) o[2], (Integer) o[3], (String) o[4], fechaNominaS));
                } // for
            } else {
                System.out.println("**ATENCION: lista vacia ????**");
            }
            //*  ---------------------------------------------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR al ejecutar consulta  *Typed Query * :?() " + ex);
            try {
                throw ex;
            } catch (Exception ex1) {
                Logger.getLogger(NomConceptoHistDatJpaController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally
        return (lista);
    }  //  listaAsigConceptos().

    //--------------------------------------------------------------------------
    public void eliminarHistConceptos(String rifEmpresa, java.util.Date fechaCalculo) {
        EntityManager em = null;
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY para MySQL :--------------------------------------
            String txtQuery = "DELETE FROM NomConceptoHist_Dat "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    fechaNomina = ?2 "; 
                    /*   Revisar esto ?? ERROR ================?????????????????????????
                    + "        AND    Exists ( SELECT T2.nroTrabajador "
                    + "                        FROM   NomConceptoHist_Dat T2 "
                    + "                        WHERE  T2.fechaNomina = NomConceptoHist_Dat.fechaNomina "
                    + "                        limit 1 ) "; 
                    */
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, fechaCalculo);
            int deletedCount = query.executeUpdate(); 
            //System.out.println("SE HAN ELIMINADO "+deletedCount+"REGISROS");
            em.getTransaction().commit();
        }  catch (Exception ex ) {
            System.err.println("ERROR al ejecutar *Typed Query * :?() " + ex);
            Logger.getLogger(NomConceptoHistDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally. 
    } // eliminarHistConceptos().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void procesarRegsHist(String rifEmpresa, java.util.Date fechaCierre, Integer codNomina) {
        EntityManager em = null;
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY para MySQL :--------------------------------------
            /*
            ( rifEmpresa, codNomina, nroTrabajador, codConcepto, fechaNomina, 
              cantidad, monto, total, porcentaje, montoMin, montoMax, frecuenciaCalculo, 
|             codFormula, frecuenciaPago, formaPago, inicializable,  observacion )
            */   
            String txtQuery = "INSERT INTO NomConceptoHist_Dat ( rifEmpresa, codNomina, nroTrabajador, codConcepto, fechaNomina, turno, " +
                              "                                  cantidad, monto, total, porcentaje, montoMin, montoMax, frecuenciaCalculo, " + 
                              "                                  codFormula, frecuenciaPago, formaPago, inicializable,  observacion1, observacion2 ) " + 
                              "SELECT                            rifEmpresa, codNomina, nroTrabajador, codConcepto, ?1, turno, " +
                              "                                  cantidad, monto, total, porcentaje, montoMin, montoMax, frecuenciaCalculo, " + 
                              "                                  codFormula, frecuenciaPago, formaPago, inicializable,  observacion1, observacion2 " +  
                              "FROM NomCalculo_Dat " + 
                              "WHERE rifEmpresa = ?2 " + 
                              "AND   codNomina = ?3 ";  
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, fechaCierre);
            query.setParameter(2, rifEmpresa);
            query.setParameter(3, codNomina);
            int insertCount = query.executeUpdate(); 
            //System.out.println("SE HAN ELIMINADO "+deletedCount+"REGISROS");
            em.getTransaction().commit();
        }  catch (Exception ex ) {
            System.err.println("( NomConceptoHistDatJpaController ) ERROR al ejecutar *Typed Query * :?() " + ex);
            Logger.getLogger(NomConceptoHistDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally. 
    } // procesarRegsHist().
    
}
