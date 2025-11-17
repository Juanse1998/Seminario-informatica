package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Recibo {
    
    private int idRecibo;
    private int idPago; 
    private LocalDate fechaEmision;
    private String formato; 
    
    
    public Recibo() {
        this.idRecibo = 0;
        this.idPago = 0;
        this.fechaEmision = LocalDate.now();
        this.formato = "Digital";
    }
    
    
    public Recibo(int idRecibo, int idPago, LocalDate fechaEmision, String formato) {
        this.idRecibo = idRecibo;
        this.idPago = idPago;
        this.fechaEmision = fechaEmision;
        this.formato = formato;
    }
    
    
    public Recibo(int idPago, LocalDate fechaEmision, String formato) {
        this.idPago = idPago;
        this.fechaEmision = fechaEmision;
        this.formato = formato;
    }
    
    
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
