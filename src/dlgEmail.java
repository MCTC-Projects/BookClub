import javax.swing.*;
import java.awt.event.*;

public class dlgEmail extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblSubject;
    private JLabel lblMessage;
    private JTextField txtSenderEmail;
    private JTextField txtPassword;
    private JTextField txtSubject;
    private JTextArea txtEmailMessage;

    private String [] recipientList =  { "DrNowzik@gmail.com","RuxinWins@yahoo.com", "darylschmit@yahoo.com"};



    public dlgEmail() {
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
        String sender = txtSenderEmail.getText();
        String password = txtPassword.getText();
        String subject = txtSubject.getText();
        String message = txtEmailMessage.getText();
        for (String r: recipientList){
            Emailer.sendEmail(sender,password,r,subject,message);
        }
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        dlgEmail dialog = new dlgEmail();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
