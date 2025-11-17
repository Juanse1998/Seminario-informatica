package vista;

import controlador.InmobiliariaDB;
import modelo.*;
import excepciones.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class PanelContratos extends JPanel {
    private InmobiliariaDB inmobiliaria;
    private ArrayList<Contrato> contratos;
    private JTable tablaContratos;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnEliminar, btnVerDetalles, btnActualizar;

    public PanelContratos(InmobiliariaDB inmobiliaria) {
        this.inmobiliaria = inmobiliaria;
        this.contratos = new ArrayList<>();
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("GESTIÓN DE CONTRATOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        
        String[] columnas = { "ID", "Tipo", "Propiedad", "Inquilino", "Propietario", "Monto", "Fecha Inicio",
                "Fecha Fin" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaContratos = new JTable(modeloTabla);
        tablaContratos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaContratos);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnAgregar = new JButton("Nuevo Contrato");
        btnAgregar.addActionListener(e -> agregarContrato());
        panelBotones.add(btnAgregar);

        btnVerDetalles = new JButton("Ver Detalles");
        btnVerDetalles.addActionListener(e -> verDetalles());
        panelBotones.add(btnVerDetalles);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarContrato());
        panelBotones.add(btnEliminar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarDatos());
        panelBotones.add(btnActualizar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Contrato c : inmobiliaria.getContratos()) {
            String direccionPropiedad = "N/A";
            String nombreInquilino = "N/A";
            String nombrePropietario = "N/A";

            try {
                Propiedad prop = inmobiliaria.buscarPropiedadPorId(c.getIdPropiedad());
                direccionPropiedad = prop.getDireccion();
            } catch (Exception e) {
            }

            try {
                if (c.getIdInquilino() > 0) {
                    Cliente inq = inmobiliaria.buscarClientePorId(c.getIdInquilino());
                    nombreInquilino = inq.getNombreCompleto();
                }
            } catch (Exception e) {
            }

            try {
                Cliente prop = inmobiliaria.buscarClientePorId(c.getIdPropietario());
                nombrePropietario = prop.getNombreCompleto();
            } catch (Exception e) {
            }

            Object[] fila = {
                    c.getIdContrato(),
                    c.getTipo(),
                    direccionPropiedad,
                    nombreInquilino,
                    nombrePropietario,
                    String.format("$%.2f", c.getMonto()),
                    c.getFechaInicio().format(formatter),
                    c.getFechaFin() != null ? c.getFechaFin().format(formatter) : "Indefinido"
            };
            modeloTabla.addRow(fila);
        }
    }

    private void agregarContrato() {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Nuevo Contrato", true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<String> cmbTipo = new JComboBox<>(new String[] { "alquiler", "venta" });

        JComboBox<String> cmbPropiedad = new JComboBox<>();
        cmbPropiedad.addItem("Seleccione una propiedad");
        for (Propiedad p : inmobiliaria.getPropiedades()) {
            if (p.getEstado().equals("disponible")) {
                cmbPropiedad.addItem(p.getId() + " - " + p.getDireccion() + " ($" + p.getPrecio() + ")");
            }
        }

        JComboBox<String> cmbInquilino = new JComboBox<>();
        cmbInquilino.addItem("Seleccione un inquilino");
        for (Cliente c : inmobiliaria.getClientes()) {
            if (c.getTipo().equals("inquilino") || c.getTipo().equals("ambos")) {
                cmbInquilino.addItem(c.getId() + " - " + c.getNombreCompleto() + " (DNI: " + c.getDni() + ")");
            }
        }

        JComboBox<String> cmbPropietario = new JComboBox<>();
        cmbPropietario.addItem("Seleccione un propietario");
        for (Cliente c : inmobiliaria.getClientes()) {
            if (c.getTipo().equals("propietario") || c.getTipo().equals("ambos")) {
                cmbPropietario.addItem(c.getId() + " - " + c.getNombreCompleto() + " (DNI: " + c.getDni() + ")");
            }
        }

        JTextField txtMonto = new JTextField();
        JTextField txtFechaInicio = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JTextField txtFechaFin = new JTextField(
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        panelFormulario.add(new JLabel("Tipo de Contrato:"));
        panelFormulario.add(cmbTipo);
        panelFormulario.add(new JLabel("Propiedad:"));
        panelFormulario.add(cmbPropiedad);
        panelFormulario.add(new JLabel("Inquilino:"));
        panelFormulario.add(cmbInquilino);
        panelFormulario.add(new JLabel("Propietario:"));
        panelFormulario.add(cmbPropietario);
        panelFormulario.add(new JLabel("Monto Mensual:"));
        panelFormulario.add(txtMonto);
        panelFormulario.add(new JLabel("Fecha Inicio (dd/MM/yyyy):"));
        panelFormulario.add(txtFechaInicio);
        panelFormulario.add(new JLabel("Fecha Fin (dd/MM/yyyy):"));
        panelFormulario.add(txtFechaFin);

        dialogo.add(panelFormulario, BorderLayout.CENTER);

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                int indexPropiedad = cmbPropiedad.getSelectedIndex();
                int indexInquilino = cmbInquilino.getSelectedIndex();
                int indexPropietario = cmbPropietario.getSelectedIndex();

                if (indexPropiedad == 0) {
                    throw new CampoVacioException("Debe seleccionar una propiedad");
                }
                if (indexInquilino == 0 && cmbTipo.getSelectedItem().equals("alquiler")) {
                    throw new CampoVacioException("Debe seleccionar un inquilino para contratos de alquiler");
                }
                if (indexPropietario == 0) {
                    throw new CampoVacioException("Debe seleccionar un propietario");
                }

                String propSeleccionada = (String) cmbPropiedad.getSelectedItem();
                int idPropiedad = Integer.parseInt(propSeleccionada.split(" - ")[0]);

                int idInquilino = 0;
                if (indexInquilino > 0) {
                    String inquilinoSeleccionado = (String) cmbInquilino.getSelectedItem();
                    idInquilino = Integer.parseInt(inquilinoSeleccionado.split(" - ")[0]);
                }

                String propietarioSeleccionado = (String) cmbPropietario.getSelectedItem();
                int idPropietario = Integer.parseInt(propietarioSeleccionado.split(" - ")[0]);

                String tipo = (String) cmbTipo.getSelectedItem();
                double monto = Double.parseDouble(txtMonto.getText().trim());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fechaInicio = LocalDate.parse(txtFechaInicio.getText().trim(), formatter);
                LocalDate fechaFin = LocalDate.parse(txtFechaFin.getText().trim(), formatter);

                Contrato contrato = new Contrato(0, tipo, fechaInicio, fechaFin,
                        monto, idPropiedad, idInquilino, idPropietario, 1);

                inmobiliaria.agregarContrato(contrato);

                JOptionPane.showMessageDialog(dialogo,
                        "Contrato creado exitosamente!",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                cargarDatos();
                dialogo.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo,
                        "Error: Verifique que el monto sea un número válido",
                        "Error de formato", JOptionPane.ERROR_MESSAGE);
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

    private void verDetalles() {
        int filaSeleccionada = tablaContratos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un contrato de la tabla",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Contrato contrato = buscarContratoPorId(id);

        if (contrato != null) {
            JOptionPane.showMessageDialog(this,
                    contrato.mostrarInfo(),
                    "Detalles del Contrato", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eliminarContrato() {
        int filaSeleccionada = tablaContratos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un contrato de la tabla",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este contrato?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                Contrato contrato = buscarContratoPorId(id);

                if (contrato != null) {
                    inmobiliaria.eliminarContrato(contrato.getIdContrato());
                    cargarDatos();
                    JOptionPane.showMessageDialog(this, "Contrato eliminado exitosamente");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Contrato buscarContratoPorId(int id) {
        return inmobiliaria.buscarContratoPorId(id);
    }

    public ArrayList<Contrato> getContratos() {
        return inmobiliaria.getContratos();
    }
}
