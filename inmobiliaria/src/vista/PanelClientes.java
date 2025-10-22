package vista;

import controlador.Inmobiliaria;
import modelo.Cliente;
import excepciones.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel de gestión de clientes
 */
public class PanelClientes extends JPanel {
    private Inmobiliaria inmobiliaria;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnModificar, btnEliminar, btnBuscar, btnActualizar;
    private JTextField txtBuscar;
    
    public PanelClientes(Inmobiliaria inmobiliaria) {
        this.inmobiliaria = inmobiliaria;
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("GESTIÓN DE CLIENTES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con tabla
        String[] columnas = {"ID", "Nombre", "Apellido", "Teléfono", "Email", "Tipo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones y búsqueda
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar por nombre:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarClientes());
        panelBusqueda.add(btnBuscar);
        
        btnActualizar = new JButton("Mostrar Todos");
        btnActualizar.addActionListener(e -> cargarDatos());
        panelBusqueda.add(btnActualizar);
        
        panelInferior.add(panelBusqueda, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        btnAgregar = new JButton("Agregar Cliente");
        btnAgregar.addActionListener(e -> agregarCliente());
        panelBotones.add(btnAgregar);
        
        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarCliente());
        panelBotones.add(btnModificar);
        
        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarCliente());
        panelBotones.add(btnEliminar);
        
        panelInferior.add(panelBotones, BorderLayout.CENTER);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        
        for (Cliente c : inmobiliaria.getClientes()) {
            Object[] fila = {
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getTelefono(),
                c.getEmail(),
                c.getTipoCliente()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void agregarCliente() {
        mostrarFormularioCliente(null);
    }
    
    private void mostrarFormularioCliente(Cliente clienteExistente) {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                     clienteExistente == null ? "Agregar Cliente" : "Modificar Cliente", 
                                     true);
        dialogo.setLayout(new BorderLayout(10, 10));
        
        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Campos del formulario
        JTextField txtNombre = new JTextField(clienteExistente != null ? clienteExistente.getNombre() : "");
        JTextField txtApellido = new JTextField(clienteExistente != null ? clienteExistente.getApellido() : "");
        JTextField txtDni = new JTextField(clienteExistente != null ? clienteExistente.getDni() : "");
        JTextField txtTelefono = new JTextField(clienteExistente != null ? clienteExistente.getTelefono() : "");
        JTextField txtEmail = new JTextField(clienteExistente != null ? clienteExistente.getEmail() : "");
        JTextField txtDireccion = new JTextField(clienteExistente != null ? clienteExistente.getDireccion() : "");
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"inquilino", "propietario", "ambos"});
        if (clienteExistente != null) cmbTipo.setSelectedItem(clienteExistente.getTipo());
        
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Apellido:"));
        panelFormulario.add(txtApellido);
        panelFormulario.add(new JLabel("DNI:"));
        panelFormulario.add(txtDni);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);
        panelFormulario.add(new JLabel("Tipo de Cliente:"));
        panelFormulario.add(cmbTipo);
        
        dialogo.add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            try {
                // MANEJO DE EXCEPCIONES: try-catch
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String dni = txtDni.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String email = txtEmail.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String tipo = (String) cmbTipo.getSelectedItem();
                
                Cliente cliente = new Cliente(
                    clienteExistente != null ? clienteExistente.getId() : 0,
                    nombre, apellido, dni, telefono, email, direccion, tipo
                );
                
                if (clienteExistente == null) {
                    inmobiliaria.agregarCliente(cliente);
                    JOptionPane.showMessageDialog(dialogo, "Cliente agregado exitosamente");
                } else {
                    inmobiliaria.modificarCliente(cliente);
                    JOptionPane.showMessageDialog(dialogo, "Cliente modificado exitosamente");
                }
                
                cargarDatos();
                dialogo.dispose();
                
            } catch (CampoVacioException ex) {
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
    
    private void modificarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un cliente de la tabla", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Cliente cliente = inmobiliaria.buscarClientePorId(id);
            mostrarFormularioCliente(cliente);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un cliente de la tabla", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este cliente?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                inmobiliaria.eliminarCliente(id);
                cargarDatos();
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void buscarClientes() {
        String textoBusqueda = txtBuscar.getText().trim();
        
        if (textoBusqueda.isEmpty()) {
            cargarDatos();
            return;
        }
        
        modeloTabla.setRowCount(0);
        
        for (Cliente c : inmobiliaria.buscarClientesPorNombre(textoBusqueda)) {
            Object[] fila = {
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getTelefono(),
                c.getEmail(),
                c.getTipoCliente()
            };
            modeloTabla.addRow(fila);
        }
    }
}
