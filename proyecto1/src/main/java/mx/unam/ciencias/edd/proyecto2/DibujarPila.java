package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Coleccion;

public class DibujarPila<T> implements GraficableSVG<T> {
    
    private Pila<T> pila;
    private int elementos;
    
    public DibujarPila(Coleccion<T> coleccion) {
        pila = new Pila<T>();
        elementos = coleccion.getElementos();
        for (T elemento : coleccion)
            pila.mete(elemento);
    }

    @Override
    public void graficarSVG(double largo, double ancho) {
        SVG svg = new SVG(largo, ancho);
        double largoRectangulo = largo / (elementos + 2);
        double anchoRectangulo = largoRectangulo;
        int i = 1;
        while (!pila.esVacia()) {
            T elemento = pila.saca();
            svg.rectanguloConTexto(Pareja.crearPareja(anchoRectangulo, largoRectangulo * i++), largoRectangulo,
                    anchoRectangulo, ColorSVG.NEGRO, elemento.toString());
        }
        svg.imprimirSVG();
    }
}