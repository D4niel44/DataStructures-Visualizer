package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Coleccion;

/**
 * Clase para dibujar árboles AVL y representarlos en formato SVG.
 * @param <T> Tipo del árbol.
 */
public class DibujarArbolAVL<T extends Comparable<T>> extends DibujarArbolBinario<T> {

    /**
     * Crea un árbol AVL dibujable.
     * @param coleccion Colección con los elementos del árbol AVL.
     */
    public DibujarArbolAVL(Coleccion<T> coleccion) {
        super(new ArbolAVL<>(coleccion));
    }

    /*Sobreescribe el método de la clase padre para añadir la altura y a¿balance a los árboles AVL.*/ 
    @Override
    protected void graficarAuxiliar(VerticeArbolBinario<T> vertice, Pareja<Double, Double> puntoVertice, SVG svg,
            double radio, double incremento) {
        graficarAristas(vertice, puntoVertice, svg, radio, incremento);
        svg.circuloConTexto(puntoVertice, radio, ColorSVG.NEGRO, ColorSVG.BLANCO, ColorSVG.NEGRO,
                vertice.get().toString());
        int altura = vertice.altura();
        int alturaI = (vertice.hayIzquierdo()) ? vertice.izquierdo().altura() : -1;
        int alturaD = (vertice.hayDerecho()) ? vertice.derecho().altura() : -1;
        if (!vertice.hayPadre())
            svg.texto(Pareja.crearPareja(puntoVertice.getX(), puntoVertice.getY() - radio * 1.25 - 2), ColorSVG.NEGRO,
                    radio / 2, String.format("{%d/%d}", altura, alturaI - alturaD));
        else if (vertice.padre().hayIzquierdo() && vertice.padre().izquierdo() == vertice)
            svg.texto(Pareja.crearPareja(puntoVertice.getX() - (radio / 3), puntoVertice.getY() - radio * 1.25 - 2),
                    ColorSVG.NEGRO, radio / 2, String.format("{%d/%d}", altura, alturaI - alturaD));
        else
            svg.texto(Pareja.crearPareja(puntoVertice.getX() + (radio / 3), puntoVertice.getY() - radio * 1.25 - 2),
                    ColorSVG.NEGRO, radio * 3/5, String.format("{%d/%d}", altura, alturaI - alturaD));
    }
}