package excepciones;


public class ElementoNoEncontradoException extends Exception {
    
    
    public ElementoNoEncontradoException() {
        super("El elemento buscado no fue encontrado.");
    }
    
    
    public ElementoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
    
    public ElementoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
