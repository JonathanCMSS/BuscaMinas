package exceptions;

public class JuegoFinalizadoException extends Exception {
    public JuegoFinalizadoException() {
        super("El juego ha Finalizado");
    }
}
