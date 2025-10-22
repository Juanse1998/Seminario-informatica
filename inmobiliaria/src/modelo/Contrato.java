package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase Contrato - Representa un contrato de alquiler o venta
 * Mapea a la tabla Contrato de la base de datos
 */
public class Contrato {
    // ENCAPSULAMIENTO: atributos privados
    private int idContrato;
    private String tipo; // 'alquiler', 'venta'
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double monto;
    private int idPropiedad; // FK a Propiedad
    private int idInquilino; // FK a Cliente (puede ser null)
    private int idPropietario; // FK a Cliente
    private int idUsuario; // FK a Usuarios (quien generó el contrato)
    
    // Constructor por defecto
    public Contrato() {
        this.idContrato = 0;
        this.tipo = "alquiler";
        this.fechaInicio = LocalDate.now();
        this.fechaFin = null;
        this.monto = 0.0;
        this.idPropiedad = 0;
        this.idInquilino = 0;
        this.idPropietario = 0;
        this.idUsuario = 0;
    }
    
    // Constructor con parámetros completo
    public Contrato(int idContrato, String tipo, LocalDate fechaInicio, LocalDate fechaFin,
                   double monto, int idPropiedad, int idInquilino, int idPropietario, int idUsuario) {
        this.idContrato = idContrato;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.monto = monto;
        this.idPropiedad = idPropiedad;
        this.idInquilino = idInquilino;
        this.idPropietario = idPropietario;
        this.idUsuario = idUsuario;
    }
    
    // Constructor sin ID (para inserción en BD)
    public Contrato(String tipo, LocalDate fechaInicio, LocalDate fechaFin,
                   double monto, int idPropiedad, int idInquilino, int idPropietario, int idUsuario) {
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.monto = monto;
        this.idPropiedad = idPropiedad;
        this.idInquilino = idInquilino;
        this.idPropietario = idPropietario;
        this.idUsuario = idUsuario;
    }
    
    // Getters y Setters - ENCAPSULAMIENTO
    public int getIdContrato() {
        return idContrato;
    }
    
    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public int getIdPropiedad() {
        return idPropiedad;
    }
    
    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }
    
    public int getIdInquilino() {
        return idInquilino;
    }
    
    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }
    
    public int getIdPropietario() {
        return idPropietario;
    }
    
    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    // Método para mostrar información del contrato
    public String mostrarInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format(
            "=== CONTRATO ===\n" +
            "ID: %d\n" +
            "Tipo: %s\n" +
            "Fecha Inicio: %s\n" +
            "Fecha Fin: %s\n" +
            "Monto: $%.2f\n" +
            "ID Propiedad: %d\n" +
            "ID Inquilino: %d\n" +
            "ID Propietario: %d\n" +
            "ID Usuario: %d",
            idContrato, tipo, fechaInicio.format(formatter),
            (fechaFin != null ? fechaFin.format(formatter) : "N/A"),
            monto, idPropiedad, idInquilino, idPropietario, idUsuario
        );
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("ID: %d | %s | %s | $%.2f", 
                           idContrato, tipo, fechaInicio.format(formatter), monto);
    }
}
