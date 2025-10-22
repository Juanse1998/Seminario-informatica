package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase Reserva - Representa una reserva o contrato de propiedad
 */
public class Reserva {
    // ENCAPSULAMIENTO: atributos privados
    private int id;
    private Propiedad propiedad;
    private Cliente cliente;
    private LocalDate fecha;
    private double monto;
    private String estado; // "Activa", "Cancelada", "Finalizada"
    
    // Constructor por defecto
    public Reserva() {
        this.id = 0;
        this.propiedad = null;
        this.cliente = null;
        this.fecha = LocalDate.now();
        this.monto = 0.0;
        this.estado = "Activa";
    }
    
    // Constructor con parámetros
    public Reserva(int id, Propiedad propiedad, Cliente cliente, 
                   LocalDate fecha, double monto, String estado) {
        this.id = id;
        this.propiedad = propiedad;
        this.cliente = cliente;
        this.fecha = fecha;
        this.monto = monto;
        this.estado = estado;
    }
    
    // Getters y Setters - ENCAPSULAMIENTO
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Propiedad getPropiedad() {
        return propiedad;
    }
    
    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    // Método para mostrar información de la reserva
    public String mostrarInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format(
            "=== RESERVA ===\n" +
            "ID: %d\n" +
            "Propiedad: %s\n" +
            "Cliente: %s\n" +
            "Fecha: %s\n" +
            "Monto: $%.2f\n" +
            "Estado: %s",
            id, 
            (propiedad != null ? propiedad.getDireccion() : "N/A"),
            (cliente != null ? cliente.getNombreCompleto() : "N/A"),
            fecha.format(formatter),
            monto,
            estado
        );
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("ID: %d | Prop: %s | Cliente: %s | Fecha: %s | $%.2f | %s",
                           id,
                           (propiedad != null ? propiedad.getDireccion() : "N/A"),
                           (cliente != null ? cliente.getNombreCompleto() : "N/A"),
                           fecha.format(formatter),
                           monto,
                           estado);
    }
}
