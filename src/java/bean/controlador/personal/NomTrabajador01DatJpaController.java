/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.personal;

import bean.controlador.personal.exceptions.NonexistentEntityException;
import bean.controlador.personal.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomTrabajador01Dat;
import bean.modelo.entidad.NomTrabajador01DatPK;
import bean.modelo.entidad.TrabajadorView;
import bean.utilitario.controlador.JpaUtilEMF;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author henrypb
 */
public class NomTrabajador01DatJpaController implements Serializable {

    /*
     public NomTrabajador01DatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    private EntityManagerFactory emf = null;

    public NomTrabajador01DatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomTrabajador01Dat nomTrabajador01Dat) throws PreexistingEntityException, Exception {
        if (nomTrabajador01Dat.getNomTrabajador01DatPK() == null) {
            nomTrabajador01Dat.setNomTrabajador01DatPK(new NomTrabajador01DatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomTrabajador01Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomTrabajador01Dat(nomTrabajador01Dat.getNomTrabajador01DatPK()) != null) {
                throw new PreexistingEntityException("NomTrabajador01Dat " + nomTrabajador01Dat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomTrabajador01Dat nomTrabajador01Dat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomTrabajador01Dat = em.merge(nomTrabajador01Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomTrabajador01DatPK id = nomTrabajador01Dat.getNomTrabajador01DatPK();
                if (findNomTrabajador01Dat(id) == null) {
                    throw new NonexistentEntityException("The nomTrabajador01Dat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomTrabajador01DatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomTrabajador01Dat nomTrabajador01Dat;
            try {
                nomTrabajador01Dat = em.getReference(NomTrabajador01Dat.class, id);
                nomTrabajador01Dat.getNomTrabajador01DatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomTrabajador01Dat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomTrabajador01Dat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomTrabajador01Dat> findNomTrabajador01DatEntities() {
        return findNomTrabajador01DatEntities(true, -1, -1);
    }

    public List<NomTrabajador01Dat> findNomTrabajador01DatEntities(int maxResults, int firstResult) {
        return findNomTrabajador01DatEntities(false, maxResults, firstResult);
    }

    private List<NomTrabajador01Dat> findNomTrabajador01DatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomTrabajador01Dat.class));
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

    public NomTrabajador01Dat findNomTrabajador01Dat(NomTrabajador01DatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomTrabajador01Dat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomTrabajador01DatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomTrabajador01Dat> rt = cq.from(NomTrabajador01Dat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 10 octubre 2018 13:17. 
    // Create dynamic native queries: 
    //--------------------------------------------------------------------------
    public List<TrabajadorView> getListaTrab(String rifEmpresa) {
        EntityManager em = null;
        List<TrabajadorView> lista = new ArrayList<TrabajadorView>();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lista = ejecutarQuery(em, rifEmpresa);  // ** em.persist(nomConfigDat); **
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomTrabajador01DatJpaController ) : " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (lista);
    } // insertRegistro()

    //--------------------------------------------------------------------------
    // Henry J. Pulgar B. Bqto: 17 septiembre 2018. 
    //--------------------------------------------------------------------------
    private List<TrabajadorView> ejecutarQuery(EntityManager em, String rifEmpresa) {
        List<TrabajadorView> lista = new ArrayList<TrabajadorView>();
        //          Atributo nro: 0       1                2             3            4            5     
        String txtQuery = "SELECT foto, t2.tipoNomina, t1.nroTrabajador, cedulaID , t1.nombre1, t1.apellido1 "
                + "        FROM   NomTrabajador00_Dat t1 LEFT JOIN NomTrabajador01_Dat t2 "
                + "        ON     ( t1.rifEmpresa = t2.rifEmpresa and t1.nroTrabajador = t2.nroTrabajador ) "
                + "        WHERE   t1.rifEmpresa=? "
                + "        ORDER BY t2.tipoNomina, t1.nroTrabajador ";
        //Query query = em.createNativeQuery("update NomConfig_Dat set rutaAbsFot=?");
        Query query = em.createNativeQuery(txtQuery);
        //query.setParameter(1, "/miRuta/");
        query.setParameter(1, rifEmpresa);
        //query.executeUpdate();
        BufferedImage foto = null;
        Integer tipoNomina = 0;
        Integer nroTrab = 0;
        String cedula = null;
        String nombre = null;
        String apellido = null;
        List<Object[]> listaObj = query.getResultList();
        for (Object[] obj : listaObj) {
            if (obj[0] != null) {
                foto = new LibreriaHP().getImagen((byte[]) obj[0]);
            } else {
                foto = null;
            }
            tipoNomina = (Integer) obj[1];
            nroTrab = (Integer) obj[2];
            cedula = (String) obj[3];
            nombre = (String) obj[4];
            apellido = (String) obj[5];
            lista.add(new TrabajadorView(foto, tipoNomina, nroTrab, cedula, nombre, apellido));
        }
        return (lista);
    }  // ejecutarQuery() 

    //--------------------------------------------------------------------------
    // HJPB: 12 octubre 2020 08:45. 
    //--------------------------------------------------------------------------
    public Integer getTurno(String rifEmpresa, Integer codNomina, Integer nroTrabajador) {
        EntityManager em = null;
        Integer turno = 0;
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            String txtQuery = "SELECT turno "
                    + "        FROM   NomTrabajador01_Dat "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    tipoNomina = ?2 "
                    + "        AND    nroTrabajador = ?3 ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codNomina);
            query.setParameter(3, nroTrabajador);
            List<Object> lista = query.getResultList();
            //if (lista.isEmpty()) { System.out.println("***LISTA turno VACIA***"); }
            //else  { System.out.println("***LISTA turno NO VACIA***"); }
            if (lista != null && !lista.isEmpty()) {
                for (Object o : lista) {
                    turno = (Integer) o;
                }  // for 
                //System.out.println("Trab.Nro="+nroTrabajador+"turno="+turno+"******");
            }
            em.getTransaction().commit();   // Revisar 
        } catch (Exception ex) {
            System.err.println("(NomTrabajador01DatJpaController) ERROR al ejecutar *Typed Query * :?() " + ex);
            //Logger.getLogger(NomTrabajador01DatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally. 
        return (turno);
    } // getTurno().

    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    // Fecha: 20 octubre 2020.   
    //                     ============
    // doc: utilizando JPA NamedQuery() - Arquitectura Java. 
    //                     ============
    //      www.arquitecturajava.com ...
    //--------------------------------------------------------------------------
    public NomTrabajador01Dat getTrabajador(String rifEmpresa, Integer nroTrabajador) {
        EntityManager em = getEntityManager();
        TypedQuery<NomTrabajador01Dat> q = em.createNamedQuery("NomTrabajador01Dat.findByRifEmpresa", NomTrabajador01Dat.class);
        q.setParameter("rifEmpresa", rifEmpresa);
        q.setParameter("nroTrabajador", nroTrabajador);
        NomTrabajador01Dat nomTrabajador01Dat = null;
        try {
            nomTrabajador01Dat = q.getSingleResult();
            /*
             for (Iterator<NomConceptoDat> item = lista.iterator(); item.hasNext();) {
             nomConceptoDat = item.next();
             listaS.add(nomConceptoDat.getNomConceptoDatPK().getCodConcepto() + "-" + nomConceptoDat.getDescripcion());
             }
             */
        } catch (Exception ex) {
            System.err.println("(NomTrabajador01DatJpaController) ERROR al ejecutar *Typed Query* getTrabajador :?() " + ex);
            //Logger.getLogger(NomTrabajador01DatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
        }
        return (nomTrabajador01Dat);
    } // getTrabajador(). 

}
