package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ComparableIndexable;

/**
 * Clase que proporciona un adaptador para números enteros.
 */
public class AdaptadorEnteros implements ComparableIndexable<AdaptadorEnteros> {

    private int elemento;
    private int indice;

    /**
     * Crea un entero indexable a partir de el entero pasado como parámetro.
     * 
     * @param elemento número entero a indexar.
     */
    public AdaptadorEnteros(int elemento) {
        this.elemento = elemento;
    }

    /**
     * Compara dos elementos
     * 
     * @param otro el otro elemento.
     * @return un número menor que cero si el primer entero es menor, igual a 0 si
     *         son iguales y mator a 0 si el primero es mayor.
     */
    @Override
    public int compareTo(AdaptadorEnteros otro) {
        return elemento - otro.elemento;
    }

    /**
     * Obtiene el indice que ocupa el elemento.
     * @return el indice del elemento.
     */
    @Override
    public int getIndice() {
        return indice;
    }

    /**
     * Modifica el indice del elemento.
     * @param indice Nuevo indice del elemento.
     */
    @Override
    public void setIndice(int indice) {
        this.indice = indice;
    }

}