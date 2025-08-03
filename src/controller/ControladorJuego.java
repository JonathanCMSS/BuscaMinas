package controller;
import exceptions.CoordenadaFueraDeRangoException;
import exceptions.JuegoFinalizadoException;
import model.Jugador;
import model.TableroBuscaminas;
import view.VistaConsola;

import java.io.*;
import java.time.Duration;
import java.time.Instant;

/**
 * Controlador principal del juego Buscaminas (MVC)
 */
public class ControladorJuego {
    private TableroBuscaminas tablero;
    private Jugador jugador;
    private VistaConsola vista;
    private boolean enEjecucion;
    private Instant inicioPartida;
    private static final String RUTA_PARTIDA = "partida.dat";
    private static final String RUTA_JUGADOR = "jugador.dat";

    public ControladorJuego() {
        this.vista = new VistaConsola();
        this.enEjecucion = true;
    }

    public void iniciar() {
        vista.mostrarEncabezado();
        inicializarJugador();
        buclePrincipal();
        vista.cerrar();
    }

    private void inicializarJugador() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_JUGADOR))) {
            this.jugador = (Jugador) ois.readObject();
            vista.mostrarEstado("Bienvenido de nuevo, " + jugador.getNombre());
        } catch (Exception e) {
            registrarNuevoJugador();
        }
    }

    private void registrarNuevoJugador() {
        vista.mostrarEstado("Nuevo jugador detectado");
        String nombre;
        do {
            nombre = vista.leerComando("Ingrese su nombre: ");
        } while (nombre.isBlank());

        this.jugador = new Jugador(nombre);
    }

    private void buclePrincipal() {
        while (enEjecucion) {
            vista.mostrarMenuPrincipal();
            procesarOpcion(vista.leerOpcionMenu());
        }
        guardarJugador();
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> iniciarNuevaPartida();
            case 3 -> mostrarEstadisticas();
            case 4 -> vista.mostrarInstrucciones();
            case 5 -> enEjecucion = false;
            default -> vista.mostrarError("Opcion no valida");
        }
    }

    private void iniciarNuevaPartida() {
        this.tablero = new TableroBuscaminas();
        this.inicioPartida = Instant.now();
        System.out.println("Partida iniciada en: " + inicioPartida);
        jugarPartida();
    }

    private void jugarPartida() {
        while (!tablero.isJuegoFinalizado()) {
            vista.limpiarPantalla();
            vista.dibujarTablero(tablero);
            procesarComando(vista.leerComando());
        }
        finalizarPartida();
    }

    private void procesarComando(String comando) {
        try {
            if (comando.startsWith("marcar ")) {
                procesarMarcado(comando.substring(7));
            } else if (comando.matches("[a-jA-J](10|[1-9])")) {
                procesarDescubrimiento(comando);
            } else {
                procesarComandoEspecial(comando);
            }
        } catch (Exception e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void procesarMarcado(String coordenada) throws JuegoFinalizadoException, CoordenadaFueraDeRangoException {
        int[] pos = convertirCoordenada(coordenada);
        tablero.marcarCelda(pos[0], pos[1]);
        vista.mostrarEstado("Casilla marcada/desmarcada");
    }

    private void procesarDescubrimiento(String coordenada) throws JuegoFinalizadoException, CoordenadaFueraDeRangoException {
        int[] pos = convertirCoordenada(coordenada);
        if (!tablero.descubrirCelda(pos[0], pos[1])) {
            vista.mostrarDerrota();
            jugador.registrarDerrota();
        } else {
            System.out.println("Verificando victoria: " + tablero.isVictoria());
            if (tablero.isVictoria()) {
                Instant finPartida = Instant.now();
                long tiempo = Duration.between(inicioPartida, finPartida).toMillis();
                System.out.println("Inicio partida: " + inicioPartida);
                System.out.println("Fin partida: " + finPartida);
                System.out.println("Tiempo calculado: " + tiempo + " ms");
                System.out.println("Tiempo en segundos: " + (tiempo/1000.0));

                vista.mostrarVictoria();
                jugador.registrarVictoria(tiempo);
            }
        }
    }

    private int[] convertirCoordenada(String coord) throws CoordenadaFueraDeRangoException {
        try {
            int fila = coord.toUpperCase().charAt(0) - 'A';
            int columna = Integer.parseInt(coord.substring(1)) - 1;

            if (fila < 0 || fila >= TableroBuscaminas.getFilas() ||
                    columna < 0 || columna >= TableroBuscaminas.getColumnas()) {
                throw new CoordenadaFueraDeRangoException(fila, columna);
            }

            return new int[]{fila, columna};
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new CoordenadaFueraDeRangoException(-1, -1);
        }
    }

    private void procesarComandoEspecial(String comando) {
        switch (comando) {
            case "guardar" -> guardarPartida();
            case "ayuda" -> vista.mostrarInstrucciones();
            case "salir" -> enEjecucion = false;
            default -> vista.mostrarError("Comando no reconocido");
        }
    }

    private void finalizarPartida() {
        vista.dibujarTablero(tablero);
        vista.mostrarEstado("Partida finalizada. Regresando al menú principal...");
    }

    private void mostrarEstadisticas() {
        vista.mostrarEstado(jugador.getEstadisticas());
    }

    private void guardarPartida() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_PARTIDA))) {
            oos.writeObject(new PartidaGuardada(tablero, inicioPartida));
            vista.mostrarEstado("Partida guardada exitosamente");
        } catch (IOException e) {
            vista.mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    private void guardarJugador() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_JUGADOR))) {
            oos.writeObject(jugador);
        } catch (IOException e) {
            vista.mostrarError("No se guardaron estadisticas: " + e.getMessage());
        }
    }

    private record PartidaGuardada(TableroBuscaminas tablero, Instant inicioPartida) implements Serializable {
        private static final long serialVersionUID = 1L;
    }
}