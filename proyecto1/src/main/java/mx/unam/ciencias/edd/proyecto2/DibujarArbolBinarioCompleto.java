package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.Coleccion;

public class DibujarArbolBinarioCompleto<T> extends DibujarArbolBinario<T> {

    public DibujarArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(new ArbolBinarioCompleto<>(coleccion));
    }
}