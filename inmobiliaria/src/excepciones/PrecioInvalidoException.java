package excepciones;

/**
 * Excepción personalizada para precios inválidos
 * Demuestra el MANEJO DE EXCEPCIONES personalizadas
 */
public class PrecioInvalidoException extends Exception {
    
    // Constructor por defecto
    public PrecioInvalidoException() {
        super("El precio ingresado no es válido. Debe ser mayor a 0.");
    }
    
    // Constructor con mensaje personalizado
    public PrecioInvalidoException(String mensaje) {
        super(mensaje);
    }
    
    // Constructor con mensaje y causa
    public PrecioInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
