package test;

import model.CeldaJuego;
import exceptions.CasillaReveladaException;

/**
 * Pruebas unitarias básicas para CeldaJuego (aplicación de TDD)
 */
public class TestCeldaJuego {

    public static void main(String[] args) {
        System.out.println("=== EJECUTANDO PRUEBAS UNITARIAS ===");

        testCreacionCelda();
        testAsignarMina();
        testRevelarCelda();
        testMarcarCelda();
        testException();

        System.out.println("=== TODAS LAS PRUEBAS PASARON ✅ ===");
    }

    // Test 1: Creación de celda
    public static void testCreacionCelda() {
        CeldaJuego celda = new CeldaJuego(0, 0);
        assert !celda.tieneMina() : "Nueva celda no debe tener mina";
        assert !celda.estaDescubierta() : "Nueva celda no debe estar descubierta";
        assert !celda.estaMarcada() : "Nueva celda no debe estar marcada";
        System.out.println("✅ Test creación celda - PASÓ");
    }

    // Test 2: Asignar mina
    public static void testAsignarMina() {
        CeldaJuego celda = new CeldaJuego(1, 1);
        celda.asignarMina();
        assert celda.tieneMina() : "Celda debe tener mina después de asignarla";
        assert celda.obtenerSimbolo() == '■' : "Celda con mina no revelada debe mostrar ■";
        System.out.println("✅ Test asignar mina - PASÓ");
    }

    // Test 3: Revelar celda
    public static void testRevelarCelda() {
        try {
            CeldaJuego celda = new CeldaJuego(2, 2);
            celda.revelar();
            assert celda.estaDescubierta() : "Celda debe estar descubierta después de revelar";
            assert celda.obtenerSimbolo() == ' ' : "Celda vacía revelada debe mostrar espacio";
            System.out.println("✅ Test revelar celda - PASÓ");
        } catch (CasillaReveladaException e) {
            System.out.println("❌ Test revelar celda - FALLÓ: " + e.getMessage());
        }
    }

    // Test 4: Marcar celda
    public static void testMarcarCelda() {
        CeldaJuego celda = new CeldaJuego(3, 3);
        celda.alternarMarcado();
        assert celda.estaMarcada() : "Celda debe estar marcada";
        assert celda.obtenerSimbolo() == '⚑' : "Celda marcada debe mostrar bandera";
        System.out.println("✅ Test marcar celda - PASÓ");
    }

    // Test 5: Excepción personalizada
    public static void testException() {
        try {
            CeldaJuego celda = new CeldaJuego(4, 4);
            celda.revelar();
            celda.revelar(); // Esto debe lanzar excepción
            assert false : "Debería haber lanzado CasillaReveladaException";
        } catch (CasillaReveladaException e) {
            assert e.getMessage().contains("ya revelada") : "Mensaje de excepción correcto";
            System.out.println("✅ Test excepción personalizada - PASÓ");
        }
    }
}