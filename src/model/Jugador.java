package model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Maneja la información y estadísticas del jugador en el Buscaminas
public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int partidasJugadas;
    private int partidasGanadas;
    private long mejorTiempo;
    private LocalDateTime fechaUltimaPartida;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mejorTiempo = Long.MAX_VALUE;
    }

    public void registrarVictoria(long tiempoPartida) {
        System.out.println("Registrando victoria con tiempo: " + tiempoPartida + "ms");
        partidasJugadas++;
        partidasGanadas++;
        if (tiempoPartida < mejorTiempo) {
            mejorTiempo = tiempoPartida;
        }
        fechaUltimaPartida = LocalDateTime.now();
    }

    public void registrarDerrota() {
        partidasJugadas++;
        fechaUltimaPartida = LocalDateTime.now();
    }

    public String getEstadisticas() {
        return String.format(
                "Jugador: %s | Victorias: %d/%d (%.1f%%) | Mejor tiempo: %s | Ultima partida: %s",
                nombre,
                partidasGanadas,
                partidasJugadas,
                partidasJugadas > 0 ? (partidasGanadas * 100.0 / partidasJugadas) : 0,
                mejorTiempo == Long.MAX_VALUE ? "N/A" : formatTiempo(mejorTiempo),
                fechaUltimaPartida != null ? fechaUltimaPartida.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A"
        );
    }

    private String formatTiempo(long ms) {
        System.out.println("Formateando tiempo: " + ms + "ms");

        if (ms <= 0) {
            return "00:00";
        }

        long totalSegundos = ms / 1000;
        long seg = totalSegundos % 60;
        long min = totalSegundos / 60;

        return String.format("%02d:%02d", min, seg);
    }

    // Getters básicos
    public String getNombre() { return nombre; }
    public int getVictorias() { return partidasGanadas; }
    public int getPartidas() { return partidasJugadas; }
    public long getMejorTiempo() { return mejorTiempo; }
}
