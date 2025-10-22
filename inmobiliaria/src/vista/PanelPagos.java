package vista;

import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Panel de gestión de pagos y recibos
 */
public class PanelPagos extends JPanel {
    private PanelContratos panelContratos;
    private ArrayList<Pago> pagos;
    private ArrayList<Recibo> recibos;
    private JTable tablaPagos;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrarPago, btnGenerarRecibo, btnImprimirRecibo, btnActualizar;
    
    public PanelPagos(PanelContratos panelContratos) {
        this.panelContratos = panelContratos;
        this.pagos = new ArrayList<>();
        this.recibos = new ArrayList<>();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("GESTIÓN DE PAGOS Y RECIBOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con tabla
        String[] columnas = {"ID Pago", "ID Contrato", "Fecha", "Monto", "Método", "Recibo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPagos = new JTable(modeloTabla);
        tablaPagos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaPagos);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        btnRegistrarPago = new JButton("Registrar Pago");
        btnRegistrarPago.addActionListener(e -> registrarPago());
        panelBotones.add(btnRegistrarPago);
        
        btnGenerarRecibo = new JButton("Generar Recibo");
        btnGenerarRecibo.addActionListener(e -> generarRecibo());
        panelBotones.add(btnGenerarRecibo);
        
        btnImprimirRecibo = new JButton("Imprimir Recibo");
        btnImprimirRecibo.addActionListener(e -> imprimirRecibo());
        panelBotones.add(btnImprimirRecibo);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarDatos());
        panelBotones.add(btnActualizar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Pago p : pagos) {
            String tieneRecibo = tieneRecibo(p.getIdPago()) ? "Sí" : "No";
            
            Object[] fila = {
                p.getIdPago(),
                p.getIdContrato(),
                p.getFechaPago().format(formatter),
                String.format("$%.2f", p.getMonto()),
                p.getMetodoPago(),
                tieneRecibo
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void registrarPago() {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                     "Registrar Pago", true);
        dialogo.setLayout(new BorderLayout(10, 10));
        
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // ComboBox de contratos activos
        JComboBox<String> cmbContrato = new JComboBox<>();
        cmbContrato.addItem("Seleccione un contrato");
        for (Contrato c : panelContratos.getContratos()) {
            cmbContrato.addItem("ID: " + c.getIdContrato() + " - " + c.getTipo() + " - $" + c.getMonto());
        }
        
        JTextField txtMonto = new JTextField();
        JTextField txtFecha = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JComboBox<String> cmbMetodo = new JComboBox<>(new String[]{"efectivo", "transferencia", "tarjeta"});
        JTextArea txtObservaciones = new JTextArea(3, 20);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        
        panelFormulario.add(new JLabel("Contrato:"));
        panelFormulario.add(cmbContrato);
        panelFormulario.add(new JLabel("Monto:"));
        panelFormulario.add(txtMonto);
        panelFormulario.add(new JLabel("Fecha (dd/MM/yyyy):"));
        panelFormulario.add(txtFecha);
        panelFormulario.add(new JLabel("Método de Pago:"));
        panelFormulario.add(cmbMetodo);
        panelFormulario.add(new JLabel("Observaciones:"));
        panelFormulario.add(scrollObs);
        
        dialogo.add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            try {
                int indexContrato = cmbContrato.getSelectedIndex();
                
                if (indexContrato == 0) {
                    throw new Exception("Debe seleccionar un contrato");
                }
                
                // Extraer ID del contrato
                String contratoSeleccionado = (String) cmbContrato.getSelectedItem();
                int idContrato = Integer.parseInt(contratoSeleccionado.split(" - ")[0].replace("ID: ", ""));
                
                double monto = Double.parseDouble(txtMonto.getText().trim());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim(), formatter);
                String metodo = (String) cmbMetodo.getSelectedItem();
                String observaciones = txtObservaciones.getText().trim();
                
                // Crear pago
                int nuevoId = pagos.size() + 1;
                Pago pago = new Pago(nuevoId, idContrato, fecha, monto, metodo, observaciones);
                pagos.add(pago);
                
                JOptionPane.showMessageDialog(dialogo, 
                    "Pago registrado exitosamente!\nID: " + nuevoId + 
                    "\n\n¿Desea generar el recibo ahora?", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                cargarDatos();
                dialogo.dispose();
                
                // Preguntar si quiere generar recibo
                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Desea generar el recibo para este pago?",
                    "Generar Recibo",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    generarReciboParaPago(pago);
                }
                
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
    
    private void generarRecibo() {
        int filaSeleccionada = tablaPagos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un pago de la tabla", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idPago = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Pago pago = buscarPagoPorId(idPago);
        
        if (pago != null) {
            if (tieneRecibo(idPago)) {
                JOptionPane.showMessageDialog(this, 
                    "Este pago ya tiene un recibo generado", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            generarReciboParaPago(pago);
        }
    }
    
    private void generarReciboParaPago(Pago pago) {
        String[] opciones = {"Digital", "Impreso"};
        int formato = JOptionPane.showOptionDialog(
            this,
            "Seleccione el formato del recibo:",
            "Formato de Recibo",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (formato >= 0) {
            int nuevoId = recibos.size() + 1;
            Recibo recibo = new Recibo(nuevoId, pago.getIdPago(), LocalDate.now(), opciones[formato]);
            recibos.add(recibo);
            
            JOptionPane.showMessageDialog(this, 
                "Recibo generado exitosamente!\nID: " + nuevoId + "\nFormato: " + opciones[formato], 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            cargarDatos();
            
            // Si es impreso, preguntar si quiere imprimir ahora
            if (formato == 1) {
                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Desea imprimir el recibo ahora?",
                    "Imprimir Recibo",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcion == JOptionPane.YES_OPTION) {
                    imprimirReciboEspecifico(recibo, pago);
                }
            }
        }
    }
    
    private void imprimirRecibo() {
        int filaSeleccionada = tablaPagos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione un pago de la tabla", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idPago = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Pago pago = buscarPagoPorId(idPago);
        Recibo recibo = buscarReciboPorIdPago(idPago);
        
        if (recibo == null) {
            JOptionPane.showMessageDialog(this, 
                "Este pago no tiene un recibo generado.\nGenere el recibo primero.", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        imprimirReciboEspecifico(recibo, pago);
    }
    
    private void imprimirReciboEspecifico(Recibo recibo, Pago pago) {
        // Crear panel con el contenido del recibo
        JPanel panelRecibo = crearPanelRecibo(recibo, pago);
        
        // Mostrar vista previa
        JDialog dialogoPrevia = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
                                           "Vista Previa del Recibo", true);
        dialogoPrevia.setLayout(new BorderLayout());
        dialogoPrevia.add(new JScrollPane(panelRecibo), BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnImprimir = new JButton("Imprimir");
        JButton btnCerrar = new JButton("Cerrar");
        
        btnImprimir.addActionListener(e -> {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(new Printable() {
                public int print(Graphics g, PageFormat pf, int page) {
                    if (page > 0) return NO_SUCH_PAGE;
                    
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.translate(pf.getImageableX(), pf.getImageableY());
                    
                    // Escalar para que quepa en la página
                    double scaleX = pf.getImageableWidth() / panelRecibo.getWidth();
                    double scaleY = pf.getImageableHeight() / panelRecibo.getHeight();
                    double scale = Math.min(scaleX, scaleY);
                    g2d.scale(scale, scale);
                    
                    panelRecibo.print(g2d);
                    return PAGE_EXISTS;
                }
            });
            
            if (job.printDialog()) {
                try {
                    job.print();
                    JOptionPane.showMessageDialog(dialogoPrevia, 
                        "Recibo enviado a la impresora", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(dialogoPrevia, 
                        "Error al imprimir: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnCerrar.addActionListener(e -> dialogoPrevia.dispose());
        
        panelBotones.add(btnImprimir);
        panelBotones.add(btnCerrar);
        dialogoPrevia.add(panelBotones, BorderLayout.SOUTH);
        
        dialogoPrevia.setSize(600, 700);
        dialogoPrevia.setLocationRelativeTo(this);
        dialogoPrevia.setVisible(true);
    }
    
    private JPanel crearPanelRecibo(Recibo recibo, Pago pago) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Obtener datos del contrato
        Contrato contrato = buscarContratoPorId(pago.getIdContrato());
        String datosContrato = "N/A";
        if (contrato != null) {
            datosContrato = "Contrato #" + contrato.getIdContrato() + " - " + contrato.getTipo();
        }
        
        // Encabezado
        JLabel lblTitulo = new JLabel("RECIBO DE PAGO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        
        panel.add(Box.createVerticalStrut(10));
        
        JLabel lblEmpresa = new JLabel("INMOBILIARIA SGI");
        lblEmpresa.setFont(new Font("Arial", Font.BOLD, 16));
        lblEmpresa.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblEmpresa);
        
        panel.add(Box.createVerticalStrut(30));
        
        // Información del recibo
        agregarLinea(panel, "Recibo N°:", String.valueOf(recibo.getIdRecibo()), true);
        agregarLinea(panel, "Fecha de Emisión:", recibo.getFechaEmision().format(formatter), false);
        agregarLinea(panel, "Formato:", recibo.getFormato(), false);
        
        panel.add(Box.createVerticalStrut(20));
        
        // Información del pago
        JLabel lblPago = new JLabel("DETALLES DEL PAGO");
        lblPago.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblPago);
        
        panel.add(Box.createVerticalStrut(10));
        
        agregarLinea(panel, "ID Pago:", String.valueOf(pago.getIdPago()), false);
        agregarLinea(panel, "Contrato:", datosContrato, false);
        agregarLinea(panel, "Fecha de Pago:", pago.getFechaPago().format(formatter), false);
        agregarLinea(panel, "Método de Pago:", pago.getMetodoPago().toUpperCase(), false);
        agregarLinea(panel, "Monto:", String.format("$%.2f", pago.getMonto()), true);
        
        if (pago.getObservaciones() != null && !pago.getObservaciones().isEmpty()) {
            panel.add(Box.createVerticalStrut(10));
            agregarLinea(panel, "Observaciones:", pago.getObservaciones(), false);
        }
        
        panel.add(Box.createVerticalStrut(40));
        
        // Pie de página
        JSeparator separator = new JSeparator();
        panel.add(separator);
        
        panel.add(Box.createVerticalStrut(20));
        
        JLabel lblFirma = new JLabel("_________________________");
        lblFirma.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblFirma);
        
        JLabel lblTextoFirma = new JLabel("Firma Autorizada");
        lblTextoFirma.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTextoFirma);
        
        return panel;
    }
    
    private void agregarLinea(JPanel panel, String etiqueta, String valor, boolean destacar) {
        JPanel lineaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lineaPanel.setBackground(Color.WHITE);
        
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 12));
        lineaPanel.add(lblEtiqueta);
        
        JLabel lblValor = new JLabel(valor);
        if (destacar) {
            lblValor.setFont(new Font("Arial", Font.BOLD, 14));
            lblValor.setForeground(new Color(0, 100, 0));
        } else {
            lblValor.setFont(new Font("Arial", Font.PLAIN, 12));
        }
        lineaPanel.add(lblValor);
        
        panel.add(lineaPanel);
    }
    
    private boolean tieneRecibo(int idPago) {
        for (Recibo r : recibos) {
            if (r.getIdPago() == idPago) {
                return true;
            }
        }
        return false;
    }
    
    private Pago buscarPagoPorId(int id) {
        for (Pago p : pagos) {
            if (p.getIdPago() == id) {
                return p;
            }
        }
        return null;
    }
    
    private Recibo buscarReciboPorIdPago(int idPago) {
        for (Recibo r : recibos) {
            if (r.getIdPago() == idPago) {
                return r;
            }
        }
        return null;
    }
    
    private Contrato buscarContratoPorId(int id) {
        for (Contrato c : panelContratos.getContratos()) {
            if (c.getIdContrato() == id) {
                return c;
            }
        }
        return null;
    }
}
