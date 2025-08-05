package model;

import exceptions.CasillaReveladaException;
import java.io.Serializable;

// HERENCIA APLICADA: CeldaJuego extiende ElementoJuego
public class CeldaJuego extends ElementoJuego implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean contieneMina;
    private boolean descubierta;
    private boolean marcada;
    private int minasCercanas;

    public CeldaJuego(int fila, int columna) {
        super(fila, columna);  // Llama al constructor padre
        this.contieneMina = false;
        this.descubierta = false;
        this.marcada = false;
        this.minasCercanas = 0;
    }

    // POLIMORFISMO: implementación específica del método abstracto
    @Override
    public char obtenerSimbolo() {
        if (this.marcada) return '⚑';
        if (!this.descubierta) return '■';
        if (this.contieneMina) return '☢';
        if (this.minasCercanas == 0) return ' ';
        return (char) ('0' + this.minasCercanas);
    }

    public void asignarMina() {
        this.contieneMina = true;
    }

    public void revelar() throws CasillaReveladaException {
        if (this.descubierta) {
            throw new CasillaReveladaException("Celda " + obtenerPosicion() + " ya revelada");
        }
        this.descubierta = true;
        this.marcada = false;
    }

    public void alternarMarcado() {
        if (!this.descubierta) {
            this.marcada = !this.marcada;
        }
    }

    // Método legacy para compatibilidad
    public char obtenerSimboloVisual() {
        return obtenerSimbolo();
    }

    // Método legacy para compatibilidad
    public String obtenerPosicionTexto() {
        return obtenerPosicion();
    }

    // Métodos de acceso
    public boolean tieneMina() { return contieneMina; }
    public boolean estaDescubierta() { return descubierta; }
    public boolean estaMarcada() { return marcada; }
    public int getMinasCercanas() { return minasCercanas; }
    public void setMinasCercanas(int cantidad) { this.minasCercanas = cantidad; }

    public String obtenerEstadoCompleto() {
        return String.format("Celda %s - Mina: %b | Descubierta: %b | Marcada: %b | Vecinas: %d",
                obtenerPosicion(), contieneMina, descubierta, marcada, minasCercanas);
    }

    @Override
    public String toString() {
        return String.valueOf(obtenerSimbolo());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CeldaJuego)) return false;
        CeldaJuego otra = (CeldaJuego) obj;
        return fila == otra.fila && columna == otra.columna;
    }

    @Override
    public int hashCode() {
        return 31 * fila + columna;
    }
}