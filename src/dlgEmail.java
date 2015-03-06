import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class dlgEmail extends JDialog {
    private JPanel contentPane;
    private JButton BtnSend;
    private JButton buttonCancel;
    private JLabel lblSubject;
    private JLabel lblMessage;
    private JTextField txtSenderEmail;
    private JTextField txtPassword;
    private JTextField txtSubject;
    private JTextArea txtEmailMessage;

    public ArrayList<Member> recipientList = new ArrayList<Member>();

    public dlgEmail() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(BtnSend);

        //recipientList = /*get from*/

        BtnSend.addActionListener(new ActionListener() {
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
        String sender = frmLogin.USER_EMAIL;
        String password = frmLogin.USER_PASSWORD;
        String subject = txtSubject.getText();
        String message = txtEmailMessage.getText();
        for (Member r: recipientList){
            Emailer.sendEmail(sender,password,r.getEmail(),subject,message);
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
