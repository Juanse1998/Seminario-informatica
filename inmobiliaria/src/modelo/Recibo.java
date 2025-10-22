package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase Recibo - Representa un recibo emitido por un pago
 * Mapea a la tabla Recibo de la base de datos
 */
public class Recibo {
    // ENCAPSULAMIENTO: atributos privados
    private int idRecibo;
    private int idPago; // FK a Pago
    private LocalDate fechaEmision;
    private String formato; // 'Digital', 'Impreso'
    
    // Constructor por defecto
    public Recibo() {
        this.idRecibo = 0;
        this.idPago = 0;
        this.fechaEmision = LocalDate.now();
        this.formato = "Digital";
    }
    
    // Constructor con parámetros completo
    public Recibo(int idRecibo, int idPago, LocalDate fechaEmision, String formato) {
        this.idRecibo = idRecibo;
        this.idPago = idPago;
        this.fechaEmision = fechaEmision;
        this.formato = formato;
    }
    
    // Constructor sin ID (para inserción en BD)
    public Recibo(int idPago, LocalDate fechaEmision, String formato) {
        this.idPago = idPago;
        this.fechaEmision = fechaEmision;
        this.formato = formato;
    }
    
    // Getters y Setters - ENCAPSULAMIENTO
    public int getIdRecibo() {
        return idRecibo;
    }
    
    public void setIdRecibo(int idRecibo) {
        this.idRecibo = idRecibo;
    }
    
    public int getIdPago() {
        return idPago;
    }
    
    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }
    
    public LocalDate getFechaEmision() {
        return fechaEmision;
    }
    
    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public String getFormato() {
        return formato;
    }
    
    public void setFormato(String formato) {
        this.formato = formato;
    }
    
    // Método para mostrar información del recibo
    public String mostrarInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format(
            "=== RECIBO ===\n" +
            "ID: %d\n" +
            "ID Pago: %d\n" +
            "Fecha Emisión: %s\n" +
            "Formato: %s",
            idRecibo, idPago, fechaEmision.format(formatter), formato
        );
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("ID: %d | Pago: %d | %s | %s", 
                           idRecibo, idPago, fechaEmision.format(formatter), formato);
    }
}
