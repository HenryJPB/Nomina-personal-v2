/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.libreria;

/**
 *
 * @author henrypb
 */
// *///////////////////////////////////////////////////////////////////////* //
    public class ClassArregloTokens {
        
        public enum TIPO_TOKEN {
        BLANK, CONCEPTO, DIGITO, FUNCION, NUMERO, PROMEDIO, SIMBOLO, SIGNO, OPERADOR, NO_APLICA
    }

        //private enum TIPO_TOKEN { DIGITO, SIMBOLO, SIGNO, OPERADOR } 
        private int contadorTokens;
        private String[] arregloTokens;
        private TIPO_TOKEN[] arregloTipoToken;

        public ClassArregloTokens(int contadorTokens, String[] arregloTokens, TIPO_TOKEN[] arregloTipoToken) {
            this.contadorTokens = contadorTokens;
            this.arregloTokens = arregloTokens;
            this.arregloTipoToken = arregloTipoToken;
        }

        public int getContadorTokens() {
            return contadorTokens;
        }

        public void setContadorTokens(int contadorTokens) {
            this.contadorTokens = contadorTokens;
        }

        public String[] getArregloTokens() {
            return arregloTokens;
        }

        public void setArregloTokens(String[] arregloTokens) {
            this.arregloTokens = arregloTokens;
        }

        public TIPO_TOKEN[] getArregloTipoToken() {
            return arregloTipoToken;
        }

        public void setArregloTipoToken(TIPO_TOKEN[] arregloTipoToken) {
            this.arregloTipoToken = arregloTipoToken;
        }

    }  // private class ClassArregloTokens 
