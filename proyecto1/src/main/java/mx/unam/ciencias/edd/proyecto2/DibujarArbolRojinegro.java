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
            double radio, double incremento) {
        graficarAristas(vertice, puntoVertice, svg, radio, incremento);
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