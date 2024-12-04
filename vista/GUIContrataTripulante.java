package vista;

import controlador.ControladorEmpresas;

import javax.swing.*;
import java.awt.event.*;

public class GUIContrataTripulante extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxRUTEMPRESA;
    private JComboBox comboBoxNOMBRE_EMPRESA;
    private JRadioButton radioButtonRUT;
    private JRadioButton radioButtonPASAPORTE;
    private JTextField textFieldIDENTIFICADOR;
    private JTextField textFieldNOMBRE;
    private JTextField textFieldAPPATERNO;
    private JTextField textFieldAPMATERNO;
    private JTextField textFieldCALLE;
    private JTextField textFieldNUMEROCALLE;
    private JComboBox comboBoxCOMUNA;
    private JComboBox comboBoxNACIONALIDAD;
    private JRadioButton radioButtonTipoTriConductor;
    private JRadioButton radioButtonTipoTripAuxiliar;
    private JRadioButton radioButtonTrataSR;
    private JRadioButton radioButtonTrataSra;

    public GUIContrataTripulante() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        String[][] datos = ControladorEmpresas.getInstance().listEmpresas();

        for (String[] string:datos){
            comboBoxRUTEMPRESA.addItem(string[0]);
            comboBoxNOMBRE_EMPRESA.addItem(string[1]);
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
        comboBoxRUTEMPRESA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onComboBoxRutEmpresa();
            }
        });
        comboBoxNOMBRE_EMPRESA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onComboxNombreEmpresa();
            }
        });
    }

    private void onComboxNombreEmpresa() {
        int selectedIndex = comboBoxNOMBRE_EMPRESA.getSelectedIndex();
        comboBoxRUTEMPRESA.setSelectedIndex(selectedIndex);
    }

    private void onComboBoxRutEmpresa() {
        int selectedIndex = comboBoxRUTEMPRESA.getSelectedIndex();
        comboBoxNOMBRE_EMPRESA.setSelectedIndex(selectedIndex);
    }

    private void onOK() {
        // add your code here

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void display() {
        GUIContrataTripulante dialog = new GUIContrataTripulante();
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}


//FALTAN COSAS