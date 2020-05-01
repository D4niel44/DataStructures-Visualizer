package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;

public abstract class DibujarArbolBinario<T> implements GraficableSVG<T> {

    protected ArbolBinario<T> arbolBinario;

    public DibujarArbolBinario(ArbolBinario<T> arbol) {
        arbolBinario = arbol;
    }

    @Override
    public void graficarSVG(double largo, double ancho) {
        if (arbolBinario.esVacia())
            return;
        SVG svg = new SVG(largo, ancho);
        double altura = arbolBinario.altura();
        double diametro = Math.min(ancho / (2 * arbolBinario.getElementos() + 2), largo / (2 * altura + 2));
        graficarAuxiliar(arbolBinario.raiz(), Pareja.crearPareja((ancho) / 2, diametro), svg, diametro / 2, ancho / 2);
        svg.imprimirSVG();
    }

    protected void graficarAuxiliar(VerticeArbolBinario<T> vertice, Pareja<Double, Double> puntoVertice, SVG svg,
            double radio, double incremento) {
        graficarAristas(vertice, puntoVertice, svg, radio, incremento);
        svg.circuloConTexto(puntoVertice, radio, ColorSVG.NEGRO, ColorSVG.BLANCO, ColorSVG.NEGRO,
                vertice.get().toString());
    }

    protected void graficarAristas(VerticeArbolBinario<T> vertice, Pareja<Double, Double> puntoVertice, SVG svg,
            double radio, double incremento) {
        if (vertice.hayIzquierdo()) {
            Pareja<Double, Double> izquierdo = Pareja.crearPareja(puntoVertice.getX() - (incremento / 2),
                    puntoVertice.getY() + (radio * 2));
            svg.linea(puntoVertice, izquierdo, ColorSVG.NEGRO);
            graficarAuxiliar(vertice.izquierdo(), izquierdo, svg, radio, incremento / 2);
        }
        if (vertice.hayDerecho()) {
            Pareja<Double, Double> derecho = Pareja.crearPareja(puntoVertice.getX() + incremento / 2,
                    puntoVertice.getY() + (radio * 2));
            svg.linea(puntoVertice, derecho, ColorSVG.NEGRO);
            graficarAuxiliar(vertice.derecho(), derecho, svg, radio, incremento / 2);
        }
    }
}