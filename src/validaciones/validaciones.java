package Validaciones;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Fernando
 */
public class validaciones {

    public String reemplazar_espacios(JTextField re) {

        String r;

        r = re.getText().trim().replaceAll(" ", "_");

        return r;

    }

    public String reemplazar_espacios_combos(JComboBox re) {

        String r;

        r = re.getSelectedItem().toString().trim().replaceAll(" ", "_");

        return r;

    }

    public String reemplazar_guion(JTextField re) {

        String r;

        r = re.getText().trim().replaceAll("_", " ");

        return r;

    }

    public String reemplazar_guion_combos(JComboBox re) {

        String r;

        r = re.getSelectedItem().toString().trim().replaceAll("_", " ");

        return r;

    }

    public boolean estaVacio(String cad) {

        if (cad.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void validar_Solo_Letras(JTextField re) {
        re.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent re) {
                char tecla = re.getKeyChar();
                if (!(Character.isLetter(tecla)) && !(Character.isWhitespace(tecla)) && !(KeyEvent.VK_BACK_SPACE == tecla)) {
                    Toolkit.getDefaultToolkit().beep();
                    re.consume();
                    JOptionPane.showMessageDialog(null, "Ingresa Solo Letras", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void validar_Solo_Numeros(JTextField re) {
        re.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent re) {
                char tecla = re.getKeyChar();
                if (!(Character.isDigit(tecla)) && !(KeyEvent.VK_BACK_SPACE == tecla) && !(KeyEvent.VK_PERIOD == tecla)) {
                    Toolkit.getDefaultToolkit().beep();
                    re.consume();
                    JOptionPane.showMessageDialog(null, "Ingresa Solo Numeros", "Error", JOptionPane.WARNING_MESSAGE);

                }
            }
        });
    }

    public void validar_Punto(JTextField txt) {
        txt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent re) {
                char c = re.getKeyChar();

                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
                        && (c != '.')) {
                    re.consume();
                }
                if (c == '.' && txt.getText().trim().contains(".")) {
                    re.consume();
                }
            }
        });
    }

}
