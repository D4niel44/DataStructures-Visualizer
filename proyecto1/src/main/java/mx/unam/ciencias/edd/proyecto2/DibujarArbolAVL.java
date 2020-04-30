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
            double radio) {
        if (vertice.hayIzquierdo()) {
            Pareja<Double, Double> izquierdo = Pareja.crearPareja(puntoVertice.getX() / 2,
                    puntoVertice.getY() + (radio * 2));
            svg.linea(puntoVertice, izquierdo, ColorSVG.NEGRO);
            graficarAuxiliar(vertice.izquierdo(), izquierdo, svg, radio);
        }
        if (vertice.hayDerecho()) {
            Pareja<Double, Double> derecho = Pareja.crearPareja(puntoVertice.getX() * 1.5,
                    puntoVertice.getY() + (radio * 2));
            svg.linea(puntoVertice, derecho, ColorSVG.NEGRO);
            graficarAuxiliar(vertice.izquierdo(), derecho, svg, radio);
        }
        svg.circuloConTexto(puntoVertice, radio, ColorSVG.NEGRO, ColorSVG.NEGRO, ColorSVG.BLANCO,
                vertice.get().toString());
        int altura = vertice.altura();
        int alturaI = (vertice.hayIzquierdo()) ? vertice.izquierdo().altura() : -1;
        int alturaD = (vertice.hayDerecho()) ? vertice.derecho().altura() : -1;
        if (!vertice.hayPadre())
            svg.texto(Pareja.crearPareja(puntoVertice.getX(), puntoVertice.getY() - radio * 1.25 - 2), ColorSVG.NEGRO,
                    radio / 2, String.format("{%d/%d}", altura, alturaI - alturaD));
        else if (vertice.padre().izquierdo() == vertice)
            svg.texto(Pareja.crearPareja(puntoVertice.getX() - radio, puntoVertice.getY() - radio * 1.25 - 2),
                    ColorSVG.NEGRO, radio / 2, String.format("{%d/%d}", altura, alturaI - alturaD));
        else
            svg.texto(Pareja.crearPareja(puntoVertice.getX() + radio, puntoVertice.getY() - radio * 1.25 - 2),
                    ColorSVG.NEGRO, radio / 2, String.format("{%d/%d}", altura, alturaI - alturaD));
    }
}