package exceptions;

public class CoordenadaFueraDeRangoException extends Exception {
    public CoordenadaFueraDeRangoException(int fila, int columna) {
        super(String.format("Coordenadas inválidas: Fila %d, Columna %d", fila, columna));
    }
}