package model;

import exceptions.CasillaReveladaException;
import exceptions.CoordenadaFueraDeRangoException;
import exceptions.JuegoFinalizadoException;

import java.io.Serializable;
import java.util.Random;

public class TableroBuscaminas implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int FILAS = 10;
    private static final int COLUMNAS = 10;
    private static final int TOTAL_MINAS = 15;

    private CeldaJuego[][] celdas;
    private int minasRestantes;
    private boolean juegoFinalizado;
    private boolean victoria;

    // Marcamos como transient para evitar problemas de serialización
    private transient Random generador;

    public TableroBuscaminas() {
        this.celdas = new CeldaJuego[FILAS][COLUMNAS];
        this.minasRestantes = TOTAL_MINAS;
        this.generador = new Random();
        inicializarTablero();
        distribuirMinas();
        calcularMinasAlrededor();
    }

    private void inicializarTablero() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                celdas[fila][columna] = new CeldaJuego(fila, columna);
            }
        }
    }

    private void distribuirMinas() {
        int minasColocadas = 0;
        while (minasColocadas < TOTAL_MINAS) {
            int fila = generador.nextInt(FILAS);
            int columna = generador.nextInt(COLUMNAS);

            if (!celdas[fila][columna].tieneMina()) {
                celdas[fila][columna].asignarMina();
                minasColocadas++;
            }
        }
    }

    private void calcularMinasAlrededor() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (!celdas[fila][columna].tieneMina()) {
                    int minas = contarMinasVecinas(fila, columna);
                    celdas[fila][columna].setMinasCercanas(minas);
                }
            }
        }
    }

    private int contarMinasVecinas(int fila, int columna) {
        int contador = 0;
        for (int i = Math.max(0, fila-1); i <= Math.min(FILAS-1, fila+1); i++) {
            for (int j = Math.max(0, columna-1); j <= Math.min(COLUMNAS-1, columna+1); j++) {
                if (celdas[i][j].tieneMina()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public boolean descubrirCelda(int fila, int columna) throws JuegoFinalizadoException, CoordenadaFueraDeRangoException {
        if (juegoFinalizado) {
            throw new JuegoFinalizadoException();
        }

        if (!esCoordenadaValida(fila, columna)) {
            throw new CoordenadaFueraDeRangoException(fila, columna);
        }

        CeldaJuego celda = celdas[fila][columna];

        if (celda.estaDescubierta()) {
            return true;
        }

        try {
            celda.revelar();
        } catch (CasillaReveladaException e) {
            return true;
        }

        if (celda.tieneMina()) {
            revelarTodasMinas();
            juegoFinalizado = true;
            return false;
        }

        if (celda.getMinasCercanas() == 0) {
            expandirZonaSegura(fila, columna);
        }

        if (verificarVictoria()) {
            juegoFinalizado = true;
            victoria = true;
        }

        return true;
    }

    private void expandirZonaSegura(int fila, int columna) {
        for (int i = Math.max(0, fila-1); i <= Math.min(FILAS-1, fila+1); i++) {
            for (int j = Math.max(0, columna-1); j <= Math.min(COLUMNAS-1, columna+1); j++) {
                if (!celdas[i][j].estaDescubierta() && !celdas[i][j].tieneMina()) {
                    try {
                        descubrirCelda(i, j);
                    } catch (Exception e) {
                        // Ignorar excepciones durante la expansión
                    }
                }
            }
        }
    }

    private void revelarTodasMinas() {
        for (CeldaJuego[] fila : celdas) {
            for (CeldaJuego celda : fila) {
                if (celda.tieneMina()) {
                    try {
                        celda.revelar();
                    } catch (CasillaReveladaException e) {
                        // Ignorar excepciones
                    }
                }
            }
        }
    }

    private boolean verificarVictoria() {
        for (CeldaJuego[] fila : celdas) {
            for (CeldaJuego celda : fila) {
                if (!celda.tieneMina() && !celda.estaDescubierta()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void marcarCelda(int fila, int columna) throws JuegoFinalizadoException, CoordenadaFueraDeRangoException {
        if (juegoFinalizado) {
            throw new JuegoFinalizadoException();
        }

        if (!esCoordenadaValida(fila, columna)) {
            throw new CoordenadaFueraDeRangoException(fila, columna);
        }

        CeldaJuego celda = celdas[fila][columna];
        boolean estabaMarcada = celda.estaMarcada();

        celda.alternarMarcado();

        // ACTUALIZAR CONTADOR DE MINAS RESTANTES
        if (!estabaMarcada && celda.estaMarcada()) {
            minasRestantes--; // Se marcó una celda
        } else if (estabaMarcada && !celda.estaMarcada()) {
            minasRestantes++; // Se desmarcó una celda
        }
    }

    private boolean esCoordenadaValida(int fila, int columna) {
        return fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS;
    }

    // Método para reinicializar el generador después de la deserialización
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.generador = new Random(); // Reinicializar el generador Random
    }

    // Getters
    public CeldaJuego[][] getCeldas() { return celdas; }
    public boolean isJuegoFinalizado() { return juegoFinalizado; }
    public boolean isVictoria() { return victoria; }
    public int getMinasRestantes() { return minasRestantes; }
    public static int getFilas() { return FILAS; }
    public static int getColumnas() { return COLUMNAS; }
}