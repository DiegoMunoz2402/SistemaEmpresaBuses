package vista;

import javax.swing.*;

public class GUICreaEmpresa extends javax.swing.JDialog {
    private javax.swing.JPanel contentPane;
    private javax.swing.JButton buttonOK;
    private javax.swing.JButton buttonCancel;
    private JPanel DatosEmpresa;
    private JTextField textFieldRUT;
    private JTextField textFieldNOMBRE;
    private JTextField textFieldURL;

    public GUICreaEmpresa() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                onCancel();
            }
        }, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String rut = textFieldRUT.getText();
        String nombre = textFieldNOMBRE.getText();
        String url = textFieldURL.getText();

        if (rut.isEmpty() || nombre.isEmpty() || url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un valor valido");
            return;
        }
        if (!rut.matches("\\d{2}\\.\\d{3}\\.\\d{3}-[\\dkK]")) {
            JOptionPane.showMessageDialog(this, "El formato del R.U.T. es incorrecto");
            return;
        }
        try {
            ControladorEmpresas.getInstance().createEmpresa(Rut.of(rut), nombre, url);
            JOptionPane.showMessageDialog(this, "Empresa guardada exitosamente");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ya existe un empresa con el mismo R.U.T" + e.getMessage());
        }
        textFieldNOMBRE.setText("");
        textFieldRUT.setText("");
        textFieldURL.setText("");
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        GUICreaEmpresa dialog = new GUICreaEmpresa();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
