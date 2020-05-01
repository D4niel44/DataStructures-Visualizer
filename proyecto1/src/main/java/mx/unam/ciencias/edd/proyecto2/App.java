package mx.unam.ciencias.edd.proyecto2;

/**
 * Contiene el método main y se encarga de imprimir en pantalla el resultado y
 * los errores.
 */
public class App {

    /**
     * Método principal. Recibe los argumentos introducidos por el usuario crea. 
     * Ejecuta el programa para ordenar los archivos e imprime el resultado en pantalla.
     */
    public static void main(String[] args) {
        try {
            Graficador app = new Graficador(args);
            app.ejecutar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}