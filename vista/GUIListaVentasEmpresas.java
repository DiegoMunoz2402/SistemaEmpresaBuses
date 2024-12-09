package vista;

import controlador.ControladorEmpresas;
import excepciones.SVPException;
import utilidades.Rut;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.*;

public class GUIListaVentasEmpresas extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxRut;
    private JComboBox comboBoxNombre;
    private JTable JTableListarVentas;

    public GUIListaVentasEmpresas() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        String[][] datos = ControladorEmpresas.getInstance().listEmpresas();

        for (int i = 0; i < datos.length; i++) {
            comboBoxRut.addItem(datos[i][0]);
            comboBoxNombre.addItem(datos[i][1]);
        }

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        comboBoxRut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onComboBoxRut();
            }
        });
        comboBoxNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onComboBoxNombre();
            }
        });
    }

    private void onComboBoxNombre() {
        int seleccion = comboBoxNombre.getSelectedIndex();
        comboBoxRut.setSelectedIndex(seleccion);
    }

    private void onComboBoxRut() {
        int selectedIndex = comboBoxRut.getSelectedIndex();
        comboBoxNombre.setSelectedIndex(selectedIndex);
    }

    private void JtableListarVentas() {

    }


    private void onOK() {
        // add your code here
        int seleccion = comboBoxRut.getSelectedIndex();
        String rut = String.valueOf(comboBoxRut.getItemAt(seleccion));
        String[][] datos;
        try {
            datos = ControladorEmpresas.getInstance().listVentasEmpresa(Rut.of(rut));
            if (datos.length > 0) {
                String[] columnas = {"Fecha", "Tipo", "Monto pagado", "Tipo pago"};
                TableModel tableModel = new DefaultTableModel(datos, columnas) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                JTableListarVentas.setModel(tableModel);
            } else {
                JOptionPane.showMessageDialog(this, "No hay ventas registradas", "", JOptionPane.ERROR_MESSAGE);
            }
        }catch (SVPException e){
            JOptionPane.showMessageDialog(this,e.getMessage(),"",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void display() {
        GUIListaVentasEmpresas dialog = new GUIListaVentasEmpresas();
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}
