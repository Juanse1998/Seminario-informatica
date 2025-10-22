package excepciones;

/**
 * Excepción personalizada para campos vacíos
 * Demuestra el MANEJO DE EXCEPCIONES personalizadas
 */
public class CampoVacioException extends Exception {
    
    // Constructor por defecto
    public CampoVacioException() {
        super("Hay campos obligatorios que están vacíos.");
    }
    
    // Constructor con mensaje personalizado
    public CampoVacioException(String campo) {
        super("El campo '" + campo + "' no puede estar vacío.");
    }
    
    // Constructor con mensaje y causa
    public CampoVacioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
