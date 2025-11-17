package excepciones;


public class CampoVacioException extends Exception {
    
    
    public CampoVacioException() {
        super("Hay campos obligatorios que están vacíos.");
    }
    
    
    public CampoVacioException(String campo) {
        super("El campo '" + campo + "' no puede estar vacío.");
    }
    
    
    public CampoVacioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
