package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;

public class DibujarLista<T> implements GraficableSVG {

    private Lista<T> lista;

    /**
     * Crea una lista dibujable en un SVG a partir de la lista pasada com parámetro.
     * 
     * @param lista Lista a dibujar.
     */
    public DibujarLista(Lista<T> lista) {
        this.lista = lista;
    }

    /**
     * Genera un SVG con una representación gráfica de la lista y lo imprime en la
     * salida estándar.
     * 
     * @param largo largo del SVG a generar.
     * @param ancho ancho del SVG a generar.
     */
    @Override
    public void graficarSVG(double largo, double ancho) {
        SVG svg = new SVG(largo, ancho);
        int totalElementos = lista.getElementos();
        double anchoRectangulo = ancho / (2 * totalElementos + 1);
        double largoRectangulo = anchoRectangulo;
        int i = 1;
        Iterator<T> iterador = lista.iterator();
        while (iterador.hasNext()) {
            T elemento = iterador.next();
            svg.rectanguloConTexto(Pareja.crearPareja(anchoRectangulo * i++, largoRectangulo), largoRectangulo,
                    anchoRectangulo, ColorSVG.NEGRO, elemento.toString());
            if (iterador.hasNext())
                svg.flechaDoble(Pareja.crearPareja(anchoRectangulo * i++ + 2, largoRectangulo * 1.5),
                        Pareja.crearPareja(anchoRectangulo * i - 2, largoRectangulo * 1.5), ColorSVG.NEGRO);
        }
        svg.imprimirSVG();
    }
}