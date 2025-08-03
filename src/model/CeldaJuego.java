package model;

public class CeldaJuego {
    private boolean contieneMina;
    private boolean descubierta;
    private boolean marcada;
    private int minasCercanas;
    private final int posicionX;
    private final int posicionY;

    /**
     * Crea una nueva celda en la posición dada,
       posicionX coordenada horizontal (columna)
       posicionY coordenada vertical (fila)
     */
    public CeldaJuego(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.contieneMina = false;
        this.descubierta = false;
        this.marcada = false;
        this.minasCercanas = 0;
    }

    // Comportamiento principal
    public void asignarMina() {
        this.contieneMina = true;
    }

    /**
       Muestra el contenido de la celda
       CasillaReveladaException si la celda ya fue revelada
     */
    public void revelar() throws CasillaReveladaException {
        if (this.descubierta) {
            throw new CasillaReveladaException("Celda " + obtenerPosicionTexto() + " ya revelada");
        }
        this.descubierta = true;
        this.marcada = false;
    }

    /**
      Su funcion es alternar el estado de marcado de la celda
     */
    public void alternarMarcado() {
        if (!this.descubierta) {
            this.marcada = !this.marcada;
        }
    }

    /**
      Devuelve el símbolo que representa la celda,
      @return el carácter que muestra su estado actual
     */
    public char obtenerSimboloVisual() {
        if (this.marcada) return '⚑';
        if (!this.descubierta) return '■';
        if (this.contieneMina) return '☢';
        if (this.minasCercanas == 0) return ' ';
        return (char) ('0' + this.minasCercanas);
    }

    /**
      Devuelve la posición como texto,
      @return cadena con la posición
     */
    public String obtenerPosicionTexto() {
        return String.format("%c%d", 'A' + posicionY, posicionX + 1);
    }

    // Métodos de acceso
    public boolean tieneMina() { return contieneMina; }
    public boolean estaDescubierta() { return descubierta; }
    public boolean estaMarcada() { return marcada; }
    public int getMinasCercanas() { return minasCercanas; }
    public void setMinasCercanas(int cantidad) { this.minasCercanas = cantidad; }
    public int getPosicionX() { return posicionX; }
    public int getPosicionY() { return posicionY; }

    /**
      Genera información detallada del estado de la celda.
      @return cadena con todos los atributos
     */
    public String obtenerEstadoCompleto() {
        return String.format("Celda %s - Mina: %b | Descubierta: %b | Marcada: %b | Vecinas: %d",
                obtenerPosicionTexto(), contieneMina, descubierta, marcada, minasCercanas);
    }

    @Override
    public String toString() {
        return String.valueOf(obtenerSimboloVisual());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CeldaJuego)) return false;
        CeldaJuego otra = (CeldaJuego) obj;
        return posicionX == otra.posicionX && posicionY == otra.posicionY;
    }

    @Override
    public int hashCode() {
        return 31 * posicionX + posicionY;
    }
}
