import javax.swing.*;
import java.awt.event.*;

public class dlgAddMember extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtName;
    private JTextField txtEmailAddress;

    public dlgAddMember() {
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
        if ( isValidInput())


// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary

        dispose();
    }

    public static void main(String[] args) {
        dlgAddMember dialog = new dlgAddMember();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private boolean isValidInput(){
        return Validator.isPresent(txtName, "Name", true) &&
                Validator.isPresent(txtEmailAddress, "Email", true)&&
                Validator.isValidGmailAddress(txtEmailAddress, "Email", true);

    }
}
