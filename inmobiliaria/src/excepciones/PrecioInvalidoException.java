package excepciones;


public class PrecioInvalidoException extends Exception {
    
    
    public PrecioInvalidoException() {
        super("El precio ingresado no es válido. Debe ser mayor a 0.");
    }
    
    
    public PrecioInvalidoException(String mensaje) {
        super(mensaje);
    }
    
    
    public PrecioInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
