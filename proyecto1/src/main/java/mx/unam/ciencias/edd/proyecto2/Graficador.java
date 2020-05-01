package mx.unam.ciencias.edd.proyecto2;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Coleccion;

public class Graficador {

    private Lista<Integer> elementos;
    private String claseGraficar;

    public Graficador(String[] args) {
        if (args.length > 1) {
            /* excepcion argumentos no validos. */}
        elementos = new Lista<Integer>();
        claseGraficar = null;
        leerArchivo((args.length == 0) ? null : args[0]);
    }

    public void ejecutar() {
        double largo = 0, ancho = 0;
        GraficableSVG estructura = null;
        if (claseGraficar == null)
            throw new ExcepcionArgumentoInvalido("No se ha especificado la estructura de Datos a graficar.");
        if (elementos.esVacia())
            throw new ExcepcionArgumentoInvalido("La estructura a graficar debe tener elementos.");
        switch (claseGraficar) {
            case "Lista":
                estructura = new DibujarLista<>(elementos);
                largo = 150;
                ancho = Math.min(elementos.getElementos() * 50, 1000);
                break;
            case "Cola":
                estructura = new DibujarCola<>(elementos);
                largo = 150;
                ancho = Math.min(elementos.getElementos() * 50, 1000);
                break;
            case "Pila":
                estructura = new DibujarPila<>(elementos);
                largo = Math.min(elementos.getElementos() * 50, 1000);
                ancho = 150;
                break;
            case "ArbolBinarioOrdenado":
                estructura = new DibujarArbolBinarioOrdenado<>(elementos);
                ancho = Math.min(elementos.getElementos() * 50, 1000);
                largo = ancho;
                break;
            case "ArbolBinarioCompleto":
                estructura = new DibujarArbolBinarioCompleto<>(elementos);
                ancho = Math.min(elementos.getElementos() * 50, 1000);
                largo = ancho;
                break;
            case "ArbolRojinegro":
                estructura = new DibujarArbolRojinegro<>(elementos);
                ancho = Math.min(elementos.getElementos() * 50, 1000);
                largo = ancho;
                break;
            case "ArbolAVL":
                estructura = new DibujarArbolAVL<>(elementos);
                ancho = Math.min(elementos.getElementos() * 50, 1000);
                largo = ancho;
                break;
            case "Grafica":
                if (elementos.getElementos() % 2 != 0)
                    throw new ExcepcionArgumentoInvalido("Las gráficas deben recibir un número par de elementos.");
                estructura = new DibujarGrafica<>(elementos);
                ancho = Math.min(elementos.getElementos() * 50, 1000);
                largo = ancho;
                break;
            case "MonticuloMinimo":
                Coleccion<AdaptadorEnteros> coleccion = new Lista<>();
                for (Integer elemento : elementos)
                    coleccion.agrega(new AdaptadorEnteros(elemento));
                estructura = new DibujarMonticuloMinimo<>(coleccion);
                ancho = Math.min(elementos.getElementos() * 50, 1000);
                largo = ancho;
                break;
            default:
                throw new ExcepcionArgumentoInvalido("La clase " + claseGraficar + " no es una clase válida.");
        }
        estructura.graficarSVG(largo, ancho);
    }

    /**
     * Lee un archivo del sistema. Si se le pasa una cadena null crea un Reader a
     * partir de la entrada estándar
     */
    private void leerArchivo(String rutaArchivo) {
        BufferedReader bufer = null;
        try {
            if (rutaArchivo == null)
                bufer = new BufferedReader(new InputStreamReader(System.in));
            else
                bufer = new BufferedReader(new FileReader(new File(rutaArchivo)));
        } catch (FileNotFoundException e) {
            throw new ExcepcionArchivoNoEncontrado("No se pudo leer: " + e.getMessage(), e);
        }
        try {
            procesarArchivo(bufer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void procesarArchivo(BufferedReader bufer) throws IOException {
        boolean bool = true;
        do {
            StringBuilder cadena = new StringBuilder();
            int c = bufer.read();
            // si c es -1 entonces se termino de leer el archivo por lo que se termina el
            // ciclo
            if (c == -1) {
                bool = false;
            } else if (c == 0x0023) { // si c es # se descartan todos los caracteres hasta la siguiente linea.
                while (bool) {
                    c = bufer.read();
                    // si c es -1 entonces se termino de leer el archivo por lo que se termina el
                    // ciclo
                    if (c == -1)
                        bool = false;
                    // Si se encuentra alguno de los caracteres de fin de linea se rompe le ciclo y
                    // se continua leyendo normalmente.
                    else if (c == 0x000A || c == 0x000B || c == 0x000C || c == 0x000D || c == 0x0085 || c == 0x2028
                            || c == 0x2029)
                        break;
                }
            } else if (Character.isWhitespace(c)) { // Si se encuentra con un espacio o un caracter de fin de linea lo
                                                    // ignora.
                continue;
            } else { // Si no cayo en un caso anterior es porque el caracter es un caracter que debe
                     // ser procesado
                cadena.append((char) c);
                while (bool) {
                    c = bufer.read();
                    if (c == -1)
                        bool = false;
                    else if (Character.isWhitespace(c) || c == 0x0023)
                        break;
                    cadena.append((char) c);
                }
                if (claseGraficar == null)
                    claseGraficar = cadena.toString();
                else
                    try {
                        elementos.agregaFinal(Integer.parseInt(cadena.toString()));
                    } catch (NumberFormatException e) {
                        // Excepcion cadena no valida.
                    }
            }
        } while (bool);
    }

}