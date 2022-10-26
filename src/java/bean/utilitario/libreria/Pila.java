/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.libreria;

import static org.zkoss.zk.ui.util.Clients.alert;

/**
 *
 * @author henrypb
 */
//*-////////////////////////////////////////////////////////////////////////
public class Pila {

    final int TOPE = 80;

    Object[] pila = new Object[TOPE];
    Integer contador = -1;

    //----------------------------------------------------------------------
    public Boolean vacia() {
        return (contador < 0);
    }  // stackVacia()

    //----------------------------------------------------------------------
    public void push(Object o) {
        if (contador < TOPE) {
            pila[++contador] = o;
        } else {
            alert("STACK OVERFLOW");
        }
    } // push(String token)

    //----------------------------------------------------------------------
    public Object pop() {
        if (vacia()) {
            return null;
        } else {
            return pila[contador--];
        }
    }

    //----------------------------------------------------------------------
    public Object tope() {
        if (vacia()) {
            return null;
        } else {
            return pila[contador];
        }
    }

    //----------------------------------------------------------------------
    public Integer longitud() {
        return (contador + 1);
    }

}  // class Pila {}
