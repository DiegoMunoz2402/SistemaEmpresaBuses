package vista;

import controlador.ControladorEmpresas;
import excepciones.SVPException;
import utilidades.Rut;

import javax.swing.*;
import java.awt.event.*;

public class GUICreaEmpresa extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldRUT;
    private JTextField textFieldNOMBRE;
    private JTextField textFieldURL;

    public GUICreaEmpresa() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
    }

    private void onOK() {
        // add your code here
        String rut = textFieldRUT.getText();
        String nombre = textFieldNOMBRE.getText();
        String url = textFieldURL.getText();
        if (rut.isBlank() || nombre.isBlank() || url.isBlank()){
            JOptionPane.showMessageDialog(this,"Uno o mas campos invalidos","",JOptionPane.ERROR_MESSAGE);
        }else if (!rut.matches("\\d{2}\\.\\d{3}\\.\\d{3}-[\\dkK]")) {
            JOptionPane.showMessageDialog(this, "El formato del R.U.T. es incorrecto","",JOptionPane.ERROR_MESSAGE);
        }else {
            try {
                ControladorEmpresas.getInstance().createEmpresa(Rut.of(rut), nombre, url);
                JOptionPane.showMessageDialog(this,"Empresa guardada exitosamente","Informacion",JOptionPane.INFORMATION_MESSAGE);
            }catch (SVPException e) {
                JOptionPane.showMessageDialog(this,e.getMessage(),"",JOptionPane.ERROR_MESSAGE);
            }
        }
        textFieldNOMBRE.setText("");
        textFieldRUT.setText("");
        textFieldURL.setText("");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void display() {
        GUICreaEmpresa dialog = new GUICreaEmpresa();
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}
