package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override
        public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override
        public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            vecinos = new Lista<Vertice>();
            color = Color.NINGUNO;
        }

        /* Regresa el elemento del vértice. */
        @Override
        public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override
        public int getGrado() {
            return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override
        public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override
        public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es igual
     * al número de vértices.
     * 
     * @return el número de elementos en la gráfica.
     */
    @Override
    public int getElementos() {
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * 
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento es <code>null</code> o ya
     *                                  había sido agregado a la gráfica.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("La gráfica no acepta elementos vacíos.");
        if (this.contiene(elemento))
            throw new IllegalArgumentException("La gráfica ya contiene el elemento.");
        vertices.agregaFinal(new Vertice(elemento));
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la gráfica.
     * El peso de la arista que conecte a los elementos será 1.
     * 
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *                                  igual a b.
     */
    public void conecta(T a, T b) {
        Vertice va = busquedaVertice(a);
        Vertice vb = busquedaVertice(b);
        if (va == null)
            throw new NoSuchElementException("El elemento a no se encuentra en la gráfica.");
        if (vb == null)
            throw new NoSuchElementException("El elemento b no se encuentra en la gráfica.");
        if (va == vb)
            throw new IllegalArgumentException("El elemento a es igual al elemento b.");
        if (sonVecinos(a, b))
            throw new IllegalArgumentException("Los elementos a y b ya están conectados.");
        va.vecinos.agregaFinal(vb);
        vb.vecinos.agregaFinal(va);
        aristas++;
    }

    /**
     * Busca el vértice que contiene al elemento dado.
     * 
     * @param elemento Elemento a buscar.
     * @return Vertice que contiene al elemento buscado o null si el elemento no se
     *         encuentra en la gráfica.
     */
    private Vertice busquedaVertice(T elemento) {
        for (Vertice v : vertices)
            if (v.elemento.equals(elemento))
                return v;
        return null;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * 
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException   si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        Vertice va = busquedaVertice(a);
        Vertice vb = busquedaVertice(b);
        if (va == null)
            throw new NoSuchElementException("El elemento a no se encuentra en la gráfica.");
        if (vb == null)
            throw new NoSuchElementException("El elemento b no se encuentra en la gráfica.");
        if (!sonVecinos(a, b))
            throw new IllegalArgumentException("Los elementos a y b ya están conectados.");
        va.vecinos.elimina(vb);
        vb.vecinos.elimina(va);
        aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * 
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        return busquedaVertice(elemento) != null;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido en
     * la gráfica.
     * 
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *                                gráfica.
     */
    @Override
    public void elimina(T elemento) {
        Vertice v = busquedaVertice(elemento);
        if (v == null)
            throw new NoSuchElementException("El elemento no está contenido en la gráfica.");
        vertices.elimina(v);
        for (Vertice vecino : v.vecinos) {
            vecino.vecinos.elimina(v);
            aristas--;
        }
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos deben
     * estar en la gráfica.
     * 
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro
     *         caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        Vertice va = busquedaVertice(a);
        Vertice vb = busquedaVertice(b);
        if (va == null)
            throw new NoSuchElementException("El elemento a no se encuentra en la gráfica.");
        if (vb == null)
            throw new NoSuchElementException("El elemento b no se encuentra en la gráfica.");
        return va.vecinos.contiene(vb);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * 
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        Vertice vertice = busquedaVertice(elemento);
        if (vertice == null)
            throw new NoSuchElementException("El elemento no se encuentra en la gráfica.");
        return vertice;
    }

    /**
     * Define el color del vértice recibido.
     * 
     * @param vertice el vértice al que queremos definirle el color.
     * @param color   el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice.getClass() != Vertice.class)
            throw new IllegalArgumentException("El vertice no es instancia de la clase correcta.");
        ((Vertice) vertice).color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * 
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en otro
     *         caso.
     */
    public boolean esConexa() {
        dfs(vertices.getPrimero().elemento, (VerticeGrafica<T> v) -> {
        });
        for (Vertice v : vertices)
            if (v.color == Color.ROJO)
                return false;
        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en el
     * orden en que fueron agregados.
     * 
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for (Vertice vertice : vertices)
            accion.actua(vertice);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el orden
     * determinado por BFS, comenzando por el vértice correspondiente al elemento
     * recibido. Al terminar el método, todos los vértices tendrán color
     * {@link Color#NINGUNO}.
     * 
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *                 recorrido.
     * @param accion   la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        Vertice v = busquedaVertice(elemento);
        if (v == null)
            throw new NoSuchElementException("El elemento no se encuentra contenido en la gráfica.");
        for (Vertice vertice : vertices)
            vertice.color = Color.ROJO;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(v);
        v.color = Color.NINGUNO;
        while (!cola.esVacia()) {
            v = cola.saca();
            accion.actua(v);
            for (Vertice vecino : v.vecinos)
                if (vecino.color == Color.ROJO) {
                    vecino.color = Color.NINGUNO;
                    cola.mete(vecino);
                }
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el orden
     * determinado por DFS, comenzando por el vértice correspondiente al elemento
     * recibido. Al terminar el método, todos los vértices tendrán color
     * {@link Color#NINGUNO}.
     * 
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *                 recorrido.
     * @param accion   la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        Vertice v = busquedaVertice(elemento);
        if (v == null)
            throw new NoSuchElementException("El elemento no se encuentra contenido en la gráfica.");
        for (Vertice vertice : vertices)
            vertice.color = Color.ROJO;
        Pila<Vertice> pila = new Pila<>();
        pila.mete(v);
        v.color = Color.NINGUNO;
        while (!pila.esVacia()) {
            v = pila.saca();
            accion.actua(v);
            for (Vertice vecino : v.vecinos)
                if (vecino.color == Color.ROJO) {
                    vecino.color = Color.NINGUNO;
                    pila.mete(vecino);
                }
        }
    }

    /**
     * Nos dice si la gráfica es vacía.
     * 
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en otro
     *         caso.
     */
    @Override
    public boolean esVacia() {
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override
    public void limpia() {
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * 
     * @return una representación en cadena de la gráfica.
     */
    @Override
    public String toString() {
        String s = "{";
        for (Vertice v : vertices) {
            s += v.elemento + ", ";
            v.color = Color.ROJO;
        }
        s += "}, {";
        for (Vertice v : vertices) {
            v.color = Color.NEGRO;
            for (Vertice vecino : v.vecinos)
                if (vecino.color != Color.NEGRO)
                    s += "(" + v.elemento + ", " + vecino.elemento + "), ";
        }
        return s + "}";
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Grafica<T> grafica = (Grafica<T>) objeto;
        if (this.vertices.getElementos() != grafica.vertices.getElementos() || this.aristas != grafica.aristas)
            return false;
        for (Vertice v : this.vertices) {
            Vertice v2 = grafica.busquedaVertice(v.elemento);
            if (v2 == null)
                return false;
            for (Vertice vecino : v.vecinos) {
                boolean a = false;
                for (Vertice vecino2 : v2.vecinos)
                    if (vecino.elemento.equals(vecino2.elemento)) {
                        a = true;
                        break;
                    }
                if (!a)
                    return false;
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el orden
     * en que fueron agregados sus elementos.
     * 
     * @return un iterador para iterar la gráfica.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
}
