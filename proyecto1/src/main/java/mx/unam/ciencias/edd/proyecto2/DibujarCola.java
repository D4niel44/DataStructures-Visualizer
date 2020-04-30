package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Coleccion;

public class DibujarCola<T> implements GraficableSVG {

    private Cola<T> cola;
    private int elementos;

    public DibujarCola(Coleccion<T> coleccion) {
        cola = new Cola<T>();
        elementos = coleccion.getElementos();
        for (T elemento : coleccion)
            cola.mete(elemento);
    }

    @Override
    public void graficarSVG(double largo, double ancho) {
        SVG svg = new SVG(largo, ancho);
        double anchoRectangulo = ancho / (2 * elementos + 1);
        double largoRectangulo = anchoRectangulo;
        int i = 1;
        double anchoAux = anchoRectangulo;
        svg.flechaSencilla(Pareja.crearPareja(5.0, largoRectangulo * 1.5),
                    Pareja.crearPareja(anchoAux * i - 2, largoRectangulo * 1.5), ColorSVG.NEGRO);
        while (!cola.esVacia()) {
            T elemento = cola.saca();
            svg.rectanguloConTexto(Pareja.crearPareja(anchoAux * i++, largoRectangulo), largoRectangulo,
                    anchoRectangulo, ColorSVG.NEGRO, elemento.toString());
            svg.flechaSencilla(Pareja.crearPareja(anchoAux * i++ + 2, largoRectangulo * 1.5),
                    Pareja.crearPareja(anchoAux * i - 2, largoRectangulo * 1.5), ColorSVG.NEGRO);
        }
        svg.imprimirSVG();
    }

}