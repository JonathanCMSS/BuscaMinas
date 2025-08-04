package model;

/**
 * Interface para objetos que pueden ser guardados y cargados (aplicación de interfaces)
 */
public interface Persistible {
    void guardar(String rutaArchivo);
    void cargar(String rutaArchivo);
}