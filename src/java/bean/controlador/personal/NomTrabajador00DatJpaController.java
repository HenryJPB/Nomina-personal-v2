/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.personal;

import bean.controlador.personal.exceptions.NonexistentEntityException;
import bean.controlador.personal.exceptions.PreexistingEntityException;
import bean.modelo.entidad.NomTrabajador00Dat;
import bean.modelo.entidad.NomTrabajador00DatPK;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
public class NomTrabajador00DatJpaController implements Serializable {

    /*
     public NomTrabajador00DatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    public NomTrabajador00DatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomTrabajador00Dat nomTrabajador00Dat) throws PreexistingEntityException, Exception {
        if (nomTrabajador00Dat.getNomTrabajador00DatPK() == null) {
            nomTrabajador00Dat.setNomTrabajador00DatPK(new NomTrabajador00DatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomTrabajador00Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomTrabajador00Dat(nomTrabajador00Dat.getNomTrabajador00DatPK()) != null) {
                throw new PreexistingEntityException("NomTrabajador00Dat " + nomTrabajador00Dat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomTrabajador00Dat nomTrabajador00Dat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomTrabajador00Dat = em.merge(nomTrabajador00Dat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomTrabajador00DatPK id = nomTrabajador00Dat.getNomTrabajador00DatPK();
                if (findNomTrabajador00Dat(id) == null) {
                    throw new NonexistentEntityException("The nomTrabajador00Dat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomTrabajador00DatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomTrabajador00Dat nomTrabajador00Dat;
            try {
                nomTrabajador00Dat = em.getReference(NomTrabajador00Dat.class, id);
                nomTrabajador00Dat.getNomTrabajador00DatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomTrabajador00Dat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomTrabajador00Dat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomTrabajador00Dat> findNomTrabajador00DatEntities() {
        return findNomTrabajador00DatEntities(true, -1, -1);
    }

    public List<NomTrabajador00Dat> findNomTrabajador00DatEntities(int maxResults, int firstResult) {
        return findNomTrabajador00DatEntities(false, maxResults, firstResult);
    }

    private List<NomTrabajador00Dat> findNomTrabajador00DatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomTrabajador00Dat.class));
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

    public NomTrabajador00Dat findNomTrabajador00Dat(NomTrabajador00DatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomTrabajador00Dat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomTrabajador00DatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomTrabajador00Dat> rt = cq.from(NomTrabajador00Dat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    // Bqto: 13 de septiembre 2018 14:01 
    // doc: utilizando JPA NamedQuery() - Arquitectura Java. 
    //      www.arquitecturajava.com ...
    //--------------------------------------------------------------------------
    public List<NomTrabajador00Dat> getlistaTrab(String rifEmpresa) {
        EntityManager em = getEntityManager();
        //em.getTransaction().begin();
        //NomTrabajador00Dat nomTrabajador00Dat;
        //nomTrabajador00Dat = em.getReference(NomTrabajador00Dat.class, id);
        //nomTrabajador00Dat.getNomTrabajador00DatPK();
        TypedQuery<NomTrabajador00Dat> consultarTrabxEmpresa = em.createNamedQuery("NomTrabajador00Dat.findByRifEmpresa", NomTrabajador00Dat.class);
        consultarTrabxEmpresa.setParameter("rifEmpresa", rifEmpresa);
        List<NomTrabajador00Dat> lista = consultarTrabxEmpresa.getResultList();
        return (lista);
    } // getlistaTrabxEmpresa()

    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    //                     ============
    // doc: utilizando JPA NamedQuery() - Arquitectura Java. 
    //                     ============
    //      www.arquitecturajava.com ...
    //--------------------------------------------------------------------------
    public List<String> getTrabajadores(String rifEmpresa) {
        EntityManager em = getEntityManager();
        TypedQuery<NomTrabajador00Dat> qLista = em.createNamedQuery("NomTrabajador00Dat.findByRifEmpresa", NomTrabajador00Dat.class);
        qLista.setParameter("rifEmpresa", rifEmpresa);
        List<NomTrabajador00Dat> lista = qLista.getResultList();
        List<String> listaS = new ArrayList<>();
        NomTrabajador00Dat nomTrabajador00Dat = null;
        for (Iterator<NomTrabajador00Dat> item = lista.iterator(); item.hasNext();) {
            nomTrabajador00Dat = item.next();
            listaS.add(nomTrabajador00Dat.getNomTrabajador00DatPK().getNroTrabajador() + "-" + nomTrabajador00Dat.getNombre1() + " " + nomTrabajador00Dat.getNombre2() + " " + nomTrabajador00Dat.getApellido1() + " " + nomTrabajador00Dat.getApellido2());
        }
        em.close();
        return (listaS);
    } // getTiposNomina().  

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 03 septiembre 2020 10:09. 
    // Create dynamic native queries: TYPED query. 
    //--------------------------------------------------------------------------
    public String getNombreTrabajador(String rifEmpresa, Integer codNomina, Integer nroTrabajador) {
        EntityManager em = getEntityManager();
        String nombre = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            String txtQuery = "SELECT concat_ws(' ',nombre1,nombre2,apellido1,apellido2) AS nombre "
                    + "        FROM   NomTrabajador00_Dat, NomTrabajador01_Dat "
                    + "        WHERE  NomTrabajador00_Dat.rifEmpresa = ?1 "
                    + "        AND    NomTrabajador01_Dat.rifEmpresa = NomTrabajador00_Dat.rifEmpresa "
                    + "        AND    NomTrabajador00_Dat.nroTrabajador = ?2 "
                    + "        AND    NomTrabajador01_Dat.nroTrabajador = NomTrabajador00_Dat.nroTrabajador "
                    + "        AND    NomTrabajador01_Dat.tipoNomina = ?3 "
                    + "        AND    ( NomTrabajador01_Dat.status IS NULL OR NomTrabajador01_Dat.status <> 'R' )";
            Query q = em.createNativeQuery(txtQuery);
            q.setParameter(1, rifEmpresa);
            q.setParameter(2, nroTrabajador);
            q.setParameter(3, codNomina);
            List<String> lista = q.getResultList();
            for (String s : lista) {
                //System.out.println("****nombre(s)="+s+"****");
                nombre = (String) s;
            }  // for
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomTrabajador00DatJpaController ) : " + ex);
            throw ex;
            //Logger.getLogger(NomTiposNominaDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (nombre);
    }  // getNombreTrabajador()

    //--------------------------------------------------------------------------
    // Autor: Henry J. Pulgar B. Bqto: 21 octubre 2020 13:33. 
    // Create dynamic native queries: TYPED query. 
    //--------------------------------------------------------------------------
    public NomTrabajador00Dat getTrabajador(String rifEmpresa, Integer codNomina, Integer nroTrabajador) {
        EntityManager em = getEntityManager();
        NomTrabajador00Dat nomTrabajador00Dat = null; 
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            String txtQuery = "SELECT NomTrabajador00_Dat.rifEmpresa, NomTrabajador00_Dat.nroTrabajador, foto, nacionalidad, "
                    + "               cedulaID, nombre1, nombre2, apellido1, apellido2, "
                    + "               alias, sexo, edoCivil, fechaNac, telefonoHab1, "
                    + "               telefonoHab2, telefonoMovil1, telefonoMovil2, email1, "
                    + "               email2, direccionHab1, direccionHab2, direccionHab3, "
                    + "               codigoPostalHab, parroquiaHab, municipioHab, ciudadHab, "
                    + "               estadoHab, paisHab, codigoPostalNac, parroquiaNac, "
                    + "               municipioNac, ciudadNac, estadoNac, paisNac, "
                    + "               nombreVarAux1, varAux1, nombreVarAux2, varAux2 "
                    + "        FROM   NomTrabajador00_Dat, NomTrabajador01_Dat "
                    + "        WHERE  NomTrabajador00_Dat.rifEmpresa = ?1 "
                    + "        AND    NomTrabajador01_Dat.rifEmpresa = NomTrabajador00_Dat.rifEmpresa "
                    + "        AND    NomTrabajador00_Dat.nroTrabajador = ?2 "
                    + "        AND    NomTrabajador01_Dat.nroTrabajador = NomTrabajador00_Dat.nroTrabajador "
                    + "        AND    NomTrabajador01_Dat.tipoNomina = ?3 "
                    + "        AND    ( NomTrabajador01_Dat.status IS NULL OR NomTrabajador01_Dat.status <> 'R' )";
            Query q = em.createNativeQuery(txtQuery);
            q.setParameter(1, rifEmpresa);
            q.setParameter(2, nroTrabajador);
            q.setParameter(3, codNomina);
            Object[] o = (Object[]) q.getSingleResult(); 
            if (o!=null) {
                NomTrabajador00DatPK pk = new NomTrabajador00DatPK((String) o[0], (Integer) o[1] ); 
                nomTrabajador00Dat = new NomTrabajador00Dat( pk, (byte[]) o[2], (String) o[3], (String) o[4], (String) o[5], (String) o[6], (String) o[7], (String) o[8], (String) o[9], (String) o[10],
                                                           (String) o[11], (java.util.Date) o[12], (String) o[13], (String) o[14], (String) o[15], (String) o[16], (String) o[17], (String) o[18], 
                                                           (String) o[19], (String) o[20], (String) o[21], (String) o[22], (String) o[23], (String) o[24], (String) o[25], (String) o[26], (String) o[27], 
                                                           (String) o[28], (String) o[29], (String) o[30], (String) o[31], (String) o[32], (String) o[33], (String) o[34], (String) o[35], (String) o[36], (String) o[37] );
            } // if 
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomTrabajador00DatJpaController ) : " + ex);
            //throw ex;
            //Logger.getLogger(NomTiposNominaDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return (nomTrabajador00Dat);
    }  // getTrabajador()

}
