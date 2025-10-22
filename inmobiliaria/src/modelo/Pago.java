package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase Pago - Representa un pago realizado en un contrato
 * Mapea a la tabla Pago de la base de datos
 */
public class Pago {
    // ENCAPSULAMIENTO: atributos privados
    private int idPago;
    private int idContrato; // FK a Contrato
    private LocalDate fechaPago;
    private double monto;
    private String metodoPago; // 'efectivo', 'transferencia', 'tarjeta'
    private String observaciones;
    
    // Constructor por defecto
    public Pago() {
        this.idPago = 0;
        this.idContrato = 0;
        this.fechaPago = LocalDate.now();
        this.monto = 0.0;
        this.metodoPago = "efectivo";
        this.observaciones = "";
    }
    
    // Constructor con parámetros completo
    public Pago(int idPago, int idContrato, LocalDate fechaPago, double monto,
               String metodoPago, String observaciones) {
        this.idPago = idPago;
        this.idContrato = idContrato;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.observaciones = observaciones;
    }
    
    // Constructor sin ID (para inserción en BD)
    public Pago(int idContrato, LocalDate fechaPago, double monto,
               String metodoPago, String observaciones) {
        this.idContrato = idContrato;
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.observaciones = observaciones;
    }
    
    // Getters y Setters - ENCAPSULAMIENTO
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
    
    // Método para mostrar información del pago
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
