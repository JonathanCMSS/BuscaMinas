package exceptions;

public class FormatoEntradaException extends IllegalArgumentException {

    //Constructor base con mensaje predeterminado

    public FormatoEntradaException() {
        super("Entrada inválida. ingrese solo valores correctos");
    }

    /**
       Constructor con mensaje personalizado
       descripcionError da la descripción específica del error
     */
    public FormatoEntradaException(String descripcionError) {
        super(descripcionError);
    }

    /**
     * Constructor con causa original
       descripcionError Descripción del error
       errorOriginal Excepción que causó este error
     */
    public FormatoEntradaException(String descripcionError, Throwable errorOriginal) {
        super(descripcionError, errorOriginal);
    }

    // Mensaje de error
    public String generarReporteError() {
        String reporte = "ERROR: " + getMessage();
        if (getCause() != null) {
            reporte += "\nCausa: " + getCause().getMessage();
        }
        return reporte;
    }
}