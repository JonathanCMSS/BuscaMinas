import controller.ControladorJuego;

public class JuegoBuscaMinas {

    public static void main(String[] args) {

        try {
            // Crear e iniciar el controlador principal
            ControladorJuego controlador = new ControladorJuego();
            controlador.iniciar();

        } catch (Exception e) {
            System.err.println("Error : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Juego de Busca Minas finalizado");
    }
}
