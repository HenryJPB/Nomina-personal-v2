/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.controlador.concepto.exceptions.PreexistingEntityException;
import bean.modelo.entidad.AsigConcepto;
import bean.modelo.entidad.NomAsigConceptoDat;
import bean.modelo.entidad.NomAsigConceptoDatPK;
import bean.utilitario.controlador.JpaUtilEMF;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @author hpulgar
 */
public class NomAsigConceptoDatJpaController implements Serializable {

    /*
     public NomAsigConceptoDatJpaController(EntityManagerFactory emf) {
     this.emf = emf;
     }
     */
    public NomAsigConceptoDatJpaController() {
        this.emf = JpaUtilEMF.getEntityManagerFactory();
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NomAsigConceptoDat nomAsigConceptoDat) throws PreexistingEntityException, Exception {
        if (nomAsigConceptoDat.getNomAsigConceptoDatPK() == null) {
            nomAsigConceptoDat.setNomAsigConceptoDatPK(new NomAsigConceptoDatPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nomAsigConceptoDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNomAsigConceptoDat(nomAsigConceptoDat.getNomAsigConceptoDatPK()) != null) {
                throw new PreexistingEntityException("NomAsigConceptoDat " + nomAsigConceptoDat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NomAsigConceptoDat nomAsigConceptoDat) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nomAsigConceptoDat = em.merge(nomAsigConceptoDat);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                NomAsigConceptoDatPK id = nomAsigConceptoDat.getNomAsigConceptoDatPK();
                if (findNomAsigConceptoDat(id) == null) {
                    throw new NonexistentEntityException("The nomAsigConceptoDat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(NomAsigConceptoDatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NomAsigConceptoDat nomAsigConceptoDat;
            try {
                nomAsigConceptoDat = em.getReference(NomAsigConceptoDat.class, id);
                nomAsigConceptoDat.getNomAsigConceptoDatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nomAsigConceptoDat with id " + id + " no longer exists.", enfe);
            }
            em.remove(nomAsigConceptoDat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NomAsigConceptoDat> findNomAsigConceptoDatEntities() {
        return findNomAsigConceptoDatEntities(true, -1, -1);
    }

    public List<NomAsigConceptoDat> findNomAsigConceptoDatEntities(int maxResults, int firstResult) {
        return findNomAsigConceptoDatEntities(false, maxResults, firstResult);
    }

    private List<NomAsigConceptoDat> findNomAsigConceptoDatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NomAsigConceptoDat.class));
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

    public NomAsigConceptoDat findNomAsigConceptoDat(NomAsigConceptoDatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NomAsigConceptoDat.class, id);
        } finally {
            em.close();
        }
    }

    public int getNomAsigConceptoDatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NomAsigConceptoDat> rt = cq.from(NomAsigConceptoDat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    //*////////*Pool de rutinas personalizadas de esta clase*////////////////*//
    //--------------------------------------------------------------------------
    // Autor: HJPB. Bqto. Nov, 03 2020 11:15 
    //--------------------------------------------------------------------------
    public void actualizar(NomAsigConceptoDat nomAsigConceptoDat) {
        EntityManager em = null;
        //java.sql.Date sqlFecha = new java.sql.Date(fecha.getTime());
        String rifEmpresa = nomAsigConceptoDat.getNomAsigConceptoDatPK().getRifEmpresa();
        Integer codNomina = nomAsigConceptoDat.getNomAsigConceptoDatPK().getCodNomina();
        Integer nroTrabajador = nomAsigConceptoDat.getNomAsigConceptoDatPK().getNroTrabajador();
        Integer codConcepto = nomAsigConceptoDat.getNomAsigConceptoDatPK().getCodConcepto();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            String txtQuery = "UPDATE NomAsigConcepto_Dat "
                    + "        SET    cantidad = " + nomAsigConceptoDat.getCantidad() + ", "
                    + "               monto = " + nomAsigConceptoDat.getMonto() + ", "
                    + "               porcentaje = " + nomAsigConceptoDat.getPorcentaje() + ", "
                    + "               frecuenciaCalculo = '" + nomAsigConceptoDat.getFrecuenciaCalculo() + "', "
                    + "               frecuenciaPago = '" + nomAsigConceptoDat.getFrecuenciaPago() + "', "
                    + "               codFormula = " + nomAsigConceptoDat.getCodFormula() + ", "
                    + "               inicializable = '" + nomAsigConceptoDat.getInicializable() + "', "
                    + "               activo = '" + nomAsigConceptoDat.getActivo() + "', "
                    + "               observacion1 = '" + nomAsigConceptoDat.getObservacion1() + "', "
                    + "               observacion2 = '" + nomAsigConceptoDat.getObservacion2() + "' "
                    + "        WHERE  rifEmpresa = ?1 "
                    + "        AND    codNomina = ?2 "
                    + "        AND    nroTrabajador = ?3 "
                    + "        AND    codConcepto = ?4 ";
            //
            //System.out.println("Query="+txtQuery+" ***");
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codNomina);
            query.setParameter(3, nroTrabajador);
            query.setParameter(4, codConcepto);
            //
            int updateCount = query.executeUpdate();
            //System.out.println("SE HAN ELIMINADO "+deletedCount+"REGISROS");
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR al ejecutar *Typed Query* / Metodo actualizar() de la clase NomAsigConceptoJpaController() :?() " + ex);
            Logger.getLogger(NomAsigConceptoDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally. 
    } // actualizar().

    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    // Bqto: 31 agosto 2020 09:55   
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
            String txtQuery = "SELECT NomAsigConcepto_Dat.codNomina AS codNomina, "
                    + "               NomAsigConcepto_Dat.nroTrabajador AS nroTrabajador, "
                    + "               concat_ws(' ',nombre1,nombre2,apellido1,apellido2) AS nombre, "
                    + "               NomAsigConcepto_Dat.codConcepto as codConcepto, "
                    + "               descripcion,  "
                    + "               NomAsigConcepto_Dat.cantidad, NomAsigConcepto_Dat.monto, "
                    + "               NomAsigConcepto_Dat.porcentaje "
                    + "        FROM   NomAsigConcepto_Dat, NomTrabajador00_Dat, NomConcepto_Dat "
                    + "        WHERE  NomAsigConcepto_Dat.rifEmpresa = ?1 "
                    + "        AND    NomTrabajador00_Dat.rifEmpresa = NomAsigConcepto_Dat.rifEmpresa "
                    + "        AND    NomTrabajador00_Dat.nroTrabajador = NomAsigConcepto_Dat.nroTrabajador "
                    + "        AND    NomConcepto_Dat.rifEmpresa = NomAsigConcepto_Dat.rifEmpresa "
                    + "        AND    NomConcepto_Dat.codNomina = NomAsigConcepto_Dat.codNomina "
                    + "        AND    NomConcepto_Dat.codConcepto = NomAsigConcepto_Dat.codConcepto "
                    + "        ORDER  BY NomAsigConcepto_Dat.codNomina, NomAsigConcepto_Dat.nroTrabajador, NomConcepto_Dat.codConcepto ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            //-----
            //BigDecimal totalCosto = BigDecimal.ZERO;
            //BigDecimal existencia = BigDecimal.ZERO;
            //-----
            List<Object[]> listaObj = query.getResultList();
            if (!listaObj.isEmpty()) {
                String cantidadS, montoS, porcentajeS = "";
                Double valor = 0.00;
                for (Object[] o : listaObj) {
                    valor = (Double) o[5];
                    if (valor != null) {
                        cantidadS = new bean.utilitario.libreria.LibreriaHP().formatoDecimal.format(valor);
                    } else {
                        cantidadS = "0";
                    }
                    //System.out.print("valor="+valor+"**formateado="+cantidadS+"*****"); 
                    valor = (Double) o[6];
                    if (valor != null) {
                        montoS = new bean.utilitario.libreria.LibreriaHP().formatoDecimal.format(valor);
                    } else {
                        montoS = "0";
                    }
                    valor = (Double) o[7];
                    if (valor != null) {
                        porcentajeS = new bean.utilitario.libreria.LibreriaHP().formatoDecimal.format(valor);
                    } else {
                        porcentajeS = "0";
                    }
                    //System.out.println("***fin");       
                    //cantidadS =  new bean.utilitario.libreria.LibreriaHP().formatoDecimal.format(  (Double) o[5] ); 
                    //montoS =  new bean.utilitario.libreria.LibreriaHP().formatoDecimal.format(  (Double) o[6] ); 
                    //porcentajeS =  new bean.utilitario.libreria.LibreriaHP().formatoDecimal.format(  (Double) o[7] ); 
                    //System.out.print("cantidad="+(Double) o[5]+"**"); 
                    //System.out.print("monto="+(Double) o[6]+"**"); 
                    //System.out.println("%="+(Double) o[7]+"***"); 
                    lista.add(new AsigConcepto((Integer) o[0], (Integer) o[1], (String) o[2], (Integer) o[3], (String) o[4], cantidadS, montoS, porcentajeS));
                    //lista.add(new AsigConcepto( (Integer) o[0], (Integer) o[1], (String) o[2], (Integer) o[3], (String) o[4], (Double) o[5], (Double) o[6] , (Double) o[7] ) );
                } // for
            } else {
                System.out.println("**ATENCION: lista vacia ????**");
            }
            //*  ---------------------------------------------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("( NomAsigConceptoDatJpaController ) ERROR al ejecutar consulta  *Typed Query * :?() " + ex);
            try {
                throw ex;
            } catch (Exception ex1) {
                Logger.getLogger(NomAsigConceptoDatJpaController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally
        return (lista);
    }  //  listaAsigConceptos().

    //--------------------------------------------------------------------------
    // Autor: Henry Jose Pulgar Blanco
    //                     ============
    // doc: utilizando JPA NamedQuery() - Arquitectura Java. 
    //                     ============
    //      www.arquitecturajava.com ...
    //--------------------------------------------------------------------------
    public List<NomAsigConceptoDat> getListaAsigConceptos(String rifEmpresa) {
        EntityManager em = getEntityManager();
        TypedQuery<NomAsigConceptoDat> qLista = em.createNamedQuery("NomAsigConceptoDat.findByRifEmpresa", NomAsigConceptoDat.class);
        qLista.setParameter("rifEmpresa", rifEmpresa);
        List<NomAsigConceptoDat> lista = qLista.getResultList();
        //
        /*
         NomAsigConceptoDat nomAsigConceptoDat = null;
         for (Iterator<NomAsigConceptoDat> item = lista.iterator(); item.hasNext();) {
         nomAsigConceptoDat  = item.next();
         //listaS.add( nomConceptoDat.getNomConceptoDatPK().getCodConcepto() + "-" + nomConceptoDat.getDescripcion() );
         System.out.println("codNomina="+nomAsigConceptoDat.getNomAsigConceptoDatPK().getCodNomina()+" nroTrabajador="+nomAsigConceptoDat.getNomAsigConceptoDatPK().getNroTrabajador()+" codConcepto="+nomAsigConceptoDat.getNomAsigConceptoDatPK().getCodConcepto()+"****"); 
         }
         */
        //
        em.close();
        return (lista);
    } // getListaAsigConceptos().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public List<NomAsigConceptoDat> getListaAsigConceptos_OLD(String rifEmpresa, String baseCalculo) {
        EntityManager em = getEntityManager();
        TypedQuery<NomAsigConceptoDat> qLista = em.createNamedQuery("NomAsigConceptoDat.findByRifEmpresa", NomAsigConceptoDat.class);  // Adaptado x HJPB. Octubre, 12 2020 13:45 
        qLista.setParameter("rifEmpresa", rifEmpresa);
        qLista.setParameter("baseCalculo", baseCalculo);
        List<NomAsigConceptoDat> lista = qLista.getResultList();
        em.close();
        return (lista);
    } // getListaAsigConceptos_OLD().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public List<NomAsigConceptoDat> getListaAsigConceptos(String rifEmpresa, String baseCalculo) {
        final String p_activo = "S";   // S/N. 
        EntityManager em = null;
        List<NomAsigConceptoDat> lista = new ArrayList<NomAsigConceptoDat>();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY:   ----------------------------------------------
            String txtQuery = "SELECT NomAsigConcepto_Dat.rifEmpresa, NomAsigConcepto_Dat.codNomina, "
                    + "       NomAsigConcepto_Dat.nroTrabajador, NomAsigConcepto_Dat.codConcepto, "
                    + "       NomAsigConcepto_Dat.fechaInicial, NomAsigConcepto_Dat.fechaFinal, "
                    + "       NomAsigConcepto_Dat.cantidad, NomAsigConcepto_Dat.monto, "
                    + "       NomAsigConcepto_Dat.porcentaje, NomAsigConcepto_Dat.montoMin, "
                    + "       NomAsigConcepto_Dat.montoMax, NomAsigConcepto_Dat.frecuenciaCalculo, "
                    + "       NomAsigConcepto_Dat.calcular, NomAsigConcepto_Dat.codFormula, NomAsigConcepto_Dat.frecuenciaPago, "
                    + "       NomAsigConcepto_Dat.formaPago, NomAsigConcepto_Dat.inicializable, "
                    + "       NomAsigConcepto_Dat.activo, NomAsigConcepto_Dat.observacion1, NomAsigConcepto_Dat.observacion2 "
                    + "FROM   NomAsigConcepto_Dat, NomConcepto_Dat "
                    + "WHERE  NomAsigConcepto_Dat.rifEmpresa = ?1 "
                    + "AND    NomConcepto_Dat.rifEmpresa = NomAsigConcepto_Dat.rifEmpresa "
                    + "AND    NomAsigConcepto_Dat.codConcepto = NomConcepto_Dat.codConcepto "
                    + "AND    NomAsigConcepto_Dat.codNomina = NomConcepto_Dat.codNomina "
                    + "AND    NomAsigConcepto_Dat.frecuenciaCalculo = ?2 "
                    + "AND    NomConcepto_Dat.activo = ?3 "
                    + "AND    NomAsigConcepto_Dat.activo = ?3 "
                    + "AND    NomAsigConcepto_Dat.nroTrabajador = any ( SELECT nroTrabajador "
                    + "                                                 FROM   NomTrabajador01_Dat "
                    + "                                                 WHERE  NomTrabajador01_Dat.nroTrabajador = NomAsigConcepto_Dat.nroTrabajador "
                    + "                                                 AND    NomTrabajador01_Dat.tipoNomina = NomAsigConcepto_Dat.codNomina "
                    + "                                                 AND    NomTrabajador01_Dat.status != 'R' "
                    + "                                                ) "
                    + "ORDER  BY NomAsigConcepto_Dat.codNomina, NomAsigConcepto_Dat.nroTrabajador, NomAsigConcepto_Dat.codConcepto ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, baseCalculo);
            query.setParameter(3, p_activo);
            java.util.Date fechaVigencia;
            Double valor = 0.00;
            List<Object[]> listaObj = query.getResultList();
            if (listaObj != null && !listaObj.isEmpty()) {
                String rif = null;
                Integer codNomina = 0;
                Integer nroTrabajador = 0;
                Integer codConcepto = 0;
                NomAsigConceptoDatPK nomAsigConceptoDatPK = new NomAsigConceptoDatPK();
                java.util.Date fechaInicial;
                java.util.Date fechaFinal;
                Double cantidad, monto, porcentaje, montoMin, montoMax = 0.00;
                String calcular, frecCalculo, frecPago, formaPago, codFormula, inicializable, activo, observacion1, observacion2 = null;
                for (Object[] obj : listaObj) {
                    /*
                     fechaVigencia = (java.util.Date) obj[0];
                     valor = (Double) obj[1];
                     */
                    rif = (String) obj[0];
                    codNomina = (Integer) obj[1];
                    nroTrabajador = (Integer) obj[2];
                    codConcepto = (Integer) obj[3];
                    nomAsigConceptoDatPK = new NomAsigConceptoDatPK(rif, codNomina, nroTrabajador, codConcepto);
                    fechaInicial = (java.util.Date) obj[4];
                    fechaFinal = (java.util.Date) obj[5];
                    cantidad = (Double) obj[6];
                    monto = (Double) obj[7];
                    porcentaje = (Double) obj[8];
                    montoMin = (Double) obj[9];
                    montoMax = (Double) obj[10];
                    frecCalculo = (String) obj[11];
                    calcular = (String) obj[12];
                    codFormula = (String) obj[13];
                    frecPago = (String) obj[14];
                    formaPago = (String) obj[15];
                    inicializable = (String) obj[16];
                    activo = (String) obj[17];
                    observacion1 = (String) obj[18];
                    observacion2 = (String) obj[19];
                    /*
                     this.nomAsigConceptoDatPK = nomAsigConceptoDatPK;
                     this.fechaInicial = fechaInicial;
                     this.fechaFinal = fechaFinal;
                     this.cantidad = cantidad;
                     this.monto = monto;
                     this.porcentaje = porcentaje;
                     this.montoMin = montoMin;
                     this.montoMax = montoMax;
                     this.frecuenciaCalculo = frecuenciaCalculo;
                     this.codFormula = codFormula;
                     this.frecuenciaPago = frecuenciaPago;
                     this.formaPago = formaPago;
                     this.inicializable = inicializable;
                     this.activo = activo;
                     this.observacion = observacion;
                     */
                    lista.add(new NomAsigConceptoDat(nomAsigConceptoDatPK, fechaInicial, fechaFinal, cantidad, monto, porcentaje, montoMin, montoMax, frecCalculo, calcular, codFormula, frecPago, formaPago, inicializable, activo, observacion1, observacion2));
                }  // for
            }  // if 
            //*  ---------------------------------------------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomAsigConceptoDatJpaController.getListAsigConceptos ) : " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally
        return (lista);
    } // getListaAsigConceptos()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public List<NomAsigConceptoDat> getListaAsigConceptos(String rifEmpresa, String baseCalculo, Integer codNomina) {
        final String p_activo = "S";   // S/N. 
        EntityManager em = null;
        List<NomAsigConceptoDat> lista = new ArrayList<NomAsigConceptoDat>();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY:   ----------------------------------------------
            String txtQuery = "SELECT NomAsigConcepto_Dat.rifEmpresa, NomAsigConcepto_Dat.codNomina, "
                    + "       NomAsigConcepto_Dat.nroTrabajador, NomAsigConcepto_Dat.codConcepto, "
                    + "       NomAsigConcepto_Dat.fechaInicial, NomAsigConcepto_Dat.fechaFinal, "
                    + "       NomAsigConcepto_Dat.cantidad, NomAsigConcepto_Dat.monto, "
                    + "       NomAsigConcepto_Dat.porcentaje, NomAsigConcepto_Dat.montoMin, "
                    + "       NomAsigConcepto_Dat.montoMax, NomAsigConcepto_Dat.frecuenciaCalculo, "
                    + "       NomAsigConcepto_Dat.calcular, NomAsigConcepto_Dat.codFormula, NomAsigConcepto_Dat.frecuenciaPago, "
                    + "       NomAsigConcepto_Dat.formaPago, NomAsigConcepto_Dat.inicializable, "
                    + "       NomAsigConcepto_Dat.activo, NomAsigConcepto_Dat.observacion1, NomAsigConcepto_Dat.observacion2 "
                    + "FROM   NomAsigConcepto_Dat, NomConcepto_Dat "
                    + "WHERE  NomAsigConcepto_Dat.rifEmpresa = ?1 "
                    + "AND    NomConcepto_Dat.rifEmpresa = NomAsigConcepto_Dat.rifEmpresa "
                    + "AND    NomAsigConcepto_Dat.codConcepto = NomConcepto_Dat.codConcepto "
                    + "AND    NomAsigConcepto_Dat.codNomina = ?2 "
                    + "AND    NomAsigConcepto_Dat.codNomina = NomConcepto_Dat.codNomina "
                    + "AND    NomAsigConcepto_Dat.frecuenciaCalculo = ?3 "
                    + "AND    NomConcepto_Dat.activo = ?4 "
                    + "AND    NomAsigConcepto_Dat.activo = ?4 "
                    + "AND    ( NomAsigConcepto_Dat.nroTrabajador = any ( SELECT nroTrabajador "
                    + "                                                   FROM   NomTrabajador01_Dat "
                    + "                                                   WHERE  NomTrabajador01_Dat.rifEmpresa = ?1 "
                    + "                                                   AND    NomTrabajador01_Dat.nroTrabajador = NomAsigConcepto_Dat.nroTrabajador "
                    + "                                                   AND    NomTrabajador01_Dat.tipoNomina = NomAsigConcepto_Dat.codNomina "
                    + "                                                   AND    NomTrabajador01_Dat.status != 'R' "
                    + "                                                 ) "
                    + "                      OR                           "
                    + "       NomAsigConcepto_Dat.nroTrabajador = any ( SELECT nroTrabajador "
                    + "                                                 FROM   NomTrabajador02_Dat "
                    + "                                                 WHERE  NomTrabajador02_Dat.rifEmpresa = ?1 "
                    + "                                                 AND    NomTrabajador02_Dat.nroTrabajador = NomAsigConcepto_Dat.nroTrabajador "
                    + "                                                 AND    NomTrabajador02_Dat.codNomina  = NomAsigConcepto_Dat.codNomina "
                    + "                                                ) ) "
                    + "ORDER  BY NomAsigConcepto_Dat.codNomina, NomAsigConcepto_Dat.nroTrabajador, NomAsigConcepto_Dat.codConcepto ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codNomina);
            query.setParameter(3, baseCalculo);
            query.setParameter(4, p_activo);
            java.util.Date fechaVigencia;
            Double valor = 0.00;
            List<Object[]> listaObj = query.getResultList();
            if (listaObj != null && !listaObj.isEmpty()) {
                String rif = null;
                //Integer codNomina = 0;
                Integer nroTrabajador = 0;
                Integer codConcepto = 0;
                NomAsigConceptoDatPK nomAsigConceptoDatPK = new NomAsigConceptoDatPK();
                java.util.Date fechaInicial;
                java.util.Date fechaFinal;
                Double cantidad, monto, porcentaje, montoMin, montoMax = 0.00;
                String calcular, frecCalculo, frecPago, formaPago, codFormula, inicializable, activo, observacion1, observacion2 = null;
                for (Object[] obj : listaObj) {
                    /*
                     fechaVigencia = (java.util.Date) obj[0];
                     valor = (Double) obj[1];
                     */
                    rif = (String) obj[0];
                    codNomina = (Integer) obj[1];
                    nroTrabajador = (Integer) obj[2];
                    codConcepto = (Integer) obj[3];
                    nomAsigConceptoDatPK = new NomAsigConceptoDatPK(rif, codNomina, nroTrabajador, codConcepto);
                    fechaInicial = (java.util.Date) obj[4];
                    fechaFinal = (java.util.Date) obj[5];
                    cantidad = (Double) obj[6];
                    monto = (Double) obj[7];
                    porcentaje = (Double) obj[8];
                    montoMin = (Double) obj[9];
                    montoMax = (Double) obj[10];
                    frecCalculo = (String) obj[11];
                    calcular = (String) obj[12];
                    codFormula = (String) obj[13];
                    frecPago = (String) obj[14];
                    formaPago = (String) obj[15];
                    inicializable = (String) obj[16];
                    activo = (String) obj[17];
                    observacion1 = (String) obj[18];
                    observacion2 = (String) obj[19];
                    /*
                     this.nomAsigConceptoDatPK = nomAsigConceptoDatPK;
                     this.fechaInicial = fechaInicial;
                     this.fechaFinal = fechaFinal;
                     this.cantidad = cantidad;
                     this.monto = monto;
                     this.porcentaje = porcentaje;
                     this.montoMin = montoMin;
                     this.montoMax = montoMax;
                     this.frecuenciaCalculo = frecuenciaCalculo;
                     this.codFormula = codFormula;
                     this.frecuenciaPago = frecuenciaPago;
                     this.formaPago = formaPago;
                     this.inicializable = inicializable;
                     this.activo = activo;
                     this.observacion = observacion;
                     */
                    lista.add(new NomAsigConceptoDat(nomAsigConceptoDatPK, fechaInicial, fechaFinal, cantidad, monto, porcentaje, montoMin, montoMax, frecCalculo, calcular, codFormula, frecPago, formaPago, inicializable, activo, observacion1, observacion2));
                }  // for
            }  // if 
            //*  ---------------------------------------------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomAsigConceptoDatJpaController.getListAsigConceptos ) : " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally
        return (lista);
    } // getListaAsigConceptos().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public List<Integer> getListaTrabajadoresAsig(String rifEmpresa, String baseCalculo, Integer codNomina) {
        final String p_activo = "S";   // S/N. 
        EntityManager em = null;
        List<Integer> lista = new ArrayList<Integer>();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY:   ----------------------------------------------
            String txtQuery = "SELECT  DISTINCT NomAsigConcepto_Dat.nroTrabajador "
                    + "        FROM    NomAsigConcepto_Dat, NomConcepto_Dat "
                    + "        WHERE   NomAsigConcepto_Dat.rifEmpresa = ?1 "
                    + "        AND     NomConcepto_Dat.rifEmpresa = NomAsigConcepto_Dat.rifEmpresa "
                    + "        AND     NomAsigConcepto_Dat.codConcepto = NomConcepto_Dat.codConcepto "
                    + "        AND     NomAsigConcepto_Dat.codNomina = ?2 "
                    + "        AND     NomAsigConcepto_Dat.codNomina = NomConcepto_Dat.codNomina "
                    + "        AND     NomAsigConcepto_Dat.frecuenciaCalculo = ?3 "
                    + "        AND     NomConcepto_Dat.activo = ?4 "
                    + "        AND     NomAsigConcepto_Dat.activo = ?4 "
                    + "        AND     ( NomAsigConcepto_Dat.nroTrabajador = any ( SELECT nroTrabajador "
                    + "                                                            FROM   NomTrabajador01_Dat "
                    + "                                                            WHERE  NomTrabajador01_Dat.rifEmpresa = ?1 "
                    + "                                                            AND    NomTrabajador01_Dat.nroTrabajador = NomAsigConcepto_Dat.nroTrabajador "
                    + "                                                            AND    NomTrabajador01_Dat.tipoNomina = NomAsigConcepto_Dat.codNomina "
                    + "                                                            AND    NomTrabajador01_Dat.status != 'R' "
                    + "                                                          ) "
                    + "                               OR                           "
                    + "                  NomAsigConcepto_Dat.nroTrabajador = any ( SELECT nroTrabajador "
                    + "                                                            FROM   NomTrabajador02_Dat "
                    + "                                                            WHERE  NomTrabajador02_Dat.rifEmpresa = ?1 "
                    + "                                                            AND    NomTrabajador02_Dat.nroTrabajador = NomAsigConcepto_Dat.nroTrabajador "
                    + "                                                            AND    NomTrabajador02_Dat.codNomina  = NomAsigConcepto_Dat.codNomina "
                    + "                                                           ) ) "
                    + "        ORDER  BY NomAsigConcepto_Dat.nroTrabajador ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codNomina);
            query.setParameter(3, baseCalculo);
            query.setParameter(4, p_activo);
            lista = query.getResultList();
            //if (lista != null && !lista.isEmpty()) {
            //    System.out.println("***LISTA DE fichas NO VACIA**");
            //}
            //*  ---------------------------------------------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomAsigConceptoDatJpaController.getListTrabajadoresAsig ) : " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally
        return (lista);
    } // getListaTrabajadoresAsig().  

