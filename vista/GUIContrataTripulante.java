package vista;

import controlador.ControladorEmpresas;
import excepciones.SVPException;
import modelo.TipoDocumento;
import utilidades.*;

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
        radioButtonRUT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxNACIONALIDAD.setVisible(false);
            }
        });
        radioButtonPASAPORTE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxNACIONALIDAD.setVisible(true);
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
        Tratamiento tratamiento;
        int selectIndexNacionalidad = comboBoxNACIONALIDAD.getSelectedIndex();
        String nacionalidad = String.valueOf(comboBoxNACIONALIDAD.getItemAt(selectIndexNacionalidad));
        if (radioButtonTrataSR.isSelected()){
            tratamiento = Tratamiento.SR;
        }else {
            tratamiento = Tratamiento.SRA;
        }
        String identificador = textFieldIDENTIFICADOR.getText();
        String nombre = textFieldNOMBRE.getText();
        String apPat = textFieldAPPATERNO.getText();
        String apMat = textFieldAPMATERNO.getText();
        String calle = textFieldCALLE.getText();
        String nrostr = textFieldNUMEROCALLE.getText();
        int selectedIndex = comboBoxRUTEMPRESA.getSelectedIndex();
        String rutEmpresa = String.valueOf(comboBoxRUTEMPRESA.getItemAt(selectedIndex));
        int selectIndexComuna = comboBoxCOMUNA.getSelectedIndex();
        String comuna = String.valueOf(comboBoxCOMUNA.getItemAt(selectIndexComuna));
        if (identificador.isBlank() || nombre.isBlank() || apMat.isBlank() || apPat.isBlank() || calle.isBlank() || nrostr.isBlank()){
            JOptionPane.showMessageDialog(this,"Uno o mas campos invalidos","",JOptionPane.ERROR_MESSAGE);
        }
        try {
            int nro = Integer.parseInt(nrostr);
            Nombre nombreformat = new Nombre(nombre,apPat,apMat,tratamiento);
            IdPersona idPersona = null;
            if (radioButtonRUT.isSelected()){
                if (!identificador.matches("\\d{2}\\.\\d{3}\\.\\d{3}-[\\dkK]")){
                    JOptionPane.showMessageDialog(this, "El formato del R.U.T. es incorrecto","",JOptionPane.ERROR_MESSAGE);
                }else {
                    idPersona = Rut.of(identificador);
                }
            }else {
                if (!identificador.matches("[A-Z]{2,3}\\d{3,4}")){
                    JOptionPane.showMessageDialog(this, "El formato de la patente es incorrecto","",JOptionPane.ERROR_MESSAGE);
                }else {
                    idPersona = Pasaporte.of(identificador, nacionalidad);
                }
            }
            if (idPersona == null){
                JOptionPane.showMessageDialog(this,"Error al crear el identificador de persona","",JOptionPane.ERROR_MESSAGE);
            }
            Direccion direccion = new Direccion(calle,nro,comuna);
            if (radioButtonTipoTriConductor.isSelected()){
                ControladorEmpresas.getInstance().hireConductorForEmpresa(Rut.of(rutEmpresa),idPersona,nombreformat,direccion);
                JOptionPane.showMessageDialog(this,"Conductor contratado exitosamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
            }else {
                ControladorEmpresas.getInstance().hireAuxiliarForEmpresa(Rut.of(rutEmpresa),idPersona,nombreformat,direccion);
                JOptionPane.showMessageDialog(this,"Auxiliar contratado exitosamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"Numero invalido","",JOptionPane.ERROR_MESSAGE);
        } catch (SVPException e){
            JOptionPane.showMessageDialog(this,e.getMessage(),"",JOptionPane.ERROR_MESSAGE);
        }
        textFieldAPMATERNO.setText("");
        textFieldAPPATERNO.setText("");
        textFieldNOMBRE.setText("");
        textFieldCALLE.setText("");
        textFieldIDENTIFICADOR.setText("");
        textFieldNUMEROCALLE.setText("");
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

}