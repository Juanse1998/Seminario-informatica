package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Pago {
    
    private int idPago;
    private int idContrato; 
    private LocalDate fechaPago;
    private double monto;
    private String metodoPago; 
    private String observaciones;
    
    
    public Pago() {
        this.idPago = 0;
        this.idContrato = 0;
        this.fechaPago = LocalDate.now();
        this.monto = 0.0;
        this.metodoPago = "efectivo";
        this.observaciones = "";
    }
    
    
    public Pago(int idPago, int idContrato, LocalDate fechaPago, double monto,
               String metodoPago, String observaciones) {
        this.idPago = idPago;
        this.idContrato = idContrato;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.observaciones = observaciones;
    }
    
    
    public Pago(int idContrato, LocalDate fechaPago, double monto,
               String metodoPago, String observaciones) {
        this.idContrato = idContrato;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.observaciones = observaciones;
    }
    
    
    public int getIdPago() {
        return idPago;
    }
    
    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }
    
    public int getIdContrato() {
        return idContrato;
    }
    
    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }
    
    public LocalDate getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
    public String mostrarInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format(
            "=== PAGO ===\n" +
            "ID: %d\n" +
            "ID Contrato: %d\n" +
            "Fecha: %s\n" +
            "Monto: $%.2f\n" +
            "Método: %s\n" +
            "Observaciones: %s",
            idPago, idContrato, fechaPago.format(formatter),
            monto, metodoPago, observaciones
        );
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("ID: %d | Contrato: %d | %s | $%.2f | %s", 
                           idPago, idContrato, fechaPago.format(formatter), monto, metodoPago);
    }
}
