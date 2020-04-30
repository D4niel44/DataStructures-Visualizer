package mx.unam.ciencias.edd.proyecto2;

public enum ColorSVG {
    ROJO("red"),
    AZUL("blue"),
    NEGRO("black"),
    BLANCO("white");

    private String valor;

    private ColorSVG(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}