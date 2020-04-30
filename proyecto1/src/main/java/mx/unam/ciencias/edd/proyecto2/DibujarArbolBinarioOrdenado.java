package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.Coleccion;

public class DibujarArbolBinarioOrdenado<T extends Comparable<T>> extends DibujarArbolBinario<T> {

    public DibujarArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(new ArbolBinarioOrdenado<>(coleccion));
    }
}