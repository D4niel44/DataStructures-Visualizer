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
    public void graficarSVG() {
        double largo = 75;
        double ancho = elementos * 50 + 5;
        SVG svg = new SVG(largo, ancho);
        int i = 1;
        while (!cola.esVacia()) {
            T elemento = cola.saca();
            svg.rectanguloConTexto(Pareja.crearPareja(25.0 * i++, 25.0), 25.0, 25.0, ColorSVG.NEGRO,
                    elemento.toString());
            if (!cola.esVacia())
                svg.flechaSencilla(Pareja.crearPareja(25.0 * i++ + 2, 25.0 * 1.5),
                        Pareja.crearPareja(25.0 * i - 2, 25.0 * 1.5), ColorSVG.NEGRO);
        }
        svg.imprimirSVG();
    }

}