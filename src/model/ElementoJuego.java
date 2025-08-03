package model;

/**
 * Clase base abstracta para elementos del juego (aplicación de herencia)
 */
public abstract class ElementoJuego {
    protected final int fila;
    protected final int columna;

    public ElementoJuego(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    // Método abstracto - polimorfismo
    public abstract char obtenerSimbolo();

    // Método común heredado
    public String obtenerPosicion() {
        return String.format("%c%d", 'A' + fila, columna + 1);
    }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }
}