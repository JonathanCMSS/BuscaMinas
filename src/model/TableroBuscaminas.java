package model;

import exceptions.CasillaReveladaException;
import exceptions.CoordenadaFueraDeRangoException;
import exceptions.JuegoFinalizadoException;

import java.util.Random;

/**
 * Clase que gestiona la lógica principal del tablero del Buscaminas.
 * Controla la colocación de minas, descubrimiento de celdas y estado del juego.
 */
public class TableroBuscaminas {
    // Constantes para la configuración del juego
    private static final int FILAS = 10;          // Número de filas del tablero
    private static final int COLUMNAS = 10;       // Número de columnas del tablero
    private static final int TOTAL_MINAS = 15;    // Número total de minas en el tablero

    // Matriz que representa las celdas del juego
    private CeldaJuego[][] celdas;
    // Contador de minas restantes por marcar
    private int minasRestantes;
    // Estado del juego
    private boolean juegoFinalizado;
    // Indica si el jugador ganó
    private boolean victoria;
    // Generador de números aleatorios para colocar minas
    private Random generador;

    /**
      Constructor del tablero de Buscaminas.
      Inicializa el tablero, coloca las minas y calcula las minas adyacentes.
     */
    public TableroBuscaminas() {
        this.celdas = new CeldaJuego[FILAS][COLUMNAS];
        this.minasRestantes = TOTAL_MINAS;
        this.generador = new Random();
        inicializarTablero();      // Crea todas las celdas vacías
        distribuirMinas();         // Coloca las minas aleatoriamente
        calcularMinasAlrededor();  // Calcula minas adyacentes para cada celda
    }

    /**
      Inicializa todas las celdas del tablero.
     */
    private void inicializarTablero() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                celdas[fila][columna] = new CeldaJuego(fila, columna);
            }
        }
    }

    /**
     * Distribuye aleatoriamente las minas en el tablero.
     */
    private void distribuirMinas() {
        int minasColocadas = 0;
        while (minasColocadas < TOTAL_MINAS) {
            // Genera coordenadas aleatorias
            int fila = generador.nextInt(FILAS);
            int columna = generador.nextInt(COLUMNAS);

            // Si la celda no tiene mina, coloca una
            if (!celdas[fila][columna].tieneMina()) {
                celdas[fila][columna].asignarMina();
                minasColocadas++;
            }
        }
    }

    /**
     * Calcula el número de minas adyacentes para cada celda que no tiene mina.
     */
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

    /**
     * Cuenta las minas en las celdas vecinas a una posición dada
     */
    private int contarMinasVecinas(int fila, int columna) {
        int contador = 0;
        // Recorre las 8 celdas vecinas (incluyendo diagonales)
        for (int i = Math.max(0, fila-1); i <= Math.min(FILAS-1, fila+1); i++) {
            for (int j = Math.max(0, columna-1); j <= Math.min(COLUMNAS-1, columna+1); j++) {
                if (celdas[i][j].tieneMina()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Intenta descubrir una celda en el tablero.
     */
    public boolean descubrirCelda(int fila, int columna) throws JuegoFinalizadoException, CoordenadaFueraDeRangoException {
        // Verificar estado del juego
        if (juegoFinalizado) {
            throw new JuegoFinalizadoException();
        }

        // Validar coordenadas
        if (!esCoordenadaValida(fila, columna)) {
            throw new CoordenadaFueraDeRangoException(fila, columna);
        }

        CeldaJuego celda = celdas[fila][columna];

        // Si la celda ya está descubierta, no hacer nada
        if (celda.estaDescubierta()) {
            return true;
        }

        try {
            celda.revelar();  // Revelar la celda
        } catch (CasillaReveladaException e) {
            return true;
        }

        // Si la celda tiene mina, terminar el juego
        if (celda.tieneMina()) {
            revelarTodasMinas();  // Mostrar todas las minas
            juegoFinalizado = true;
            return false;
        }

        // Si es una celda segura sin minas alrededor, expandir
        if (celda.getMinasCercanas() == 0) {
            expandirZonaSegura(fila, columna);
        }

        // Verificar si el jugador ganó
        if (verificarVictoria()) {
            juegoFinalizado = true;
            victoria = true;
        }

        return true;
    }

    /**
     * Expande el área de celdas seguras cuando se descubre una celda con 0 minas alrededor.
       @param fila Fila de la celda central
       @param columna Columna de la celda central
     */
    private void expandirZonaSegura(int fila, int columna) {
        // Recorre las 8 celdas vecinas
        for (int i = Math.max(0, fila-1); i <= Math.min(FILAS-1, fila+1); i++) {
            for (int j = Math.max(0, columna-1); j <= Math.min(COLUMNAS-1, columna+1); j++) {
                // Descubre celdas no descubiertas y sin minas
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

    /**
     * Revela todas las minas en el tablero
     */
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

    /**
     * Verifica si el jugador ha ganado (todas las celdas sin minas descubiertas).
     * @return true si el jugador ganó, false en caso contrario
     */
    private boolean verificarVictoria() {
        for (CeldaJuego[] fila : celdas) {
            for (CeldaJuego celda : fila) {
                // Si hay una celda sin mina no descubierta, no se ha ganado
                if (!celda.tieneMina() && !celda.estaDescubierta()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Marca o desmarca una celda como sospechosa de contener mina.
     * @param fila Fila de la celda a marcar
     * @param columna Columna de la celda a marcar
     * @throws JuegoFinalizadoException Si el juego ya terminó
     * @throws CoordenadaFueraDeRangoException Si las coordenadas son inválidas
     */
    public void marcarCelda(int fila, int columna) throws JuegoFinalizadoException, CoordenadaFueraDeRangoException {
        if (juegoFinalizado) {
            throw new JuegoFinalizadoException();
        }

        if (!esCoordenadaValida(fila, columna)) {
            throw new CoordenadaFueraDeRangoException(fila, columna);
        }

        celdas[fila][columna].alternarMarcado();
    }

    /**
     * Verifica si unas coordenadas están dentro del rango válido del tablero.
     * @param fila Fila a verificar
     * @param columna Columna a verificar
     * @return true si las coordenadas son válidas, false en caso contrario
     */
    private boolean esCoordenadaValida(int fila, int columna) {
        return fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS;
    }

    // Métodos getter para acceder a los atributos del tablero

    public CeldaJuego[][] getCeldas() { return celdas; }
    public boolean isJuegoFinalizado() { return juegoFinalizado; }
    public boolean isVictoria() { return victoria; }
    public int getMinasRestantes() { return minasRestantes; }
    public static int getFilas() { return FILAS; }
    public static int getColumnas() { return COLUMNAS; }
}