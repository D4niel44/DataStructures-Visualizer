package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.ComparableIndexable;
import mx.unam.ciencias.edd.Coleccion;

public class DibujarMonticuloMinimo<T extends ComparableIndexable<T>> implements GraficableSVG {

    private MonticuloMinimo<T> monticulo;

    public DibujarMonticuloMinimo(Coleccion<T> coleccion) {
        monticulo = new MonticuloMinimo<>(coleccion);
    }

    @Override
    public void graficarSVG(double largo, double ancho) {
        SVG svg = new SVG(largo, ancho);
        int totalElementos = monticulo.getElementos();
        double anchoRectangulo = ancho / (2 * totalElementos + 1);
        double largoRectangulo = anchoRectangulo;
        int i = 1;
        for (T elemento : monticulo) {
            svg.rectanguloConTexto(Pareja.crearPareja(anchoRectangulo * i, largoRectangulo), largoRectangulo,
                    anchoRectangulo, ColorSVG.NEGRO, elemento.toString());
            if (elemento.getIndice() * 2 + 1 >= totalElementos)
                continue;
            double xInicial = anchoRectangulo * i * 1.5;
            svg.flechaCurva(Pareja.crearPareja(xInicial + 2, largoRectangulo - 2),
                    Pareja.crearPareja(xInicial + 2 + anchoRectangulo * i, largoRectangulo - 2),
                    Pareja.crearPareja(xInicial + 2 + (anchoRectangulo * i) / 2, 1 / 2 * largoRectangulo),
                    ColorSVG.NEGRO);
            if (elemento.getIndice() * 2 + 2 >= totalElementos)
                continue;
            svg.flechaCurva(Pareja.crearPareja(xInicial + 2, largoRectangulo * 2 + 2),
                    Pareja.crearPareja(xInicial + 2 + anchoRectangulo * i, largoRectangulo * 2 + 2),
                    Pareja.crearPareja(xInicial + 2 + (anchoRectangulo * i) / 2, 5 / 2 * largoRectangulo),
                    ColorSVG.NEGRO);
        }
        svg.imprimirSVG();
    }

}