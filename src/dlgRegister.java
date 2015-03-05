import javax.swing.*;
import java.awt.event.*;

public class dlgRegister extends JDialog {
    private JPanel contentPane;
    private JButton btnStartBookClub;
    private JButton buttonCancel;
    private JTextField txtName;
    private JTextField txtEmailAddress;
    private JTextField txtBCN;
    private JLabel lbnName;

    public dlgRegister() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnStartBookClub);

        btnStartBookClub.addActionListener(new ActionListener() {
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

        //TODO: Validate text fields:
        //  -Call Va
        dispose();


    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        dlgRegister dialog = new dlgRegister();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private boolean isValidInput() {
        return Validator.isPresent(txtName, "Name", true) &&
                Validator.isPresent(txtEmailAddress, "Email Address", true) &&
                Validator.isPresent(txtBCN, "Book Club Name", true) &&
                Validator.isValidGmailAddress(txtEmailAddress, "Email Address", true);


    }
}
