package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeGrafica;
import java.util.Iterator;

public class DibujarGrafica<T> implements GraficableSVG<T> {

    private Grafica<Pareja<T, Pareja<Double, Double>>> grafica;

    public DibujarGrafica(Coleccion<T> coleccion) {
        grafica = new Grafica<>();
        Iterator<T> iterador = coleccion.iterator();
        while (iterador.hasNext()) {
            Pareja<Double, Double> aux = Pareja.crearPareja(0.0, 0.0);
            Pareja<T, Pareja<Double, Double>> primerElemento = Pareja.crearPareja(iterador.next(), aux);
            if (!grafica.contiene(primerElemento))
                grafica.agrega(primerElemento);
            Pareja<Double, Double> aux2 = Pareja.crearPareja(0.0, 0.0);
            Pareja<T, Pareja<Double, Double>> segundoElemento = Pareja.crearPareja(iterador.next(), aux2);
            if (primerElemento.equals(segundoElemento))
                continue;
            if (!grafica.contiene(segundoElemento))
                grafica.agrega(segundoElemento);
            if (!grafica.sonVecinos(primerElemento, segundoElemento))
                grafica.conecta(primerElemento, segundoElemento);
        }
    }

    @Override
    public void graficarSVG(double largo, double ancho) {
        SVG svg = new SVG(largo, ancho);
        double diametroM = Math.min(largo - 20, ancho - 20);
        double radioVertice = Math.min(15, Math.PI * diametroM / (grafica.getElementos() * 2.6));
        double angulo = (2 * Math.PI) / grafica.getElementos();
        int i = 0;
        for (Pareja<T, Pareja<Double, Double>> elemento : grafica) {
            Pareja<Double, Double> puntoVertice = elemento.getY();
            VerticeGrafica<Pareja<T, Pareja<Double, Double>>> vertice = grafica.vertice(elemento);
            if (puntoVertice.getX() == 0.0)
                cambiarPunto(puntoVertice, largo, ancho, diametroM, angulo, i++);
            if (vertice.getColor() != Color.NEGRO)
                grafica.setColor(vertice, Color.NEGRO);
            for (VerticeGrafica<Pareja<T, Pareja<Double, Double>>> vecino : vertice.vecinos()) {
                Pareja<Double, Double> puntoVecino = vecino.get().getY();
                if (puntoVecino.getX() == 0.0)
                    cambiarPunto(puntoVecino, largo, ancho, diametroM, angulo, i++);
                if (vecino.getColor() != Color.NEGRO)
                    svg.linea(puntoVertice, puntoVecino, ColorSVG.NEGRO);
            }
            svg.circuloConTexto(puntoVertice, radioVertice, ColorSVG.NEGRO, ColorSVG.BLANCO, ColorSVG.NEGRO,
                    elemento.getX().toString());
        }
        svg.imprimirSVG();
    }

    private void cambiarPunto(Pareja<Double, Double> punto, double largo, double ancho, double diametro, double angulo,
            int i) {
        punto.setX((ancho / 2) + ((diametro / 2) * Math.cos(angulo * i)));
        punto.setY((largo / 2) + ((diametro / 2) * Math.sin(angulo * i)));
    }
}