    //--------------------------------------------------------------------------
    // Autor: HJPB.  Bqto: 10 diciembre 2020. 
    //   ** Aplicando poliformismo 
    //--------------------------------------------------------------------------
    public List<NomAsigConceptoDat> getListaAsigConceptos(String rifEmpresa, String baseCalculo, Integer codNomina, Integer ficha ) {
        final String p_activo = "S";   // S/N. 
        EntityManager em = null;
        List<NomAsigConceptoDat> lista = new ArrayList<NomAsigConceptoDat>();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            //* Ejecutar QUERY:   ----------------------------------------------
            String txtQuery = "SELECT NomAsigConcepto_Dat.rifEmpresa, NomAsigConcepto_Dat.codNomina, "
                    + "       NomAsigConcepto_Dat.nroTrabajador, NomAsigConcepto_Dat.codConcepto, "
                    + "       NomAsigConcepto_Dat.fechaInicial, NomAsigConcepto_Dat.fechaFinal, "
                    + "       NomAsigConcepto_Dat.cantidad, NomAsigConcepto_Dat.monto, "
                    + "       NomAsigConcepto_Dat.porcentaje, NomAsigConcepto_Dat.montoMin, "
                    + "       NomAsigConcepto_Dat.montoMax, NomAsigConcepto_Dat.frecuenciaCalculo, "
                    + "       NomAsigConcepto_Dat.calcular, NomAsigConcepto_Dat.codFormula, NomAsigConcepto_Dat.frecuenciaPago, "
                    + "       NomAsigConcepto_Dat.formaPago, NomAsigConcepto_Dat.inicializable, "
                    + "       NomAsigConcepto_Dat.activo, NomAsigConcepto_Dat.observacion1, NomAsigConcepto_Dat.observacion2 "
                    + "FROM   NomAsigConcepto_Dat, NomConcepto_Dat "
                    + "WHERE  NomAsigConcepto_Dat.rifEmpresa = ?1 "
                    + "AND    NomConcepto_Dat.rifEmpresa = NomAsigConcepto_Dat.rifEmpresa "
                    + "AND    NomAsigConcepto_Dat.codConcepto = NomConcepto_Dat.codConcepto "
                    + "AND    NomAsigConcepto_Dat.codNomina = ?2 "
                    + "AND    NomAsigConcepto_Dat.codNomina = NomConcepto_Dat.codNomina "
                    + "AND    NomAsigConcepto_Dat.frecuenciaCalculo = ?3 "
                    + "AND    NomConcepto_Dat.activo = ?4 "
                    + "AND    NomAsigConcepto_Dat.activo = ?4 "
                    + "AND    NomAsigConcepto_Dat.nroTrabajador = ?5 "
                    + "AND    ( NomAsigConcepto_Dat.nroTrabajador = any ( SELECT nroTrabajador "
                    + "                                                   FROM   NomTrabajador01_Dat "
                    + "                                                   WHERE  NomTrabajador01_Dat.rifEmpresa = ?1 "
                    + "                                                   AND    NomTrabajador01_Dat.nroTrabajador = NomAsigConcepto_Dat.nroTrabajador "
                    + "                                                   AND    NomTrabajador01_Dat.tipoNomina = NomAsigConcepto_Dat.codNomina "
                    + "                                                   AND    NomTrabajador01_Dat.status != 'R' "
                    + "                                                 ) "
                    + "                      OR                           "
                    + "       NomAsigConcepto_Dat.nroTrabajador = any ( SELECT nroTrabajador "
                    + "                                                 FROM   NomTrabajador02_Dat "
                    + "                                                 WHERE  NomTrabajador02_Dat.rifEmpresa = ?1 "
                    + "                                                 AND    NomTrabajador02_Dat.nroTrabajador = NomAsigConcepto_Dat.nroTrabajador "
                    + "                                                 AND    NomTrabajador02_Dat.codNomina  = NomAsigConcepto_Dat.codNomina "
                    + "                                                ) ) "
                    + "ORDER  BY NomAsigConcepto_Dat.codNomina, NomAsigConcepto_Dat.nroTrabajador, NomAsigConcepto_Dat.codConcepto ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codNomina);
            query.setParameter(3, baseCalculo);
            query.setParameter(4, p_activo);
            query.setParameter(5, ficha);
            java.util.Date fechaVigencia;
            Double valor = 0.00;
            List<Object[]> listaObj = query.getResultList();
            if (listaObj != null && !listaObj.isEmpty()) {
                String rif = null;
                //Integer codNomina = 0;
                Integer nroTrabajador = 0;
                Integer codConcepto = 0;
                NomAsigConceptoDatPK nomAsigConceptoDatPK = new NomAsigConceptoDatPK();
                java.util.Date fechaInicial;
                java.util.Date fechaFinal;
                Double cantidad, monto, porcentaje, montoMin, montoMax = 0.00;
                String calcular, frecCalculo, frecPago, formaPago, codFormula, inicializable, activo, observacion1, observacion2 = null;
                for (Object[] obj : listaObj) {
                    /*
                     fechaVigencia = (java.util.Date) obj[0];
                     valor = (Double) obj[1];
                     */
                    rif = (String) obj[0];
                    codNomina = (Integer) obj[1];
                    nroTrabajador = (Integer) obj[2];
                    codConcepto = (Integer) obj[3];
                    nomAsigConceptoDatPK = new NomAsigConceptoDatPK(rif, codNomina, nroTrabajador, codConcepto);
                    fechaInicial = (java.util.Date) obj[4];
                    fechaFinal = (java.util.Date) obj[5];
                    cantidad = (Double) obj[6];
                    monto = (Double) obj[7];
                    porcentaje = (Double) obj[8];
                    montoMin = (Double) obj[9];
                    montoMax = (Double) obj[10];
                    frecCalculo = (String) obj[11];
                    calcular = (String) obj[12];
                    codFormula = (String) obj[13];
                    frecPago = (String) obj[14];
                    formaPago = (String) obj[15];
                    inicializable = (String) obj[16];
                    activo = (String) obj[17];
                    observacion1 = (String) obj[18];
                    observacion2 = (String) obj[19];
                    /*
                     this.nomAsigConceptoDatPK = nomAsigConceptoDatPK;
                     this.fechaInicial = fechaInicial;
                     this.fechaFinal = fechaFinal;
                     this.cantidad = cantidad;
                     this.monto = monto;
                     this.porcentaje = porcentaje;
                     this.montoMin = montoMin;
                     this.montoMax = montoMax;
                     this.frecuenciaCalculo = frecuenciaCalculo;
                     this.codFormula = codFormula;
                     this.frecuenciaPago = frecuenciaPago;
                     this.formaPago = formaPago;
                     this.inicializable = inicializable;
                     this.activo = activo;
                     this.observacion = observacion;
                     */
                    lista.add(new NomAsigConceptoDat(nomAsigConceptoDatPK, fechaInicial, fechaFinal, cantidad, monto, porcentaje, montoMin, montoMax, frecCalculo, calcular, codFormula, frecPago, formaPago, inicializable, activo, observacion1, observacion2));
                }  // for
            }  // if 
            //*  ---------------------------------------------------------------
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR ( NomAsigConceptoDatJpaController.getListAsigConceptos ) : " + ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally
        return (lista);
    } // getListaAsigConceptos().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void inicializarAtributos(String rifEmpresa, Integer codNomina) {
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
            String txtQuery = "UPDATE NomAsigConcepto_Dat "
                    + "SET     cantidad = 0.00, "
                    + "        monto = 0.00, "
                    + "        porcentaje = 0.00, "
                    + "        observacion1 = '', "
                    + "        observacion2 = '' "
                    + "WHERE rifEmpresa = ?1 "
                    + "AND   codNomina = ?2 "
                    + "AND   ( inicializable IS null OR inicializable = 'S' ) ";
            Query query = em.createNativeQuery(txtQuery);
            query.setParameter(1, rifEmpresa);
            query.setParameter(2, codNomina);
            int updateCount = query.executeUpdate();
            //System.out.println("SE HAN ELIMINADO "+deletedCount+"REGISROS");
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.err.println("ERROR al ejecutar *Typed Query * :?() " + ex);
            Logger.getLogger(NomAsigConceptoDatJpaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }  // finally. 
    } // inicializarAtributos().

}
