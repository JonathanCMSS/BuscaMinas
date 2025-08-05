package model;

/**
 * Clase base abstracta para elementos del juego (aplicación de herencia)
 */
import java.io.Serializable;

public abstract class ElementoJuego implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int fila;
    protected int columna;

    public ElementoJuego(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public abstract char obtenerSimbolo();

    public String obtenerPosicion() {
        return String.format("%c%d", 'A' + fila, columna + 1);
    }

    // Getters
    public int getFila() { return fila; }
    public int getColumna() { return columna; }
}