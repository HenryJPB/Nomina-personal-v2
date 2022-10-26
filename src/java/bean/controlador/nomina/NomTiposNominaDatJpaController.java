/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.nomina;

import bean.controlador.nomina.exceptions.NonexistentEntityException;
import bean.controlador.nomina.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomTiposNominaDat;
import bean.modelo.entidad.NomTiposNominaDatPK;
import bean.utilitario.controlador.JpaUtilEMF;
import bean.utilitario.libreria.LibreriaHP;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author henrypb
 */
public class NomTiposNominaDatJpaController implements Serializable {

    /*
     public NomTiposNominaDatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    public NomTiposNominaDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomTiposNominaDat nomTiposNominaDat) throws PreexistingEntityException, Exception {
        if (nomTiposNominaDat.getNomTiposNominaDatPK() == null) {
            nomTiposNominaDat.setNomTiposNominaDatPK(new NomTiposNominaDatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomTiposNominaDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomTiposNominaDat(nomTiposNominaDat.getNomTiposNominaDatPK()) != null) {
                throw new PreexistingEntityException("NomTiposNominaDat " + nomTiposNominaDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomTiposNominaDat nomTiposNominaDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomTiposNominaDat = em.merge(nomTiposNominaDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomTiposNominaDatPK id = nomTiposNominaDat.getNomTiposNominaDatPK();
                if (findNomTiposNominaDat(id) == null) {
                    throw new NonexistentEntityException("The nomTiposNominaDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomTiposNominaDatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomTiposNominaDat nomTiposNominaDat;
            try {
                nomTiposNominaDat = em.getReference(NomTiposNominaDat.class, id);
                nomTiposNominaDat.getNomTiposNominaDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomTiposNominaDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomTiposNominaDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomTiposNominaDat> findNomTiposNominaDatEntities() {
        return findNomTiposNominaDatEntities(true, -1, -1);
    }

    public List<NomTiposNominaDat> findNomTiposNominaDatEntities(int maxResults, int firstResult) {
        return findNomTiposNominaDatEntities(false, maxResults, firstResult);
    }

    private List<NomTiposNominaDat> findNomTiposNominaDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomTiposNominaDat.class));
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

    public NomTiposNominaDat findNomTiposNominaDat(NomTiposNominaDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomTiposNominaDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomTiposNominaDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomTiposNominaDat> rt = cq.from(NomTiposNominaDat.class);
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
    public List<String> getTiposNomina(String rifEmpresa) {
        EntityManager em = getEntityManager();
        TypedQuery<NomTiposNominaDat> consultarTiposNominaEmpresa = em.createNamedQuery("NomTiposNominaDat.findByRifEmpresa", NomTiposNominaDat.class);
        consultarTiposNominaEmpresa.setParameter("rifEmpresa", rifEmpresa);
        List<NomTiposNominaDat> listaTiposNomina = consultarTiposNominaEmpresa.getResultList();
        List<String> lista = new ArrayList<>();
        NomTiposNominaDat nomTiposNominaDat = null;
        for (Iterator<NomTiposNominaDat> item = listaTiposNomina.iterator(); item.hasNext();) {
            nomTiposNominaDat = item.next();
            lista.add(nomTiposNominaDat.getNomTiposNominaDatPK().getCodNomina() + "-" + nomTiposNominaDat.getNombreNomina());
        }
        em.close();
        return (lista);
    } // getTiposNomina().  

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 03 septiembre 2020 10:09. 
    // Create dynamic native queries: TYPED query. 
    //--------------------------------------------------------------------------
    public String getNombreNomina(String rifEmpresa, Integer codNomina) {
        EntityManager em = getEntityManager();
        String nombre = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            String txtQuery = "SELECT nombreNomina "
                    + "        FROM   NomTiposNomina_Dat "
                    + "        WHERE  rifEmpresa= ?1 AND codNomina = ?2 ";
            Query q = em.createNativeQuery(txtQuery);
            q.setParameter(1, rifEmpresa);
            q.setParameter(2, codNomina);
            List<String> lista = q.getResultList();
            for (String s : lista) {
                //System.out.println(s);  
                nombre = (String) s;
            }  // for
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomTiposNominaJpaController ) : " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (nombre);
    }  // getNombreNomina()

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 21 septiembre 2020 11:09. 
    // Create dynamic native queries: TYPED query. 
    //--------------------------------------------------------------------------
    public NomTiposNominaDat getNomina(String rifEmpresa, Integer codNomina) {
        EntityManager em = getEntityManager();
        NomTiposNominaDat nomTiposNomina = new NomTiposNominaDat();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            String txtQuery = "SELECT * "
                    + "        FROM   NomTiposNomina_Dat "
                    + "        WHERE  rifEmpresa= ?1 AND codNomina = ?2 ";
            Query q = em.createNativeQuery(txtQuery);
            q.setParameter(1, rifEmpresa);
            q.setParameter(2, codNomina);
            List<Object[]> lista = q.getResultList();
            if (lista != null && !lista.isEmpty()) {
                for (Object[] o : lista) {
                    //nomTiposNomina.getNomTiposNominaDatPK().setRifEmpresa(rifEmpresa);
                    //nomTiposNomina.getNomTiposNominaDatPK().setCodNomina(codNomina);
                    //System.out.println("o0=" + o[0] + " o1=" + o[1] + " o2=" + o[2] + " o3=" + o[3] + " o4=" + o[4] + "**");
                    // NomTiposNominaDatPK                                                                  nombreNomina  ultimaRotacion         proxRotacion           ultimaRotacion1        proxRotacion1          ultimaRotacion2        proxRotacion2           baseCalculo
                    nomTiposNomina = new NomTiposNominaDat(new NomTiposNominaDatPK(rifEmpresa, codNomina), (String) o[2], (java.util.Date) o[3], (java.util.Date) o[4], (java.util.Date) o[5], (java.util.Date) o[6], (java.util.Date) o[7], (java.util.Date) o[8], (String) o[9]);
                }  // for 
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomTiposNominaJpaController ) : " + ex);
            throw ex;
        } finally {
            em.close();
        }
        return (nomTiposNomina);
    } // getNomina(); 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void actualizarRotacionNomina(String rifEmpresa, Integer codNomina, String frecCalculo, java.util.Date fechaCierre, java.util.Date fechaPago) {
        EntityManager em = null;
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        Integer intervalo = 0;
        String txtQuery = null;
        switch (frecCalculo) {
            case "S": {
                intervalo = 7;
                if (fechaCierre.equals(fechaPago)) {
                    txtQuery = "UPDATE NomTiposNomina_Dat "
                            + " SET    ultimaRotacion  = proxRotacion, "
                            + "        proxRotacion    = DATE_ADD( proxRotacion, INTERVAL ?1 DAY ),  "
                            + "        ultimaRotacion1 = DATE_ADD( ultimaRotacion1, INTERVAL (?1)*2 DAY ), "
                            + "        proxRotacion1   = DATE_ADD( proxRotacion1, INTERVAL (?1)*2 DAY ) "
                            + " WHERE rifEmpresa = ?2 "
                            + " AND   codNomina = ?3 ";
                } else {
                    txtQuery = "UPDATE NomTiposNomina_Dat "
                            + " SET    ultimaRotacion = proxRotacion, "
                            + "        proxRotacion   = DATE_ADD( proxRotacion, INTERVAL ?1 DAY ) "
                            + " WHERE rifEmpresa = ?2 "
                            + " AND   codNomina = ?3 ";
                }
                break;
            }   // dias
            case "Q": {
                intervalo = 15;
                if (fechaCierre.equals(new LibreriaHP().getUltDiaMes(fechaCierre))) {
                    txtQuery = "UPDATE NomTiposNomina_Dat "
                            + " SET    ultimaRotacion = proxRotacion, "
                            + "        proxRotacion   = DATE_ADD( LAST_DAY( proxRotacion ), INTERVAL ?1 DAY ) "
                            + " WHERE rifEmpresa = ?2 "
                            + " AND   codNomina = ?3 ";
                } else {
                    txtQuery = "UPDATE NomTiposNomina_Dat "
                            + " SET    ultimaRotacion = proxRotacion, "
                            + "        proxRotacion   = LAST_DAY( proxRotacion ) "
                            + " WHERE rifEmpresa = ?2 "
                            + " AND   codNomina = ?3 ";
                }  // if-else 
                break;
            }
            default:
                intervalo = 7;
        }  // switch()
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, intervalo);
            query.setParameter(2, rifEmpresa);
            query.setParameter(3, codNomina);
            int updateCount = query.executeUpdate();
            //System.out.println("SE HAN ELIMINADO "+deletedCount+"REGISROS");
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR al ejecutar *Typed Query * :?() " + ex);
            Logger.getLogger(NomTiposNominaDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally. 
    } // actualizarRotacionNomina().

}
