package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Color;

public class DibujarArbolRojinegro<T extends Comparable<T>> extends DibujarArbolBinario<T> {

    public DibujarArbolRojinegro(Coleccion<T> coleccion) {
        super(new ArbolRojinegro<>(coleccion));
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
        svg.circuloConTexto(puntoVertice, radio, ColorSVG.NEGRO,
                colorToColorSVG(((ArbolRojinegro<T>) arbolBinario).getColor(vertice)), ColorSVG.BLANCO,
                vertice.get().toString());
    }

    private ColorSVG colorToColorSVG(Color color) {
        if (color == Color.NEGRO)
            return ColorSVG.NEGRO;
        if (color == Color.ROJO)
            return ColorSVG.ROJO;
        return ColorSVG.BLANCO;
    }
}