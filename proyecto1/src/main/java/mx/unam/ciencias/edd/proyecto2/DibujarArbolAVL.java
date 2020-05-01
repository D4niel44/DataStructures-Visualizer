package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Coleccion;

public class DibujarArbolAVL<T extends Comparable<T>> extends DibujarArbolBinario<T> {

    public DibujarArbolAVL(Coleccion<T> coleccion) {
        super(new ArbolAVL<>(coleccion));
    }

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
            svg.texto(Pareja.crearPareja(puntoVertice.getX() - radio, puntoVertice.getY() - radio * 1.25 - 2),
                    ColorSVG.NEGRO, radio / 2, String.format("{%d/%d}", altura, alturaI - alturaD));
        else
            svg.texto(Pareja.crearPareja(puntoVertice.getX() + radio, puntoVertice.getY() - radio * 1.25 - 2),
                    ColorSVG.NEGRO, radio * 3/5, String.format("{%d/%d}", altura, alturaI - alturaD));
    }
}