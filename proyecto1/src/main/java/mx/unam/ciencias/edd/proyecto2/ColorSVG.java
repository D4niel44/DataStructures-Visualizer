package mx.unam.ciencias.edd.proyecto2;

public enum ColorSVG {
    ROJO("red"),
    AZUL("blue"),
    NEGRO("black");

    private String valor;

    private ColorSVG(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}