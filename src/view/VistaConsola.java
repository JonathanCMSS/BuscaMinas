package view;

import model.TableroBuscaminas;
import java.util.Scanner;

/**
 * Vista en consola para el juego Buscaminas (MVC) - version final
 */
public class VistaConsola {
    private final Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    // metodos

    public void mostrarEncabezado() {
        System.out.println("\n" +
                "============================================\n" +
                "||        ✨GAME BUSCAMINAS✨             ||\n" +
                "||             ✨(2025)✨                 ||\n" +
                "============================================\n");
    }

    public void mostrarInstrucciones() {
        System.out.println("""
            \nINSTRUCCION PARA JUGAR===========================================
            Se juega asi: Primero debes ingresar la letra y luego el numero (B3)
            \n""");
    }

    // interaccion con el jugador

    public String leerComando() {
        System.out.print("\nIngrese comando: ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public String leerComando(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public int leerOpcionMenu() {
        try {
            System.out.print("Seleccione opción: ");
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    // tablero

    public void dibujarTablero(TableroBuscaminas tablero) {
        System.out.println("\n   " + "1 2 3 4 5 6 7 8 9 10");

        for (int i = 0; i < TableroBuscaminas.getFilas(); i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < TableroBuscaminas.getColumnas(); j++) {
                System.out.print("|" + tablero.getCeldas()[i][j].obtenerSimboloVisual());
            }
            System.out.println("|");
        }
        System.out.println("\nMinas restantes: " + tablero.getMinasRestantes());
    }


    public void mostrarError(String mensaje) {
        System.out.println("\n⚠ Error: " + mensaje);
    }

    public void mostrarEstado(String mensaje) {
        System.out.println("\n• " + mensaje);
    }

    public void mostrarVictoria() {
        System.out.println("""
            \n-------------------------------
              |        ✅¡VICTORIA!✅       |
              -------------------------------""");
    }

    public void mostrarDerrota() {
        System.out.println("""
            \n--------------------------------------------
              | ❌❗Has pisado una mina, has perdido❗❌ |
              --------------------------------------------""");
    }

    // ------------------ MENÚS ------------------

    public void mostrarMenuPrincipal() {
        System.out.println("""
            \nMENÚ DE OPCIONES:
            1. Nueva partida
            3. Estadísticas
            4. Instrucciones
            5. Salir
            ----------------------------""");
    }


    public void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void cerrar() {
        scanner.close();
    }
}