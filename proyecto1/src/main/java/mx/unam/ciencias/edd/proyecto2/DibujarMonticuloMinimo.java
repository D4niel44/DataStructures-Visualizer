package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.ComparableIndexable;
import mx.unam.ciencias.edd.Coleccion;

public class DibujarMonticuloMinimo<T extends ComparableIndexable<T>> extends DibujarArbolBinarioCompleto<T> {

    private MonticuloMinimo<T> monticulo;

    public DibujarMonticuloMinimo(Coleccion<T> coleccion) {
        super(new MonticuloMinimo<>(coleccion));
    }
}