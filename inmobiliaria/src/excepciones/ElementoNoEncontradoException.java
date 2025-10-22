package excepciones;

/**
 * Excepción personalizada para elementos no encontrados
 * Demuestra el MANEJO DE EXCEPCIONES personalizadas
 */
public class ElementoNoEncontradoException extends Exception {
    
    // Constructor por defecto
    public ElementoNoEncontradoException() {
        super("El elemento buscado no fue encontrado.");
    }
    
    // Constructor con mensaje personalizado
    public ElementoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
    // Constructor con mensaje y causa
    public ElementoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
