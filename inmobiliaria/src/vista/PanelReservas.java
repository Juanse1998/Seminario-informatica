package vista;

import controlador.Inmobiliaria;
import modelo.*;
import excepciones.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class PanelReservas extends JPanel {
    private Inmobiliaria inmobiliaria;
    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnCancelar, btnEliminar, btnActualizar;
    
    public PanelReservas(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = inmobiliaria;
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("GESTIÓN DE RESERVAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        
        String[] columnas = {"ID", "Propiedad", "Cliente", "Fecha", "Monto", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaReservas = new JTable(modeloTabla);
        tablaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        add(scrollPane, BorderLayout.CENTER);
        
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        btnAgregar = new JButton("Agregar Reserva");
        btnAgregar.addActionListener(e -> agregarReserva());
        panelBotones.add(btnAgregar);
        
        btnCancelar = new JButton("Cancelar Reserva");
        btnCancelar.addActionListener(e -> cancelarReserva());
        panelBotones.add(btnCancelar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarReserva());
        panelBotones.add(btnEliminar);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarDatos());
        panelBotones.add(btnActualizar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Reserva r : inmobiliaria.getReservas()) {
            Object[] fila = {
                r.getId(),
                r.getPropiedad() != null ? r.getPropiedad().getDireccion() : "N/A",
                r.getCliente() != null ? r.getCliente().getNombreCompleto() : "N/A",
                r.getFecha().format(formatter),
                String.format("$%.2f", r.getMonto()),
                r.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void agregarReserva() {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                     "Agregar Reserva", true);
        dialogo.setLayout(new BorderLayout(10, 10));
        
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JComboBox<String> cmbPropiedad = new JComboBox<>();
        cmbPropiedad.addItem("Seleccione una propiedad");
        
        
        for (Propiedad p : inmobiliaria.getPropiedades()) {
            
            if (p.getEstado().equals("Disponible")) {
                cmbPropiedad.addItem(p.getId() + " - " + p.getDireccion());
            }
        }
        
        
        JComboBox<String> cmbCliente = new JComboBox<>();
        cmbCliente.addItem("Seleccione un cliente");
        for (Cliente c : inmobiliaria.getClientes()) {
            cmbCliente.addItem(c.getId() + " - " + c.getNombreCompleto());
        }
        
        JTextField txtMonto = new JTextField();
        JTextField txtFecha = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Activa", "Cancelada", "Finalizada"});
        
        panelFormulario.add(new JLabel("Propiedad:"));
        panelFormulario.add(cmbPropiedad);
        panelFormulario.add(new JLabel("Cliente:"));
        panelFormulario.add(cmbCliente);
        panelFormulario.add(new JLabel("Monto:"));
        panelFormulario.add(txtMonto);
        panelFormulario.add(new JLabel("Fecha (dd/MM/yyyy):"));
        panelFormulario.add(txtFecha);
        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(cmbEstado);
        
        dialogo.add(panelFormulario, BorderLayout.CENTER);
        
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            try {
                
                int indexPropiedad = cmbPropiedad.getSelectedIndex();
                int indexCliente = cmbCliente.getSelectedIndex();
                
                if (indexPropiedad == 0 || indexCliente == 0) {
                    throw new CampoVacioException("Debe seleccionar una propiedad y un cliente");
                }
                
                
                String propSeleccionada = (String) cmbPropiedad.getSelectedItem();
                int idPropiedad = Integer.parseInt(propSeleccionada.split(" - ")[0]);
                
                
                String clienteSeleccionado = (String) cmbCliente.getSelectedItem();
                int idCliente = Integer.parseInt(clienteSeleccionado.split(" - ")[0]);
                
                Propiedad propiedad = inmobiliaria.buscarPropiedadPorId(idPropiedad);
                Cliente cliente = inmobiliaria.buscarClientePorId(idCliente);
                
                double monto = Double.parseDouble(txtMonto.getText().trim());
                
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), formatter);
                
                String estado = (String) cmbEstado.getSelectedItem();
                
                Reserva reserva = new Reserva(0, propiedad, cliente, fecha, monto, estado);
                
                inmobiliaria.agregarReserva(reserva);
                JOptionPane.showMessageDialog(dialogo, "Reserva agregada exitosamente");
                
                cargarDatos();
                dialogo.dispose();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, 
                    "Error: Verifique que el monto sea un número válido", 
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (PrecioInvalidoException ex) {
                JOptionPane.showMessageDialog(dialogo, ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, 
                    "Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        
        dialogo.pack();
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }
    
    private void cancelarReserva() {
        int filaSeleccionada = tablaReservas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione una reserva de la tabla", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de cancelar esta reserva?",
            "Confirmar cancelación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                inmobiliaria.cancelarReserva(id);
                cargarDatos();
                JOptionPane.showMessageDialog(this, "Reserva cancelada exitosamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarReserva() {
        int filaSeleccionada = tablaReservas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione una reserva de la tabla", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar esta reserva?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                inmobiliaria.eliminarReserva(id);
                cargarDatos();
                JOptionPane.showMessageDialog(this, "Reserva eliminada exitosamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
