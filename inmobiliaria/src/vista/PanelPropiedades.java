package vista;

import controlador.InmobiliariaDB;
import modelo.*;
import excepciones.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class PanelPropiedades extends JPanel {
    private InmobiliariaDB inmobiliaria;
    private JTable tablaPropiedades;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnModificar, btnEliminar, btnVerDetalles, btnBuscar, btnOrdenar, btnActualizar;
    private JTextField txtBuscar;

    public PanelPropiedades(InmobiliariaDB inmobiliaria) {
        this.inmobiliaria = inmobiliaria;
        inicializarComponentes();
        cargarDatos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("GESTIÓN DE PROPIEDADES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        
        String[] columnas = { "ID", "Dirección", "Tipo", "Operación", "Precio", "Estado", "Agente" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tablaPropiedades = new JTable(modeloTabla);
        tablaPropiedades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaPropiedades);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));

        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar por dirección:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarPropiedades());
        panelBusqueda.add(btnBuscar);

        btnActualizar = new JButton("Mostrar Todas");
        btnActualizar.addActionListener(e -> cargarDatos());
        panelBusqueda.add(btnActualizar);

        panelInferior.add(panelBusqueda, BorderLayout.NORTH);

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnAgregar = new JButton("Agregar Propiedad");
        btnAgregar.addActionListener(e -> agregarPropiedad());
        panelBotones.add(btnAgregar);

        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarPropiedad());
        panelBotones.add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarPropiedad());
        panelBotones.add(btnEliminar);

        btnVerDetalles = new JButton("Ver detalles");
        btnVerDetalles.addActionListener(e -> verDetallesPropiedad());
        panelBotones.add(btnVerDetalles);

        btnOrdenar = new JButton("Ordenar por Precio");
        btnOrdenar.addActionListener(e -> ordenarPorPrecio());
        panelBotones.add(btnOrdenar);

        panelInferior.add(panelBotones, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);

        
        for (Propiedad p : inmobiliaria.getPropiedades()) {
            Object[] fila = {
                    p.getId(),
                    p.getDireccion(),
                    p.getTipo(),
                    p.getOperacion(),
                    String.format("$%.2f", p.getPrecio()),
                    p.getEstado(),
                    p.getAgente()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void agregarPropiedad() {
        
        String[] opciones = { "Residencial", "Comercial" };
        int tipo = JOptionPane.showOptionDialog(
                this,
                "Seleccione el tipo de propiedad:",
                "Tipo de Propiedad",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        
        if (tipo == 0) {
            mostrarFormularioResidencial(null);
        } else if (tipo == 1) {
            mostrarFormularioComercial(null);
        }
    }

    private void mostrarFormularioResidencial(PropiedadResidencial propExistente) {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                propExistente == null ? "Agregar Propiedad Residencial" : "Modificar Propiedad Residencial",
                true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(11, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JTextField txtDireccion = new JTextField(propExistente != null ? propExistente.getDireccion() : "");
        JTextField txtTipo = new JTextField(propExistente != null ? propExistente.getTipo() : "Casa");
        JComboBox<String> cmbOperacion = new JComboBox<>(new String[] { "Venta", "Alquiler" });
        if (propExistente != null)
            cmbOperacion.setSelectedItem(propExistente.getOperacion());
        JTextField txtPrecio = new JTextField(propExistente != null ? String.valueOf(propExistente.getPrecio()) : "");
        JComboBox<String> cmbEstado = new JComboBox<>(new String[] { "Disponible", "Reservada", "Vendida" });
        if (propExistente != null)
            cmbEstado.setSelectedItem(propExistente.getEstado());
        JTextField txtAgente = new JTextField(propExistente != null ? propExistente.getAgente() : "");
        JTextField txtHabitaciones = new JTextField(
                propExistente != null ? String.valueOf(propExistente.getHabitaciones()) : "");
        JTextField txtBaños = new JTextField(propExistente != null ? String.valueOf(propExistente.getBaños()) : "");
        JCheckBox chkGarage = new JCheckBox("", propExistente != null && propExistente.isGarage());
        JTextField txtMetros = new JTextField(
                propExistente != null ? String.valueOf(propExistente.getMetrosCuadrados()) : "");

        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);
        panelFormulario.add(new JLabel("Tipo (Casa/Depto):"));
        panelFormulario.add(txtTipo);
        panelFormulario.add(new JLabel("Operación:"));
        panelFormulario.add(cmbOperacion);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(cmbEstado);
        panelFormulario.add(new JLabel("Agente:"));
        panelFormulario.add(txtAgente);
        panelFormulario.add(new JLabel("Habitaciones:"));
        panelFormulario.add(txtHabitaciones);
        panelFormulario.add(new JLabel("Baños:"));
        panelFormulario.add(txtBaños);
        panelFormulario.add(new JLabel("Garage:"));
        panelFormulario.add(chkGarage);
        panelFormulario.add(new JLabel("Metros cuadrados:"));
        panelFormulario.add(txtMetros);

        dialogo.add(panelFormulario, BorderLayout.CENTER);

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                
                String direccion = txtDireccion.getText().trim();
                String tipo = txtTipo.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String estado = (String) cmbEstado.getSelectedItem();
                String descripcion = "Propiedad residencial"; 
                int idPropietario = 1; 
                int habitaciones = Integer.parseInt(txtHabitaciones.getText().trim());
                int baños = Integer.parseInt(txtBaños.getText().trim());
                boolean garage = chkGarage.isSelected();
                double metros = Double.parseDouble(txtMetros.getText().trim());

                PropiedadResidencial propiedad = new PropiedadResidencial(
                        propExistente != null ? propExistente.getId() : 0,
                        direccion, tipo, precio, estado, descripcion, idPropietario,
                        habitaciones, baños, garage, metros);

                if (propExistente == null) {
                    inmobiliaria.agregarPropiedad(propiedad);
                    JOptionPane.showMessageDialog(dialogo, "Propiedad agregada exitosamente");
                } else {
                    inmobiliaria.modificarPropiedad(propiedad);
                    JOptionPane.showMessageDialog(dialogo, "Propiedad modificada exitosamente");
                }

                cargarDatos();
                dialogo.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo,
                        "Error: Verifique que los campos numéricos sean válidos",
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

    private void mostrarFormularioComercial(PropiedadComercial propExistente) {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                propExistente == null ? "Agregar Propiedad Comercial" : "Modificar Propiedad Comercial",
                true);
        dialogo.setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridLayout(10, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JTextField txtDireccion = new JTextField(propExistente != null ? propExistente.getDireccion() : "");
        JTextField txtTipo = new JTextField(propExistente != null ? propExistente.getTipo() : "Local Comercial");
        JComboBox<String> cmbOperacion = new JComboBox<>(new String[] { "Venta", "Alquiler" });
        if (propExistente != null)
            cmbOperacion.setSelectedItem(propExistente.getOperacion());
        JTextField txtPrecio = new JTextField(propExistente != null ? String.valueOf(propExistente.getPrecio()) : "");
        JComboBox<String> cmbEstado = new JComboBox<>(new String[] { "Disponible", "Reservada", "Vendida" });
        if (propExistente != null)
            cmbEstado.setSelectedItem(propExistente.getEstado());
        JTextField txtAgente = new JTextField(propExistente != null ? propExistente.getAgente() : "");
        JTextField txtMetros = new JTextField(
                propExistente != null ? String.valueOf(propExistente.getMetrosCuadrados()) : "");
        JTextField txtZona = new JTextField(propExistente != null ? propExistente.getZonaComercial() : "");
        JCheckBox chkGastronomia = new JCheckBox("", propExistente != null && propExistente.isApto_gastronomia());
        JTextField txtCapacidad = new JTextField(
                propExistente != null ? String.valueOf(propExistente.getCapacidadPersonas()) : "");

        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(txtTipo);
        panelFormulario.add(new JLabel("Operación:"));
        panelFormulario.add(cmbOperacion);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(cmbEstado);
        panelFormulario.add(new JLabel("Agente:"));
        panelFormulario.add(txtAgente);
        panelFormulario.add(new JLabel("Metros cuadrados:"));
        panelFormulario.add(txtMetros);
        panelFormulario.add(new JLabel("Zona comercial:"));
        panelFormulario.add(txtZona);
        panelFormulario.add(new JLabel("Apto gastronomía:"));
        panelFormulario.add(chkGastronomia);
        panelFormulario.add(new JLabel("Capacidad (personas):"));
        panelFormulario.add(txtCapacidad);

        dialogo.add(panelFormulario, BorderLayout.CENTER);

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                String direccion = txtDireccion.getText().trim();
                String tipo = txtTipo.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                String estado = (String) cmbEstado.getSelectedItem();
                String descripcion = "Propiedad comercial"; 
                int idPropietario = 1; 
                double metros = Double.parseDouble(txtMetros.getText().trim());
                String zona = txtZona.getText().trim();
                boolean gastronomia = chkGastronomia.isSelected();
                int capacidad = Integer.parseInt(txtCapacidad.getText().trim());

                PropiedadComercial propiedad = new PropiedadComercial(
                        propExistente != null ? propExistente.getId() : 0,
                        direccion, tipo, precio, estado, descripcion, idPropietario,
                        metros, zona, gastronomia, capacidad);

                if (propExistente == null) {
                    inmobiliaria.agregarPropiedad(propiedad);
                    JOptionPane.showMessageDialog(dialogo, "Propiedad agregada exitosamente");
                } else {
                    inmobiliaria.modificarPropiedad(propiedad);
                    JOptionPane.showMessageDialog(dialogo, "Propiedad modificada exitosamente");
                }

                cargarDatos();
                dialogo.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo,
                        "Error: Verifique que los campos numéricos sean válidos",
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

    private void modificarPropiedad() {
        int filaSeleccionada = tablaPropiedades.getSelectedRow();

        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una propiedad de la tabla",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Propiedad prop = inmobiliaria.buscarPropiedadPorId(id);

            
            if (prop instanceof PropiedadResidencial) {
                mostrarFormularioResidencial((PropiedadResidencial) prop);
            } else if (prop instanceof PropiedadComercial) {
                mostrarFormularioComercial((PropiedadComercial) prop);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPropiedad() {
        int filaSeleccionada = tablaPropiedades.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una propiedad de la tabla",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar esta propiedad?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                inmobiliaria.eliminarPropiedad(id);
                cargarDatos();
                JOptionPane.showMessageDialog(this, "Propiedad eliminada exitosamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarPropiedades() {
        String textoBusqueda = txtBuscar.getText().trim();

        if (textoBusqueda.isEmpty()) {
            cargarDatos();
            return;
        }

        modeloTabla.setRowCount(0);

        
        for (Propiedad p : inmobiliaria.buscarPropiedadesPorDireccion(textoBusqueda)) {
            Object[] fila = {
                    p.getId(),
                    p.getDireccion(),
                    p.getTipo(),
                    p.getOperacion(),
                    String.format("$%.2f", p.getPrecio()),
                    p.getEstado(),
                    p.getAgente()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void ordenarPorPrecio() {
        inmobiliaria.ordenarPropiedadesPorPrecio();
        cargarDatos();
        JOptionPane.showMessageDialog(this, "Propiedades ordenadas por precio");
    }

    private void verDetallesPropiedad() {
        int filaSeleccionada = tablaPropiedades.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione una propiedad de la tabla",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Propiedad propiedad = inmobiliaria.buscarPropiedadPorId(id);

            if (propiedad == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró la propiedad en la base de datos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                    "Detalles de la Propiedad", true);
            dialogo.setLayout(new BorderLayout(10, 10));

            JPanel panelContenido = new JPanel(new GridLayout(0, 2, 5, 5));
            panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            
            panelContenido.add(new JLabel("ID:"));
            panelContenido.add(new JLabel(String.valueOf(propiedad.getIdPropiedad())));

            panelContenido.add(new JLabel("Dirección:"));
            panelContenido.add(new JLabel(propiedad.getDireccion()));

            panelContenido.add(new JLabel("Tipo:"));
            panelContenido.add(new JLabel(propiedad.getTipo()));

            panelContenido.add(new JLabel("Precio:"));
            panelContenido.add(new JLabel(String.format("$%.2f", propiedad.getPrecio())));

            panelContenido.add(new JLabel("Estado:"));
            panelContenido.add(new JLabel(propiedad.getEstado()));

            panelContenido.add(new JLabel("Descripción:"));
            panelContenido.add(new JLabel(propiedad.getDescripcion() != null ? propiedad.getDescripcion() : ""));

            panelContenido.add(new JLabel("ID Propietario:"));
            panelContenido.add(new JLabel(String.valueOf(propiedad.getIdPropietario())));

            
            if (propiedad instanceof PropiedadResidencial) {
                PropiedadResidencial pr = (PropiedadResidencial) propiedad;

                panelContenido.add(new JLabel("Habitaciones:"));
                panelContenido.add(new JLabel(String.valueOf(pr.getHabitaciones())));

                panelContenido.add(new JLabel("Baños:"));
                panelContenido.add(new JLabel(String.valueOf(pr.getBaños())));

                panelContenido.add(new JLabel("Garage:"));
                panelContenido.add(new JLabel(pr.isGarage() ? "Sí" : "No"));

                panelContenido.add(new JLabel("Metros cuadrados:"));
                panelContenido.add(new JLabel(String.valueOf(pr.getMetrosCuadrados())));

            } else if (propiedad instanceof PropiedadComercial) {
                PropiedadComercial pc = (PropiedadComercial) propiedad;

                panelContenido.add(new JLabel("Metros cuadrados:"));
                panelContenido.add(new JLabel(String.valueOf(pc.getMetrosCuadrados())));

                panelContenido.add(new JLabel("Zona comercial:"));
                panelContenido.add(new JLabel(pc.getZonaComercial()));

                panelContenido.add(new JLabel("Apto gastronomía:"));
                panelContenido.add(new JLabel(pc.isApto_gastronomia() ? "Sí" : "No"));

                panelContenido.add(new JLabel("Capacidad (personas):"));
                panelContenido.add(new JLabel(String.valueOf(pc.getCapacidadPersonas())));
            }

            dialogo.add(panelContenido, BorderLayout.CENTER);

            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(e -> dialogo.dispose());
            panelBotones.add(btnCerrar);

            dialogo.add(panelBotones, BorderLayout.SOUTH);

            dialogo.pack();
            dialogo.setLocationRelativeTo(this);
            dialogo.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar detalles: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
