package vista;

import controlador.ControladorEmpresas;
import excepciones.SVPException;
import utilidades.Rut;

import javax.swing.*;
import java.awt.event.*;

public class GUICreaBus extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldPATENTE;
    private JTextField textFieldMARCA;
    private JTextField textFieldMODELO;
    private JTextField textFieldNASIENTOS;
    private JComboBox comboBoxRut;
    private JComboBox comboBoxNombre;

    public GUICreaBus() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        String[][] datos = ControladorEmpresas.getInstance().listEmpresas();

        for (String[] dato:datos){
            comboBoxRut.addItem(dato[0]);
            comboBoxNombre.addItem(dato[1]);
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
        int selectedIndex = comboBoxNombre.getSelectedIndex();
        comboBoxRut.setSelectedIndex(selectedIndex);
    }

    private void onComboBoxRut() {
        int selectedIndex = comboBoxRut.getSelectedIndex();
        comboBoxNombre.setSelectedIndex(selectedIndex);
    }

    private void onOK() {
        String patente = textFieldPATENTE.getText();
        if (!patente.matches("[A-Z]{2,3}\\d{3,4}")) {
            JOptionPane.showMessageDialog(this, "El formato de la patente es incorrecto","",JOptionPane.ERROR_MESSAGE);
            return;
        }
        String marca = textFieldMARCA.getText();
        String modelo = textFieldMODELO.getText();
        String nroAsientosstr = textFieldNASIENTOS.getText();
        int selectedIndexRut = comboBoxRut.getSelectedIndex();
        try {
            int nroAsientos = Integer.parseInt(nroAsientosstr);
            ControladorEmpresas.getInstance().createBus(patente,marca,modelo,nroAsientos, Rut.of((String)comboBoxRut.getItemAt(selectedIndexRut)));
            JOptionPane.showMessageDialog(this,"Bus creado exitosamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
        }catch (SVPException e){
            JOptionPane.showMessageDialog(this,e.getMessage(),"",JOptionPane.ERROR_MESSAGE);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Nro asientos invalido","",JOptionPane.ERROR_MESSAGE);
        }
        textFieldMARCA.setText("");
        textFieldMODELO.setText("");
        textFieldNASIENTOS.setText("");
        textFieldPATENTE.setText("");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void display() {
        GUICreaBus dialog = new GUICreaBus();
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}
