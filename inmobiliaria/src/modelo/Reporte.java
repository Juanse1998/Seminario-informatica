package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Reporte - Representa un reporte generado en el sistema
 * Mapea a la tabla Reporte de la base de datos
 */
public class Reporte {
    // ENCAPSULAMIENTO: atributos privados
    private int idReporte;
    private LocalDateTime fechaGenerado;
    private String tipo; // 'propiedades', 'contratos', 'pagos', 'clientes'
    private int generadoPor; // FK a Usuarios (puede ser null)
    
    // Constructor por defecto
    public Reporte() {
        this.idReporte = 0;
        this.fechaGenerado = LocalDateTime.now();
        this.tipo = "propiedades";
        this.generadoPor = 0;
    }
    
    // Constructor con parámetros completo
    public Reporte(int idReporte, LocalDateTime fechaGenerado, String tipo, int generadoPor) {
        this.idReporte = idReporte;
        this.fechaGenerado = fechaGenerado;
        this.tipo = tipo;
        this.generadoPor = generadoPor;
    }
    
    // Constructor sin ID (para inserción en BD)
    public Reporte(LocalDateTime fechaGenerado, String tipo, int generadoPor) {
        this.fechaGenerado = fechaGenerado;
        this.tipo = tipo;
        this.generadoPor = generadoPor;
    }
    
    // Getters y Setters - ENCAPSULAMIENTO
    public int getIdReporte() {
        return idReporte;
    }
    
    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }
    
    public LocalDateTime getFechaGenerado() {
        return fechaGenerado;
    }
    
    public void setFechaGenerado(LocalDateTime fechaGenerado) {
        this.fechaGenerado = fechaGenerado;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getGeneradoPor() {
        return generadoPor;
    }
    
    public void setGeneradoPor(int generadoPor) {
        this.generadoPor = generadoPor;
    }
    
    // Método para mostrar información del reporte
    public String mostrarInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format(
            "=== REPORTE ===\n" +
            "ID: %d\n" +
            "Fecha: %s\n" +
            "Tipo: %s\n" +
            "Generado por (ID Usuario): %d",
            idReporte, fechaGenerado.format(formatter), tipo, generadoPor
        );
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("ID: %d | %s | %s | Usuario: %d", 
                           idReporte, tipo, fechaGenerado.format(formatter), generadoPor);
    }
}
